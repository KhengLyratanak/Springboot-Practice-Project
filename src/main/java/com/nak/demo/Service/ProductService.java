package com.nak.demo.Service;

import com.nak.demo.Entity.Product;
import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.exception.model.DuplicateException;
import com.nak.demo.exception.model.ResourceNotFoundException;
import com.nak.demo.dto.product.ProductDto;
import com.nak.demo.Model.BaseResponseModelWithData;
import com.nak.demo.Repository.ProductRepository;
import com.nak.demo.dto.product.ProductResponseDto;
import com.nak.demo.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper mapper;
    
    private List<ProductDto> products = new ArrayList<>(Arrays.asList(
            new ProductDto(1L, "Coca Cola", 1.5D,"dd")
    ));
    public ResponseEntity<BaseResponseModelWithData> listProduct(){
        List<Product> products = productRepository.findAll();
        List<ProductResponseDto> dtos = mapper.toDtoList(products);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModelWithData("success","Successfully Retrieve Product",dtos));
    }
    public ResponseEntity<BaseResponseModelWithData> getProduct(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("product not found with id:" +productId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModelWithData(
                        "success",
                        "product found",
                        product));
    }
    public ResponseEntity<BaseResponseModel> createProduct(ProductDto product){
       //validate if product is already exists
        if (productRepository.existsByProductName(product.getName())){
           throw new DuplicateException("product is already existed");
        }
        Product productEntity = mapper.toEntity(product);

        productRepository.save(productEntity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success","Successfully Created Product"));
    }
    public ResponseEntity<BaseResponseModel> updateProduct(Long productId, ProductDto payload) {
        Product existing = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("product not found with id :"  +productId));

        mapper.updateEntityFrom(existing,payload);

        productRepository.save(existing);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("Success", "Successfully updated Product"));
    }
    public ResponseEntity<BaseResponseModel> deleteProduct(Long productId){
        if (!productRepository.existsById(productId)){
            throw new ResourceNotFoundException("product not found with id :"  +productId);
        }
        productRepository.deleteById(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("Success","Successfully deleted Product"));
    }

    public ResponseEntity<BaseResponseModelWithData> searchProduct(String name, Double minPrice,Double maxPrice) {
        String formattedName = name !=null ?
                name.toLowerCase()
                :name;
        List<Product> product = productRepository.findProductsWithFilters(formattedName,minPrice,maxPrice);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModelWithData("success", "product retrieved", product));
    }
}


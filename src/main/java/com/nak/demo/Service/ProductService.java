package com.nak.demo.Service;

import com.nak.demo.Entity.Product;
import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.dto.ProductDto;
import com.nak.demo.Model.BaseResponseModelWithData;
import com.nak.demo.Repository.ProductRepository;
import com.nak.demo.dto.ProductResponseDto;
import com.nak.demo.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        Optional<Product> product = productRepository.findById(productId);
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModelWithData("fail","product not found with id: "
                            +productId,null));
        }
        ProductResponseDto dto = mapper.toDto(product.get());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModelWithData("success","product found",product.get()));
    }
    public ResponseEntity<BaseResponseModel> createProduct(ProductDto payload){
       Product product = mapper.toEntity(payload);

        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success","Successfully Created Product"));
    }
    public ResponseEntity<BaseResponseModel> updateProduct(Long productId, ProductDto payload) {
        Optional<Product> existing = productRepository.findById(productId);

        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("Fail", "Product not found with id:" + productId));
        }
        Product updatedProduct = existing.get();

        mapper.updateEntityFrom(updatedProduct,payload);

        productRepository.save(updatedProduct);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("Success", "Successfully updated Product"));
    }
    public ResponseEntity<BaseResponseModel> deleteProduct(Long productId){
        if (!productRepository.existsById(productId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("fail","product not found with id: "+productId));
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


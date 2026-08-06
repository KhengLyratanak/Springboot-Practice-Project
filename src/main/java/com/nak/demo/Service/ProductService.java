package com.nak.demo.Service;

import com.nak.demo.Entity.Product;
import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.dto.base.Response;
import com.nak.demo.exception.model.DuplicateException;
import com.nak.demo.exception.model.ResourceNotFoundException;
import com.nak.demo.dto.product.ProductDto;
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

    public List<ProductResponseDto> listProduct() {
        List<Product> products = productRepository.findAll();

        List<ProductResponseDto> dtos = mapper.toDtoList(products);

       return mapper.toDtoList(products);

    }

    public ProductResponseDto getProduct(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("product not found with id:" +productId));
            return mapper.toDto(product);
    }
    public void createProduct(ProductDto product) {
        //validate if product is already exists
        if (productRepository.existsByProductName(product.getName())) {
            throw new DuplicateException("product is already existed");
        }
        Product productEntity = mapper.toEntity(product);

        productRepository.save(productEntity);

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
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("product not found with id :" + productId);
        }
        productRepository.deleteById(productId);
    }
    public List<ProductResponseDto> searchProduct(String name, Double minPrice,Double maxPrice) {
        String formattedName = name != null ?
                name.toLowerCase()
                : name;
        List<Product> products = productRepository.findProductsWithFilters(formattedName, minPrice, maxPrice);
        return mapper.toDtoList( products);
    }
}


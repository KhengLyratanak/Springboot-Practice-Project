package com.nak.demo.Service;

import com.nak.demo.Entity.Product;
import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.Model.ProductModel;
import com.nak.demo.Model.BaseResponseModelWithData;
import com.nak.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

    private List<ProductModel> products = new ArrayList<>(Arrays.asList(
            new ProductModel(1L, "Coca Cola", 1.5D,"dd")
    ));
    public ResponseEntity<BaseResponseModelWithData> listProduct(){
        List<Product> products = productRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModelWithData("success","Successfully Retrieve Product",products));
    }
    public ResponseEntity<BaseResponseModelWithData> getProduct(Long productId){
        Optional<Product> product = productRepository.findById(productId);
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModelWithData("fail","product not found with id: "
                            +productId,null));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModelWithData("success","product found",product.get()));
    }
    public ResponseEntity<BaseResponseModel> createProduct(ProductModel payload){
        Product product = new Product();
        product.setProductName(payload.getName());
        product.setPrice(payload.getPrice());
        product.setDescription(payload.getDescription());
        product.setCreateAt(LocalDateTime.now());

        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success","Successfully Created Product"));
    }
    public ResponseEntity<BaseResponseModel> updateProduct(Long productId,ProductModel payload) {
        Optional<Product> existing = productRepository.findById(productId);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("Fail", "Product not found with id:" + productId));
        }
        Product updatedProduct = existing.get();

        updatedProduct.setProductName(payload.getName());
        updatedProduct.setPrice(payload.getPrice());
        updatedProduct.setDescription(payload.getDescription());
        updatedProduct.setUpdatedAt(LocalDateTime.now());

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


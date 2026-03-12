package com.nak.demo.Controller;

import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.Model.BaseResponseModelWithData;
import com.nak.demo.Model.ProductModel;
import com.nak.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<BaseResponseModelWithData> listProduct(){
        return productService.listProduct();
    }
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseModelWithData> getProduct(@PathVariable("id") Long productId){
        return productService.getProduct(productId);
    }
    @GetMapping("/search")
    public ResponseEntity<BaseResponseModelWithData> seaerchProducts(
            @RequestParam(value = "name",required = false) String name
    ){
       return productService.searchProduct(name);
    }
    @PostMapping()
    public ResponseEntity<BaseResponseModel> createProduct(@RequestBody ProductModel payload){
        return productService.createProduct(payload);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseModel> updateProduct(@PathVariable("id") Long productId,@RequestBody ProductModel payload){
        return productService.updateProduct(productId,payload);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseModel> deleteProduct(@PathVariable("id") Long productId){
        return productService.deleteProduct(productId);
    }
}

package com.sadat.controller;

import com.sadat.dto.banking.ProductResponse;
import com.sadat.service.banking.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(){

        List<ProductResponse> response = productService.getProducts();

        if(!response.isEmpty()){

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long id){

        ProductResponse response = productService.getProduct(id);

        if(response != null){

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }
}

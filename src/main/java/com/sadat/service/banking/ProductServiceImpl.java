package com.sadat.service.banking;

import com.sadat.dto.banking.ProductResponse;
import com.sadat.model.Product;
import com.sadat.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponse> getProducts(){

        List<ProductResponse> response = new ArrayList<>();

        for(Product product : productRepository.findAll()){

            ProductResponse productResponse = ProductResponse.builder()
                    .id(product.getId())
                    .type(product.getNature().getType())
                    .code(product.getCode())
                    .name(product.getName())
                    .build();

            response.add(productResponse);
        }

        return response;
    }

    @Override
    public ProductResponse getProduct(long id){

        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isPresent()){

            Product product = productOptional.get();

            return ProductResponse.builder()
                    .id(product.getId())
                    .type(product.getNature().getType())
                    .code(product.getCode())
                    .name(product.getName())
                    .build();
        }

        return null;
    }

    @Override
    public Product getProductById(long id){

        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isPresent()) {

            return productOptional.get();
        }

        return null;
    }
}

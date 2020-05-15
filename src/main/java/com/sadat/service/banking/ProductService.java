package com.sadat.service.banking;

import com.sadat.dto.ProductResponse;
import com.sadat.model.Product;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getProducts();
    ProductResponse getProduct(long id);
    Product getProductById(long id);
}

package com.ecs.service;

import com.ecs.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> listAllProducts();

    ProductDto findById(Long productId);

}

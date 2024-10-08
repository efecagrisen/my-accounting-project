package com.ecs.service;

import com.ecs.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> listAllCompanyProducts();



    void increaseProductQuantityInStock(Long productId, Integer quantity);
    void decreaseProductQuantityInStock(Long productId, Integer quantity);

    ProductDto findById(Long productId);

    void save(ProductDto productDto);
    void update(ProductDto productDto);
    void deleteById(Long productId);

    boolean doesCompanyCategoryHaveProduct(String categoryDescription, Long companyId);

    void checkProductLowLimitAlert(Long invoiceId);
}

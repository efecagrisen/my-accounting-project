package com.ecs.service.impl;

import com.ecs.dto.ProductDto;
import com.ecs.entity.Product;
import com.ecs.mapper.MapperUtil;
import com.ecs.repository.ProductRepository;
import com.ecs.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;

    public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil) {
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<ProductDto> listAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> mapperUtil.convert(product,ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto findById(Long productId) {
        return mapperUtil.convert(productRepository.findById(productId), ProductDto.class);
    }

}

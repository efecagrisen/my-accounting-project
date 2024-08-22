package com.ecs.service.impl;

import com.ecs.dto.ProductDto;
import com.ecs.entity.Company;
import com.ecs.entity.Product;
import com.ecs.mapper.MapperUtil;
import com.ecs.repository.ProductRepository;
import com.ecs.service.ProductService;
import com.ecs.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public List<ProductDto> listAllProducts() {

        Company loggedInUserCompany = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), Company.class);

        return productRepository.findAllByCompany(loggedInUserCompany).stream()
                .map(product -> mapperUtil.convert(product,ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto findById(Long productId) {
        return mapperUtil.convert(productRepository.findById(productId), ProductDto.class);
    }

}

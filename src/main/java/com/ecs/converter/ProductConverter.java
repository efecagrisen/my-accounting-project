package com.ecs.converter;

import com.ecs.dto.ProductDto;
import com.ecs.service.ProductService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter implements Converter<String, ProductDto> {

    private final ProductService productService;

    public ProductConverter(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ProductDto convert(String source) {
        if (source==null || source.equals(""))
        return null;

        return productService.findById(Long.parseLong(source));
    }
}

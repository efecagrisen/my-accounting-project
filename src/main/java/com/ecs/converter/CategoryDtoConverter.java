package com.ecs.converter;

import com.ecs.dto.CategoryDto;
import com.ecs.service.CategoryService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoConverter implements Converter<String, CategoryDto> {

    private final CategoryService categoryService;

    public CategoryDtoConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public CategoryDto convert(String source) {

        if (source==null || source.equals("")){
            return null;
        }

        return categoryService.findById(Long.parseLong(source));
    }

}

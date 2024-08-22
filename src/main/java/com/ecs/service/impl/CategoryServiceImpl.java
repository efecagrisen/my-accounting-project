package com.ecs.service.impl;

import com.ecs.dto.CategoryDto;
import com.ecs.entity.Category;
import com.ecs.mapper.MapperUtil;
import com.ecs.repository.CategoryRepository;
import com.ecs.service.CategoryService;
import com.ecs.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public CategoryDto findById(Long categoryId) {
        return mapperUtil.convert(categoryRepository.findById(categoryId).get(),CategoryDto.class);
    }

    @Override
    public List<CategoryDto> findAll() {

        List<Category> categories = categoryRepository.findAllByCompanyId(securityService.getLoggedInUserCompanyId());

        return categories.stream()
                .map(category -> mapperUtil.convert(category,CategoryDto.class))
                .collect(Collectors.toList());
    }
}

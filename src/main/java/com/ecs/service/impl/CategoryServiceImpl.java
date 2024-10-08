package com.ecs.service.impl;

import com.ecs.dto.CategoryDto;
import com.ecs.entity.Category;
import com.ecs.entity.Company;
import com.ecs.mapper.MapperUtil;
import com.ecs.repository.CategoryRepository;
import com.ecs.service.CategoryService;
import com.ecs.service.ProductService;
import com.ecs.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final ProductService productService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil, SecurityService securityService, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.productService = productService;
    }

    @Override
    public CategoryDto findById(Long categoryId) {
        return mapperUtil.convert(categoryRepository.findById(categoryId).get(),CategoryDto.class);
    }

    @Override
    public List<CategoryDto> findAllByCompanyOrderByDescription() {

        List<Category> categories = categoryRepository.findAllByCompanyIdOrderByDescription(securityService.getLoggedInUserCompanyId());

        return categories.stream()
                .map(category -> mapperUtil.convert(category,CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void save(CategoryDto categoryDto) {

        Category categoryToSave = mapperUtil.convert(categoryDto,Category.class);
        Company loggedInUserCompany = mapperUtil.convert(securityService.getLoggedInUser().getCompany(),Company.class);
        categoryToSave.setCompany(loggedInUserCompany);
        categoryRepository.save(categoryToSave);

    }

    @Override
    public void delete(CategoryDto categoryDto) {
        Category categoryToDelete = categoryRepository.findById(categoryDto.getId()).get();
        categoryToDelete.setIsDeleted(true);
        categoryRepository.save(categoryToDelete);
    }

    @Override
    public boolean isCompanyCategoryDescriptionNotUnique(String description, Long companyId) {

        return categoryRepository.existsCategoryByDescriptionAndCompanyId(description,companyId);
    }

    @Override
    public boolean doesCompanyCategoryHaveProduct(String categoryDescription, Long companyId) {
        return productService.doesCompanyCategoryHaveProduct(categoryDescription, companyId);
    }
}

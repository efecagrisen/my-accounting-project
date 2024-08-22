package com.ecs.service;

import com.ecs.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    CategoryDto findById(Long categoryId);

    List<CategoryDto> findAll();
}
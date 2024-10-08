package com.ecs.controller;

import com.ecs.dto.CategoryDto;
import com.ecs.mapper.MapperUtil;
import com.ecs.service.CategoryService;
import com.ecs.service.SecurityService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final SecurityService securityService;
    private final MapperUtil mapperUtil;

    public CategoryController(CategoryService categoryService, SecurityService securityService, MapperUtil mapperUtil) {
        this.categoryService = categoryService;
        this.securityService = securityService;
        this.mapperUtil = mapperUtil;
    }


    @GetMapping("/list")
    public String listAllCategories(Model model){

        List<CategoryDto> categoryDtoList = categoryService.findAllByCompanyOrderByDescription();

        categoryDtoList.forEach(categoryDto -> {
            if(categoryService.doesCompanyCategoryHaveProduct(categoryDto.getDescription(), securityService.getLoggedInUserCompanyId())){
                categoryDto.setHasProduct(true);
            }
        });

        model.addAttribute("categories",categoryDtoList);

        return "/category/category-list";
    }

    @GetMapping("/create")
    public String createCategory(Model model){

        model.addAttribute("newCategory",new CategoryDto());

        return "/category/category-create";
    }

    @PostMapping("/create")
    public String insertCategory(@Valid @ModelAttribute ("newCategory") CategoryDto categoryDto, BindingResult bindingResult){

            if (categoryService.isCompanyCategoryDescriptionNotUnique(categoryDto.getDescription(),securityService.getLoggedInUserCompanyId())){
                bindingResult.rejectValue("description"," ","This category already exists");
                }

            if (bindingResult.hasErrors()){
                return "/category/category-create";
            }
        categoryService.save(categoryDto);

        return "redirect:/categories/list";

    }

    @GetMapping("/update/{id}")
    public String editCategory(@PathVariable ("id") Long id, Model model){

        CategoryDto categoryDto = categoryService.findById(id);
        if(categoryService.doesCompanyCategoryHaveProduct(categoryDto.getDescription(), securityService.getLoggedInUserCompanyId())){
            categoryDto.setHasProduct(true);
        }

        model.addAttribute("category",categoryDto);

        return "/category/category-update";
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@Valid @ModelAttribute ("category") CategoryDto categoryDto, BindingResult bindingResult){

        if (categoryService.isCompanyCategoryDescriptionNotUnique(categoryDto.getDescription(),securityService.getLoggedInUserCompanyId())){
            bindingResult.rejectValue("description"," ","This category already exists");
        }

        if (bindingResult.hasErrors()){
            return "/category/category-update";
        }

        categoryService.save(categoryDto);

        return "redirect:/categories/list";

    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable ("id") Long id, Model model) {

        categoryService.delete(categoryService.findById(id));

        return "redirect:/categories/list";
    }



}

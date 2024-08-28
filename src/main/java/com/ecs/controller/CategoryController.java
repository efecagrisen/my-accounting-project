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

        model.addAttribute("categories",categoryService.findAllByCompanyOrderByDescription());

        return "/category/category-list";
    }

    @GetMapping("/create")
    public String createCategory(Model model){

        model.addAttribute("newCategory",new CategoryDto());

        return "/category/category-create";
    }

    @PostMapping("/create")
    public String insertCategory(@Valid @ModelAttribute ("newCategory") CategoryDto categoryDto, BindingResult bindingResult,Model model){

            if (categoryService.isCategoryDescriptionNotUnique(categoryDto.getDescription(),securityService.getLoggedInUserCompanyId())){
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

        model.addAttribute("category",categoryService.findById(id));

        return "/category/category-update";
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@ModelAttribute ("category") CategoryDto categoryDto){

        categoryService.save(categoryDto);

        return "redirect:/categories/list";

    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable ("id") Long id, Model model) {

        categoryService.delete(categoryService.findById(id));

        return "redirect:/categories/list";
    }



}

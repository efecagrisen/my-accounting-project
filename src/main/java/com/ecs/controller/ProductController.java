package com.ecs.controller;

import com.ecs.dto.CompanyDto;
import com.ecs.dto.ProductDto;
import com.ecs.entity.Company;
import com.ecs.entity.Product;
import com.ecs.enums.ProductUnit;
import com.ecs.mapper.MapperUtil;
import com.ecs.repository.ProductRepository;
import com.ecs.service.CategoryService;
import com.ecs.service.ProductService;
import com.ecs.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(MapperUtil mapperUtil, SecurityService securityService, ProductRepository productRepository, ProductService productService, CategoryService categoryService) {
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.productRepository = productRepository;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String listAllCompanyProducts(Model model){

        model.addAttribute("products",productService.listAllProducts());

        return "product/product-list";
    }

    @GetMapping("/create")
    public String createProduct(Model model){

        model.addAttribute("newProduct", new ProductDto());
        model.addAttribute("categories", Arrays.asList(categoryService.findAll()));
        model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));

        return "product/product-create";
    }



}

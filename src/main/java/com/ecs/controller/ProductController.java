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
import jakarta.validation.Valid;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String listAllCompanyProducts(Model model) {
        model.addAttribute("products", productService.listAllCompanyProducts());

        return "product/product-list";
    }

    @GetMapping("/create")
    public String createProduct(Model model) {

        model.addAttribute("newProduct", new ProductDto());
        model.addAttribute("categories", categoryService.findAllByCompanyOrderByDescription());
        model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));

        return "product/product-create";
    }

    @PostMapping("/create")
    public String insertProduct(@Valid @ModelAttribute("newProduct") ProductDto productDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()){
            model.addAttribute("newProduct", productDto);
            model.addAttribute("categories", categoryService.findAllByCompanyOrderByDescription());
            model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));

            return "product/product-create";
        }

        productService.save(productDto);

        return "redirect:/products/list";
    }

    @GetMapping("/update/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {

        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories", categoryService.findAllByCompanyOrderByDescription());
        model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));

        return "product/product-update";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@Valid @ModelAttribute("product") ProductDto productDto,BindingResult bindingResult ,Model model) {

        if (bindingResult.hasErrors()){
            model.addAttribute("product", productDto);
            model.addAttribute("categories", categoryService.findAllByCompanyOrderByDescription());
            model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));

            return "product/product-update";
        }

        productService.update(productDto);

        return "redirect:/products/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id){

        productService.deleteById(id);

        return "redirect:/products/list";

    }


}

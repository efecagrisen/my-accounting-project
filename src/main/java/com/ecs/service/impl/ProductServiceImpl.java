package com.ecs.service.impl;

import com.ecs.dto.InvoiceProductDto;
import com.ecs.dto.ProductDto;
import com.ecs.entity.Company;
import com.ecs.entity.Product;
import com.ecs.exception.ProductLowLimitAlertException;
import com.ecs.mapper.MapperUtil;
import com.ecs.repository.ProductRepository;
import com.ecs.service.InvoiceProductService;
import com.ecs.service.ProductService;
import com.ecs.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final InvoiceProductService invoiceProductService;

    public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil, SecurityService securityService, InvoiceProductService invoiceProductService) {
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.invoiceProductService = invoiceProductService;
    }

    @Override
    public List<ProductDto> listAllCompanyProducts() {

        Company loggedInUserCompany = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), Company.class);

        return productRepository.findAllByCompany(loggedInUserCompany).stream()
                .map(product -> mapperUtil.convert(product,ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto findById(Long productId) {
        return mapperUtil.convert(productRepository.findById(productId), ProductDto.class);
    }

    @Override
    public void save(ProductDto productDto) {
        productRepository.save(mapperUtil.convert(productDto,Product.class));
    }

    @Override
    public void update(ProductDto productDto) {
        Product productToBeUpdated = productRepository.findById(productDto.getId()).orElseThrow();

        productDto.setQuantityInStock(productToBeUpdated.getQuantityInStock());
        save(productDto);
    }

    @Override
    public void deleteById(Long productId) {
        Product productToBeDeleted = productRepository.findById(productId).orElseThrow();
            if ( productToBeDeleted.getQuantityInStock() == 0 && ! productRepository.existsProductInAnyInvoice(productId)){
                productToBeDeleted.setIsDeleted(true);
                productRepository.save(productToBeDeleted);
            }
    }

    @Override
    public boolean doesCompanyCategoryHaveProduct(String categoryDescription, Long companyId) {
        return productRepository.existsProductByCompanyCategory(categoryDescription,companyId);
    }

    @Override
    public void increaseProductQuantityInStock(Long productId, Integer quantity) {
        Product productToBeUpdated = productRepository.findById(productId).orElseThrow();
        productToBeUpdated.setQuantityInStock(productToBeUpdated.getQuantityInStock() + quantity);
        productRepository.save(productToBeUpdated);
    }

    @Override
    public void decreaseProductQuantityInStock(Long productId, Integer quantity) {
        Product productToBeUpdated = productRepository.findById(productId).orElseThrow();

        Integer newQuantity = productToBeUpdated.getQuantityInStock() - quantity;

        if (newQuantity < 0){
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        productToBeUpdated.setQuantityInStock(newQuantity);
        productRepository.save(productToBeUpdated);
    }

    @Override
    public void checkProductLowLimitAlert(Long invoiceId) {
        List<InvoiceProductDto> invoiceProductDtoList = invoiceProductService.findByInvoiceId(invoiceId);
        List<String> productNamesBelowLowLimitAlert = new ArrayList<>();

        invoiceProductDtoList.forEach(invoiceProductDto -> {
            Integer quantityInStock = invoiceProductDto.getProduct().getQuantityInStock();
            Integer lowLimitAlert = invoiceProductDto.getProduct().getLowLimitAlert();

            if (quantityInStock < lowLimitAlert){
                productNamesBelowLowLimitAlert.add(invoiceProductDto.getProduct().getName());
            }
        });

//        String productNames = productNamesBelowLowLimitAlert.stream().collect(Collectors.joining(", "));
        String productNames = String.join(", ",productNamesBelowLowLimitAlert);

        if (! productNamesBelowLowLimitAlert.isEmpty()){
            throw new ProductLowLimitAlertException("Stock of "+ productNames + " decreased below low limit!");
        }


    }
}

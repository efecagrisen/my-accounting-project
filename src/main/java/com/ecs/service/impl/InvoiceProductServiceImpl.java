package com.ecs.service.impl;

import com.ecs.dto.InvoiceDto;
import com.ecs.dto.InvoiceProductDto;
import com.ecs.dto.ProductDto;
import com.ecs.entity.Invoice;
import com.ecs.entity.InvoiceProduct;
import com.ecs.enums.InvoiceType;
import com.ecs.mapper.MapperUtil;
import com.ecs.repository.InvoiceProductRepository;
import com.ecs.service.CompanyService;
import com.ecs.service.InvoiceProductService;
import com.ecs.service.InvoiceService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final MapperUtil mapperUtil;
    private final InvoiceProductRepository invoiceProductRepository;
    private final CompanyService companyService;
    private final InvoiceService invoiceService;

    public InvoiceProductServiceImpl(MapperUtil mapperUtil, InvoiceProductRepository invoiceProductRepository, CompanyService companyService, @Lazy InvoiceService invoiceService) {
        this.mapperUtil = mapperUtil;
        this.invoiceProductRepository = invoiceProductRepository;
        this.companyService = companyService;
        this.invoiceService = invoiceService;
    }

    @Override
    public InvoiceProductDto findById(Long id) {
        return mapperUtil.convert(invoiceProductRepository.findById(id).get(),InvoiceProductDto.class);
    }

    @Override
    public List<InvoiceProductDto> findByInvoiceId(Long id) {
        return invoiceProductRepository.findByInvoiceId(id)
                .stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct,InvoiceProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceProductDto> listAll() {
        return invoiceProductRepository.findAll().stream()
                .map(product->mapperUtil.convert(product, InvoiceProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceProductDto create(InvoiceProductDto invoiceProductDto, Long invoiceId) {

        InvoiceDto invoiceDto = invoiceService.findById(invoiceId);

        invoiceProductDto.setInvoice(invoiceDto);
        invoiceProductDto.setProfitLoss(BigDecimal.ZERO);

        InvoiceProduct invoiceProduct = mapperUtil.convert(invoiceProductDto,InvoiceProduct.class);
        invoiceProduct.setId(null); // bug fix
        InvoiceProduct savedInvoice = invoiceProductRepository.save(invoiceProduct);

        return mapperUtil.convert(savedInvoice, InvoiceProductDto.class);
    }

    @Override
    public void deleteById(Long invoiceProductId) {

        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(invoiceProductId).get();

        invoiceProduct.setIsDeleted(true);

        invoiceProductRepository.save(invoiceProduct);
    }

    @Override
    public void deleteByInvoice(InvoiceDto invoiceDto) {
        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findByInvoiceId(invoiceDto.getId());
        invoiceProductList.forEach(invoiceProduct -> invoiceProduct.setIsDeleted(true));
        invoiceProductRepository.saveAll(invoiceProductList);
    }

    @Override
    public void removeInvoiceProductFromInvoice(Long invoiceId, Long invoiceProductId) {


        List<InvoiceProductDto> invoiceProductDtoList = findByInvoiceId(invoiceId);

        invoiceProductDtoList.stream()
                .filter(invoiceProductDto -> invoiceProductDto.getId() == invoiceProductId)
                .forEach(invoiceProductDto -> deleteById(invoiceProductDto.getId()));



    }


}

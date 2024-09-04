package com.ecs.service.impl;

import com.ecs.dto.InvoiceDto;
import com.ecs.dto.InvoiceProductDto;
import com.ecs.dto.ProductDto;
import com.ecs.entity.Invoice;
import com.ecs.entity.InvoiceProduct;
import com.ecs.enums.InvoiceStatus;
import com.ecs.enums.InvoiceType;
import com.ecs.mapper.MapperUtil;
import com.ecs.repository.InvoiceProductRepository;
import com.ecs.service.CompanyService;
import com.ecs.service.InvoiceProductService;
import com.ecs.service.InvoiceService;
import com.ecs.service.SecurityService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final MapperUtil mapperUtil;
    private final InvoiceProductRepository invoiceProductRepository;
    private final CompanyService companyService;
    private final InvoiceService invoiceService;
    private final SecurityService securityService;

    public InvoiceProductServiceImpl(MapperUtil mapperUtil, InvoiceProductRepository invoiceProductRepository, CompanyService companyService, @Lazy InvoiceService invoiceService, SecurityService securityService) {
        this.mapperUtil = mapperUtil;
        this.invoiceProductRepository = invoiceProductRepository;
        this.companyService = companyService;
        this.invoiceService = invoiceService;
        this.securityService = securityService;
    }

    @Override
    public List<InvoiceProductDto> listAllCompanyInvoiceProducts() {

        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.getInvoiceProductsByInvoice_CompanyIdAndInvoice_InvoiceStatusOrderByInvoice_InsertDateTimeDesc(securityService.getLoggedInUserCompanyId(), InvoiceStatus.APPROVED);

        return invoiceProductList.stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, InvoiceProductDto.class))
                .toList();
    }

    @Override
    public InvoiceProductDto findById(Long id) {
        return mapperUtil.convert(invoiceProductRepository.findById(id).get(),InvoiceProductDto.class);
    }

    @Override
    public List<InvoiceProductDto> findByInvoiceId(Long id) {

         List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findByInvoiceId(id);
         List<InvoiceProductDto> invoiceProductDtoList = invoiceProductList.stream()
                 .map(invoiceProduct -> mapperUtil.convert(invoiceProduct,InvoiceProductDto.class))
                 .toList();

                invoiceProductDtoList.stream()
                        .peek(invoiceProductDto -> {
                            invoiceProductDto.setTotal(calculateTotal(invoiceProductDto));
                            save(invoiceProductDto);
                        })
                        .collect(Collectors.toList());
         return invoiceProductDtoList;
    }

    private static final BigDecimal PERCENTAGE_DIVISOR = BigDecimal.valueOf(100);

    @Override
    public BigDecimal calculateTotal(InvoiceProductDto invoiceProductDto) {

        BigDecimal total = invoiceProductDto.getPrice().multiply(BigDecimal.valueOf(invoiceProductDto.getQuantity()));
        BigDecimal taxRateModified =  BigDecimal.valueOf(invoiceProductDto.getTax()).divide(PERCENTAGE_DIVISOR,2, RoundingMode.HALF_UP);

        return total.add(total.multiply(taxRateModified));
    }

    @Override
    public List<InvoiceProductDto> listAll() {
        return invoiceProductRepository.findAll().stream()
                .map(product->mapperUtil.convert(product, InvoiceProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void save(InvoiceProductDto invoiceProductDto) {
        invoiceProductRepository.save(mapperUtil.convert(invoiceProductDto,InvoiceProduct.class));
    }

    @Override
    public List<InvoiceProductDto> listPurchaseInvoiceProductsQuantityNotZero(Long companyId, String productName, InvoiceType invoiceType, int quantity) {
        return invoiceProductRepository.getInvoiceProductsByInvoice_CompanyIdAndProduct_NameAndInvoice_InvoiceTypeAndQuantityNot(securityService.getLoggedInUserCompanyId(), productName,InvoiceType.PURCHASE,0)
                .stream().map(invoiceProduct -> mapperUtil.convert(invoiceProduct,InvoiceProductDto.class)).toList();
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

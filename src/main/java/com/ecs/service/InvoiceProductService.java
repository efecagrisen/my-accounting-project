package com.ecs.service;

import com.ecs.dto.InvoiceDto;
import com.ecs.dto.InvoiceProductDto;
import com.ecs.entity.Invoice;
import com.ecs.enums.InvoiceType;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceProductService {

    List<InvoiceProductDto> listAllCompanyInvoiceProducts();
    InvoiceProductDto findById(Long id);
    List<InvoiceProductDto> findByInvoiceId(Long id);

    List<InvoiceProductDto> listAll();

    BigDecimal getProfitLossBasedOnOneMonth(int year, int month, Long companyId, InvoiceType invoiceType);

    InvoiceProductDto create(InvoiceProductDto invoiceProductDto, Long invoiceId);

    void removeInvoiceProductFromInvoice(Long invoiceId, Long invoiceProductId);

    void deleteById(Long invoiceProductId);
    void deleteByInvoice(InvoiceDto invoiceDto);
    void save(InvoiceProductDto invoiceProductDto);

    List<InvoiceProductDto> listPurchaseInvoiceProductsQuantityNotZero(Long companyId,String productName, InvoiceType invoiceType, int quantity);

    BigDecimal calculateTotal(InvoiceProductDto invoiceProductDto);

    BindingResult doesProductHaveEnoughStock(InvoiceProductDto invoiceProductDTO, BindingResult bindingResult );

    BindingResult validateProductStockBeforeAddingToInvoice(InvoiceProductDto invoiceProductDTO, Long invoiceId, BindingResult bindingResult);







}

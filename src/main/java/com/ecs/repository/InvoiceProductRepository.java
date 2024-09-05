package com.ecs.repository;

import com.ecs.entity.Invoice;
import com.ecs.entity.InvoiceProduct;
import com.ecs.enums.InvoiceStatus;
import com.ecs.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct,Long> {

    List<InvoiceProduct> findByInvoiceId(Long invoiceId);

    List<InvoiceProduct> getInvoiceProductsByInvoice_CompanyIdAndProduct_NameAndInvoice_InvoiceTypeAndQuantityNot(Long companyId,String productName, InvoiceType invoiceType, int quantity);
    List<InvoiceProduct> getInvoiceProductsByInvoice_CompanyIdAndInvoice_InvoiceStatusOrderByInvoice_InsertDateTimeDesc(Long companyId, InvoiceStatus invoiceStatus);

    @Query(value = "SELECT COALESCE(SUM (ip.profitLoss),0.00) " +
            "FROM InvoiceProduct ip " +
            "WHERE YEAR (ip.insertDateTime) = ?1 " +
            "AND MONTH (ip.insertDateTime) = ?2 " +
            "AND ip.invoice.company.id = ?3 " +
            "AND ip.invoice.invoiceType = ?4")
    BigDecimal retrieveCompanyTotalProfitLossOfTheGivenMonth(int year, int month, Long companyId, InvoiceType invoiceType);




}

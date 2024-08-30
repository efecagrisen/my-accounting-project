package com.ecs.repository;

import com.ecs.entity.Invoice;
import com.ecs.entity.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct,Long> {

    List<InvoiceProduct> findByInvoiceId(Long invoiceId);




}

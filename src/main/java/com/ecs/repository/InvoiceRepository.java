package com.ecs.repository;

import com.ecs.entity.Invoice;
import com.ecs.enums.InvoiceStatus;
import com.ecs.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {


    List<Invoice> findAllByCompanyId(Long companyId);

    List<Invoice> findAllByCompanyIdAndInvoiceTypeOrderByInvoiceNoDesc(Long companyId, InvoiceType invoiceType);

    Optional<Invoice> findById(Long id);

    Invoice findTopByOrderByInsertDateTimeDesc();

    Invoice findTopByCompanyIdAndInvoiceTypeOrderByInsertDateTimeDesc(Long companyId, InvoiceType invoiceType);

    Optional<Invoice> findTopByCompanyIdAndInvoiceTypeOrderByInvoiceNoDesc(Long companyId,InvoiceType invoiceType);

    boolean existsByClientVendorId(Long clientVendorId);

    List<Invoice> findTop3ByCompanyIdAndInvoiceStatusOrderByDateDesc(Long companyId, InvoiceStatus invoiceStatus);

}

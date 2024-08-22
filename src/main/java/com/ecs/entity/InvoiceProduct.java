package com.ecs.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

@Entity
@Table(name = "invoice_products")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "is_deleted = false")

public class InvoiceProduct extends BaseEntity{

    private int quantity;
    private BigDecimal price;
    private int tax;
    private BigDecimal profitLoss;
    private int remainingQuantity;

    @ManyToOne
    private Invoice invoice;

    @ManyToOne
    private Product product;

}

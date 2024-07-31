package com.ecs.entity;
import com.ecs.enums.ProductUnit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "is_deleted = false")
public class Product extends BaseEntity{

    private String name;
    private int quantityInStock;
    private int lowLimitAlert;

    @Enumerated(EnumType.STRING)
    private ProductUnit productUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

}

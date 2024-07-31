package com.ecs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "is_deleted = false")
public class Category extends BaseEntity{

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;



}

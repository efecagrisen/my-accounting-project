package com.ecs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Where(clause = "is_deleted = false")
public class Role extends BaseEntity{

    private String description;
}

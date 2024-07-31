package com.ecs.entity;

import com.ecs.enums.CompanyStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "is_deleted = false")
public class Company extends BaseEntity{

    @Column(unique = true)
    private String title;
    private String phone;
    private String website;

    @Enumerated(EnumType.STRING)
    private CompanyStatus companyStatus;

    @OneToOne(fetch = FetchType.LAZY)
    private Address address;




}

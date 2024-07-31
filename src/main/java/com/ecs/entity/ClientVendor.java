package com.ecs.entity;

import com.ecs.enums.ClientVendorType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "clients_vendors")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "is_deleted = false")
public class ClientVendor extends BaseEntity{

    private String clientVendorName;
    private String phone;
    private String website;

    @Enumerated(EnumType.STRING)
    private ClientVendorType clientVendorType;

    @OneToOne(fetch = FetchType.LAZY)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;


}

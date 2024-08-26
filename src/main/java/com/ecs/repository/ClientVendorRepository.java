package com.ecs.repository;

import com.ecs.entity.ClientVendor;
import com.ecs.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientVendorRepository extends JpaRepository<ClientVendor,Long> {

    List<ClientVendor> getAllByCompanyIdOrderByClientVendorTypeAsc(Long loggedInUsersCompanyId);


}

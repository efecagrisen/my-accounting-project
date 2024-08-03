package com.ecs.repository;

import com.ecs.entity.Company;
import com.ecs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company,Long> {

    @Query("SELECT Company FROM Company c WHERE c.id = ?1")
    Company getCompanyForCurrent(Long id);

    @Query("SELECT c FROM Company c WHERE c.id = ?1")
    List<Company> getAllCompanyForRootUser(Long id);

}

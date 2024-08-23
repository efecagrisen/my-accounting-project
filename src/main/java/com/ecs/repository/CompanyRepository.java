package com.ecs.repository;

import com.ecs.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {

    @Query("SELECT c FROM Company c WHERE c.id = ?1 ORDER BY c.companyStatus ASC")
    Company getCompanyForCurrent(Long id);

    @Query("SELECT c FROM Company c WHERE c.id != ?1 ORDER BY c.companyStatus ASC")
    List<Company> getAllCompanyForRootUser(Long id);

    Optional<Company> findById(Long id);

    @Query("SELECT c FROM Company c JOIN User u ON c.id = u.company.id WHERE u.id != ?1")
    List<Company> findCompaniesOfNonRootUsers(Long id);

    boolean existsByTitle(String title);


}

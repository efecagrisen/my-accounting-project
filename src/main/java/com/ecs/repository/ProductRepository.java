package com.ecs.repository;

import com.ecs.entity.Company;
import com.ecs.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT p FROM Product p JOIN Category c ON p.category.id = c.id WHERE c.company = ?1 ORDER BY p.category.description ASC , p.name ASC ")
    List<Product> findAllByCompany(Company company);

    Optional<Product> findById(Long id);

    @Query("SELECT CASE WHEN COUNT (p) > 0 THEN true ELSE false END FROM Product p JOIN Category c ON p.category.id = c.id WHERE c.company.id = ?2 AND p.category.description = ?1")
    boolean existsProductByCompanyCategory(String categoryDescription, Long CompanyId);

}

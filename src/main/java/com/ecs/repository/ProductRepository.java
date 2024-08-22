package com.ecs.repository;

import com.ecs.entity.Company;
import com.ecs.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT p FROM Product p JOIN Category c ON p.category.id = c.id WHERE c.company = ?1")
    List<Product> findAllByCompany(Company company);

    Optional<Product> findById(Long id);

}

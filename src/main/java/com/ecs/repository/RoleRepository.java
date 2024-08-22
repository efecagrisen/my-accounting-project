package com.ecs.repository;

import com.ecs.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findById(Long id);

    List<Role> getRolesByIdIsNot(Long id);

}

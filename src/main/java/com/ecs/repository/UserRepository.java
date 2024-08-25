package com.ecs.repository;

import com.ecs.entity.Role;
import com.ecs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

    List<User> findUsersByCompanyIdOrderByRoleIdAsc(Long companyId);

    List<User> findAllByRoleDescriptionOrderByCompany_TitleAsc(String roleDescription);


}

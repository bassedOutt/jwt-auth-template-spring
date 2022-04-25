package com.example.jwt_auth_template.repository;

import com.example.jwt_auth_template.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}

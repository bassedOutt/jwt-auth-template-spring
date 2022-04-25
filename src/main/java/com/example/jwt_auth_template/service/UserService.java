package com.example.jwt_auth_template.service;


import com.example.jwt_auth_template.entity.Role;
import com.example.jwt_auth_template.entity.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);

    List<User> findAllUsers();

    User addRoleToUser(String email, String rolename);

    User insert(User user);

    User update(User entity);

    Role saveRole(String roleName);

    void delete(Long id);
}

package com.example.jwt_auth_template.service;

import com.example.jwt_auth_template.entity.Role;
import com.example.jwt_auth_template.entity.User;
import com.example.jwt_auth_template.repository.RoleRepository;
import com.example.jwt_auth_template.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.jwt_auth_template.util.Util.ROLE_USER;

@Slf4j
@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAllUsers() {
        log.info("fetching all users");
        return repository.findAll();
    }


    @Override
    public User addRoleToUser(String email, String rolename) {
        log.info("adding role {} to user {}", rolename, email);
        User user = repository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
        Role role = roleRepository.findByName(rolename);
        user.addRole(role);
        repository.save(user);
        return user;
    }

    @Override
    public User insert(User user) {
        log.info("saving user {} to database", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName(ROLE_USER);
        user.addRole(role);
        repository.save(user);
        return user;
    }

    @Override
    public User update(User user) {
        log.info("updating user: {}", user.getEmail());
        repository.save(user);
        return user;
    }

    @Override
    public Role saveRole(String roleName) {
        Role role = new Role(roleName);
        roleRepository.save(role);
        return role;
    }

    @Override
    public void delete(Long id) {
        log.info("deleting user with an id: {}", id);
        repository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        log.info("Searching for user with email: {}", email);
        return repository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = repository.findByEmail(s).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with email %s not found", s)));

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), authorities);
    }
}

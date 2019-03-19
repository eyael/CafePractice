package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    User findByUsername(String username);
}


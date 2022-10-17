package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Collection;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "select count(r) FROM Role r")
    int RolesCount();

    @Query(value = "select r FROM Role r")
    Collection<Role> Roles();
}
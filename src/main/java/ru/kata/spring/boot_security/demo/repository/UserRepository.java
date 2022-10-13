package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //@Query(value="SELECT u FROM User u  WHERE u.username LIKE ?1")
    @Query(value = "SELECT u FROM User u LEFT JOIN fetch u.roles where u.username LIKE ?1")
    User findByUsername(String username);

    @Query(value="SELECT u FROM User u LEFT JOIN fetch u.roles")
    List<User> findAll();

    @Query(value = "SELECT u FROM User u LEFT JOIN fetch u.roles WHERE u.id = ?1")
    User getById(Long id);

    @Query(value = "SELECT count(u) FROM User u")
    int UsersCount();
}
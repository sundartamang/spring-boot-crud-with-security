package com.blog.repositories;

import com.blog.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Integer> {

    Optional<Users> findByEmail(String email);
}

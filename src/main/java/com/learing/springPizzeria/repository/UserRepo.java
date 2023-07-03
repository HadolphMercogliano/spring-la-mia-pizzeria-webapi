package com.learing.springPizzeria.repository;

import com.learing.springPizzeria.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);
}

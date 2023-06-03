package com.localisation.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.localisation.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);
  Optional<User> findById(String email);
}
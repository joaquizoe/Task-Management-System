package org.example.taskmanagementsystem.repository;

import org.example.taskmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}

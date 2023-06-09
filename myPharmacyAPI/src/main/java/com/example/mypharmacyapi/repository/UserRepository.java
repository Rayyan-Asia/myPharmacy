package com.example.mypharmacyapi.repository;

import com.example.mypharmacyapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}

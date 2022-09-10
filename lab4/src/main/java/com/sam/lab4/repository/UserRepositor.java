package com.sam.lab4.repository;

import com.sam.lab4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositor extends JpaRepository<User, Long> {
}

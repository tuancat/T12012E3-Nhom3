package com.sam.lab4.repository;

import com.sam.lab4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   @Query("select u from User u where u.userName=?1")
    public User findByUserName(String userName);
}

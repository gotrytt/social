package com.gotryt.coop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gotryt.coop.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
    Boolean existsByMemship(String memship);

    User findByMemship(String memship);
    
    List<User> findByStatus(String status);

    @Query("SELECT DISTINCT u FROM User u WHERE u.memship LIKE %:query% or u.email LIKE %:query%")
    public List<User> searchUser(@Param("query") String query);
}

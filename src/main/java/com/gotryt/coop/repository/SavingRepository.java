package com.gotryt.coop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gotryt.coop.model.Saving;


public interface SavingRepository extends JpaRepository<Saving, Long> {

    List<Saving> findByUser_Id(Long userId);
    
}

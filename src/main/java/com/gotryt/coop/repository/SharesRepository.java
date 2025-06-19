package com.gotryt.coop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gotryt.coop.model.Shares;


public interface SharesRepository extends JpaRepository<Shares, Long> {

    List<Shares> findByUser_Id(Long userId);
    
}

package com.gotryt.coop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gotryt.coop.model.Repay;


public interface RepayRepository extends JpaRepository<Repay, Long> {

    List<Repay> findByUser_Id(Long userId);

    
}

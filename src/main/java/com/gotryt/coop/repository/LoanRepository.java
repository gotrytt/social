package com.gotryt.coop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gotryt.coop.model.Loan;


public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUser_Id(Long userId);
    
}

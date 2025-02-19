package com.gotryt.coop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gotryt.coop.dto.LoanDto;
import com.gotryt.coop.dto.RepayDto;
import com.gotryt.coop.exception.LoanException;
import com.gotryt.coop.exception.RepayException;
import com.gotryt.coop.model.Loan;
import com.gotryt.coop.model.Repay;
import com.gotryt.coop.model.User;
import com.gotryt.coop.repository.LoanRepository;
import com.gotryt.coop.repository.RepayRepository;
import com.gotryt.coop.service.LoanService;
import com.gotryt.coop.service.RepayService;
import com.gotryt.coop.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/loan")
public class LoanController {

    @Autowired
    UserService userService;

    @Autowired
    LoanService loanService;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    RepayRepository repayRepository;

    @Autowired
    RepayService repayService;
    
    @PostMapping("/apply")
    public Loan applyLoan(@RequestHeader("Authorization") String jwt, @RequestBody LoanDto loanRequest) throws LoanException {
        User user = userService.findUserProfileByJwt(jwt).getUser();
        return loanService.applyLoan(user, loanRequest);
    }

    @GetMapping("/all")
    public List<Loan> allLoans(@RequestHeader("Authorization") String jwt) throws LoanException {
        return loanRepository.findAll();
    }

    @GetMapping("/myloans")
    public List<Loan> myLoans(@RequestHeader("Authorization") String jwt) throws LoanException {
        User user = userService.findUserProfileByJwt(jwt).getUser();
        return loanRepository.findByUser_Id(user.getId());
    }

    @GetMapping("/{loanId}")
    public Optional<Loan> loanById(@RequestHeader("Authorization") String jwt, @PathVariable Long loanId) throws LoanException {
        return loanRepository.findById(loanId);
    }

    @PutMapping("/{loanId}/aprove")
    public Loan approveLoan(@RequestHeader("Authorization") String jwt, @PathVariable Long loanId) throws LoanException {
        return loanService.approveLoan(loanId);
    }

    @PostMapping("/{loanId}/repay")
    public Repay repayNow(@RequestHeader("Authorization") String jwt, @PathVariable Long loanId, @RequestBody RepayDto repayRequest) throws RepayException, LoanException {
        User user = userService.findUserProfileByJwt(jwt).getUser();
        return repayService.repayNow(user, loanId, repayRequest);
    }

    @GetMapping("/myrepays")
    public List<Repay> myRepay(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserProfileByJwt(jwt).getUser();
        return repayRepository.findByUser_Id(user.getId());
    }

    @GetMapping("/repays")
    public List<Repay> allRepay(@RequestHeader("Authorization") String jwt) {
        return repayRepository.findAll();
    }

}

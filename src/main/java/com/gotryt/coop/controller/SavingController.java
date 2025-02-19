package com.gotryt.coop.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gotryt.coop.exception.SavingException;
import com.gotryt.coop.model.Saving;
import com.gotryt.coop.model.User;
import com.gotryt.coop.repository.SavingRepository;
import com.gotryt.coop.service.SavingService;
import com.gotryt.coop.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/savings")
public class SavingController {

    @Autowired
    UserService userService;

    @Autowired
    SavingService savingService;

    @Autowired
    SavingRepository savingRepository;
    
    @PostMapping("/save")
    public Saving savevNow(@RequestHeader("Authorization") String jwt, @RequestBody BigDecimal amount) throws SavingException {
        User user = userService.findUserProfileByJwt(jwt).getUser();
        return savingService.saveNow(user, amount);
    }

    @GetMapping("/all")
    public List<Saving> allsaSavings(@RequestHeader("Authorization") String jwt) throws SavingException {
        return savingRepository.findAll();
    }

    @GetMapping("/mysavings")
    public List<Saving> mySavings(@RequestHeader("Authorization") String jwt) throws SavingException {
        User user = userService.findUserProfileByJwt(jwt).getUser();
        return savingRepository.findByUser_Id(user.getId());
    }

    @GetMapping("/{savingId}")
    public Optional<Saving> savingById(@RequestHeader("Authorization") String jwt, @PathVariable Long savingId) throws SavingException {
        return savingRepository.findById(savingId);
    }

}

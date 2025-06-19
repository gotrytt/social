package com.gotryt.coop.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String otp;         
    private String email; 
    
    @CreationTimestamp
    private LocalDateTime createdAt;      
    private LocalDateTime expiredAt;      
}

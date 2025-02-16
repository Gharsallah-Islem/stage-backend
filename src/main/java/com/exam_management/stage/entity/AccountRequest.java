package com.exam_management.stage.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "account_requests")
@Data
public class AccountRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String workCode;

    @Column(nullable = false)
    private Date joinDate;

    @Column(nullable = false)
    private String status;

    public void setStatus(String status) { // ✅ Added this method
        this.status = status;
    }
}
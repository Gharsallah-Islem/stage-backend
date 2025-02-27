package com.exam_management.stage.controller;

import com.exam_management.stage.entity.AccountRequest;
import com.exam_management.stage.service.AccountRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account-requests")
public class AccountRequestController {
    @Autowired
    private AccountRequestService accountRequestService;

    @PostMapping
    public AccountRequest createAccountRequest(@RequestBody AccountRequest accountRequest) {
        return accountRequestService.createAccountRequest(accountRequest);
    }

    @GetMapping
    public List<AccountRequest> getAllAccountRequests() {
        return accountRequestService.getAllAccountRequests();
    }

    @GetMapping("/status/{status}")
    public List<AccountRequest> getAccountRequestsByStatus(@PathVariable String status) {
        return accountRequestService.getAccountRequestsByStatus(status);
    }

    @GetMapping("/{id}")
    public Optional<AccountRequest> getAccountRequestById(@PathVariable Long id) {
        return accountRequestService.getAccountRequestById(id);
    }

    @PutMapping("/approve/{id}")
    public AccountRequest approveAccountRequest(@PathVariable Long id) {
        return accountRequestService.approveAccountRequest(id);
    }

    @PutMapping("/revoke/{id}")
    public AccountRequest revokeAccountRequest(@PathVariable Long id) {
        return accountRequestService.revokeAccountRequest(id);
    }


    @DeleteMapping("/{id}")
    public void deleteAccountRequest(@PathVariable Long id) {
        accountRequestService.deleteAccountRequest(id);
    }
}
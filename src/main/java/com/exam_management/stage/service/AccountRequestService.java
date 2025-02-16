package com.exam_management.stage.service;

import com.exam_management.stage.entity.AccountRequest;
import com.exam_management.stage.repository.AccountRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountRequestService {
    @Autowired
    private AccountRequestRepository accountRequestRepository;

    public AccountRequest createAccountRequest(AccountRequest accountRequest) {
        return accountRequestRepository.save(accountRequest);
    }

    public List<AccountRequest> getAllAccountRequests() {
        return accountRequestRepository.findAll();
    }

    public List<AccountRequest> getAccountRequestsByStatus(String status) {
        return accountRequestRepository.findByStatus(status);
    }

    public Optional<AccountRequest> getAccountRequestById(Long id) {
        return accountRequestRepository.findById(id);
    }

    public AccountRequest approveAccountRequest(Long id) {
        AccountRequest accountRequest = accountRequestRepository.findById(id).orElseThrow(() -> new RuntimeException("Account request not found"));
        accountRequest.setStatus("APPROVED");
        return accountRequestRepository.save(accountRequest);
    }

    public AccountRequest revokeAccountRequest(Long id) {
        AccountRequest accountRequest = accountRequestRepository.findById(id).orElseThrow(() -> new RuntimeException("Account request not found"));
        accountRequest.setStatus("PENDING");
        return accountRequestRepository.save(accountRequest);
    }

    public void deleteAccountRequest(Long id) {
        accountRequestRepository.deleteById(id);
    }
}
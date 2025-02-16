package com.exam_management.stage.repository;

import com.exam_management.stage.entity.AccountRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccountRequestRepository extends JpaRepository<AccountRequest, Long> {
    List<AccountRequest> findByStatus(String status);
}
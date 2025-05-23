package com.exam_management.stage.repository;

import com.exam_management.stage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByApproved(int approved);

    List<User> findByStatus(String status);
    @Query("SELECT u FROM User u WHERE u.status IN :statuses")
    List<User> findByStatuses(@Param("statuses") List<String> statuses);
}

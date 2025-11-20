package com.fxdidier.trading_portal.app.domain.repository;


import com.fxdidier.trading_portal.app.domain.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>
{
    boolean existsByAccountNumber(String accountNumber);
    Page<Account> findByUserId(Long userId, Pageable pageable);
}

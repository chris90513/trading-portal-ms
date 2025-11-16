package com.fxdidier.trading_portal.app.domain.repository;


import com.fxdidier.trading_portal.app.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>
{

}

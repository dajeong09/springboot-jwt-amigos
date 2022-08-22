package com.example.amigosuser.repository;

import com.example.amigosuser.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByNickname(String nickname);
}

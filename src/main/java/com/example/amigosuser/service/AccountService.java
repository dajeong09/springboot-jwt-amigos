package com.example.amigosuser.service;

import com.example.amigosuser.domain.Account;
import com.example.amigosuser.domain.Role;

import java.util.List;

public interface AccountService {
    Account createAccount(Account account);
    Role createRole(Role role);
    void addRoleToAccount(String nickname, String roleName);
    Account getAccount(String nickname);
    List<Account> getAccounts();

}

package com.example.amigosuser.controller;

import com.example.amigosuser.domain.Account;
import com.example.amigosuser.domain.Role;
import com.example.amigosuser.service.AccountService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>>getAccounts() {
        return ResponseEntity.ok().body(accountService.getAccounts());
    }

    @PostMapping("/account/create")
    public ResponseEntity<Account>createAccount(@RequestBody Account account) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/account/create").toUriString());
        return ResponseEntity.created(uri).body(accountService.createAccount(account));
    }

    @PostMapping("/role/create")
    public ResponseEntity<Role>createRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/create").toUriString());
        return ResponseEntity.created(uri).body(accountService.createRole(role));
    }

    @PostMapping("role/addtoaccount")
    public ResponseEntity<?>addRoleToAccount(@RequestBody RoleToUserForm form) {
        accountService.addRoleToAccount(form.getNickname(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
}

@Data
class RoleToUserForm {
    private String nickname;
    private String roleName;
}
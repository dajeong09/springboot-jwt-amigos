package com.example.amigosuser.service;

import com.example.amigosuser.domain.Account;
import com.example.amigosuser.domain.Role;
import com.example.amigosuser.repository.AccountRepository;
import com.example.amigosuser.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class AccountServiceImpl implements AccountService, UserDetailsService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        Account account = accountRepository.findByNickname(nickname);
        if(account == null) {
            log.error("회원 정보가 존재하지 않습니다.");
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        } else {
            log.info("찾으려는 유저 정보: {}", nickname);
        }
        Collection<SimpleGrantedAuthority> authiorities = new ArrayList<>();
        account.getRoles().forEach(role -> { authiorities.add(new SimpleGrantedAuthority(role.getName()));});
        return new User(account.getNickname(), account.getPassword(), authiorities);
    }

    @Override
    public Account createAccount(Account account) {
        log.info("{} 회원 정보를 DB에 저장", account.getNickname());
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public Role createRole(Role role) {
        log.info("{} 역할 정보를 DB에 저장", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToAccount(String nickname, String roleName) {
        log.info("{} 역할 정보를 {}에 추가", nickname, roleName);
        Account account = accountRepository.findByNickname(nickname);
        Role role = roleRepository.findByName(roleName);
        account.getRoles().add(role);
    }

    @Override
    public Account getAccount(String nickname) {
        log.info("{} 회원 정보 가져오기", nickname);
        return accountRepository.findByNickname(nickname);
    }

    @Override
    public List<Account> getAccounts() {
        log.info("모든 회원 정보 가져오기");
        return accountRepository.findAll();
    }


}

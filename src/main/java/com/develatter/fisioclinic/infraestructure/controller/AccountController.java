package com.develatter.fisioclinic.infraestructure.controller;

import com.develatter.fisioclinic.application.service.AccountService;
import com.develatter.fisioclinic.domain.model.Account;
import com.develatter.fisioclinic.domain.model.Role;
import com.develatter.fisioclinic.infraestructure.controller.dto.CreateAccountRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.CreateAccountResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/accounts")
@RestController
public class AccountController {
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    public AccountController(AccountService accountService, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateAccountResponse> createAccount(
            @Valid
            @RequestBody
            CreateAccountRequest request
    ) {
        Set<Role> roles = request.roles().stream()
                .map(roleCode -> Role.valueOf(roleCode.toUpperCase()))
                .collect(Collectors.toSet());

        String hashedPassword = passwordEncoder.encode(request.password());

        Account account = new Account(
                null,
                request.email(),
                hashedPassword,
                true,
                null,
                null,
                roles
        );

        Account savedAccount = accountService.createAccount(account);

        CreateAccountResponse response = new CreateAccountResponse(
                savedAccount.id(),
                savedAccount.email(),
                savedAccount.enabled(),
                savedAccount.roles().stream().map(Role::name).collect(Collectors.toSet()),
                savedAccount.createdAt()
        );

        return ResponseEntity.ok(response);
    }

}

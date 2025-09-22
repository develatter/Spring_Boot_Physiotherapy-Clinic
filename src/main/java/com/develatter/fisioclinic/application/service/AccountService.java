package com.develatter.fisioclinic.application.service;

import com.develatter.fisioclinic.application.port.in.CreateAccountUseCase;
import com.develatter.fisioclinic.application.port.out.CreateAccountPort;
import com.develatter.fisioclinic.domain.model.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements CreateAccountUseCase {
    private final CreateAccountPort createAccountPort;

    public AccountService(CreateAccountPort createAccountPort) {
        this.createAccountPort = createAccountPort;
    }

    @Override
    public Account createAccount(Account account) {
        return createAccountPort.save(account);
    }
}

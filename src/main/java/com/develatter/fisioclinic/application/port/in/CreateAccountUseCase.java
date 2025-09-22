package com.develatter.fisioclinic.application.port.in;

import com.develatter.fisioclinic.domain.model.Account;

public interface CreateAccountUseCase {
    Account createAccount(Account account);
}

package com.develatter.fisioclinic.application.port.out;

import com.develatter.fisioclinic.domain.model.Account;

public interface CreateAccountPort {
    Account save(Account account);
}

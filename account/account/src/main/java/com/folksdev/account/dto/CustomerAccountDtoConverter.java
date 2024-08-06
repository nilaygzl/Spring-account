package com.folksdev.account.dto;

import com.folksdev.account.model.Account;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerAccountDtoConverter {

    private final TransactionDtoConverter converter;

    public CustomerAccountDtoConverter(TransactionDtoConverter converter) {
        this.converter = converter;
    }

    public CustomerAccountDto convert(Account account){
        return new CustomerAccountDto(
                Objects.requireNonNull(account.getId()),
                account.getBalance(),
                account.getTransaction().stream().map(converter::convert).collect(Collectors.toSet()),
                account.getCreationDate()

        );
    }
}

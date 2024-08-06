package com.folksdev.account.service;

import com.folksdev.account.dto.AccountDto;
import com.folksdev.account.dto.AccountDtoConverter;
import com.folksdev.account.dto.CreateAccountRequest;
import com.folksdev.account.model.Account;
import com.folksdev.account.model.Customer;
import com.folksdev.account.model.Transaction;
import com.folksdev.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
   // private final TransactionService transactionService;
    private final AccountDtoConverter converter; //component yaptığım için inject edebiliyorum

    public AccountService(AccountRepository accountRepository,
                          CustomerService customerService,
                          AccountDtoConverter converter) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
       // this.transactionService = transactionService;
        this.converter = converter;
    }

    public AccountDto createAccount(CreateAccountRequest createAccountRequest){
        Customer customer = customerService.findCustomerById(createAccountRequest.getCustomerId());

        Account account = new Account(customer ,createAccountRequest.getInitialCredit(), LocalDateTime.now());

        if (createAccountRequest.getInitialCredit().compareTo(BigDecimal.ZERO) > 0){
           // Transaction transaction = transactionService.initiateMoney(account, createAccountRequest.getInitialCredit());
            Transaction transaction = new Transaction(createAccountRequest.getInitialCredit(), account);
            account.getTransaction().add(transaction);
        }
//kaydetmem gereken modeli oluşturdum kaydedicem.

        //denenecek not : TRansactionu hiç kaydetmeden direkt accountu set etsek bile jpa onu kaydeder.
        return converter.convert(accountRepository.save(account));
    }


}

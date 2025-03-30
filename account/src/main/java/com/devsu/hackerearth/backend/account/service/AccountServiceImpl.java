package com.devsu.hackerearth.backend.account.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<AccountDto> getAll() {
        // Get all accounts

        List<Object[]> resultQuery = this.accountRepository.findAllAccount();
        if (!resultQuery.isEmpty()) {

            return this.listResultAccount(resultQuery);
        }

        return null;
    }

    @Override
    public AccountDto getById(Long id) {
        // Get accounts by id
        List<Object[]> resultQuery = this.accountRepository.findByCustomerIdNative(id);

        if (resultQuery.size() == 1) {
            Object[] resultObjQuery = resultQuery.get(0);
            AccountDto responseObj = this.makerDtoAccount(resultObjQuery);

            return responseObj;

        }

        return null;

    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        // Create account

        Account accounInDb = new Account().builder()
                .number(accountDto.getNumber())
                .type(accountDto.getType())
                .initialAmount(accountDto.getInitialAmount())
                .isActive(accountDto.isActive())
                .clientId(accountDto.getClientId())
                .build();

        accounInDb = this.accountRepository.save(accounInDb);

        accountDto.setId(accounInDb.getId());

        return accountDto;
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        // Update account
        Account accountDB = new Account().builder()
                .number(accountDto.getNumber())
                .type(accountDto.getType())
                .initialAmount(accountDto.getInitialAmount())
                .isActive(accountDto.isActive())
                .clientId(accountDto.getClientId())
                .build();
        this.accountRepository.updateAccountM(accountDB);
        return accountDto;
    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        // Partial update account

        this.accountRepository.updateActiveStatus(id,partialAccountDto.isActive());

        AccountDto response = this.getById(id);

        return response;
    }

    @Override
    public void deleteById(Long id) {
        // Delete account
        this.accountRepository.deleteById(id);
    }

    private List<AccountDto> listResultAccount(List<Object[]> resultQuery) {
        List<AccountDto> resultDto = new ArrayList<>();

        for (Object[] accountObj : resultQuery) {
            resultDto.add(this.makerDtoAccount(accountObj));
        }
        return resultDto;
    }

    private AccountDto makerDtoAccount(Object[] resultObjQuery) {
        AccountDto responseObj = new AccountDto(
                resultObjQuery[0] !=null ? Long.valueOf(resultObjQuery[0].toString()):null,
                resultObjQuery[1] != null ? resultObjQuery[1].toString() : null,
                resultObjQuery[2] != null ? resultObjQuery[2].toString() : null,
                resultObjQuery[3] != null ? ((Number) resultObjQuery[3]).doubleValue() : 0.0,
                resultObjQuery[4] != null ? Boolean.parseBoolean(resultObjQuery[4].toString()) : false,
                resultObjQuery[5] != null ? Long.valueOf(resultObjQuery[5].toString()) : null
                );
        return responseObj;
    }

}

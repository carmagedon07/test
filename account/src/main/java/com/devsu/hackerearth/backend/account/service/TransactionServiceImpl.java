package com.devsu.hackerearth.backend.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.controller.exception.RejectException;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountService accountService;

    public TransactionServiceImpl(TransactionRepository transactionRepository,AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Override
    public List<TransactionDto> getAll() {
        // Get all transact
        List<Object[]> resultQuery = this.transactionRepository.findAllTransactions();
        List<TransactionDto> response = null;
        if (!resultQuery.isEmpty()) {
            response = new ArrayList();
            for (Object[] obj : resultQuery) {
                TransactionDto objResponse = this.objectToTransactionDto(obj);
                response.add(objResponse);
            }
        }

        return response;
    }

    @Override
    public TransactionDto getById(Long id) {
        // Get transactions by id
        TransactionDto response = null;
        List<Object[]> resultQuery = this.transactionRepository.findTransactionbyId(id);
        if (resultQuery.size() == 1) {
            response = this.objectToTransactionDto(resultQuery.get(0));
        }

        return response;
    }

    @Override
    public TransactionDto create(TransactionDto transactionDto) throws RejectException {
        // Create transaction
        AccountDto cuenta = this.accountService.getById(transactionDto.getAccountId());
        
        //validar si la transacion es mayor que el monto
        if(transactionDto.getAmount()>cuenta.getInitialAmount()){
            throw new RejectException("Saldo no disponible");
        }
        Double saldoFinal=cuenta.getInitialAmount()-transactionDto.getAmount();
        //
        Transaction transationDb = new Transaction().builder()
                .date(transactionDto.getDate())
                .type(transactionDto.getType())
                .amount(transactionDto.getAmount())
                .balance(transactionDto.getBalance())
                .accountId(transactionDto.getAccountId())
                .build();

        transationDb = this.transactionRepository.save(transationDb);
        transactionDto.setId(transationDb.getId());

        //actualizar cuenta
        cuenta.setInitialAmount(saldoFinal);
        this.accountService.update(cuenta);
        return transactionDto;
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd) {
        // Report
        List<BankStatementDto> response = null;
        List<Object[]> responseQuery = this.transactionRepository.findTransactionsByClientAndDateRange(clientId,
                dateTransactionStart, dateTransactionEnd);
        if (responseQuery.size() != 0) {
            response = new ArrayList<>();
            for (Object[] objectResponse : responseQuery) {
                BankStatementDto responseDto = new BankStatementDto().builder()
                        .date(objectResponse[1] != null ? (Date) objectResponse[1] : null)
                        .client(objectResponse[2] != null ? objectResponse[2].toString() : null)
                        .accountNumber(objectResponse[3] != null ? objectResponse[3].toString() : null)
                        .accountType(objectResponse[4] != null ? objectResponse[4].toString() : null)
                        .initialAmount(objectResponse[5] != null ? (Double) objectResponse[5] : 0.0)
                        .isActive(objectResponse[6] != null ? (Boolean) objectResponse[6] : false)
                        .transactionType(objectResponse[7] != null ? objectResponse[7].toString() : null)
                        .amount(objectResponse[8] != null ? (Double) objectResponse[8] : 0.0)
                        .balance(objectResponse[9] != null ? (Double) objectResponse[9] : 0.0)
                        .build();
                response.add(responseDto);

            }

        }
        return response;
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        // If you need it      
        List<Object[]> resultQuery = this.transactionRepository.findLatestTransactionByAccountId(accountId);
        TransactionDto responseDto = null;
        if (resultQuery.size() == 1) {
            Object[] response = resultQuery.get(0);
            
                responseDto = new TransactionDto().builder()
                        .id(response[0] != null ? (Long) response[0] : 0)
                        .date(response[1] != null ? (Date) response[1] : null)
                        .type(response[2] != null ? response[2].toString() : null)
                        .amount(response[3] != null ? (Double)response[3] : 0.0)
                        .amount(response[4] != null ? (Double)response[4] : 0.0)
                        .accountId(response[5] != null ? (Long)response[5] : 0)
                        .build();                             
            
        }

        return responseDto;
    }

    private TransactionDto objectToTransactionDto(Object[] obj) {
        return new TransactionDto().builder()
                .id(obj[0] != null ? ((Number) obj[0]).longValue() : null)
                .date(obj[1] != null ? (Date) obj[1] : null)
                .type(obj[2] != null ? obj[2].toString() : null)
                .amount(obj[3] != null ? ((Number) obj[3]).doubleValue() : 0.0)
                .balance(obj[4] != null ? ((Number) obj[4]).doubleValue() : 0.0)
                .accountId(obj[5] != null ? ((Number) obj[5]).longValue() : null)
                .build();
    }

}

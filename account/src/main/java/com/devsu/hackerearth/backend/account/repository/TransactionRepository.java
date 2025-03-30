package com.devsu.hackerearth.backend.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

        @Query(value = "SELECT t.id, t.date, t.type, t.amount, t.balance, t.account_id " +
                        "FROM transaction t " +
                        "ORDER BY t.date DESC", nativeQuery = true)
        List<Object[]> findAllTransactions();

        @Query(value = "SELECT t.id, t.date, t.type, a.number as accountNumber " +
                        "FROM transaction_t t " +
                        "WHERE t.id = :accountId", nativeQuery = true)
        List<Object[]> findTransactionbyId(@Param("accountId") Long accountId);

        @Query(value = "SELECT " +
                        "t.id AS id, " +
                        "t.date AS date, " +
                        "t.type AS type, " +
                        "t.amount AS amount, " +
                        "t.balance AS balance, " +
                        "t.account_id AS accountId " +
                        "FROM transaction t " +
                        "JOIN account a ON t.account_id = a.id " +
                        "WHERE a.client_id = :clientId " +
                        "AND t.date BETWEEN :startDate AND :endDate " +
                        "ORDER BY t.date DESC", nativeQuery = true)
        List<Object[]> findTransactionsByClientAndDateRange(
                        @Param("clientId") Long clientId,
                        @Param("startDate") Date startDate,
                        @Param("endDate") Date endDate);

        @Query(value = "SELECT t.id, t.date, t.type, t.amount, t.balance, t.account_id, a.number as accountNumber " +
                        "FROM transaction t " +
                        "JOIN account a ON t.account_id = a.id " +
                        "WHERE t.account_id = :accountId " +
                        "ORDER BY t.id DESC " +
                        "LIMIT 1", nativeQuery = true)
        List<Object[]> findLatestTransactionByAccountId(@Param("accountId") Long accountId);

}

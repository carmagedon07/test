package com.devsu.hackerearth.backend.account.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT id,number,type,initialAmount,isActive,clientId FROM accounts WHERE customer_id  =:customerId", nativeQuery = true)
    List<Object[]> findByCustomerIdNative(@Param("customerId") Long customerId);

    @Query(value = "SELECT number,type,initialAmount,isActive,clientId FROM accounts", nativeQuery = true)
    List<Object[]> findAllAccount();

    @Modifying
    @Query(value = "INSERT INTO accounts (number, type, balance, active, customer_id) " +
            "VALUES (:number, :type, :balance, :active, :customerId)", nativeQuery = true)
    @Transactional
    void insertAccountNative(@Param("number") String number,
            @Param("type") String type,
            @Param("balance") Double balance,
            @Param("active") Boolean active,
            @Param("customerId") Long customerId);

    @Modifying
    @Query(value = "UPDATE account " +
            "SET number = :#{#account.number}, " +
            "    type = :#{#account.type}, " +
            "    initial_amount = :#{#account.initialAmount}, " +
            "    is_active = :#{#account.active}, " +
            "    client_id = :#{#account.clientId} " +
            "WHERE id = :#{#account.id}", nativeQuery = true)
    @Transactional
    void updateAccountM(@Param("account") Account account);

    @Modifying
    @Query(value = "UPDATE account SET is_active = :active WHERE id = :accountId", nativeQuery = true)
    @Transactional
    void updateActiveStatus(@Param("accountId") Long accountId, @Param("active") Boolean active);

}

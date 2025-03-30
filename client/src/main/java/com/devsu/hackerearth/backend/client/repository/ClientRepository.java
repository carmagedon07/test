package com.devsu.hackerearth.backend.client.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.client.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = "SELECT id, dni, name, password, gender, age, address, phone, isActive " +
            "FROM client " +
            "ORDER BY id ASC", nativeQuery = true)
    List<Object[]> findAllClients();

    @Query(value = "SELECT id, dni, name, password, gender, age, address, phone, isActive " +
            "FROM client " +
            "WHERE id = :idVal", nativeQuery = true)
    List<Object[]> findClientById(@Param("idVal") Long idVal);

    @Modifying
    @Query(value = "INSERT INTO client " +
            "(dni, name, password, gender, age, address, phone, isActive) " +
            "VALUES " +
            "(:#{#client.dni}, " +
            ":#{#client.name}, " +
            ":#{#client.password}, " +
            ":#{#client.gender}, " +
            ":#{#client.age}, " +
            ":#{#client.address}, " +
            ":#{#client.phone}, " +
            ":#{#client.isActive})", nativeQuery = true)
    @Transactional
    void insertClient(@Param("client") Client client);

    @Modifying
    @Query(value = "UPDATE client SET " +
            "dni = :#{#client.dni}, " +
            "name = :#{#client.name}, " +
            "password = :#{#client.password}, " +
            "gender = :#{#client.gender}, " +
            "age = :#{#client.age}, " +
            "address = :#{#client.address}, " +
            "phone = :#{#client.phone}, " +
            "isActive = :#{#client.isActive} " +
            "WHERE id = :#{#client.id}", nativeQuery = true)
    @Transactional
    void updateClient(@Param("client") Client client);

    @Modifying
    @Query(value = "UPDATE client SET is_active = :isActive WHERE id = :id", nativeQuery = true)
    @Transactional
    void updateClientStatus(@Param("id") Long id, @Param("isActive") boolean isActive);

}

package com.Banking.transation.repository;

import com.Banking.transation.domain.Cartao;
import com.Banking.transation.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, UUID> {
    List<Cartao> findByClienteAndStatus(Cliente cliente, boolean status);

}

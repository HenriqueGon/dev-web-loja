package com.br.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.loja.modelos.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

  @Query("from Cliente where email=?1")
  public Cliente buscarClienteEmail(String email);
}

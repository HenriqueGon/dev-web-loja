package com.br.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.loja.modelos.Estado;

public interface EstadoRepositorio extends JpaRepository<Estado, Long> {

}

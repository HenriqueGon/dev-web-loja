package com.br.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.loja.modelos.Compra;

public interface CompraRepositorio extends JpaRepository<Compra, Long> {

}

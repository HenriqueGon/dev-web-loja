package com.br.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.loja.modelos.EntradaItens;

public interface EntradaItensRepositorio extends JpaRepository<EntradaItens, Long> {

}

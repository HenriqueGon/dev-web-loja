package com.br.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.loja.modelos.EntradaProduto;

public interface EntradaProdutoRepositorio extends JpaRepository<EntradaProduto, Long> {

}

package com.br.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.loja.modelos.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {

}

package com.br.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.loja.modelos.FotosProduto;

public interface FotosProdutoRepositorio extends JpaRepository<FotosProduto, Long> {

}

package com.br.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.loja.modelos.Cidade;

public interface CidadeRepositorio extends JpaRepository<Cidade, Long> {

}

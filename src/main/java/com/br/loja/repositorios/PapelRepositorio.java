package com.br.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.loja.modelos.Papel;

public interface PapelRepositorio extends JpaRepository<Papel, Long> {

}

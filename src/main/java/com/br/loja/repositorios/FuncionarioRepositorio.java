package com.br.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.loja.modelos.Funcionario;

public interface FuncionarioRepositorio extends JpaRepository<Funcionario, Long> {

}

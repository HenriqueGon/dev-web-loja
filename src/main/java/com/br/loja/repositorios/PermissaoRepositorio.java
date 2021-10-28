package com.br.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.loja.modelos.Permissao;

public interface PermissaoRepositorio extends JpaRepository<Permissao, Long> {

}

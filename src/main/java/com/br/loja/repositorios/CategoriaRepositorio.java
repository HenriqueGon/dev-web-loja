package com.br.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.loja.modelos.Categoria;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

}

package com.br.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.loja.modelos.Marca;

public interface MarcaRepositorio extends JpaRepository<Marca, Long> {

}

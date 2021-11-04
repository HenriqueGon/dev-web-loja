package com.br.loja.controle;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.loja.modelos.Categoria;
import com.br.loja.repositorios.CategoriaRepositorio;

@Controller
public class CategoriaControle {

	@Autowired
	private CategoriaRepositorio categoriaRepositorio;

	@GetMapping("/administrativo/categoria/cadastrar")
	public ModelAndView cadastrar(Categoria categoria) {
		ModelAndView mv = new ModelAndView("administrativo/categoria/cadastro");
		mv.addObject("categoria", categoria);
		return mv;
	}

	@GetMapping("/administrativo/categoria/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/categoria/lista");
		mv.addObject("listaCategoria", categoriaRepositorio.findAll());
		return mv;
	}

	@GetMapping("/administrativo/categoria/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Categoria> categoria = categoriaRepositorio.findById(id);
		return cadastrar(categoria.get());
	}

	@GetMapping("/administrativo/categoria/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Categoria> categoria = categoriaRepositorio.findById(id);
		categoriaRepositorio.delete(categoria.get());
		return listar();
	}

	@PostMapping("/administrativo/categoria/salvar")
	public ModelAndView salvar(@Valid Categoria categoria, BindingResult result) {

		if (result.hasErrors()) {
			return cadastrar(categoria);
		}

		System.out.println(categoria.getId());
		categoriaRepositorio.saveAndFlush(categoria);

		return cadastrar(new Categoria());
	}

}
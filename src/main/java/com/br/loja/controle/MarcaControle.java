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

import com.br.loja.modelos.Marca;
import com.br.loja.repositorios.MarcaRepositorio;

@Controller
public class MarcaControle {

	@Autowired
	private MarcaRepositorio marcaRepositorio;

	@GetMapping("/administrativo/marca/cadastrar")
	public ModelAndView cadastrar(Marca marca) {
		ModelAndView mv = new ModelAndView("administrativo/marca/cadastro");
		mv.addObject("marca", marca);
		return mv;
	}

	@GetMapping("/administrativo/marca/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/marca/lista");
		mv.addObject("listaMarca", marcaRepositorio.findAll());
		return mv;
	}

	@GetMapping("/administrativo/marca/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Marca> marca = marcaRepositorio.findById(id);
		return cadastrar(marca.get());
	}

	@GetMapping("/administrativo/marca/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Marca> marca = marcaRepositorio.findById(id);
		marcaRepositorio.delete(marca.get());
		return listar();
	}

	@PostMapping("/administrativo/marca/salvar")
	public ModelAndView salvar(@Valid Marca marca, BindingResult result) {

		if (result.hasErrors()) {
			return cadastrar(marca);
		}
		marcaRepositorio.saveAndFlush(marca);

		return cadastrar(new Marca());
	}

}
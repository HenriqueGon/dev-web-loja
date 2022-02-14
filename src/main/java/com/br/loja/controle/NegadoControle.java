package com.br.loja.controle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NegadoControle {
	@GetMapping("/negado")
	public ModelAndView negadoAdmin() {
		ModelAndView mv = new ModelAndView("/negado");

		return mv;
	}

	@GetMapping("/negadoCliente")
	public ModelAndView negadoCliente() {
		ModelAndView mv = new ModelAndView("/negadoCliente");

		return mv;
	}
}
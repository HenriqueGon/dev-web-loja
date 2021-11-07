package com.br.loja.controle;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.br.loja.modelos.FotosProduto;
import com.br.loja.modelos.Produto;
import com.br.loja.repositorios.CategoriaRepositorio;
import com.br.loja.repositorios.FotosProdutoRepositorio;
import com.br.loja.repositorios.MarcaRepositorio;
import com.br.loja.repositorios.ProdutoRepositorio;

@Controller
public class ProdutoControle {

	private int quantidadeLote = 50000;

	private static String caminhoImagens = "C:\\Users\\Dark Pumpkin\\Documents\\Dev\\Projetos\\dev-web-loja\\imagens";

	@Autowired
	private ProdutoRepositorio produtoRepositorio;

	@Autowired
	private CategoriaRepositorio categoriaRepositorio;

	@Autowired
	private MarcaRepositorio marcaRepositorio;

	@Autowired
	private FotosProdutoRepositorio fotosProdutoRepositorio;

	@GetMapping("/administrativo/produtos/cadastrar")
	public ModelAndView cadastrar(Produto produto) {
		ModelAndView mv = new ModelAndView("administrativo/produtos/cadastro");
		mv.addObject("produto", produto);
		mv.addObject("listaCategoria", categoriaRepositorio.findAll());
		mv.addObject("listaMarca", marcaRepositorio.findAll());
		return mv;
	}

	@GetMapping("/administrativo/produtos/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
		mv.addObject("listaProdutos", produtoRepositorio.findAll());
		mv.addObject("listaCategoria", categoriaRepositorio.findAll());
		mv.addObject("listaMarca", marcaRepositorio.findAll());
		return mv;
	}

	@GetMapping("/administrativo/produtos/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Produto> produto = produtoRepositorio.findById(id);
		return cadastrar(produto.get());
	}

	@GetMapping("/administrativo/produtos/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Produto> produto = produtoRepositorio.findById(id);
		produtoRepositorio.delete(produto.get());
		return listar();
	}

	@PostMapping("/administrativo/produtos/salvar")
	public ModelAndView salvar(@Valid Produto produto, BindingResult result, @RequestParam("files") MultipartFile[] arquivos) {
		if (result.hasErrors()) {
			return cadastrar(produto);
		}

		produtoRepositorio.saveAndFlush(produto);

		try {
			if (arquivos.length > 0) {
				for (MultipartFile arquivo : arquivos) {
					byte[] bytes = arquivo.getBytes();

					String nomeImagem = String.valueOf(produto.getId())+arquivo.getOriginalFilename();

					Path caminho =  Paths.get(caminhoImagens+nomeImagem);
					Files.write(caminho, bytes);

					FotosProduto fotosProduto = new FotosProduto(produto, nomeImagem);

					this.fotosProdutoRepositorio.saveAndFlush(fotosProduto);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return cadastrar(new Produto());
	}

	@GetMapping("/administrativo/produtos/salvar/lote")
	public ModelAndView salvarLote() {

		List<Produto> produtos = new ArrayList<Produto>();

		for (int i = 0; i < this.quantidadeLote; i++) {
			Produto produto = new Produto();
			produto.setDescricao("Inserção Automática " + i);
			produto.setValorVenda(i * 3.0);
			produto.setQuantidadeEstoque(i + 13.0);

			produtos.add(produto);
		}

		this.quantidadeLote += 50000;
		produtoRepositorio.saveAllAndFlush(produtos);

		return cadastrar(new Produto());
	}

	@GetMapping("/administrativo/produtos/mostrarImagem/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
		File imagemArquivo = new File(caminhoImagens + imagem);
		if (imagem != null || imagem.trim().length() > 0) {
			return Files.readAllBytes(imagemArquivo.toPath());
		}
		return null;
	}

	@GetMapping("/administrativo/produtos/buscar")
	public ModelAndView filtrarProdutos(@RequestParam(name = "descricao", required = false) String descricao, @RequestParam(name = "categoria", required = false) Long categoria,
	@RequestParam(name = "marca", required = false) Long marca) {
	
		ModelAndView mv = new ModelAndView("administrativo/produtos/lista");

		if (descricao != null) {
			mv.addObject("listaProdutos", this.produtoRepositorio.findAllByDescricao(descricao));
		} else if (categoria != null) {
			mv.addObject("listaProdutos", this.produtoRepositorio.findAllByCategoria(categoria));
		} else if (marca != null) {
			mv.addObject("listaProdutos", this.produtoRepositorio.findAllByMarca(marca));
		}

		mv.addObject("listaCategoria", categoriaRepositorio.findAll());
		mv.addObject("listaMarca", marcaRepositorio.findAll());

		return mv;
	}

}
package com.br.loja.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.br.loja.modelos.Cliente;
import com.br.loja.modelos.Compra;
import com.br.loja.modelos.ItensCompra;
import com.br.loja.modelos.Produto;
import com.br.loja.repositorios.ClienteRepositorio;
import com.br.loja.repositorios.CompraRepositorio;
import com.br.loja.repositorios.ItensCompraRepositorio;
import com.br.loja.repositorios.ProdutoRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CarrinhoControle {

  @Autowired
  private ClienteRepositorio clienteRepositorio;
  
  @Autowired
  private CompraRepositorio compraRepositorio;

  @Autowired
  private ItensCompraRepositorio itensCompraRepositorio;

  private List<ItensCompra> itensCompra = new ArrayList<ItensCompra>();
  private Compra compra = new Compra();
  private Cliente cliente;

  @Autowired
  private ProdutoRepositorio produtoRepositorio;

  private void calcularTotal() {

    compra.setValorTotal(0.);

    for (ItensCompra e: itensCompra) {
      compra.setValorTotal(compra.getValorTotal() + e.getValorTotal());
    }
  }

  @GetMapping("/carrinho")
  public ModelAndView chamarCarrinho() {
    ModelAndView mv = new ModelAndView("cliente/carrinho");

    calcularTotal();

    mv.addObject("compra", compra);

    mv.addObject("listaItens", itensCompra);
    return mv;
  }

  private void buscarUsuarioLogado() {
    Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();

    if (!(autenticado instanceof AnonymousAuthenticationToken)) {
      String email = autenticado.getName();

      cliente = clienteRepositorio.buscarClienteEmail(email);
    } 
  }

  @GetMapping("/finalizar")
  public ModelAndView finalizarCompra() {
    buscarUsuarioLogado();

    ModelAndView mv = new ModelAndView("cliente/finalizar");

    calcularTotal();

    mv.addObject("compra", compra);

    mv.addObject("listaItens", itensCompra);

    mv.addObject("cliente", cliente);
    return mv;
  }

  @GetMapping("/alterarQuantidade/{id}/{acao}")
  public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {
    for (ItensCompra e : itensCompra) {
      if (e.getProduto().getId().equals(id)) {
        if (acao.equals(1)) {
          e.setQuantidade(e.getQuantidade() + 1);
          e.setValorTotal(0.);
          e.setValorTotal(e.getValorTotal() + (e.getQuantidade() * e.getValorUnitario()));
        } else if (acao.equals(0)) {
          e.setQuantidade(e.getQuantidade() - 1);
          e.setValorTotal(0.);
          e.setValorTotal(e.getValorTotal() + (e.getQuantidade() * e.getValorUnitario()));
        }

        break;
      }
    }

    return "redirect:/carrinho";
  }

  @GetMapping("/removerProduto/{id}")
  public String removerProduto(@PathVariable Long id) {
    for (ItensCompra e : itensCompra) {
      if (e.getProduto().getId().equals(id)) {
        itensCompra.remove(e);

        break;
      }
    }

    return "redirect:/carrinho";
  }

  @GetMapping("/adicionarCarrinho/{id}")
  public String adicionarCarrinho(@PathVariable Long id) {
    Optional<Produto> prod = produtoRepositorio.findById(id);

    Produto p = prod.get();
    ItensCompra item = new ItensCompra();

    int controle = 0;
    for (ItensCompra e : itensCompra) {
      if (e.getProduto().getId().equals(p.getId())) {
        e.setQuantidade(e.getQuantidade() + 1);
        e.setValorTotal(0.);
        e.setValorTotal(e.getValorTotal() + (e.getQuantidade() * e.getValorUnitario()));

        controle = 1;
        break;
      }
    }

    if (controle == 0) {
      item.setProduto(p);
      item.setValorUnitario(p.getValorVenda());
      item.setQuantidade(item.getQuantidade() + 1);
      item.setValorTotal(item.getValorTotal() + (item.getQuantidade() * item.getValorUnitario()));

      itensCompra.add(item);
    }

    return "redirect:/carrinho";
  }

  @PostMapping("/finalizar/confirmar")
  public ModelAndView confirmarCompra(String formaPagamento) {
    ModelAndView mv = new ModelAndView("cliente/mensagemFinalizou");

    this.compra.setCliente(this.cliente);
    this.compra.setFormaPagamento(formaPagamento);
    this.compraRepositorio.saveAndFlush(this.compra);

    for (ItensCompra it : this.itensCompra) {
      it.setCompra(this.compra);
      this.itensCompraRepositorio.saveAndFlush(it);
    }

    this.itensCompra = new ArrayList<ItensCompra>();
    this.compra = new Compra();

    return mv;
  }
}

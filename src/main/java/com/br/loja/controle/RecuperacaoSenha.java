package com.br.loja.controle;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import com.br.loja.modelos.Funcionario;
import com.br.loja.utility.EnviarEmail;
import com.br.loja.utility.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import net.bytebuddy.utility.RandomString;

@Controller
public class RecuperacaoSenha {

  @Autowired
  FuncionarioControle funcionarioControle;

  @Autowired
  EnviarEmail enviarEmail;

  @GetMapping("/recuperar_senha")
  public String recuperarSenhaForm(Model model) {
    return "recuperar_senha";
  } 

  @PostMapping("/recuperar_senha")
  public String processoRecuperarSenha(HttpServletRequest request, Model model) {
    String email = request.getParameter("email");

    String token = RandomString.make(40);

    funcionarioControle.recuperacaoSenha(token, email);
    
    String linkRecuperacaoSenha = Utility.getSiteURL(request) + "/alterar_senha?token=" + token;

    String texto = "<p> Olá, </p>" + "<p> Você solicitou a redefinição da sua senha de acesso. </p>"
        + "<p> Clique no link abaixo para mudar sua senha! </p>" 
        + "<p> <b> <a href=\"" + linkRecuperacaoSenha + "\" > Alterar minha senha </a> </b> </p>"
        + "<p> Ignore esse email caso não tenha sido você que fez a solicitação! </p>";

    try {
      enviarEmail.enviar(email, "Link para recuperar sua senha!", texto);
    } catch (UnsupportedEncodingException | MessagingException e) {
      model.addAttribute("error", "Falha ao enviar o e-mail");

      return "erro";
    }

    return "recuperar_senha";
  }

  @GetMapping("/alterar_senha")
  public String alterarSenhaForm(@Param(value = "token") String token, Model model) {

    Funcionario funcionario = funcionarioControle.getToken(token);

    if (funcionario == null) {
      model.addAttribute("tkInvalido", "Token Inválido");

      return "tkInvalido";
    }

    model.addAttribute("token", token);
    return "alterar_senha";
  }

  @PostMapping("/alterar_senha")
  public String salvarNovaSenha(HttpServletRequest request, Model model) {
    String token = request.getParameter("token");
    String senha = request.getParameter("password");

    Funcionario funcionario = funcionarioControle.getToken(token);

    if (funcionario != null) {
      funcionarioControle.alterarSenha(funcionario, senha);
    }

    return "login";
  }
}

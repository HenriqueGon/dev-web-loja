package com.br.loja.utility;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EnviarEmail {

  @Autowired
  JavaMailSender mailSender;

  public void enviar(String email, String assunto, String texto) throws UnsupportedEncodingException, MessagingException {
    MimeMessage message = mailSender.createMimeMessage();

    MimeMessageHelper helper = new MimeMessageHelper(message);

    helper.setFrom("projetoenvioemail@gmail.com", "Shop Suporte");
    helper.setTo(email);
    helper.setSubject(assunto);
    helper.setText(texto, true);

    mailSender.send(message);
  }

}

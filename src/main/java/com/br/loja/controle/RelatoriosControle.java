package com.br.loja.controle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class RelatoriosControle {

  @Autowired
  private DataSource dataSource;

 @GetMapping("/relatorio/clientes")
  public void listarClientes(HttpServletResponse response) throws JRException, SQLException, IOException {
    InputStream jasperFile = this.getClass().getResourceAsStream("/relatorios/Clientes.jasper");

    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperFile);

    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource.getConnection());

    response.setContentType("application/pdf");
    response.setHeader("Content Disposition", "inline;filename=Relatorio.pdf");

    OutputStream outStream = response.getOutputStream();

    JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
  }

 @GetMapping("/relatorio/produtos")
  public void listarProdutos(HttpServletResponse response) throws JRException, SQLException, IOException {
    InputStream jasperFile = this.getClass().getResourceAsStream("/relatorios/Produtos.jasper");

    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperFile);

    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource.getConnection());

    response.setContentType("application/pdf");
    response.setHeader("Content Disposition", "inline;filename=Relatorio.pdf");

    OutputStream outStream = response.getOutputStream();

    JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
  }

}

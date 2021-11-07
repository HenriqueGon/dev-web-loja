package com.br.loja.utility;

import javax.servlet.http.HttpServletRequest;

public class Utility {

  public static String getSiteURL(HttpServletRequest request) {
    return request.getRequestURL().toString().replace(request.getServletPath(), "");
  }
  
}

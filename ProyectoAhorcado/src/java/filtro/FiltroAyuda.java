/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtro;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FiltroAyuda implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("El FiltroAyuda se instancia");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest peticion=(HttpServletRequest)request;
        HttpServletResponse respuesta = (HttpServletResponse)response;
        HttpSession sesion = peticion.getSession();
        
        String nuevaPalabra = (String) peticion.getParameter("nuevaPalabra");
        
                                                                        //pan                       //electroencefalografista
        if (nuevaPalabra != null && nuevaPalabra.matches("^[A-ZÑ]+") && nuevaPalabra.length() > 2 && nuevaPalabra.length() < 24){
            System.out.println("Entra por el filtro");
            chain.doFilter(request, response);
        }
        else{
            respuesta.sendRedirect("/ProyectoAhorcado/AyudanosError.jsp");
          
        }
    }

    @Override
    public void destroy() {
        System.out.println("El filtroAyuda se destruye");
    }
    
}

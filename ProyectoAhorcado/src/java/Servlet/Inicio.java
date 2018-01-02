/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Objetos.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author rafael
 */
public class Inicio extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String imagen;
    @Override
    public void init() throws ServletException {
        imagen=this.getInitParameter("Fondo");
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        HttpSession sesion = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        
            Cookie [] cookies = request.getCookies();
            for(int i=0; i<cookies.length; i++)
                {
                    Cookie cookieActual = cookies[i];
                    String identificador = cookieActual.getName();
                    String valor = cookieActual.getValue();
                    if(identificador.equals("Cookie")){
                    sesion.setAttribute("miCookie",valor);
                    }   
                }
            sesion.setAttribute("fondo", imagen);
            Usuario misesion = (Usuario)sesion.getAttribute("misesion"); //Cargamos el objeto USUARIO
            String nombreJuego = getServletContext().getInitParameter("NombreJuego");
            sesion.setAttribute("nombreJuego", nombreJuego);
            float porcentaje = misesion.getMedia() * 100; //Pedimos su media (la del usuario)
            sesion.setAttribute("Porcentaje", porcentaje); //Enviamos al template el porcentaje (media de victorias)
            RequestDispatcher paginaImprime = this.getServletContext().getRequestDispatcher("/InicioJuego.jsp"); //Nos vamos al "template"
            paginaImprime.forward(request, response);

      //}
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

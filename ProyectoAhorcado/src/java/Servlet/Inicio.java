/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Usuario usuario = LoginBBDD.objusuario;
        HttpSession sesion = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Inicio Ahorcad</title>");
            out.print("<LINK REL=StyleSheet HREF=\"./css/css.css\" TITLE=\"Contemporaneo\">");
            out.println("</head>");
            out.println("<body id=\"capa\">");
            out.println("<p class=\"titulosPA\">Bien venido " + sesion.getAttribute("usuario") + " al juego del ahorcado</p>");
            out.println("<img id=\"imagen\" src=\"./Imagenes/ahorcado.png\">");
            out.println("<img id=\"imagen2\" src=\"./Imagenes/ahorcado2.png\">");
            out.println("<ul class=\"svertical\">\n"
                    + "<li><a href=\"/ProyectoAhorcado/Ahorcado\">Empezar a jugar</a></li>\n"
                    + "<li><a href=\"/ProyectoAhorcado/Inicio\">Añadir jugador (en obras)</a></li>\n"
                    + "<li><a href=\"/ProyectoAhorcado/ImprimirTablero\">Tablero de campeones</a></li>\n"
                    + "<li><a href=\"/ProyectoAhorcado/Inicio\">¿Como se juega a esto?</a></li>"
                    + "<li><a href=\"/ProyectoAhorcado/CerrarSesion\">Adios!</a></li> </ul>");
            out.println("</body>");
            out.println("</html>");
        }
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

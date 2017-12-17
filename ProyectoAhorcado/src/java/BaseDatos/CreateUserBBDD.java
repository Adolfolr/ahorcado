/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import Objetos.Usuario;
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
public class CreateUserBBDD extends HttpServlet {

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
        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");
        String passwordrep = request.getParameter("passwordrep");
       
        BBDD bbdd = new BBDD();
        if(bbdd.existeUsuario(usuario) && password.equals(passwordrep) && !password.isEmpty() && !usuario.isEmpty()){
            System.out.println("Entro!!!!");
            bbdd.crearUsuario(usuario, password);
//            objusuario = new Usuario(usuario, bbdd.puntuacionUsuario(usuario));
            request.getSession().setAttribute("usuario", usuario);
            request.getSession().setAttribute("puntuacion", bbdd.puntuacionUsuario(usuario));
            HttpSession sesion = request.getSession();
            Usuario misesion = new Usuario(usuario);
            request.getSession().setAttribute("misesion", misesion);
            sesion.setAttribute("registrado", "true");
            bbdd.destroy();
            response.sendRedirect("/ProyectoAhorcado/Inicio");
        }else{
            bbdd.destroy();
            response.sendRedirect("/ProyectoAhorcado/create.jsp?error=error");
        }
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

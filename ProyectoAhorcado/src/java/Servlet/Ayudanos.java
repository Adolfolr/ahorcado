/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import BaseDatos.BBDD;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author rafael
 */
public class Ayudanos extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession sesion = request.getSession();
        String nuevaPalabra = request.getParameter("nuevaPalabra");
        BBDD bbdd = new BBDD();                                                     //pan                    //electroencefalografista
        if (nuevaPalabra != null && nuevaPalabra.matches("^[A-ZÑ]+") && nuevaPalabra.length() > 2 && nuevaPalabra.length() < 24) {
            System.out.println("Palabra válida");
            if (bbdd.existeYaPalabra(nuevaPalabra)) {
                String nombre = (String) sesion.getAttribute("miCookie");
                bbdd.insertarPalabra(nuevaPalabra, nombre);
                bbdd.destroy();
                sesion.setAttribute("MensajePalabra", "Palabra insertada, muchas gracias");
            } else {
                sesion.setAttribute("MensajePalabra", "Palabra ya existe, muchas gracias");
            }
        } else {
            if(nuevaPalabra!=null){
            sesion.setAttribute("MensajePalabra", "La palabra no cumple los requisitos (No espacios, no numeros, no simbolos...)");
            System.out.println("No validaaaaaaaa ------->" + nuevaPalabra);
            }
        }
        RequestDispatcher paginaImprime = this.getServletContext().getRequestDispatcher("/Ayudanos.jsp"); //Nos vamos al "template"
        paginaImprime.forward(request, response);
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

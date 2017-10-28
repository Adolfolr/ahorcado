/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author XKIER
 */
//@WebServlet(name="Juego", urlPatterns={"/Ahorcado"})
public class Juego extends HttpServlet {

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

        String[] numeroDeLetras = "patata".split("");
        String palabra = "patata";
        String letra = request.getParameter("letra");
        int resultado = palabra.indexOf(letra);
        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("contador") != null) {
            sesion.setAttribute("contador", (int) sesion.getAttribute("contador"));
            sesion.setAttribute("listaAciertos", (ArrayList<String>) sesion.getAttribute("listaAciertos"));

            //ArrayList<Integer> list = (ArrayList<Integer>)request.getSession().getAttribute("list");
        } else {
            sesion.setAttribute("contador", 0);
            //ArrayList<String> listaAciertos = new ArrayList<String>();
            ArrayList<String> listaAciertos = new ArrayList<String>();
            sesion.setAttribute("listaAciertos", listaAciertos);
        }

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Juego</title>");
            out.println("</head>");
            out.println("<body>");
            if (resultado != -1) {
                out.print("<p> Adivinaste la letra <p>");
                ArrayList<String> aux = (ArrayList<String>) sesion.getAttribute("listaAciertos");
                aux.add(letra);
                sesion.setAttribute("listaAciertos", aux);
            } else {
                out.print("<p> NO adivinaste la letra <p>");
                sesion.setAttribute("contador", (int) sesion.getAttribute("contador") + 1);
            }
            out.println("<form method=\"post\" action=\"/ProyectoAhorcado/Ahorcado\" name=\"datos\">\n"
                    + "Letra: <input name=\"letra\"><br>\n"
                    + "<button>Enviar</button></form>  <form method=\"post\" action=\"/ProyectoAhorcado/CerrarSesion\" name=\"datos\">\n"
                    + "            <button>Cerrar Sesion</button></form>\n"
                    + "    </body>");
            out.println("<p>" + letra + " e intento " + sesion.getAttribute("contador") + "<p>");
            out.println("<p> Numero de aciertos" + ((ArrayList<String>) sesion.getAttribute("listaAciertos")).size() + "<p>");
            ArrayList<String> aux = (ArrayList<String>) sesion.getAttribute("listaAciertos");
            out.println(aux.size()+"<br>");
            for (int j = 0; j < numeroDeLetras.length; j++) {
                boolean esta = false;
                for (int f = 0; f < aux.size(); f++) {
                    if (numeroDeLetras[j].equals(aux.get(f))) {
                        out.println(numeroDeLetras[j]);
                        esta = true;
                    } 
                }
                if (esta==false) {
                        out.println("_");
                    }
            }
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

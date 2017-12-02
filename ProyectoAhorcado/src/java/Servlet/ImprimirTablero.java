/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rafael
 */
public class ImprimirTablero extends HttpServlet {

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
        BBDD bbdd = new BBDD();
        Map<String, Integer> tab = bbdd.tablero();
        bbdd.destroy();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ImprimirTablero</title>");
            out.print("<LINK REL=StyleSheet HREF=\"./css/tabla.css\" TITLE=\"Contemporaneo\">");
            out.print("<LINK REL=StyleSheet HREF=\"./css/css.css\" TITLE=\"Contemporaneo\">");
            out.println("</head>");
            out.println("<body id=\"capa\">");
            out.println("<div id=\"tabla\">");
            out.println("<table>\n"
                    + "  <tr>\n"
                    + "    <th> Posicion </th>\n"
                    + "    <th> Nombre </th> \n"
                    + "    <th> Puntuacion </th>\n"
                    + "  </tr>\n");
            int i = 1;
            for (Map.Entry entry : sortByValue(tab).entrySet()) {
                out.print("<tr>"
                        +   "<td>"+i+"</td>"
                        +   "<td>"+entry.getKey()+"</td>"
                        +   "<td>"+entry.getValue()+"</td>"
                        +"</tr>");
                i++;
            }
            out.println("</table>");
            out.println("</div>");
            out.println("<ul class=\"svertical\">");
            out.println("<li><a href=\"/ProyectoAhorcado/Inicio\" name=\"letra\" >Volver</a></li></ul> <br>");
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

  //Metodo extraido de https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values-java ordena el Map segun el valor
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
    return map.entrySet()
              .stream()
              .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
              .collect(Collectors.toMap(
                Map.Entry::getKey, 
                Map.Entry::getValue, 
                (e1, e2) -> e1, 
                LinkedHashMap::new
              ));
}
}

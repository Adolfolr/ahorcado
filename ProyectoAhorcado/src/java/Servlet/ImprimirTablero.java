/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import BaseDatos.BBDD;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

        HttpSession sesion = request.getSession();

            response.setContentType("text/html;charset=UTF-8");
            //Cargar tabla de campeones de la base de datos
            BBDD bbdd = new BBDD();
            Map<String, Integer> tab = bbdd.tablero();
            bbdd.destroy();
            
            int i = 1; //Solo para numerar y quede "precioso"
            sesion.setAttribute("Tabla", "");
            //Recorremos los datos de la base de datos y los cargamos ordenado (METODO SACADO DE INTERNET)
            for (Map.Entry entry : sortByValue(tab).entrySet()) {
                String tabla = "<tr><td>" + i + "</td><td>" + entry.getKey() + "</td><td>" + entry.getValue() + "</td></tr>";
                String tablaCompleta = (String) sesion.getAttribute("Tabla") + tabla; 
                sesion.setAttribute("Tabla", tablaCompleta); //Carga la tabla en codigo html
                i++;
            }
            RequestDispatcher paginaImprime = this.getServletContext().getRequestDispatcher("/Tablero.jsp");
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

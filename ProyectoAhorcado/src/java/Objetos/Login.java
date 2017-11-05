/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author y9d1ru
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private String fichero;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        ArrayList<String> cargaFichero = new ArrayList<String>();
        boolean autentificacion = false;
        String puntuacion = "0";
        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");
        
        Map<String, Integer> myMap = new HashMap<String, Integer>();

        ServletContext servletContext = request.getServletContext();
        String nameFile = fichero;
        InputStream resourceAsStream = servletContext.getResourceAsStream(nameFile);

        ServletConfig servletConfig = this.getServletConfig();
//        Integer lineStart = Integer.parseInt(servletConfig.getInitParameter("lineToStart"));

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet login</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>Path of the file:" + nameFile + "</p>");
            out.println("<p>Line to Start:" + 1 + "</p>");
            if (resourceAsStream != null) {
                out.println("<h2>Lines:</h2>");
                InputStreamReader inputStream = new InputStreamReader(resourceAsStream);
                BufferedReader reader = new BufferedReader(inputStream);
                String lineRead;
                Integer counter = 1;
                while ((lineRead = reader.readLine()) != null) {
                    if (counter >= 1) {
                        String[] jugador = lineRead.split(" ");
                        cargaFichero.add(jugador[0]);
                        cargaFichero.add(jugador[1]);
                        cargaFichero.add(jugador[2]);
                    }
                    counter++;
                }
            } else {
                out.println("Error with the file");
            }
            for (int i = 0; i < cargaFichero.size(); i += 3) {
                out.println("<p> jugador: " + cargaFichero.get(i) + " clave: " + cargaFichero.get(i + 1) + " puntos: " + cargaFichero.get(i + 2) + "</p>");
                if (usuario.equals(cargaFichero.get(i)) && password.equals(cargaFichero.get(i + 1))) {
                    autentificacion = true;
                    puntuacion = cargaFichero.get(i+3);
                }
            }
            if(autentificacion){
                request.getSession().setAttribute("usuario", usuario);
                request.getSession().setAttribute("puntuacion", puntuacion);
                response.sendRedirect("/ProyectoAhorcado/index.html");
            }else{
                response.sendRedirect("/ProyectoAhorcado/login.html");
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

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        fichero = getInitParameter("FicheroLogin");
    }

}

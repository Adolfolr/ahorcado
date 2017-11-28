/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sir_D
 */
@WebServlet(name = "BBDD", urlPatterns = {"/BBDD"})
public class BBDD extends HttpServlet {

    private Statement statement = null;
    private Connection connection = null;

    //For this example you need to create the DB Example (root,root) and the table
    //CREATE TABLE PERSONAS (         NOMBRE VARCHAR(100),          EDAD INT); 
    @Override
    public void init() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
//?zeroDateTimeBehavior=convertToNull
        try {
            connection = DriverManager.getConnection("jdbc:derby://localhost:3306/bbddahorcado",
                    "root", "root");
        } catch (SQLException ex) {
            Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Listar Personas</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Lista de las personas:</h1>");
            out.println("<ul>");

            String query = null;
            query = "select *" + "from \"usuarios\"";
            ResultSet resultSet = null;
            try {
                synchronized (statement) {
                    resultSet = statement.executeQuery(query);
                }
                while (resultSet.next()) {
                    out.println("<li>" + resultSet.getString("Nombre")
                            + " pts: " + resultSet.getInt("Puntuacion") + "</li>");
                }
            } catch (SQLException ex) {
                gestionarErrorEnConsultaSQL(ex, request, response);
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE,
                                "No se pudo cerrar el Resulset", ex);
                    }
                }
            }
            out.println("</ul>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
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
        String nombre = request.getParameter("Nombre");
        int edad;
        try {
            edad = Integer.parseInt(request.getParameter("Puntuacion"));
        } catch (NumberFormatException e) {
            edad = -1;
        }
        ServletContext contexto = request.getServletContext();
        String query = null;

        try {
            query = "insert into\"Usuario\" values('"
                    + nombre + "'," + edad + ")";
            synchronized (statement) {
                statement.executeUpdate(query);
            }
            request.setAttribute("nextPage", this.getServletContext().getContextPath() +"/BBDD");
            RequestDispatcher paginaAltas
                    = contexto.getRequestDispatcher("/Ejemplo9/amigoInsertado.jsp");
            paginaAltas.forward(request, response);
        } catch (SQLException ex) {
            gestionarErrorEnConsultaSQL(ex, request, response);
        }

    }

    private void gestionarErrorEnConsultaSQL(SQLException ex, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        ServletContext contexto = request.getServletContext();
        Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, "No se pudo ejecutar la consulta contra la base de datos", ex);
        request.setAttribute("nextPage", this.getServletContext().getContextPath() + "/Ejemplo9/crearPersona.html");
        request.setAttribute("error", ex);       
        request.setAttribute("errorMessage", ex.getMessage());       
        Logger.getLogger(BBDD.class.getName()).log(Level.INFO, "Set "+request.getAttribute("errorMessage"));

        RequestDispatcher paginaError = contexto.getRequestDispatcher("/Ejemplo9/errorSQL.jsp");
        paginaError.forward(request, response);
    }

    @Override
    public void destroy() {
        try {
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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

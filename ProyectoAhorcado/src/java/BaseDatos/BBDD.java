/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author Sir_D
 */
@WebServlet(name = "BBDD", urlPatterns = {"/BBDD"})
public class BBDD extends HttpServlet {


    DataSource datasource;
    Statement statement = null;
    Connection connection = null;
    @Override
    public void init() {
         try {
            InitialContext initialContext = new InitialContext();
            datasource = (DataSource) initialContext.lookup("jdbc/recursoJDBC");
        } catch (NamingException ex) {
            Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public Map<String, Integer> tablero(){
     init();
     Map<String, Integer> tab = new HashMap<String, Integer>();
        try {
            String query = null;
            query = "select Nombre, Puntuacion " + "from usuarios" ;
            ResultSet resultSet = null;
            
            connection = datasource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                  tab.put(resultSet.getString("Nombre"), Integer.parseInt(resultSet.getString("Puntuacion")));
                }
            return tab;
        } catch (SQLException ex) {
            System.out.println("No existe el usuario");
            return tab;
        }
       
 }

   public boolean comprobarUsuario(String nombre, String password){
        init();
        try {
            String query = null;
            query = "select * " + "from usuarios " +"where Nombre like '"+nombre+"' and Password like '"+ password+"'";
            ResultSet resultSet = null;
            
            connection = datasource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                    return true;
                }
            return false;
            
        } catch (SQLException ex) {
            System.out.println("No existe el usuario");
            return false;
        }
       
    }
   public boolean existeUsuario(String nombre){
        init();
        try {
            String query = null;
            query = "select Nombre " + "from usuarios " +"where Nombre like '"+nombre+"'";
            ResultSet resultSet = null;
            
            connection = datasource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                    return false; //exite
                }
            return true;
            
        } catch (SQLException ex) {
            System.out.println("No existe el usuario");
            return false;
        }
       
    }
   public void crearUsuario(String nombre, String password){
        init();
        try {
            String query = null;
            query = "insert into usuarios values(null,'"+ nombre + "','" + password +"', 0, 0, 1 )";
           
            
            connection = datasource.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);
            
        } catch (SQLException ex) {
            System.out.println("Error al crear usuario");
        }
       
    }
    public int puntuacionUsuario(String nombre){
        init();
        try {
            String query = null;
            query = "select Puntuacion " + "from usuarios " +"where Nombre like '"+nombre+"'";
            ResultSet resultSet = null;
            
            connection = datasource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                    return Integer.parseInt(resultSet.getString("Puntuacion"));
                }
            return 1;
            
        } catch (SQLException ex) {
            System.out.println("No existe el usuario");
            return 1;
        }
       
    }
    public String palabra(String usuario){
        init();
        try {
            String query = null;
            query = "select palabra from palabras where idPalabra in (select idPalabra from usuarios where Nombre like '"+usuario+"')";
            ResultSet resultSet = null;
            
            connection = datasource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                    return resultSet.getString("palabra");
                }

            
        } catch (SQLException ex) {
            System.out.println("No existe el usuario");

        }
        destroy();
        return "ERROR";
    }
    public void siguientePalabra(String usuario){
        init();
        try {
            System.out.println("ENTROOOOOO");
            String query = null;
            query = "select idPalabra from palabras where idPalabra in (select idPalabra from usuarios where Nombre like '"+usuario+"')";
            ResultSet resultSet = null;
            
            connection = datasource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            int id= 0;
            while (resultSet.next()) {
                id = Integer.parseInt(resultSet.getString("idPalabra"));
            }
            id++;
            
            query = "update usuarios set idPalabra = "+id+" where Nombre like '"+usuario+"'";
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("No siguiente palabra");
            Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);

        }
        destroy();

    }
     public void actualizarPuntuacion(String usuario, int nuevaPuntuacion){
        init();
        try {
            System.out.println("ENTROOOOOO");
            String query = null;
            connection = datasource.getConnection();
            statement = connection.createStatement();
            query = "update usuarios set Puntuacion = "+nuevaPuntuacion+" where Nombre like '"+usuario+"'";
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("No siguiente palabra");
            Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);

        }
        destroy();

    }
      public float saberMedia(String nombre){
        init();
        try {
            String query = null;
            query = "select * " + "from usuarios " +"where Nombre like '"+nombre+"'";
            ResultSet resultSet = null;
            
            connection = datasource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                    return resultSet.getFloat("Media");
                }
           return 0;
            
        } catch (SQLException ex) {
            System.out.println("No existe el usuario");
            return 0;
        }
       
    }
     public void actualizarMediaParidasGanadas(String usuario, boolean ganado){
        init();
        try {
            int g = 20;
            int p = 20;
            String query = null;
            ResultSet resultSet = null;
            query="select PartidasG, PartidasP from usuarios where Nombre like '"+usuario+"'";
            connection = datasource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
             while (resultSet.next()) {
                 g = resultSet.getInt("PartidasG");
                 p = resultSet.getInt("PartidasP");
                   System.out.println("----------->"+resultSet.getInt("PartidasG")+" per "+p);
                }
            
           if(ganado){
//                int g = resultSet.getInt("PartidasG");
                g++;
                query = "update usuarios set PartidasG = "+g+" where Nombre like '"+usuario+"'";
            }else{
//                int p = resultSet.getInt("PartidasG");
                p++;
                query = "update usuarios set PartidasP = "+p+" where Nombre like '"+usuario+"'";
            }
            statement.executeUpdate(query);
//            query="select PartidasG,PartidasP from usuarios where Nombre like '"+usuario+"'";
//            resultSet = statement.executeQuery(query);
            int numPartidasTotales = g+p;
//            int g = resultSet.getInt("PartidasG");
            float media = (float)g / (float)numPartidasTotales;
            System.out.println("^^^^^^^^^^^^^^^^^^-->"+media + " tot "+numPartidasTotales);
            query = "update usuarios set Media = "+media+" where Nombre like '"+usuario+"'";
            statement.executeUpdate(query);
            destroy();
            
            
        } catch (SQLException ex) {
            System.out.println("No actulizo Media");
            Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);

        }
        destroy();

    }
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
       }

    private void gestionarErrorEnConsultaSQL(SQLException ex, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        ServletContext contexto = request.getServletContext();
        Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, "No se pudo ejecutar la consulta contra la base de datos", ex);
        request.setAttribute("nextPage", this.getServletContext().getContextPath() + "/Ejemplo10/crearPersona.html");
        request.setAttribute("error", ex);
        request.setAttribute("errorMessage", ex.getMessage());
        Logger.getLogger(BBDD.class.getName()).log(Level.INFO, "Set " + request.getAttribute("errorMessage"));

        RequestDispatcher paginaError = contexto.getRequestDispatcher("/Ejemplo10/errorSQL.jsp");
        paginaError.forward(request, response);
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public void destroy(){
        if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
          if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
          
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
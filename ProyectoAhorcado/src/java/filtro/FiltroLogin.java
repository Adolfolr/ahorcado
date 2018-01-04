/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtro;

import BaseDatos.BBDD;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author Clara
 */
public class FiltroLogin implements Filter{
    DataSource datasource;
    Statement statement = null;
    Connection connection = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("El filtro se instancia");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       HttpServletRequest sesion = (HttpServletRequest)request;
       HttpServletResponse mePiro = (HttpServletResponse)response;
       
       if((String)sesion.getSession().getAttribute("registrado")!="true"){
            mePiro.sendRedirect("/ProyectoAhorcado/loginError.jsp");
       }
       
       else{
           chain.doFilter(request, response);
       }
    }

    @Override
    public void destroy() {
        System.out.println("El filtro se destruye");
    }
    public void inicializar() {
        try {
            InitialContext initialContext = new InitialContext();
            datasource = (DataSource) initialContext.lookup("jdbc/recursoJDBC");
        } catch (NamingException ex) {
            Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean comprobarUsuario(String nombre, String password) {
        inicializar();
        try {
            String query = null;
            query = "select * " + "from usuarios " + "where Nombre like '" + nombre + "' and Password like '" + password + "'";
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
            Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

    }
    
}

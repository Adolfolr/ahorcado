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
    ResultSet resultSet = null;

    @Override
    public void init() {
        try {
            InitialContext initialContext = new InitialContext();
            datasource = (DataSource) initialContext.lookup("jdbc/recursoJDBC");
        } catch (NamingException ex) {
            Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Busqueda de la puntuación para el tablero (lo envia SIN ordenar)
    public Map<String, Integer> tablero() {
        init();
        Map<String, Integer> tab = new HashMap<String, Integer>();
        try {
            String query = null;
            query = "select Nombre, Puntuacion " + "from usuarios";
            
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
        }finally {
           destroy();
    }

    }
//Se usa para iniciar sesion, saber si la password y nombre son correctos

    public boolean comprobarUsuario(String nombre, String password) {
        init();
        try {
            String query = null;
            query = "select * " + "from usuarios " + "where Nombre like '" + nombre + "' and Password like '" + password + "'";
            

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

        }finally {
        destroy();
    }

    }
    //Saber si el "Nombre" ya esta en uso, no puede haber dos jugadores con el mismo nombre

    public boolean existeUsuario(String nombre) {
        init();
        try {
            String query = null;
            query = "select Nombre " + "from usuarios " + "where Nombre like '" + nombre + "'";
            

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
        }finally {
        destroy();
    }

    }
    //Crear usuario...

    public void crearUsuario(String nombre, String password) {
        init();
        try {
            String query = null;
            query = "insert into usuarios values(null,'" + nombre + "','" + password + "',0, 0, 0, 0, 1,'','')";

            connection = datasource.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException ex) {
            Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al crear usuario");
        }finally {
        destroy();
    }
        

    }
    //Saber puntuacion usuario

    public int puntuacionUsuario(String nombre) {
        init();
        try {
            String query = null;
            query = "select Puntuacion " + "from usuarios " + "where Nombre like '" + nombre + "'";
            

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
        }finally {
        destroy();
    }

    }

    //Saber que palabra le toca al usuario
    public String palabra(String usuario) {
        init();
        try {
            String query = null;
            query = "select palabra from palabras where idPalabra in (select idPalabra from usuarios where Nombre like '" + usuario + "')";
            

            connection = datasource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                return resultSet.getString("palabra");
            }

        } catch (SQLException ex) {
            System.out.println("No existe el usuario");

        }finally {
        destroy();
    }
        return "ERROR";
    }

    //Al ganar o perder se pasa a la siguiente palabra
    public void siguientePalabra(String usuario) {
        init();
        try {
            //System.out.println("ENTROOOOOO");
            String query = null;
            query = "select idPalabra from palabras where idPalabra in (select idPalabra from usuarios where Nombre like '" + usuario + "')";
            

            connection = datasource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            int id = 0;
            while (resultSet.next()) {
                id = Integer.parseInt(resultSet.getString("idPalabra"));
            }
            id++;

            query = "update usuarios set idPalabra = " + id + " where Nombre like '" + usuario + "'";
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("No siguiente palabra");
            Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);

        }finally {
        destroy();
    }
    }

    //Al finalizar se inserta la nueva puntuacion
    public void actualizarPuntuacion(String usuario, int nuevaPuntuacion) {
        init();
        try {
            System.out.println("ENTROOOOOO");
            String query = null;
            connection = datasource.getConnection();
            statement = connection.createStatement();
            query = "update usuarios set Puntuacion = " + nuevaPuntuacion + " where Nombre like '" + usuario + "'";
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("No siguiente palabra");
            Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);

        }finally {
        destroy();
    }
    }
    //Saber la media de partidas ganadas

    public float saberMedia(String nombre) {
        init();
        try {
            String query = null;
            query = "select * " + "from usuarios " + "where Nombre like '" + nombre + "'";
            

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
        }finally {
        destroy();
    }

    }
    //Actualizamos la media

    public void actualizarMediaParidasGanadas(String usuario, boolean ganado) {
        init();
        try {
            int g = 20;
            int p = 20;
            String query = null;
            
            query = "select PartidasG, PartidasP from usuarios where Nombre like '" + usuario + "'";
            connection = datasource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                g = resultSet.getInt("PartidasG");
                p = resultSet.getInt("PartidasP");
                //     System.out.println("----------->"+resultSet.getInt("PartidasG")+" per "+p);
            }

            if (ganado) {
//                int g = resultSet.getInt("PartidasG");
                g++;
                query = "update usuarios set PartidasG = " + g + " where Nombre like '" + usuario + "'";
            } else {
//                int p = resultSet.getInt("PartidasG");
                p++;
                query = "update usuarios set PartidasP = " + p + " where Nombre like '" + usuario + "'";
            }
            statement.executeUpdate(query);
//            query="select PartidasG,PartidasP from usuarios where Nombre like '"+usuario+"'";
//            resultSet = statement.executeQuery(query);
            int numPartidasTotales = g + p;
//            int g = resultSet.getInt("PartidasG");
            float media = (float) g / (float) numPartidasTotales;
            //System.out.println("^^^^^^^^^^^^^^^^^^-->"+media + " tot "+numPartidasTotales);
            query = "update usuarios set Media = " + media + " where Nombre like '" + usuario + "'";
            statement.executeUpdate(query);
            

        } catch (SQLException ex) {
            System.out.println("No actulizo Media");
            Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);

        }finally {
        destroy();
    }
    }
     public boolean existeYaPalabra(String nuevaPalabra) {
         init();
        try {
            String query = null;
            query = "select palabra " + "from palabras " + "where palabra like '" + nuevaPalabra + "'";
            

            connection = datasource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                return false; //exite
            }
            return true;

        } catch (SQLException ex) {
            System.out.println("Error al buscar palabra");
            return false;
        }finally {
        destroy();
    }
    }
     public void insertarPalabra(String nuevaPalabra,String usuario) {
         init();
        try {
            String query = null;
            query = "insert into palabras values(null,'" + nuevaPalabra + "','" + usuario +"')";

            connection = datasource.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException ex) {
            Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al crear palabra");
        }finally {
        destroy();
    }
     }

    public void guardarPartida(String usuario, String letra, boolean listaAciertos) {
        init();
        try {
            
            System.out.println("Nombre--->"+usuario);
            System.out.println("Letra--->"+letra);
            System.out.println("Acierto--->"+listaAciertos);

            String query = null;
            connection = datasource.getConnection();
            statement = connection.createStatement();
            String lista = null;
            if(listaAciertos){
            lista = saberListaAciertos(usuario)+letra;
            query = "update usuarios set ListaAciertos = '" + lista + "' where Nombre like '" + usuario + "'";
            }else{
            System.err.println("LETRA FALLIDA");
            lista = saberListaFallos(usuario)+letra;
            query = "update usuarios set ListaFallos = '" + lista + "' where Nombre like '" + usuario + "'";
            }
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);

        }finally {
        destroy();
    }
    }
    public String saberListaAciertos(String usuario){
        init();
        try {
            String query = null;
            query = "select * " + "from usuarios " + "where Nombre like '" + usuario + "'";
            

            connection = datasource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                return resultSet.getString("ListaAciertos");
            }
            return null;

        } catch (SQLException ex) {
            return null;
        }
    }
     public String saberListaFallos(String usuario){
        init();
        try {
            String query = null;
            query = "select * " + "from usuarios " + "where Nombre like '" + usuario + "'";
            

            connection = datasource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                return resultSet.getString("ListaFallos");
            }
            return null;

        } catch (SQLException ex) {
            return null;
        }
     }
     public void borrarLista(String usuario){
          init();
        try {
            String query = null;
            connection = datasource.getConnection();
            statement = connection.createStatement();
            String lista = null;
            query = "update usuarios set ListaAciertos = '', ListaFallos = '' where Nombre like '"+usuario+"'";
            
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);

        }finally {
        destroy();
    }
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public void destroy() {
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
         if (resultSet != null) {
            try {
                resultSet.close();
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

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
  //Palabra que hay que adivinar
 String palabra;
 int numeroLetrasPintadas=0; 
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
        
        //Palabra que hay que adivinar
        palabra = "rafa";
        
        //Dividimos la palabra en letras para comprobar con otra lista de acertados y saber su posicion
        String[] posicionLetra = palabra.split("");
        
        //Recogemos la letra que nos envian
        String letra = request.getParameter("letra");
        
        //Saber si la letra esta en la palabra. Si no esta debolvera un -1
        int resultado = palabra.indexOf(letra);

        //Recogemos la sesion
        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("intentosFallidos") != null) { //Si la sesion existe entonces NO hacemos nada
            //sesion.setAttribute("intentosFallidos", (int) sesion.getAttribute("intentosFallidos"));
            //sesion.setAttribute("listaAciertos", (ArrayList<String>) sesion.getAttribute("listaAciertos"));
            //ArrayList<Integer> list = (ArrayList<Integer>)request.getSession().getAttribute("list");
        } else {//Si NO existe creamos dos nuevos Atributos (Intentos Fallidos: Numero de intentosFallidos; ListaAciertos: guardamos en una lista las palabras acertadas) 
            sesion.setAttribute("intentosFallidos", 0);
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
            
            
            if (resultado != -1) { //Si acertamos guardamos la letra acertada
                out.print("<p style=\"color:green;\"> Adivinaste la letra <p>");
                ArrayList<String> aux = (ArrayList<String>) sesion.getAttribute("listaAciertos");
                aux.add(letra);
                sesion.setAttribute("listaAciertos", aux);
            } else { //Si fallamos aumenta en 1 en numero de intendos
                out.print("<p style=\"color:red;\"> NO adivinaste la letra <p>");
                sesion.setAttribute("intentosFallidos", (int) sesion.getAttribute("intentosFallidos") + 1);
            }
            
            //Formulario para enviar otra letra
            out.println("<form method=\"post\" action=\"/ProyectoAhorcado/Ahorcado\" name=\"datos\">\n"
                    + "Letra: <input name=\"letra\"><br>\n"
                    + "<button>Enviar</button></form>  <form method=\"post\" action=\"/ProyectoAhorcado/CerrarSesion\" name=\"datos\">\n"
                    + "            <button>Cerrar Sesion</button></form>\n"
                    + "    </body>");
            out.println("<p>" + letra + " e intento " + sesion.getAttribute("intentosFallidos") + "<p>");
            out.println("<p> Numero de aciertos" + ((ArrayList<String>) sesion.getAttribute("listaAciertos")).size() + "<p>");
            
            //"Pintar" letras acertadas
            ArrayList<String> aux = (ArrayList<String>) sesion.getAttribute("listaAciertos"); //Cargamos la lista de aciertos
            
            //Primer bucle para ir comparando letra a letra. 
            //Ejemlpo nuestra palabra es "patata" coge la primera letra "p" y la compara con la lista de aciertos, 
            //si la letra "p" esta dentro de la lista dibuja "p", si no esta, entoces dibuja " _ "
            for (int j = 0; j < posicionLetra.length; j++) { 
                boolean esta = false; //Si la letra esta en la lista TRUE, sino FALSE
                for (int f = 0; f < aux.size(); f++) {
                    if (posicionLetra[j].equals(aux.get(f))) { //Ejemplo "p" igual a ¿"t"? no, y ¿"a"? no y ¿"p"? SI dibujamos "p"  
                        out.println(posicionLetra[j]);
                        numeroLetrasPintadas ++;
                        esta = true;
                    } 
                }
                if (esta==false) {
                        out.println("_");
                    }
            }
            out.println("<br>"+numeroLetrasPintadas + "<br>");
            out.println(palabra.length()+"<br>");
            if (ganarpartida( (int)sesion.getAttribute("intentosFallidos"))){
               out.println("Has ganado"); 
            }
            if (perderpartida( (int)sesion.getAttribute("intentosFallidos"))){
               out.println("Has perdido"); 
            }
            
            out.println("</body>");
            out.println("</html>");
        }
        numeroLetrasPintadas = 0;
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
    public boolean ganarpartida(int fallos){
        if (numeroLetrasPintadas==palabra.length()&& fallos<6 ){
            return true;
        }
        return false;
    }
    public boolean perderpartida(int fallos){
        if(numeroLetrasPintadas!=palabra.length() && fallos == 6){
            return true;
        }
        return false;
    }
   
    public float calcularPuntuacion(int numeroFallos)
    {
        return 1;
    }

}

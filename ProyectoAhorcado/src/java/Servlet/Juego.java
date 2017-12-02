/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
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
    String color;
    int numeroLetrasPintadas = 0;
    float puntuacion;

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
        palabra = "MANTECA";
        String[] botones = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        //Dividimos la palabra en letras para comprobar con otra lista de acertados y saber su posicion
        String[] posicionLetra = palabra.split("");

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        //Recogemos la letra que nos envian
        String letra = request.getParameter("letra");
        boolean finPartida = false;
        boolean victoria = false;
        //Saber si la letra esta en la palabra. Si no esta debolvera un -1
        int resultado = -1;
        boolean seguimos = false;
        //Si se ha enviado alguna LETRA
        if (letra != null) {
            resultado = palabra.indexOf(letra);
            seguimos = true;
            System.out.println(letra);
        }
        String respuesta = request.getParameter("respuesta");
        HttpSession sesion = request.getSession();
        if(respuesta!=null){
            if(comprobarRespuesta(respuesta)){
                finPartida = true;
                victoria = true;
            }else{
                sesion.setAttribute("intentosFallidos", (int) sesion.getAttribute("intentosFallidos") + 1);
            }
        }
        //Recogemos la sesion
        
        if (sesion.getAttribute("intentosFallidos") != null) { //Si la sesion existe entonces NO hacemos nada
                 } else {//Si NO existe creamos dos nuevos Atributos (Intentos Fallidos: Numero de intentosFallidos; ListaAciertos: guardamos en una lista las palabras acertadas) 
            sesion.setAttribute("intentosFallidos", 0);
            //ArrayList<String> listaAciertos = new ArrayList<String>();
            ArrayList<String> listaAciertos = new ArrayList<String>();
            sesion.setAttribute("listaAciertos", listaAciertos);

            ArrayList<String> listaFallos = new ArrayList<String>();
            sesion.setAttribute("listaFallos", listaFallos);
        }

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Juego</title>");
            out.print("<LINK REL=StyleSheet HREF=\"./css/juego.css\" TITLE=\"Contemporaneo\">");
            out.print("<LINK REL=StyleSheet HREF=\"./css/tabla.css\" TITLE=\"Contemporaneo\">");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
            out.println("</head>");
            out.println("<body id=\"capa\">");
            out.println("<p class=\"titulosPA\"><b> Hola " + sesion.getAttribute("usuario") + " tu mejor puntuacion es de " + sesion.getAttribute("puntuacion") + " </b></p>");

            if (resultado != -1 && seguimos && noRepetirLetra(letra, (ArrayList<String>) sesion.getAttribute("listaAciertos")) ) { //Si acertamos guardamos la letra acertada
                out.print("<p style=\"color:green;\"> Adivinaste la letra <p>");
                ArrayList<String> aux = (ArrayList<String>) sesion.getAttribute("listaAciertos");
                aux.add(letra);
                sesion.setAttribute("listaAciertos", aux);
            } else if (seguimos &&  noRepetirLetra(letra, (ArrayList<String>) sesion.getAttribute("listaFallos")) && noRepetirLetra(letra, (ArrayList<String>) sesion.getAttribute("listaAciertos"))) { //Si fallamos aumenta en 1 en numero de intendos
                out.print("<p style=\"color:red;\"> NO adivinaste la letra <p>");
                sesion.setAttribute("intentosFallidos", (int) sesion.getAttribute("intentosFallidos") + 1);
                ArrayList<String> aux = (ArrayList<String>) sesion.getAttribute("listaFallos");
                aux.add(letra);
                sesion.setAttribute("listaFallos", aux);
            }else if(seguimos){
                out.println("<h2>Acción no valida!</h2>");
            }

            out.println("<p class=\"parrafo\">Vidas restantes: " + (6 - (int) sesion.getAttribute("intentosFallidos"))+"</p>");
            out.println("<br>");
          //"Pintar" letras acertadas
            ArrayList<String> aux = (ArrayList<String>) sesion.getAttribute("listaAciertos"); //Cargamos la lista de aciertos

            //Primer bucle para ir comparando letra a letra. 
            //Ejemlpo nuestra palabra es "patata" coge la primera letra "p" y la compara con la lista de aciertos, 
            //si la letra "p" esta dentro de la lista dibuja "p", si no esta, entoces dibuja " _ "
            
            out.println("<p id=\"palabra\">");
            for (int j = 0; j < posicionLetra.length; j++) {
                boolean esta = false; //Si la letra esta en la lista TRUE, sino FALSE
                for (int f = 0; f < aux.size(); f++) {
                    if (posicionLetra[j].equals(aux.get(f))) { //Ejemplo "p" igual a ¿"t"? no, y ¿"a"? no y ¿"p"? SI dibujamos "p"  
                        out.println(posicionLetra[j]);
                        numeroLetrasPintadas++;
                        esta = true;
                    }
                }
                if (esta == false) {
                    out.println("_");
                }
            }
            out.println("</p>");
         
            if (ganarpartida((int) sesion.getAttribute("intentosFallidos")) || victoria) {
                out.println("<br>");
                out.println("<h1 style=\"color:green;\">Has ganado</h1><br>");
                calcularPuntuacion((int) sesion.getAttribute("intentosFallidos"));
                int numero = (int)sesion.getAttribute("puntuacion");
                float f = (float)numero;
                float total = puntuacion + f;
                out.println("Tu puntuacion de esta partida es: " + puntuacion +" total puntuación: "+total);
                finPartida = true;
                out.println("<br>");
            }
            if (perderpartida((int) sesion.getAttribute("intentosFallidos"))) {
                out.println("<br>");
                out.println("<h1 style=\"color:red;\">Has perdido </h1>" + "<br>");
//                calcularPuntuacion((int) sesion.getAttribute("intentosFallidos"));
                out.println("Tu puntuacion de esta partida es: " + puntuacion + " total puntuación: "+sesion.getAttribute("puntuacion"));
                finPartida = true;
                out.println("<br>");
            }
            out.println("<br>");
            //Pintar botones
            out.println("<table>");
            for (int b = 0; b < botones.length; b++) {
                if(b%7==0){
                    out.print("<tr>");
                }
                if (bloquearBoton((ArrayList<String>) sesion.getAttribute("listaAciertos"), (ArrayList<String>) sesion.getAttribute("listaFallos"), botones[b]) || finPartida) {
                    if (finPartida) {
                        color = "orange";
                    }
                    out.print("<td><form style=\"display:inline;  method=\"post\" action=\"/ProyectoAhorcado/Ahorcado\" name=\"datos\"> <input type=\"hidden\" value=\"" + botones[b] + "\" name=\"letra\"><button disabled  class=\"button1\" style=\"background:" + color + "\">" + botones[b] + "</button></form></td>");
                } else {
                    out.print("<td><form style=\"display:inline;  method=\"post\" action=\"/ProyectoAhorcado/Ahorcado\" name=\"datos\"> <input type=\"hidden\" value=\"" + botones[b] + "\" name=\"letra\"><button  class=\"button2\">" + botones[b] + "</button></form></td>");
                }
                if(b-6%7==0){
                    out.print("</tr>");
                }
            }
            out.println("</table>");
            out.println("<br>");
            out.println("<form method=\"post\" action=\"/ProyectoAhorcado/Ahorcado\" name = \"usuario\">\n" +
"                ME LA SE: <input type=\"text\" id=\"fname\" name=\"respuesta\" style=\"text-transform:uppercase;\" onkeyup=\"javascript:this.value=this.value.toUpperCase();\"><br><br>"
                    + "<input type=\"submit\" value=\"Enviar\">\n" +
"            </form>");
            // out.println("<br>"+ numeroLetrasPintadas + "<br>");
            // out.println(palabra.length()+"<br>");
            out.println("<ul class=\"svertical\">");
            out.println("<li><a href=\"/ProyectoAhorcado/Inicio\" name=\"letra\" >Guardar partida</a></li>");
            out.println("<li><a href=\"/ProyectoAhorcado/Inicio\" name=\"letra\" >Volver</a></li>");
            out.println("</ul>");
            
            out.println("<br><br><form method=\"post\" action=\"/ProyectoAhorcado/CerrarSesion\" name=\"datos\"> <button >Cerrar Sesion</button></form>\n");
            out.println("</body>");
            out.println("</html>");
        }
        numeroLetrasPintadas = 0;
//        if (finPartida) {
//        ServletContext servletContext = request.getServletContext();
//
//        }

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

    public boolean ganarpartida(int fallos) {
        if (numeroLetrasPintadas == palabra.length() && fallos < 6) {
            return true;
        }
        return false;
    }

    public boolean perderpartida(int fallos) {
        if (numeroLetrasPintadas != palabra.length() && fallos == 6) {
            return true;
        }
        return false;
    }

    public float calcularPuntuacion(int numeroFallos) {
        //Lo unico que hace es que la puntuacion es el numero de fallos de esa partida, 
        //queda hacer lo de los jugadores y hacer la media de cada jugador
        if (ganarpartida(numeroFallos) || perderpartida(numeroFallos)) {
            puntuacion = 6 - numeroFallos;
        } else {
            puntuacion = 0;
        }
        return puntuacion;
    }

    public boolean bloquearBoton(ArrayList<String> aciertos, ArrayList<String> fallos, String nuevaLetra) {
        for (int l = 0; l < aciertos.size(); l++) {
            if (aciertos.get(l).equals(nuevaLetra)) {
                color = "green";
                return true;
            }
        }
        for (int l = 0; l < fallos.size(); l++) {
            if (fallos.get(l).equals(nuevaLetra)) {
                color = "red";
                return true;
            }
        }
        return false;
    }
    public boolean noRepetirLetra(String letra, ArrayList<String> a){
        for(int i = 0; i<a.size();i++){
            if(a.get(i).equals(letra)){
                return false;
            }
        }
        return true;
    }

public boolean comprobarRespuesta(String resp){
    if(resp.equals(palabra))return true;
    return false;
   
}
}

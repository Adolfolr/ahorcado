/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Partida;

import BaseDatos.BBDD;
import Objetos.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
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

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
          
        HttpSession sesion = request.getSession();
        //Comprobar que se ha iniciado sesion
        if ((String) sesion.getAttribute("registrado") != "true") {
            response.sendRedirect("/ProyectoAhorcado/login.jsp"); //Si no se ha iniciado sesion lo "echamos"
        } else {//Usuario verificado
            
            Usuario misesion = (Usuario) sesion.getAttribute("misesion"); //Cargamos el objeto del usuario
            BBDD bbdd = new BBDD();
            palabra = misesion.getPalabra(); //Carga la palabra que le corresponde al usuario por idPalabra

            String[] posicionLetra = palabra.split(""); //Dividimos la palabra en letras
            String letra = request.getParameter("letra"); //Recogemos la letra
            
            boolean finPartida = false; //FINAL DE PARTIDA diciendo letras
            boolean victoria = false; //HEMOS GANADO INTRODICIENDO LA PALABRA DIRECTAMENTE

            
            int resultado = -1; //Saber si la letra esta en la palabra. Si NO esta debolvera un -1
            boolean seguimos = false; //Si es falso es que hemos hecho alguna accion incorrecta

            if (letra != null) { //Si nos han mandado algo
                resultado = palabra.indexOf(letra); //comprobar si la letra esta en la palabra
                seguimos = true;
            }
            //Recogemos una posible respuesta en la caja de texto
            String respuesta = request.getParameter("respuesta");

            if (respuesta != null) {//Si nos ha llegado alguna respuesta
                if (comprobarRespuesta(respuesta)) { //comprobamos si es la correcta, si lo es finalizamos partida y victoria directa
                    
                //    misesion.setPuntuacion((6 - (int) sesion.getAttribute("intentosFallidos"))+10);
                    finPartida = true;
                    victoria = true;
              
                } else {//Si falla restamos una vida
                    sesion.setAttribute("intentosFallidos", (int) sesion.getAttribute("intentosFallidos") + 1);
                }
            }
            if (sesion.getAttribute("intentosFallidos") != null) { //Si la sesion existe entonces cargamos otra vez botones, la palabra, Fin...
                                                                   //sino se hiciera esto se repetirian por ejemplo los botones
                sesion.setAttribute("ListaBotones", "");
                sesion.setAttribute("Palabra", "");
                sesion.setAttribute("Fin", false);
                sesion.setAttribute("MensajeFinal", "");
                sesion.setAttribute("MensajeFinalColor", "");
                sesion.setAttribute("Bloquear", "");
                sesion.setAttribute("Siguiente", "");

            } else {//Si NO existe creamos dos nuevos Atributos (Intentos Fallidos: Numero de intentosFallidos; ListaAciertos: guardamos en una lista las palabras acertadas...) 
                sesion.setAttribute("intentosFallidos", 0);
                ArrayList<String> listaAciertos = new ArrayList<String>();
                sesion.setAttribute("listaAciertos", listaAciertos);

                ArrayList<String> listaFallos = new ArrayList<String>();
                sesion.setAttribute("listaFallos", listaFallos);
                sesion.setAttribute("Palabra", "");
                sesion.setAttribute("ListaBotones", "");
                sesion.setAttribute("Fin", false);
                sesion.setAttribute("MensajeFinal", "");
                sesion.setAttribute("MensajeFinalColor", "");
                sesion.setAttribute("Siguiente", "");
                sesion.setAttribute("Bloquear", "");
            }
            
            sesion.setAttribute("Nombre", misesion);//Para "enviar el Nombre al template"
          
            if (resultado != -1 && seguimos && noRepetirLetra(letra, (ArrayList<String>) sesion.getAttribute("listaAciertos"))) { //Si acertamos guardamos la letra acertada
                ArrayList<String> aux = (ArrayList<String>) sesion.getAttribute("listaAciertos");
                aux.add(letra);
                sesion.setAttribute("listaAciertos", aux);
                
                //Si fallamos aumenta en 1 en numero de intendos
            } else if (seguimos && noRepetirLetra(letra, (ArrayList<String>) sesion.getAttribute("listaFallos")) && noRepetirLetra(letra, (ArrayList<String>) sesion.getAttribute("listaAciertos"))) { 
                sesion.setAttribute("intentosFallidos", (int) sesion.getAttribute("intentosFallidos") + 1);
                ArrayList<String> aux = (ArrayList<String>) sesion.getAttribute("listaFallos");
                aux.add(letra);
                sesion.setAttribute("listaFallos", aux);
            } else if (seguimos) {
                //Acción no valida! ejemplo cargar la pagina F5
            }
            
            sesion.setAttribute("Vidas", (6 - (int) sesion.getAttribute("intentosFallidos")));//Para pintar las vidas
            ArrayList<String> aux = (ArrayList<String>) sesion.getAttribute("listaAciertos"); //Cargamos la lista de aciertos
            String pintarPalabra = (String) sesion.getAttribute("Palabra");
            for (int j = 0; j < posicionLetra.length; j++) {
                boolean esta = false; //Si la letra está en la lista TRUE, sino FALSE
                for (int f = 0; f < aux.size(); f++) {
                    if (posicionLetra[j].equals(aux.get(f))) { //Ejemplo "p" igual a ¿"t"? no, y ¿"a"? no y ¿"p"? SI dibujamos "p"  
                        pintarPalabra = pintarPalabra + " " + posicionLetra[j];
                        numeroLetrasPintadas++;
                        esta = true;
                    }
                }
                if (esta == false) {
                    pintarPalabra = pintarPalabra + " _ ";
                }
            }
            sesion.setAttribute("Palabra", pintarPalabra);
           // System.out.println("VICTORIA??------------>" + ganarpartida((int) sesion.getAttribute("intentosFallidos")));
           
           //SI SE ADIVINA LA PALABRA
            if (ganarpartida((int) sesion.getAttribute("intentosFallidos")) || victoria) {
                sesion.setAttribute("Fin", true); //Para indicar que se puede escribir
                sesion.setAttribute("MensajeFinal", "Has ganado la palabra era: " + palabra); //MENSAJE DE VICTORIA
                sesion.setAttribute("MensajeFinalColor", "green"); //Pintarlo en verde
                sesion.setAttribute("Siguiente", "<a class=\"link\" href='/ProyectoAhorcado/Ahorcado'>Siguiente palabra</a>");//Activa un boton con el mensaje "Siguiete palabra"
                sesion.setAttribute("Bloquear", "disabled"); //Bloque boton de enviar
                //---------INSERTAR LA NUEVA PUNTUCION
                calcularPuntuacion((int) sesion.getAttribute("intentosFallidos"));
                int numero = misesion.getPuntuacion();
                float f = (float) numero;
                float total = puntuacion + f;
                misesion.setPuntuacion((6 - (int) sesion.getAttribute("intentosFallidos")));
                misesion.setMedia(true);
                finPartida = true;
                //RESETEAMOS VALORES E INICIAMOS CON LA NUEVA PALABRA
                bbdd.siguientePalabra(misesion.getNombre());
                sesion.setAttribute("intentosFallidos", 0);
                ArrayList<String> listaAciertos = new ArrayList<String>();
                sesion.setAttribute("listaAciertos", listaAciertos);

                ArrayList<String> listaFallos = new ArrayList<String>();
                sesion.setAttribute("listaFallos", listaFallos);
                //Imprimir puntuación
            }
           //PERDEMOS
            if (perderpartida((int) sesion.getAttribute("intentosFallidos"))) {
                sesion.setAttribute("Fin", true);
                sesion.setAttribute("MensajeFinal", "Has perdido la palabra era: " + palabra);
                sesion.setAttribute("MensajeFinalColor", "red");
               sesion.setAttribute("Siguiente", "<a class=\"link\" href='/ProyectoAhorcado/Ahorcado'>Siguiente palabra</a>");//Activa un boton con el mensaje "Siguiete palabra"
               sesion.setAttribute("Bloquear", "disabled"); //Bloquea el boton de enviar
               misesion.setMedia(false);
//                calcularPuntuacion((int) sesion.getAttribute("intentosFallidos"));
                finPartida = true;
                bbdd.siguientePalabra(misesion.getNombre());
                sesion.setAttribute("intentosFallidos", 0);
                //ArrayList<String> listaAciertos = new ArrayList<String>();
                ArrayList<String> listaAciertos = new ArrayList<String>();
                sesion.setAttribute("listaAciertos", listaAciertos);

                ArrayList<String> listaFallos = new ArrayList<String>();
                sesion.setAttribute("listaFallos", listaFallos);
            }

            //PINTAR BOTONES COMO UNA TABLA
            String[] botones = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
            String listaBotones = (String) sesion.getAttribute("ListaBotones");
            for (int b = 0; b < botones.length; b++) {
                if (b % 7 == 0) {
                    listaBotones = listaBotones + "<tr>";
                    sesion.setAttribute("ListaBotones", listaBotones);
                }
                if (bloquearBoton((ArrayList<String>) sesion.getAttribute("listaAciertos"), (ArrayList<String>) sesion.getAttribute("listaFallos"), botones[b]) || finPartida) {
                    if (finPartida) {
                        color = "orange";
                    }
                    listaBotones = listaBotones + "<td><form style=\"display:inline;\"  method=\"post\" action=\"/ProyectoAhorcado/Ahorcado\" name=\"datos\"> <input type=\"hidden\" value=\"" + botones[b] + "\" name=\"letra\"><button disabled  class=\"button1\" style=\"background:" + color + "\">" + botones[b] + "</button></form></td>";
                    sesion.setAttribute("ListaBotones", listaBotones);
                } else {
                    listaBotones = listaBotones + "<td><form style=\"display:inline;\"  method=\"post\" action=\"/ProyectoAhorcado/Ahorcado\" name=\"datos\"> <input type=\"hidden\" value=\"" + botones[b] + "\" name=\"letra\"><button  class=\"button2\">" + botones[b] + "</button></form></td>";
                    sesion.setAttribute("ListaBotones", listaBotones);
                }
                if (b - 6 % 7 == 0) {
                    listaBotones = listaBotones + "</tr>";
                    sesion.setAttribute("ListaBotones", listaBotones);
                }
            }
            //   System.out.println(listaBotones);
            numeroLetrasPintadas = 0;
            bbdd.destroy();
            RequestDispatcher paginaImprime = this.getServletContext().getRequestDispatcher("/JuegoAhorcado.jsp");
            paginaImprime.forward(request, response);

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

    public void calcularPuntuacion(int numeroFallos) {
        //Lo unico que hace es que la puntuacion es el numero de fallos de esa partida, 
        //queda hacer lo de los jugadores y hacer la media de cada jugador
        if (ganarpartida(numeroFallos) || perderpartida(numeroFallos)) {
            puntuacion = 6 - numeroFallos;
        } else {
            puntuacion = 0;
        }
        //return puntuacion;
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

    public boolean noRepetirLetra(String letra, ArrayList<String> a) {
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).equals(letra)) {
                return false;
            }
        }
        return true;
    }

    public boolean comprobarRespuesta(String resp) {
        if (resp.equals(palabra)) {
            return true;
        }
        return false;

    }
}

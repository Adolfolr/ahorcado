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
        
        //Se recogeran los atributos de sesion
        HttpSession sesion = request.getSession();
        if((String)sesion.getAttribute("registrado")!="true"){ 
            response.sendRedirect("/ProyectoAhorcado/login.jsp");
        }else{
        Usuario misesion = (Usuario)sesion.getAttribute("misesion");
        
//-------- Para poder recibir nuestra amiga la Ñ ---------------
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
//Botones        
        String[] botones = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

//------------ CONEXION BBD    ---------------------------------
        BBDD bbdd = new BBDD();//Se usara para saber que palabra toca, inseratar puntuacion y guardar

 //"siguiente" saber si nos piden la siguiente palabra (solo podran pedirnos siguiente palabra si ganamos o perdemos)
        String siguiente = request.getParameter("siguiente");
//        boolean sp = false; // "sp" boolean que se usara para "borrar" el contador de vidas, desbloquear botones...
//
//        if(siguiente!=null){//Condicion de si nos llega la peticion de cambiar palabra 
//        bbdd.siguientePalabra(misesion.getNombre()); //Sumamos +1 a la columna del usuario de la id de la palabra que le toca
//        sp = true;
//        }
//------------------- CARGAMOS PALABRA -------------------------------------
        palabra = misesion.getPalabra();
       
        //Dividimos la palabra en letras para comprobar con otra lista de acertados y saber su posicion
        String[] posicionLetra = palabra.split("");

        //Recogemos la letra que nos envian
        String letra = request.getParameter("letra");
        
        boolean finPartida = false; //FINAL DE PARTIDA
        boolean victoria = false; //HEMOS GANADO INTRODICIENDO LA PALABRA DIRECTAMENTE
        
        //Saber si la letra esta en la palabra. Si no esta debolvera un -1
        int resultado = -1;
        boolean seguimos = false; //Si es falso es que hemos hecho alguna accion incorrecta
        
        //Si se ha enviado alguna LETRA
        if (letra != null) {
            resultado = palabra.indexOf(letra); //comprobar si la letra esta en la palabra
            seguimos = true; 
            }
        //Hemos escrito una palabra directamente y hay que comprobarla
        String respuesta = request.getParameter("respuesta");
        
        if(respuesta!=null){//Si nos ha llegado alguna respuesta
            if(comprobarRespuesta(respuesta)){ //comprobamos si es la correcta, si lo es finalizamos partida y victoria directa
                finPartida = true;
                victoria = true;
            }else{//Si falla restamos una vida
                sesion.setAttribute("intentosFallidos", (int) sesion.getAttribute("intentosFallidos") + 1);
            }
        }

        if (sesion.getAttribute("intentosFallidos") != null) { //Si la sesion existe entonces NO hacemos nada
                 } else {//Si NO existe creamos dos nuevos Atributos (Intentos Fallidos: Numero de intentosFallidos; ListaAciertos: guardamos en una lista las palabras acertadas) 
            sesion.setAttribute("intentosFallidos", 0);
            ArrayList<String> listaAciertos = new ArrayList<String>();
            sesion.setAttribute("listaAciertos", listaAciertos);

            ArrayList<String> listaFallos = new ArrayList<String>();
            sesion.setAttribute("listaFallos", listaFallos);
        }
        //Si hemos pedido otra palabra "reseteamos los valores"
//        if(sp){
//            sesion.setAttribute("intentosFallidos", 0);
//            //ArrayList<String> listaAciertos = new ArrayList<String>();
//            ArrayList<String> listaAciertos = new ArrayList<String>();
//            sesion.setAttribute("listaAciertos", listaAciertos);
//
//            ArrayList<String> listaFallos = new ArrayList<String>();
//            sesion.setAttribute("listaFallos", listaFallos);
//        }

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Juego</title>");
            out.print("<LINK REL=StyleSheet HREF=\"./css/tabla.css\" TITLE=\"Contemporaneo\">");
            out.print("<LINK REL=StyleSheet HREF=\"./css/juego.css\" TITLE=\"Contemporaneo\">");
           
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
             out.println("<meta charset=\"UTF-8\">");
            out.println("</head>");
            out.println("<body id=\"capa\">");
            out.println("<p class=\"titulosPA\"><b> Hola " + misesion.getNombre() + " tu mejor puntuacion es de " + misesion.getPuntuacion() + " </b></p>");
                //Acertamos letra                    no hacmos trampa
            if (resultado != -1 && seguimos && noRepetirLetra(letra, (ArrayList<String>) sesion.getAttribute("listaAciertos")) ) { //Si acertamos guardamos la letra acertada
                out.print("<p style=\"color:green;\"> Adivinaste la letra <p>");
                ArrayList<String> aux = (ArrayList<String>) sesion.getAttribute("listaAciertos");
                aux.add(letra);
                sesion.setAttribute("listaAciertos", aux);
              //SINO ACERTAMOS LA LETRA Y NO ES UNA LETRA YA FALLIDA
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
          



//------------------  "Pintar" letras acertadas -------------------
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
                out.println("<h1 style=\"color:green;\">Has ganado la palabra es "+palabra+"</h1><br>");
                calcularPuntuacion((int) sesion.getAttribute("intentosFallidos"));
                int numero = misesion.getPuntuacion();
                float f = (float)numero;
                float total = puntuacion + f;
                misesion.setPuntuacion(7);
                out.println("Tu puntuacion de esta partida es: " + puntuacion +" total puntuación: "+misesion.getPuntuacion());
                misesion.setMedia(true);
                finPartida = true;
                bbdd.siguientePalabra(misesion.getNombre());
                out.println("<br>");
                out.println("<form method=\"post\" action=\"/ProyectoAhorcado/Ahorcado\" name = \"siguientep\">\n" +
"                <input type=\"hidden\" name=\"siguiente\" value=\"si\">"
                    + "<input type=\"submit\" value=\"Siguiente palabra!\">\n" +
"            </form>");
                 sesion.setAttribute("intentosFallidos", 0);
            //ArrayList<String> listaAciertos = new ArrayList<String>();
            ArrayList<String> listaAciertos = new ArrayList<String>();
            sesion.setAttribute("listaAciertos", listaAciertos);

            ArrayList<String> listaFallos = new ArrayList<String>();
            sesion.setAttribute("listaFallos", listaFallos);
            }
            if (perderpartida((int) sesion.getAttribute("intentosFallidos"))) {
                out.println("<br>");
                out.println("<h1 style=\"color:red;\">Has perdido </h1>" + "<br>");
                misesion.setMedia(false);
//                calcularPuntuacion((int) sesion.getAttribute("intentosFallidos"));
                out.println("Tu puntuacion de esta partida es: " + puntuacion + " total puntuación: "+ misesion.getPuntuacion());
                finPartida = true;
                bbdd.siguientePalabra(misesion.getNombre());
                  out.println("<form method=\"post\" action=\"/ProyectoAhorcado/Ahorcado\" name = \"siguientep\">\n" +
"                <input type=\"hidden\" name=\"siguiente\" value=\"si\">"
                    + "<input type=\"submit\" value=\"Siguiente palabra!\">\n" +
"            </form>");
                 sesion.setAttribute("intentosFallidos", 0);
            //ArrayList<String> listaAciertos = new ArrayList<String>();
            ArrayList<String> listaAciertos = new ArrayList<String>();
            sesion.setAttribute("listaAciertos", listaAciertos);

            ArrayList<String> listaFallos = new ArrayList<String>();
            sesion.setAttribute("listaFallos", listaFallos);
                out.println("<br>");
            }
            out.println("<br>");
//----------------------PINTAR BOTONES EN UNA TABLA------------------------
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
            
            //DIBUJOS DE LAS VIDAS
            out.println("<img id=\"vidas\" src=\"./Imagenes/"+(6 - (int) sesion.getAttribute("intentosFallidos"))+".png\">");

            out.println("<br>");
            out.println("<form method=\"post\" action=\"/ProyectoAhorcado/Ahorcado\" name = \"usuario\">\n" +
"                ME LA SE: <input type=\"text\" id=\"fname\" name=\"respuesta\" style=\"text-transform:uppercase;\" onkeyup=\"javascript:this.value=this.value.toUpperCase();\"><br><br>"
                    + "<input type=\"submit\" value=\"Enviar\">\n" +
"            </form>");
        
//-------------------MENU-------------------------------------------
            out.println("<ul class=\"svertical\">");
            out.println("<li><a href=\"/ProyectoAhorcado/Inicio\" name=\"letra\" >Guardar partida</a></li>");
            out.println("<li><a href=\"/ProyectoAhorcado/Inicio\" name=\"letra\" >Volver</a></li>");
            out.println("</ul>");
            out.println("<br><br><form method=\"post\" action=\"/ProyectoAhorcado/CerrarSesion\" name=\"datos\"> <button >Cerrar Sesion</button></form>\n");
            out.println("</body>");
            out.println("</html>");
        }
        numeroLetrasPintadas = 0;
         bbdd.destroy();
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

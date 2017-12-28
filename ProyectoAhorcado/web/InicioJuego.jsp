<%-- 
    Document   : JuegoAhorcado
    Created on : 16-dic-2017, 18:46:36
    Author     : rafael
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tablero de campeones</title>
       <link rel="stylesheet" href="./css/css.css">
    </head>
    <body id="capa">
        <%@include file="menu.html"%>
        <p class="titulosPA"><b> Bienvenido ${misesion.getNombre()} al juego del Ahorcado, tu media es ${Porcentaje}%</b></p>
         <img id="imagen" src="./Imagenes/${fondo}">
         <img id="imagen2" src="./Imagenes/file1.png">
          <ul class="svertical">
            <li><a href="/ProyectoAhorcado/Ahorcado">Empezar a jugar</a></li>
                    <li><a href="/ProyectoAhorcado/Inicio">Añadir jugador (en obras)</a></li>
                    <li><a href="/ProyectoAhorcado/ImprimirTablero">Tablero de campeones</a></li>
                    <li><a href="/ProyectoAhorcado/Tutorial.jsp">¿Como se juega a esto?</a></li>
                    <li><a href="/ProyectoAhorcado/CerrarSesion">Adios!</a></li> </ul> 
        </body>
</html>

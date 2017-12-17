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
        <title>JSP Page</title>
        <link rel="stylesheet" href="./css/juego.css">
        <link rel="stylesheet" href="./css/tablaBotones.css">
    </head>
    <body id="capa">
        <%@include file="menu.html"%>
        <p class="titulosPA"><b> Hola ${Nombre.getNombre()} tu mejor puntuacion es de ${Nombre.getPuntuacion()} </b></p>
        <p style="color:${MensajeFinalColor}">${MensajeFinal}</p>
        <p id="palabra"> ${Palabra} </p>

        <table>
            ${ListaBotones}
        </table>
        <form method="post" action="/ProyectoAhorcado/Ahorcado" name = "usuario">
            ME LA SE: <input type="text" id="fname" name="respuesta" style="text-transform:uppercase;" onkeyup="javascript:this.value = this.value.toUpperCase();"><br><br>
        <input type="submit" value="Enviar">
        </form>
        <a href='/ProyectoAhorcado/Ahorcado'>${Siguiente}</a>
        <img id="vidas" src="./Imagenes/${Vidas}.png">
    </body>
</html>

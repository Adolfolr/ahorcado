
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ahorcado ayudanos</title>
        <link rel="stylesheet" href="./css/juego.css">
        <link rel="stylesheet" href="./css/tablaBotones.css">
    </head>
    <body id="capa">
        <%@include file="menu.html"%>
        <p class="titulosPA"><b> Hola ${misesion.getNombre()} muchas gracias por ayudarnos, inserta una palabra, no se admiten signos, ni números.</b></p>
       
        <form method="post" action="/ProyectoAhorcado/Ayudanos">
           Inserte la nueva palabra: <input type="text" id="fname" name="nuevaPalabra" style="text-transform:uppercase;" onkeyup="javascript:this.value = this.value.toUpperCase();" ><br><br>
            <input type="submit" value="Enviar">
        </form>
        <p class="centrar" style="color:blue ;position: absolute; left: 20%; margin-top: 25px">${MensajePalabra}</p>
    </body>
</html>

<%-- 
    Document   : Tablero
    Created on : 17-dic-2017, 21:03:39
    Author     : rafael
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio Juego del Ahorcado</title>
        <link rel="stylesheet" href="./css/tabla.css">
     </head>
    <body id="capa">
        <%@include file="menu.html"%>
        <div id="tabla">
            <table>
                <tr>  
                    <th> Posicion </th>
                    <th> Nombre </th> 
                    <th> Puntuacion </th>
                </tr>
                        ${Tabla}
            </table>
        </div>
    </body>
</html>

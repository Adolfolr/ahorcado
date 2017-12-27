<%-- 
    Document   : loginError
    Created on : 27-dic-2017, 23:46:27
    Author     : Clara
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Ahorcado</title>
        <LINK REL=StyleSheet HREF="./css/log.css" TITLE=\"Contemporaneo\">
    </head>
    <body id="capa">
        <div class="container">
            <form method="post" action="/ProyectoAhorcado/LoginBBDD" name = "usuario">
                Iniciar Sesion <br>
                Usuario: <input type="text" id="fname" name="usuario"><br>
                Password: <input type="password" name ="password"><br>
                <input type="submit" value="Enviar">
            </form>
            <form method="post" action="/ProyectoAhorcado/create.jsp" name = "usuario">
               <br>
                <input type="submit" value="Crear cuenta">
            </form>
            <form method="post" action="/ProyectoAhorcado/Tutorial.jsp" name = "usuario">
               <br>
                <input type="submit" value="Tutorial">
            </form>
            out.println("<p style=\"color:red;\"> El usuario o contraseña son incorrectos</p>");
            out.print("<br>");
            out.print("<br>");
             </div>
    </body>
</html>

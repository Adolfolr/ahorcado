<%-- 
    Document   : login
    Created on : 30-nov-2017, 21:45:49
    Author     : rafael
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
                Usuario: <input type="text" id="fname" name="usuario"><br>
                Password: <input type="text" name ="password"><br>
                <input type="submit" value="Enviar">
            </form>
        
        <% String error = request.getParameter("error");
    if (error != null) {
        out.println("<p style=\"color:red;\"> El usuario o contraseña son incorrectos</p>");
    }else{
        out.print("<br>");
        out.print("<br>");
        out.print("<br>");
}%>
    </div>
    </body>
</html>

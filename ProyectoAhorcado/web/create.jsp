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
        <title>Crear cuenta Ahorcado</title>
        <LINK rel=StyleSheet href="./css/log.css">
    </head>
    <body id="capa">
        <div class="container">
            <form method="post" action="/ProyectoAhorcado/CreateUserBBDD" name = "usuario">
                Crear cuenta <br>
                Usuario: <input type="text" id="fname" name="usuario"><br>
                Password: <input type="text" name ="password"><br>
                Repetir password: <input type="text" name ="passwordrep"><br>
                <input type="submit" value="Enviar">
            </form>
            <form method="post" action="/ProyectoAhorcado/login.jsp" name = "usuario">
                <br>
                <input type="submit" value="Iniciar sesion">
            </form>
            <% String error = request.getParameter("error");
                if (error != null) {
                    out.println("<p style=\"color:red;\"> El usuario ya existe o la contraseña es incorrecta</p>");
                }%>
        </div>
    </body>
</html>

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
    </head>
    <body>

 <form method="post" action="/ProyectoAhorcado/LoginBBDD" name = "usuario">
            Usuario: <input name="usuario"><br>
            Password: <input name ="password"><br>
            <button> Enviar </button>
        </form>
<% String error = request.getParameter("error");
if (error!=null) out.println("<p style=\"color:red;\"> El usuario o contraseña son incorrectos</p>"); %>
    </body>
</html>

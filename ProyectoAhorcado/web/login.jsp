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
        <LINK REL=StyleSheet HREF="./css/log.css">
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
            <form method="get" action="/ProyectoAhorcado/LoginBBDD" name = "usuario">
               <br>
                <input type="submit" value="Tutorial">
            </form>  
             </div>
    </body>
</html>

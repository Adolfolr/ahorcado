<%-- 
    Document   : Tutorial
    Created on : 06-dic-2017, 18:35:07
    Author     : rafael
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
        <LINK REL=StyleSheet HREF="./css/juego.css" TITLE="Contemporaneo">
    
        <LINK REL=StyleSheet HREF="./css/tablero.css" TITLE="Contemporaneo">
    
	<title>Tutorial</title>
</head>
<body id="capa">
	<div class="titulosPA">
		<p>¿Como se juega al ahorcado?</p>
	</div>
    <p class="parrafo">El juego del ahorcado es un juego muy sencillo y divertido, consiste en adivinar una palabra, se van diciendo letras (consejo ¡Empezar por las vocales!) y según se van descubriendo la palabra se puede resolver directamente, pero cuidado, si la letra que dices no está dentro de la palabra ¡Perderás una vida!, al igual si decides resolver y fallas.</p>	

<div class="container">
<p class="titulosEtiqueta">Antes de nada, crea una cuenta</p>
<p class="parrafo">¡No podrás jugar sino tienes una cuenta! regístrate con un Nombre, una contraseña y a empezar a jugar puedes pulsar el siguiente link que te llevara a la creación de tu cuenta. <a href="/ProyectoAhorcado/create.jsp"> Crear cuenta</a></p>
<p class="titulosEtiqueta">¡Ya tienes cuenta y estas listo para empezar!</p>
<p class="parrafo">Pues inicia sesión, muy fácil escribe el Nombre y la Contraseña, dentro encontraras un menú de inicio que te dará varias opciones.</p>
<ol class="parrafo">
	<li>Empecemos: No esperemos más, nada más pulsar esta opción te llevará al fantástico juego del Ahorcado donde deberás clicar en los botones para ir dando posibles soluciones y así descubrir la palabra.</li>
	<li>Añadir amigo: (Obras)</li>
	<li>Ver tablero de campeones: Entra y ve como de reñido está el juego, cuantos menos errores cometas mas puntos ganas y más arriba de la tabla estarás.</li>
	<li>Adiós: Pondrá adiós, pero no es una despedida, porque deseamos que vuelvas cuanto antes y demuestres tu ingenio.</li>
</ol>
<%  HttpSession sesion = request.getSession();
    
    String estoyjugando = (String) sesion.getAttribute("registrado"); 
    if (estoyjugando == "true") {
        out.println("<ul class=\"svertical\"> <li><a href=\"/ProyectoAhorcado/Inicio\" >Volver</a></li></ul>");
    }else{
        out.println("<ul class=\"svertical\"> <li><a href=\"/ProyectoAhorcado/login.jsp\" >Volver</a></li></ul>");    
    }
    System.out.println("-------------TUTORIAL---------------->"+estoyjugando);
%>
</div >
</body>
</html>
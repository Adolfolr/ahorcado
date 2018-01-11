

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
        <link rel=StyleSheet href="./css/juego.css">
        <link href="https://fonts.googleapis.com/css?family=Slabo+27px" rel="stylesheet">
        <title>Tutorial</title>
</head>
<style>
    .parrafo{
        font-family: 'Slabo 27px', serif;
        font-size: 18px; 
    }
</style>
<body id="capa">
	<div class="titulosPA">
		<p>¿Como se juega al Ahorcadito?</p>
	</div>
    <p class="parrafo">El juego del Ahorcadito es un juego muy sencillo y divertido, consiste en adivinar una palabra, se van diciendo letras (consejo ¡Empezar por las vocales!) 
        y si estas letras están en la palabra aparecerán. Así hasta descubrir la palabra, se puede resolver directamente, pero cuidado, si la letra que dices no está dentro de la palabra ¡Perderás una vida!, al igual si decides resolver y fallas.</p>	

<div class="container">
<p class="titulosPA">Antes de nada, crea una cuenta</p>
<p class="parrafo">¡No podrás jugar sino tienes una cuenta! regístrate con un Nombre, una contraseña y a empezar a jugar, puedes pulsar el siguiente link que te llevara a la creación de tu cuenta. <a href="/ProyectoAhorcado/create.jsp"> Crear cuenta</a></p>
<p class="titulosPA">¡Ya tienes cuenta y estas listo para empezar!</p>
<p class="parrafo">Pues inicia sesión, muy fácil escribe el Nombre y la Contraseña, dentro encontraras un menú de inicio que te dará varias opciones.</p>
<ol class="parrafo">
    <li><b>Empecemos:</b> No esperemos más, nada más pulsar esta opción te llevará al fantástico juego del Ahorcadito donde deberás clicar en los botones para ir dando posibles soluciones y así descubrir la palabra.</li>
	<li><b>Ayudanos:</b> Propón palabras y haz que el juego no termine nunca (Aviso: cada palabra propuesta y aceptada se guardará junto al nombre del usuario que la propuso, si esa palabra se considera ofensiva o no existe, se podrá proceder a algún tipo de penalización)</li>
	<li><b>Ver tablero de campeones:</b> Entra y ve como de reñido está el juego, cuantos menos errores cometas mas puntos ganas y más arriba de la tabla estarás.</li>
	<li><b>Adiós:</b> Pondrá adiós, pero no es una despedida, porque deseamos que vuelvas cuanto antes y demuestres tu ingenio.</li>
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
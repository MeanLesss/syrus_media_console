<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Syrus media console</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link
	href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700,800,900"
	rel="stylesheet">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet" href="css/style.css">
<script src="https://kit.fontawesome.com/b4b848ea5a.js"
	crossorigin="anonymous"></script>
</head>
<body>
	<!-- nav panel -->
	<div class="wrapper d-flex align-items-stretch">

		<%@include file="navBar.jsp"%>

		<!-- Page Content  -->
		<div id="content" class="p-4 p-md-5 pt-5">
		   <%
                /* String child = request.getParameter("child"); */
                if ("videos".equals(child)) {
                  %><%@ include file="videos.jsp" %><%
                } else if ("home".equals(child)) {
              
                    %><%@ include file="home.jsp" %><%
                } else if ("musics".equals(child)) {
	                
	                %><%@ include file="musics.jsp" %><%
	            } else if ("groups".equals(child)) {
	                
	                %><%@ include file="groups.jsp" %><%
	            } else if ("setting".equals(child)) {
	                
	                %><%@ include file="setting.jsp" %><%
	            }
	             else 
	            {
                    %><%@ include file="home.jsp" %><%
                }
            %>
			
	
		</div>
	</div>

	<script src="js/jquery.min.js"></script>
	<script src="js/popper.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/main.js"></script>
</body>
</html>

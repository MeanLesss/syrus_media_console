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

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />

<link rel="stylesheet" href="css/style.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="sweetalert2.min.js"></script>
<script src="https://kit.fontawesome.com/b4b848ea5a.js"
	crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.5.1.js" ></script>
<link href="https://vjs.zencdn.net/7.17.0/video-js.css" rel="stylesheet">
<script src="https://vjs.zencdn.net/7.17.0/video.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/videojs-rtsp-stream@1"></script>
</head>
<body>
	<!-- nav panel -->
	<div class="wrapper d-flex align-items-stretch">

		<%@include file="navBar.jsp"%>

		<!-- Page Content  -->
		<div id="content" class="p-5 p-md-5 p-lg-5 pt-5">
			<%
			/* String child = request.getParameter("child");  */
			if ("videos".equals(child)) {
			%><%@ include file="videos.jsp"%>
			<%
			} else if ("home".equals(child)) {
			%><%@ include file="home.jsp"%>
			<%
			} else if ("musics".equals(child)) {
			%><%@ include file="musics.jsp"%>
			<%
			} else if ("groups".equals(child)) {
			%><%@ include file="groups.jsp"%>
			<%
			} else if ("setting".equals(child)) {
			%><%@ include file="setting.jsp"%>
			<%
			} else if ("viewVideo".equals(child)) {
			%><%@ include file="viewVideo.jsp"%>
			<%
			} else {
			%><%@ include file="home.jsp"%>
			<%
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

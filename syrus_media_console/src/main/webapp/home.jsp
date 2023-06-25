<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
<title>Insert title here</title>
</head>
<body>
	<h2 class='mb-4'>Dashboard</h2>
	<div class="border shadow card rounded">
		<canvas id="myChart" style="width: 100%; max-width: 100%"></canvas>
	</div>


	<script>
		
	<%
	if(request.getAttribute("overalls")!=null){	
		List<Integer> overalls = (List<Integer>) request.getAttribute("overalls");
	%>
		var xValues = [ "Videos", "Musics", "Thumbnails" ];
		/* 	var yValues = [55, 49, 44]; */
		var yValues = [
	<% for (int i = 0; i < overalls.size(); i++) { %><%= overalls.get(i) %><% if (i < overalls.size() - 1) { %>
		,
	<% } %><% } %>
		];
		var barColors = [ "#b91d47", "#00aba9", "#2b5797", "#e8c3b9", "#1e7145" ];

		new Chart("myChart", {
			type : "doughnut",
			data : {
				labels : xValues,
				datasets : [ {
					backgroundColor : barColors,
					data : yValues
				} ]
			},
			options : {
				title : {
					display : true,
					text : "Overall medias"
				}
			}
		});
		<%}
	%>
	</script>
</body>
</html>
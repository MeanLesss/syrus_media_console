<!Doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Icons font CSS-->
<link href="vendor/mdi-font/css/material-design-iconic-font.min.css"
	rel="stylesheet" media="all">
<link href="vendor/font-awesome-4.7/css/font-awesome.min.css"
	rel="stylesheet" media="all">
<!-- Font special for pages-->
<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Vendor CSS-->
<link href="vendor/select2/select2.min.css" rel="stylesheet" media="all">
<link href="vendor/datepicker/daterangepicker.css" rel="stylesheet"
	media="all">
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<!-- Main CSS-->
<link href="css/main.css" rel="stylesheet" media="all">

<link href="https://vjs.zencdn.net/7.17.0/video-js.css" rel="stylesheet">
<script src="https://vjs.zencdn.net/7.17.0/video.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/videojs-rtsp-stream@1"></script>

<title>Syrus media center</title>

<style type="text/css">
.btn {
	margin: 5px 0;
}
</style>

</head>
<body>
	<div class="page-wrapper bg-gra-01 p-t-180 p-b-100 font-poppins">
		<div class="wrapper wrapper--w780">
			<div class="card card-3">
				<div class="card-heading"></div>
				<div class="card-body">
					<h3 class="title">Syrus Media Center</h3>
					<%
					if (request.getAttribute("errorMessage") != null) {
					%>
					<div class="alert alert-danger" role="alert">
						<%=request.getAttribute("errorMessage")%>
					</div>
					<%
					}
					%>
					<h3 class="title">Log in</h3>

					<form action="/syrus_media_console/Login_user" method="POST">
						<div class="input-group">
							<input class="input--style-3" type="text" id="username"
								name="username" placeholder="Username">
						</div>
						<div class="input-group">
							<input class="input--style-3" type="password" id="password"
								name="password" placeholder="Password">
						</div>
						<div class="p-t-10">
							<input class="btn btn--pill btn--purple" type="submit"
								value="Log in"> <a
								class="btn btn--pill btn--green btn--reverse"
								href="SignUpController">Sign up</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- Option 1: Bootstrap Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
</body>
</html>
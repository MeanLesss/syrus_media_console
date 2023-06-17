<!Doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<title>Syrus media center</title>
</head>
<body>
	<h1>Syrus media center</h1>
	<h3>Log in</h3>
	<form action="/syrus_media_console/Login_user" method="POST">
		<label for="username">Username :</label> 
			<input class="form-control" type="text" id="username" name="username">
			<br>
			<labelfor="password">Password :</label> 
			<input class="form-control" type="password" id="password" name="password">
			<br>
			<a class="btn btn-primary" href="SignUpController">Sign up</a> 
			<input class="btn btn-success" type="submit" value="Log in">

	</form>
	<!-- Option 1: Bootstrap Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
</body>
</html>
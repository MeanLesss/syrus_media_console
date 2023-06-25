<%@page import="models.Music"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<head>
<meta charset="ISO-8859-1">
<title>Music</title>
</head>
<style>
a {
	color: #494ca2;
}
</style>
<body>
	<h2>Musics</h2>
	<button type="button" class="btn btn-primary" data-bs-toggle="modal"
		data-bs-target="#myModal">
		<i class="fa fa-upload"></i> Upload
	</button>
	<!-- The Modal -->
	<div class="modal" id="myModal">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Upload music</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>

				<!-- Modal body -->
				<!-- action="VideoController" method="POST"  -->
				<form enctype="multipart/form-data" id="upload-form">
					<div class="modal-body">
						<h5>Choose a music(.mp3):</h5>
						<input class="form-control" type="file" name="music" accept=".mp3"
							required />
						<h5>Choose a thumbnail(.jpg/.png):</h5>
						<input class="form-control" type="file" name="thumb"
							accept=".jpg,.png" />
						<h5>Title :</h5>
						<input class="form-control" type="text" name="title" required />
						<h5>Description:</h5>
						<input class="form-control" type="text" name="description" />
						<h5>Genre:</h5>
						<input class="form-control" type="text" name="genre" /> <input
							class="form-control" type="number" name="user_id" hidden
							value="<%=session.getAttribute("user_id")%>" />
					</div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" class="btn btn-danger"
							data-bs-dismiss="modal">Close</button>
						<input class="btn btn-primary" type="submit" value="Upload" />
					</div>
				</form>
				<progress id="upload-progress" value="0" max="100"></progress>
			</div>
		</div>
	</div>

	<div id="cardContainer" class="row gap-2">

		<!-- The card display -->
		<%
		if (request.getAttribute("musicList") != null) {

			List<Music> musics = (List<Music>) request.getAttribute("musicList");
			for (Music music : musics) {
		%>
		<div class="card p-0 mt-2 shadow-lg bg-body rounded"
			style="width: 20rem;">
			<img src="<%=request.getContextPath() + music.getThumbnail_path()%>"
				style="width: 100%; height: 180px;" alt="thumbnail"
				class="card-img-top">
			<div class="card-body">
				<%-- <h5>
					<a
						href="SelectedVideoController?child=viewVideo&videoID=<%=music.getId()%>"
						style="overflow: hidden; width: 100%;"> <span
						class="fa fa-video mr-3"></span> <%=music.getTitle()%>
					</a>
				</h5> --%>
				<p class="card-text" style="overflow: hidden; width: 100%;"><%=music.getDescription()%></p>
				<audio src="<%=request.getContextPath() + music.getFile_path()%>"
					controls preload="auto"></audio>
			</div>
		</div>


		<%--  <video id="player" class="video-js" controls preload="auto" width="640" height="268">
        <source src="<%=request.getContextPath()+video.getFile_path()%>" >
    </video> --%>
		<%
		}
		} else {
		%>
		<h1>Empty</h1>
		<%
		}
		%>
	</div>
	<!-- 	<script>
		var player = videojs('player');
	</script> -->
	<script type="text/javascript">
		document
				.getElementById('upload-form')
				.addEventListener(
						'submit',
						function(event) {
							event.preventDefault();
							var formData = new FormData(this);
							var xhr = new XMLHttpRequest();
							xhr.open('POST', 'MusicController');
							xhr.upload
									.addEventListener(
											'progress',
											function(event) {
												if (event.lengthComputable) {
													var percentComplete = event.loaded
															/ event.total * 100;
													document
															.getElementById('upload-progress').value = percentComplete;
												}
											});
							xhr.send(formData);

							xhr.addEventListener('load', function(event) {
								// Check the status code of the response
								if (this.status === 200) {
									// The request was successful
									alert('Upload successful:',
											this.responseText);
								} else {
									// The request failed
									alert('Upload failed:', this.statusText);
								}
							});
						});
	</script>
</body> 
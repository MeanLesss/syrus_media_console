<%@page import="java.util.List"%>
<%@page import="models.Video"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%-- <%@ include file="master.jsp" %> --%>
<body>
	<h2>Videos</h2>
	<%-- 	<%
	if (request.getAttribute("upload_error") != null) {
	%>
	<div class="alert alert-danger" role="alert">
		<%=request.getAttribute("upload_error")%>
	</div>
	<%
	} else {
	%>
	<div class="alert alert-success" role="alert">
		<%=request.getAttribute("upload_success")%>
	</div>
	<%
	}
	%> --%>
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
					<h4 class="modal-title">Upload video</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>

				<!-- Modal body -->
				<!-- action="VideoController" method="POST"  -->
				<form enctype="multipart/form-data" id="upload-form">
					<div class="modal-body">
						<h5>Choose a video(.mp4/.wav):</h5>
						<input class="form-control" type="file" name="video"
							accept=".mp4,.wav" required />
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

	<!-- The card display -->
	<%
	if (request.getAttribute("videos") != null) {

		List<Video> videos = (List<Video>) request.getAttribute("videos");
		for (Video video : videos) {
	%>
	<h1><%=video.getTitle()%></h1>
	<img src="<%=request.getContextPath()+video.getThumbnail_path()%>" alt="image">
	<%
	}
	} else {
	%>
	<h1>Empty</h1>
	<%
	}
	%>
	<%-- 	<img src="<%= filePath %>" alt="Image">
	<!-- or -->
	<video src="<%= filePath %>" controls></video> --%>

	<script type="text/javascript">
		document
				.getElementById('upload-form')
				.addEventListener(
						'submit',
						function(event) {
							event.preventDefault();
							var formData = new FormData(this);
							var xhr = new XMLHttpRequest();
							xhr.open('POST', 'VideoController');
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
</html>
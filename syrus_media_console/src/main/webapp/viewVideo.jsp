<%@page import="models.Video"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="https://vjs.zencdn.net/7.17.0/video-js.css" rel="stylesheet">
<script src="https://vjs.zencdn.net/7.17.0/video.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/videojs-rtsp-stream@1"></script>
<script type="text/javascript">
	window.VIDEOJS_NO_DYNAMIC_STYLE = true;
</script>

<title>View Video</title>
<style type="text/css">
.vjs-poster {
	background-size: cover !important;
}

h1 {
	color: #494ca2
}

.vjs-16-9 {
	width: 100% !important;
	height: 40% !important;
	padding: 0;
}
/* Change all text and icon colors in the player. */
.vjs-matrix.video-js {
	color: #00ff00;
}

/* Change the border of the big play button. */
.vjs-matrix.vjs-big-play-button {
	border-color: #00ff00;
}

/* Change the color of various "bars". */
.vjs-matrix .vjs-volume-level, .vjs-matrix .vjs-play-progress,
	.vjs-matrix .vjs-slider-bar {
	background: #00ff00;
}

.videoContainer {
	display: flex;
	height: 80% !important;
}
</style>

</head>

<%
// Get the value of the "videoID" parameter from the request object
/*     String videoID = request.getParameter("videoID");

    // Look up the video object from a database or other data source using the videoID
    Video video = new Video(videoID).getVideoByID(videoID);

    // Use the video object
    String title = video.getTitle();
    String url = video.getFile_path(); */
%>
<body class="p-0 m-0">
	<div class="d-flex row w-100 ">
		<div class="videoContainer w-100 justify-content-center">
			<%
			if (request.getAttribute("video") != null) {

				Video video = (Video) request.getAttribute("video");
			%>
			<video id="player"
				class="video-js
				vjs-big-play-centered
				vjs-show-big-play-button-on-pause
				vjs-16-9
				vjs-fluid
				vjs-matrix
				vjs-layout-medium
				vjs-layout-large 
				vjs-layout-small 
				vjs-layout-x-small"
				controls preload="auto">
				<source src="<%=request.getContextPath() + video.getFile_path()%>">
			</video>
		</div>

		<div class="accordion" id="accordionPanelsStayOpenExample ">
			<div class="accordion-item">
				<h2 class="accordion-header" id="panelsStayOpen-headingOne">
					<button class="accordion-button" type="button"
						data-bs-toggle="collapse"
						data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="true"
						aria-controls="panelsStayOpen-collapseOne">
						<h1><%=video.getTitle()%></h1>
					</button>
				</h2>
				<div id="panelsStayOpen-collapseOne"
					class="accordion-collapse collapse"
					aria-labelledby="panelsStayOpen-headingOne">
					<div class="accordion-body">
						<h6>Description:</h6>
						<p><%=video.getDescription()%></p>
						<h6>Genre: </h6>
						<p  class="badge rounded-pill bg-info text-dark"><%=video.getGenre()%></p>
					</div>
				</div>
			</div>
			<%
			}
			%>
		</div>
	</div>
	<script>
		var player = videojs('player', {
			aspectRatio : '16:9'
		});
		player.fluid(false);
		/* player.height=50;	 */
		player.on('pause', function() {

			// Modals are temporary by default. They dispose themselves when they are
			// closed; so, we can create a new one each time the player is paused and
			// not worry about leaving extra nodes hanging around.
			/* var modal = player.createModal('<h1>This is a modal!</h1>');

			// When the modal closes, resume playback.
			modal.addClass('vjs-my-fancy-modal');
			modal.on('modalclose', function() {
				player.play();
			}); */
		});
	</script>
</body>
</html>
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


<title>View Video</title>
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
<body>
	selected video
	<%
	if (request.getAttribute("video") != null) {

		Video video = (Video)request.getAttribute("video");
		%>
	<video id="player" class="video-js" controls preload="auto" width="640" height="268">
        <source src="<%=request.getContextPath()+ video.getFile_path()%>">
    </video>
    <%
	}
	%>
	   <script>
        var player = videojs('player');
    </script>
</body>
</html>
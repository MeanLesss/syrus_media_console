<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<style>
	a{
		text-decoration:none !important;
		/* font-size: 18px !important; */
	}
</style>
<nav id="sidebar" class="active">
	<div class="custom-menu">
		<button type="button" id="sidebarCollapse" class="btn btn-primary">
			<i class="fas fa-bars"></i> <span class="sr-only">Toggle Menu</span>
		</button>
	</div>
	<div class="p-4">
		<h3>
			<a href="master.jsp" class="text-white logo">Syrus media center</a>
		</h3>
		<%
		String child = request.getParameter("child");
		%>
		<ul class="list-unstyled components mb-5">
			<li class="<%= "home".equals(child) ? "active" : ""%> " ><a href="master.jsp?child=home"><span
					class="fa fa-home mr-3 "></span> Home</a></li>
			<%-- <li class="<%= "videos".equals(child) ? "active" : ""%> "><a href="master.jsp?child=videos"><span
					class="fa fa-video mr-3"></span> Videos</a></li> --%>
			<li class="<%= "videos".equals(child) ? "active" : ""%> "><a href="VideoController"><span
			class="fa fa-video mr-3"></span> Videos</a></li>
			<li class="<%= "musics".equals(child) ? "active" : ""%> "><a href="master.jsp?child=musics"><span class="fa fa-music mr-3"></span>
					Musics</a></li>
			<li class="<%= "groups".equals(child) ? "active" : ""%> "><a href="master.jsp?child=groups"><span class="fa fa-users mr-3"></span>
					Groups</a></li>
			<li class="<%= "#".equals(child) ? "active" : ""%> "><a href="master.jsp?child=setting"><span class="fa fa-cog mr-3"></span> Setting</a></li>
		</ul>

		<!-- %> -->
		<div class="footer">
			<%
			if (session.getAttribute("username") != null) { %>
			    <div class="logo" role="alert">
				    <h6>Username:
				        <%= session.getAttribute("username") %>
			        </h6>
			    </div>
			    <div>
			    	<h6>ID:
			        	<%= session.getAttribute("user_id") %>
			    	</h6>
			    </div>
			<% } else{  %>
				<div class="logo" role="alert">
			        unknown
			    </div>
			<% } %>
		
			<ul class="list-unstyled components mb-5">
				<form action=Logout_user method="GET">
					<button class="btn btn-danger" type="submit">
						<span class="fa fa-door-open mr-3"></span> Log out
					</button>
				</form>
			</ul>
		</div>

	</div>
</nav>
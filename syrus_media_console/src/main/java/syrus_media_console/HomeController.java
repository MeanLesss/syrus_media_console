package syrus_media_console;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Video;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

/**
 * Servlet implementation class HomeController
 */
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/syrus_media_console")
	private DataSource dataSource;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HomeController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Integer> overalls = new ArrayList<>(Arrays.asList());
		
//		get all the count of media [Video,Music,Thumbnails]
		PrintWriter out = response.getWriter();
		String query = "SELECT COUNT(DISTINCT v.file_path) AS countVideo,COUNT(DISTINCT m.file_path) AS countMusic,COUNT(DISTINCT v.thumbnail_path) + COUNT(DISTINCT m.thumbnail_path) AS countThumb FROM videos AS v JOIN musics AS m ON m.user_id = v.user_id WHERE v.user_id = ?;";
		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(query);
			HttpSession session = request.getSession();
			ps.setString(1, session.getAttribute("user_id").toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int count_video = rs.getInt("countVideo");
				int count_music = rs.getInt("countMusic");
				int count_thumb = rs.getInt("countThumb");
			
				overalls.add(count_video);
				overalls.add(count_music);
				overalls.add(count_thumb);
			}
		} catch (SQLException e) {
			// Handle exception
			out.println("<h1>" + e.getMessage() + "</h1>");
			e.printStackTrace();
		}
		
		
		
		
		
//		overalls.add(10);
		
		request.setAttribute("overalls", overalls);
		request.getRequestDispatcher("master.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

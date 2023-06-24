package syrus_media_console;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Video; 

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * Servlet implementation class SelectedVideoController
 */
public class SelectedVideoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/syrus_media_console")
	private DataSource dataSource;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectedVideoController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String video_id = request.getParameter("videoID").toString();
		PrintWriter out = response.getWriter();
		String query = "SELECT * FROM videos WHERE id = ?";
		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, video_id);
			ResultSet rs = ps.executeQuery();
			Video video = null;
			while (rs.next()) {
				 video = new Video(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
						rs.getString("file_path"), rs.getString("thumbnail_path"), rs.getString("genre"),
						rs.getString("user_id"));
			}
			request.setAttribute("video", video);
			request.getRequestDispatcher("master.jsp?child=viewVideo").forward(request, response);
		} catch (SQLException e) {
			// Handle exception
			out.println("<h1>" + e.getMessage() + "</h1>");
			e.printStackTrace();
		}
//		request.getRequestDispatcher("master.jsp?child=viewVideo").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

package syrus_media_console;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Video;

import java.io.IOException;
import java.io.InputStream; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * Servlet implementation class MobileController
 */

public class MobileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/syrus_media_console")
	private DataSource dataSource;

	
	  private static final Logger logger = Logger.getLogger(MobileController.class.getName());
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MobileController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		String video_id = request.getParameter("videoID").toString();
		String video_id = "54";
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

			// Set the content type to video
			response.setContentType("video/mp4");

			// Get the video file as an input stream
			InputStream in = getServletContext().getResourceAsStream(video.getFile_path());

			 // Log a message at the INFO level
	        logger.log(Level.INFO, "This is an info message:",getServletContext().getResourceAsStream(video.getFile_path()));
			// Get the output stream to write the video data to
			ServletOutputStream out = response.getOutputStream();

			// Write the video data to the output stream
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}

			// Close the streams
			in.close();
			out.close();
		} catch (SQLException e) {
			// Handle exception
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
 
		}
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

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

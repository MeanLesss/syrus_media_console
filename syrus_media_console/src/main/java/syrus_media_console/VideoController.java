package syrus_media_console;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import models.Video;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.System.Logger;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.mysql.cj.x.protobuf.MysqlxNotice.Warning.Level;

//This @MultipartConfig is needed in other to user the multipart form
@MultipartConfig
public class VideoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/syrus_media_console")
	private DataSource dataSource;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VideoController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Video> videos = new ArrayList<>();
		PrintWriter out = response.getWriter();
		String query = "SELECT * FROM videos WHERE user_id = ?";
		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(query);
			HttpSession session = request.getSession();
			ps.setString(1, session.getAttribute("user_id").toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Video video = new Video(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
						rs.getString("file_path"), rs.getString("thumbnail_path"), rs.getString("genre"),
						rs.getString("user_id"));
				// video.file_path = rs.getString("file_path");
				videos.add(video);
			}
		} catch (SQLException e) {
			// Handle exception
			out.println("<h1>" + e.getMessage() + "</h1>");
			e.printStackTrace();
		}

		request.setAttribute("videoList", videos);
		request.getRequestDispatcher("master.jsp?child=videos").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String title = request.getParameter("title");
		Part video = request.getPart("video");
		Part thumbnail = request.getPart("thumb");
		String description = request.getParameter("description");
		String genre = request.getParameter("genre");
		String user_id = request.getParameter("user_id");
//		String video_path = "C:\\syrus_media_console\\files\\videos";
//		String thum_path = "C:\\syrus_media_console\\files\\thumbnails";
		
		String video_path = "/files/videos";
		String thum_path = "/files/thumbnails";
		
		String videoFileName = getFileName(video);
		String thumbnailFileName =  getFileName(thumbnail);
		String query = "insert into videos(title,description,file_path,thumbnail_path,genre,user_id) values(?,?,?,?,?,?)";

		ServletContext context = getServletContext();
		PrintWriter out = response.getWriter();
		// Get the video data from the request
		InputStream videoData = video.getInputStream();
		// Get the Thumnail data from the request
		InputStream thumData = thumbnail.getInputStream();
		// Create a new file in the specified directory with the specified name
		File videoFile = new File(context.getRealPath(video_path), videoFileName);
		File thumbFile = new File(context.getRealPath(thum_path), thumbnailFileName);
//		System.out.println("Video : " + videoData);
		FileOutputStream outputStream_video = new FileOutputStream(videoFile);
		FileOutputStream outputStream_thumb = new FileOutputStream(thumbFile);
		try (Connection conn = dataSource.getConnection()) {

			// Create video directory if it does not exist
			File videoDir = new File(context.getRealPath(video_path));
			if (!videoDir.exists()) {
				videoDir.mkdirs();
			}
			// Create thumbnail directory if it does not exist
			File thumbnailDir = new File(context.getRealPath(thum_path));
			if (!thumbnailDir.exists()) {
				thumbnailDir.mkdirs();
			}
			// Save video file to directory

			if (videoData.available() == 0) {
				System.out.println("The videoData input stream is empty");
			} else {

				// Write the video data to the file
				byte[] buffer = new byte[1024 * 1024];
				int bytesRead;
				while ((bytesRead = videoData.read(buffer)) != -1) {
					outputStream_video.write(buffer, 0, bytesRead);
				}
			}
			// Save thuumbnail file to directory

			if (thumData.available() == 0) {
				System.out.println("The thumData input stream is empty");
			} else {

				// Write the video data to the file
				byte[] buffer = new byte[1024 * 1024];
				int bytesRead;
				while ((bytesRead = thumData.read(buffer)) != -1) {
					outputStream_thumb.write(buffer, 0, bytesRead);
				}
			}

//			Save file everthing to db
			PreparedStatement ps = conn.prepareStatement(query);

			ps.setString(1, title);
			ps.setString(2, description);
			ps.setString(3, video_path + File.separator + videoFileName);
			ps.setString(4, thum_path + File.separator + thumbnailFileName);
			ps.setString(5, genre);
			ps.setString(6, user_id);

			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				response.setContentType("text/plain");
				out.println("Upload successful");
//				request.setAttribute("upload_success", "Upload success enjoy your movie!");
//				request.getRequestDispatcher("master.jsp?child=videos").forward(request, response);

				// Write the resource URL and path to the response
				out.println("Resource URL: " + context.getContextPath() + thum_path + thumbnailFileName);
				out.println("Resource path: " + context.getRealPath("") + thum_path + thumbnailFileName);
			} else {
				response.setContentType("text/plain");
				out.println("Upload failed");
//				request.setAttribute("upload_error", "Upload failed please check your input!");
//				request.getRequestDispatcher("master.jsp?child=videos").forward(request, response);
			}

		} catch (SQLException e) {
			out.println(request.getParameterValues("title"));
			out.println(request.getParameterValues("description"));
			out.println(request.getParameterValues("user_id"));
			out.println(request.getParameterValues("title"));
			out.println("<h1>" + e.getMessage() + "</h1>");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			out.println("<h1>" + e.getMessage() + "</h1>");
			e.printStackTrace();
		} finally {

			// Close the streams
			outputStream_video.close();
			outputStream_thumb.close();
			videoData.close();
		}
	}

	private String getFileName(final Part part) {
//	    final String partHeader = part.getHeader("content-disposition");
////	    Logger.log(Level.INFO, "Part Header = {0}", partHeader);
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

}

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
import models.Music;
import models.Video;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.io.OutputStream;
import java.io.PrintWriter;
/*import java.lang.System.Logger;
import java.nio.file.Paths;*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
 

//This @MultipartConfig is needed in other to user the multipart form
@MultipartConfig
public class MusicController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/syrus_media_console")
	private DataSource dataSource;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MusicController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Music> musics = new ArrayList<>();
		PrintWriter out = response.getWriter();
		String query = "SELECT * FROM musics WHERE user_id = ?";
		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(query);
			HttpSession session = request.getSession();
			String user_id;
			Object sessionUserId = session.getAttribute("user_id");
			if (sessionUserId != null) {
			    user_id = sessionUserId.toString();
			} else {
			    user_id = request.getParameter("user_id");
			}
			
			
			ps.setString(1, user_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Music music = new Music(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
						rs.getString("file_path"), rs.getString("thumbnail_path"), rs.getString("genre"),
						rs.getString("user_id"));
				// music.file_path = rs.getString("file_path");
				musics.add(music);
			}
		} catch (SQLException e) {
			// Handle exception
			out.println("<h1>" + e.getMessage() + "</h1>");
			e.printStackTrace();
		}
		if(request.getParameter("user_id") != null && request.getParameter("user_id") != "") {
			 // Send a JSON response containing the user details 
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for (int i = 0; i < musics.size(); i++) {
			    Music music = musics.get(i);
			    sb.append("{");
			    sb.append("\"id\":").append(music.getId()).append(",");
			    sb.append("\"title\":\"").append(music.getTitle()).append("\",");
			    sb.append("\"description\":\"").append(music.getDescription()).append("\",");
			    sb.append("\"file_path\":\"").append(music.getFile_path()).append("\",");
			    sb.append("\"thumbnail_path\":\"").append(music.getThumbnail_path()).append("\",");
			    sb.append("\"genre\":\"").append(music.getGenre()).append("\",");
			    sb.append("\"user_id\":").append(music.getUser_id());
			    sb.append("}");
			    if (i < musics.size() - 1) {
			        sb.append(",");
			    }
			}
			sb.append("]");

			String jsonString = sb.toString();

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(jsonString);
			out.flush();
          return;
		}

		request.setAttribute("musicList", musics);
		request.getRequestDispatcher("master.jsp?child=musics").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String title = request.getParameter("title");
		Part music = request.getPart("music");
		Part thumbnail = request.getPart("thumb");
		String description = request.getParameter("description");
		String genre = request.getParameter("genre");
		String user_id = request.getParameter("user_id");
//		String music_path = "C:\\syrus_media_console\\files\\musics";
//		String thum_path = "C:\\syrus_media_console\\files\\thumbnails";
		
		String music_path = "/files/musics";
		String thum_path = "/files/thumbnails";
		
		String musicFileName = getFileName(music);
		String thumbnailFileName =  getFileName(thumbnail);
		String query = "insert into musics(title,description,file_path,thumbnail_path,genre,user_id) values(?,?,?,?,?,?)";

		ServletContext context = getServletContext();
		PrintWriter out = response.getWriter();
		// Get the music data from the request
		InputStream musicData = music.getInputStream();
		// Get the Thumnail data from the request
		InputStream thumData = thumbnail.getInputStream();
		// Create a new file in the specified directory with the specified name
		File musicFile = new File(context.getRealPath(music_path), musicFileName);
		File thumbFile = new File(context.getRealPath(thum_path), thumbnailFileName);
//		System.out.println("music : " + musicData);
		FileOutputStream outputStream_music = new FileOutputStream(musicFile);
		FileOutputStream outputStream_thumb = new FileOutputStream(thumbFile);
		try (Connection conn = dataSource.getConnection()) {

			// Create music directory if it does not exist
			File musicDir = new File(context.getRealPath(music_path));
			if (!musicDir.exists()) {
				musicDir.mkdirs();
			}
			// Create thumbnail directory if it does not exist
			File thumbnailDir = new File(context.getRealPath(thum_path));
			if (!thumbnailDir.exists()) {
				thumbnailDir.mkdirs();
			}
			// Save music file to directory

			if (musicData.available() == 0) {
				System.out.println("The musicData input stream is empty");
			} else {

				// Write the music data to the file
				byte[] buffer = new byte[1024 * 1024];
				int bytesRead;
				while ((bytesRead = musicData.read(buffer)) != -1) {
					outputStream_music.write(buffer, 0, bytesRead);
				}
			}
			// Save thuumbnail file to directory

			if (thumData.available() == 0) {
				System.out.println("The thumData input stream is empty");
			} else {

				// Write the music data to the file
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
			ps.setString(3, music_path + "/" + musicFileName);
			ps.setString(4, thum_path + "/" + thumbnailFileName);
			ps.setString(5, genre);
			ps.setString(6, user_id);

			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				response.setContentType("text/plain");
				out.println("Upload successful");
//				request.setAttribute("upload_success", "Upload success enjoy your movie!");
//				request.getRequestDispatcher("master.jsp?child=musics").forward(request, response);

				// Write the resource URL and path to the response
				out.println("Resource URL: " + context.getContextPath() + thum_path + thumbnailFileName);
				out.println("Resource path: " + context.getRealPath("") + thum_path + thumbnailFileName);
			} else {
				response.setContentType("text/plain");
				out.println("Upload failed");
//				request.setAttribute("upload_error", "Upload failed please check your input!");
//				request.getRequestDispatcher("master.jsp?child=musics").forward(request, response);
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
			outputStream_music.close();
			outputStream_thumb.close();
			musicData.close();
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

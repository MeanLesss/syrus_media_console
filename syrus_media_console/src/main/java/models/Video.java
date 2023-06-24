package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import jakarta.annotation.Resource;


public class Video {
	
	
	int id;
	String title;
	String description;
	String file_path;
	String thumbnail_path;
	String genre;
	String user_id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public Video() {};
	public Video(String video_id) {
		super();
		this.id = Integer.parseInt(video_id);
	};
	public Video(int id, String title, String description, String file_path, String thumbnail_path, String genre,
			String user_id) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.file_path = file_path;
		this.thumbnail_path = thumbnail_path;
		this.genre = genre;
		this.user_id = user_id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getThumbnail_path() {
		return thumbnail_path;
	}
	public void setThumbnail_path(String thumbnail_path) {
		this.thumbnail_path = thumbnail_path;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	/*
	 * @Resource(name = "jdbc/syrus_media_console") private DataSource dataSource;
	 * public Video getVideoByID(String video_id) { Video video = null; try
	 * (Connection conn = dataSource.getConnection()) { String query =
	 * "SELECT * FROM videos WHERE id = ?"; PreparedStatement ps =
	 * conn.prepareStatement(query); ps.setString(1, this.id+""); ResultSet rs =
	 * ps.executeQuery();
	 * 
	 * while (rs.next()) { video = new Video(rs.getInt("id"), rs.getString("title"),
	 * rs.getString("description"), rs.getString("file_path"),
	 * rs.getString("thumbnail_path"), rs.getString("genre"),
	 * rs.getString("user_id"));
	 * 
	 * }
	 * 
	 * } catch (SQLException e) { // Handle exception // out.println("<h1>" +
	 * e.getMessage() + "</h1>"); e.printStackTrace(); } return video; }
	 */
}

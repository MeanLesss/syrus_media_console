package syrus_media_console;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;



/**
 * Servlet implementation class Login_user
 */
public class Login_user extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/syrus_media_console")
	private DataSource dataSource;


	public Login_user() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		request.getRequestDispatcher("index.jsp").forward(request, response);
		request.getRequestDispatcher("HomeController").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String query = "select * from users where username=?and password = ?";
		PrintWriter out = response.getWriter();
		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(query);
			if(request.getParameter("username")== null || request.getParameter("username")== "") {
				BufferedReader reader = request.getReader();
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
				    sb.append(line);
				}
				String requestBody = sb.toString();

				// Parse the JSON data
				Map<String, String> json = new HashMap<>();
				Pattern pattern = Pattern.compile("\"(\\w+)\":\\s*\"([^\"]+)\"");
				Matcher matcher = pattern.matcher(requestBody);
				while (matcher.find()) {
				    String key = matcher.group(1);
				    String value = matcher.group(2);
				    json.put(key, value);
				}

				username = json.get("Username");
				password = json.get("Password");
//				confirmPass = json.get("ConfirmPassword");
//				email = json.get("Email");
//				phone = json.get("Phone");

			}
			
			
			
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int user_id = rs.getInt("id");				
			    String email = rs.getString("email");  
                String phone = rs.getString("phone_number");  

				// get the session object
				HttpSession session = request.getSession();
				// set the session timeout (in seconds)
//				session.setMaxInactiveInterval(60 * 6000); //1 hour session 
				// redirect to master page
				session.setAttribute("username", username);
				session.setAttribute("user_id", user_id);

				if(request.getParameter("username")== null || request.getParameter("username")== "") {
					 // Send a JSON response containing the user details 
	                response.setContentType("application/json");
	                response.setCharacterEncoding("UTF-8");
	            
	                String jsonString = String.format("{\"Success\": \"%s\", \"Error\": \"%s\", \"Username\": \"%s\", \"ID\": \"%s\", \"Email\": \"%s\", \"Phone\": \"%s\"}", "Successfully Log in!", "", username, user_id, email, phone);

	                out.print(jsonString);
	                out.flush();
	                return;
				}
				 
				request.getRequestDispatcher("HomeController").forward(request, response);
//				request.getRequestDispatcher("master.jsp").forward(request, response);
			} else {
				request.setAttribute("errorMessage", "User not found! <br>Invalid login credentials!");
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}

		} catch (SQLException e) {
			
			if(request.getParameter("username")== null || request.getParameter("username")== "") {
				 // Send a JSON response containing the user details
              response.setContentType("application/json");
              response.setCharacterEncoding("UTF-8");
              out.print("{\"Success\": \"" + "" + "\", \"Error\": \"" + e.getMessage() + "\", \"User\": {\"Username\": \"" + username + "\", \"Password\": \"" + password + "\"}}");
              out.flush();
              return;
			}
			
			out.println("<h1>" + e.getMessage() + e.getLocalizedMessage() + "</h1>");
			e.printStackTrace();
		}

	}

}

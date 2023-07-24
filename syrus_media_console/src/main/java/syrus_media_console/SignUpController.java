package syrus_media_console;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource; 

/**
 * Servlet implementation class SignUpController
 */
public class SignUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/syrus_media_console")
	private DataSource dataSource;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignUpController() {
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
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		request.getRequestDispatcher("signUp.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String confirmPass = request.getParameter("confirm_pass");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			
		try (Connection conn = dataSource.getConnection()) {
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
				confirmPass = json.get("ConfirmPassword");
				email = json.get("Email");
				phone = json.get("Phone");

			}
			
//			Logger.INFO("SignUp", "username: " + username +"password: " + password +"email: " + email );
			System.out.println( "username: " + username +" password: " + password + " confirm_password: " + confirmPass + " email: " + email);
			if (password.equals(confirmPass)) {
				String query = "insert into users(username,password,email,phone_number) values(?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(query);

				ps.setString(1, username);
				ps.setString(2, password);
				ps.setString(3, email);
				ps.setString(4, phone);
				int rowsAffected = ps.executeUpdate();
//				Success response to mobile
				if(request.getParameter("username")== null || request.getParameter("username")== "") {
					 // Send a JSON response containing the user details
	                response.setContentType("application/json");
	                response.setCharacterEncoding("UTF-8");
	                out.print("{\"Success\": \"" + "Successfully sign up!" + "\", \"Error\": \"" + "" + "\", \"User\": {\"Username\": \"" + username + "\", \"Password\": \"" + password + "\", \"Email\": \"" + email + "\", \"Phone\": \"" + phone + "\"}}");
	                out.flush();
	                return;
				}

//				Response to web
				if (rowsAffected > 0) {

					request.getRequestDispatcher("index.jsp").forward(request, response);
				} else {
					request.getRequestDispatcher("master.jsp").forward(request, response);
				}
				
			} else {
//				Mobile error password not match
				if(request.getParameter("username")== null || request.getParameter("username")== "") {
					 // Send a JSON response containing the user details
	                response.setContentType("application/json");
	                response.setCharacterEncoding("UTF-8");
	                out.print("{\"Success\": \"" + "" + "\", \"Error\": \"" + "The password doesn't match!" + "\", \"User\": {\"Username\": \"" + username + "\", \"Password\": \"" + password + "\", \"Email\": \"" + email + "\", \"Phone\": \"" + phone + "\"}}");
	                out.flush();
	                return;
				}
//				Web error password not match
				request.setAttribute("error", "Password and confirm password do not match.");
				request.getRequestDispatcher("signUp.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			if(request.getParameter("username")== null || request.getParameter("username")== "") {
				 // Send a JSON response containing the user details
               response.setContentType("application/json");
               response.setCharacterEncoding("UTF-8");
               out.print("{\"Success\": \"" + "" + "\", \"Error\": \"" + e.getMessage() + "\", \"User\": {\"Username\": \"" + username + "\", \"Password\": \"" + password + "\", \"Email\": \"" + email + "\", \"Phone\": \"" + phone + "\"}}");
               out.flush();
               return;
			}
			out.println("<h1>" + e.getMessage() + e.getLocalizedMessage() + "</h1>");
			e.printStackTrace();
		}
//		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}

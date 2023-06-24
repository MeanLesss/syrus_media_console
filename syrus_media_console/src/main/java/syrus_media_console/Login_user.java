package syrus_media_console;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String query = "select * from users where username=?and password = ?";
		PrintWriter out = response.getWriter();
		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(query);
			
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int user_id = rs.getInt("id");
				// get the session object
				HttpSession session = request.getSession();
				// set the session timeout (in seconds)
//				session.setMaxInactiveInterval(60 * 6000); //1 hour session 
				// redirect to master page
				session.setAttribute("username", username);
				session.setAttribute("user_id", user_id);

				request.getRequestDispatcher("master.jsp").forward(request, response);
			} else {
				request.setAttribute("errorMessage", "User not found! <br>Invalid login credentials!");
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}

		} catch (SQLException e) {
			out.println("<h1>" + e.getMessage() + e.getLocalizedMessage() + "</h1>");
			e.printStackTrace();
		}

	}

}

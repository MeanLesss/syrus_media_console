package syrus_media_console;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
		try (Connection conn = dataSource.getConnection()) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String confirmPass = request.getParameter("confirm_pass");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			if (password.equals(confirmPass)) {
				String query = "insert into users(username,password,email,phone_number) values(?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(query);

				ps.setString(1, username);
				ps.setString(2, password);
				ps.setString(3, email);
				ps.setString(4, phone);
				int rowsAffected = ps.executeUpdate();
				if (rowsAffected > 0) {

					request.getRequestDispatcher("index.jsp").forward(request, response);
				} else {
					request.getRequestDispatcher("master.jsp").forward(request, response);
				}

			} else {
				request.setAttribute("error", "Password and confirm password do not match.");
				request.getRequestDispatcher("signUp.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			out.println("<h1>" + e.getMessage() + e.getLocalizedMessage() + "</h1>");
			e.printStackTrace();
		}
//		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}

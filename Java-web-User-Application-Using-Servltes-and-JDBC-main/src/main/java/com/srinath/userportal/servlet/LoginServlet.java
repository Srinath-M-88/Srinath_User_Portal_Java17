package com.srinath.userportal.servlet;

import static java.sql.DriverManager.getConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class LoginServlet extends GenericServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnfEx) {
			// TODO Auto-generated catch block
			cnfEx.printStackTrace();
		}
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

		res.setContentType("text/html"); // MIME
		PrintWriter out = res.getWriter();

		out.println("<html>");
		out.println("<body>");
		out.println("<h1> Welcome to Srinath's User Portal </h1>");

		var username = req.getParameter("uname");
		var login_password = req.getParameter("lpass");

		var sqlQuery = "SELECT u.first_name, u.last_name FROM users u JOIN credentials c ON u.user_id = c.user_id WHERE c.username = ? AND c.login_password = ? ";

		try (var conn = getConnection("jdbc:mysql://localhost:3306/srinath_user_portal", "root", "Sr1n@th");
				var stmt = conn.prepareStatement(sqlQuery)) {
			
			stmt.setString(1, username);
			stmt.setString(2, login_password);
			var rs = stmt.executeQuery();
			if(rs.next()) {
				out.println("Welcome " + rs.getString(1) + " " + rs.getString(2));
			}else {
				out.println("Invalid Access. ");
			}

		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}

		

		out.println("<html>");
		out.println("</body>");
		out.println("<p><a href=\"index.html\">Back to Home</a></p>");
		out.println("</html>");

	}

}

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

public class PasswordUpdateServlet extends GenericServlet{
	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnfEx) {
			
			cnfEx.printStackTrace();
		}
	}
	
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html"); //MIME
		PrintWriter out = res.getWriter();
		
		
		out.println("<html>");
		out.println("<body>");
		out.println("<h1> Welcome to Srinath's User Portal </h1>");
		
		var uidparam = req.getParameter("uid");
		int user_id = Integer.parseInt(uidparam);
		var username = req.getParameter("uname");
		var new_password = req.getParameter("npass");

		var sqlQuery = "UPDATE credentials \r\n"
				+ "SET login_password = ?\r\n"
				+ "WHERE user_id = ? AND username = ?;";

		try (var conn = getConnection("jdbc:mysql://localhost:3306/srinath_user_portal", "root", "Sr1n@th");
				var stmt = conn.prepareStatement(sqlQuery);) {

			
			
			stmt.setString(1, new_password);
			stmt.setInt(2, user_id);
			stmt.setString(3, username);
			var noOfRowsEffected = stmt.executeUpdate();

			if (noOfRowsEffected != 0) {

				out.println("Password changed successfully");

			} else {
				
				out.println("Invalid user_id or Password");

			}

		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace(out);
		}
		
		out.println("<html>");
		out.println("</body>");
		out.println("<p><a href=\"index.html\">Back to Home</a></p>");
		out.println("</html>");
		
	}
	

}

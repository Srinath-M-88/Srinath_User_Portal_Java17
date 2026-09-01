package com.srinath.userportal.servlet;

import static java.sql.DriverManager.getConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class UserDirectoryServlet extends GenericServlet{
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
		res.setContentType("text/html"); //MIME
		PrintWriter out = res.getWriter();
		
		
		out.println("<html>");
		out.println("<body>");
		out.println("<h1> Welcome to Srinath's User Portal </h1>");
		out.println("<table border='2px' width='100%'>");
		out.println("<tr>");
		out.println("<th>User_Id</th>");
		out.println("<th>First Name</th>");
		out.println("<th>Last Name</th>");
		out.println("<th>Credential_Id</th>");
		out.println("<th>Username</th>");
		out.println("<th>Password</th>");
		out.println("</tr>");
		
		
		
		var sqlQuery = "SELECT \r\n"
				+ "    u.user_id,\r\n"
				+ "    u.first_name,\r\n"
				+ "    u.last_name,\r\n"
				+ "    c.credentials_id,\r\n"
				+ "    c.username,\r\n"
				+ "    c.login_password\r\n"
				+ "FROM users u\r\n"
				+ "INNER JOIN credentials c ON u.user_id = c.user_id;";

		try (var conn = getConnection("jdbc:mysql://localhost:3306/srinath_user_portal", "root", "Sr1n@th");
				var stmt = conn.prepareStatement(sqlQuery);
				var rs = stmt.executeQuery()) {
			
			while(rs.next()) {
				out.println("<tr>");
				out.println("<td>" + rs.getInt(1) + "</td>");
				out.println("<td>" + rs.getString(2) + "</td>");
				out.println("<td>" + rs.getString(3) + "</td>");
				out.println("<td>" + rs.getInt(4) + "</td>");
				out.println("<td>" + rs.getString(5) + "</td>");
				out.println("<td>" + rs.getString(6) + "</td>");
				 
				
				out.println("</tr>");
			}

		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
		
		out.println("</table>");
		out.println("<html>");
		out.println("</body>");
		out.println("</html>");
		
	}

}

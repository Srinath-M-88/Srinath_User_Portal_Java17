package com.srinath.userportal.servlet;

import static java.sql.DriverManager.getConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class RegisterServlet extends GenericServlet {
	@Override
	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnfEx) {
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

		var firstName = req.getParameter("fname");
		var lastName = req.getParameter("lname");
		var username = req.getParameter("uname");
		var login_password = req.getParameter("lpass");

		var sqlQuery1 = "INSERT INTO users(first_name, last_name) VALUES (?,?)";
		var sqlQuery2 = "INSERT INTO credentials(user_id, username, login_password) VALUES (?,?,?)";

		try (var conn = getConnection("jdbc:mysql://localhost:3306/srinath_user_portal", "root", "Sr1n@th");
		     var stmt1 = conn.prepareStatement(sqlQuery1, PreparedStatement.RETURN_GENERATED_KEYS);
		     var stmt2 = conn.prepareStatement(sqlQuery2, PreparedStatement.RETURN_GENERATED_KEYS)) {

		    
		    stmt1.setString(1, firstName);
		    stmt1.setString(2, lastName);
		    
		    var noOfRowsEffected = stmt1.executeUpdate();

		    if (noOfRowsEffected != 0) {
		        ResultSet rs = stmt1.getGeneratedKeys();
		        if (rs.next()) {
		            int userId = rs.getInt(1);
		            
		            out.println("Congratulations! Your User has been added!");
		            out.println("<br>Here is the User ID assigned: " + userId);

		            stmt2.setInt(1, userId);
		            stmt2.setString(2, username);
		            stmt2.setString(3, login_password);

		            var noOfRowsEffected1 = stmt2.executeUpdate();
		            if (noOfRowsEffected1 != 0) {
		                ResultSet rs2 = stmt2.getGeneratedKeys();
		                if (rs2.next()) {
		                    out.println("<br>Here is the Credentials ID assigned: " + rs2.getString(1));
		                }
		                rs2.close();
		            } else {
		                out.println("Failed to insert credentials.");
		            }
		        }
		        rs.close();
		    } else {
		        out.println("Please try again");
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

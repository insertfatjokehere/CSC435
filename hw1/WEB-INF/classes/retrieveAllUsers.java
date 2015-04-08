import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/retrieveAllUsers")
public class retrieveAllUsers extends HttpServlet {
   
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		HttpSession session = request.getSession(false);
        RequestDispatcher error = request.getRequestDispatcher("errLog");
        RequestDispatcher view = request.getRequestDispatcher("/viewAllUsers.jsp");
        String user = null;
        
        try {
            user = session.getAttribute("user").toString();
        } catch (NullPointerException ex) {
        	error.forward(request, response);
        }

        GetAllUsers all = new GetAllUsers(user);

        try {
        	String[] results = all.fetchUsers();
        	request.setAttribute("all_users", results);
        	view.forward(request, response);

        } catch (ClassNotFoundException | SQLException ex) {
			Logger.getLogger(retrieveAllUsers.class.getName()).log(Level.SEVERE, null, ex);
        }

	}

	public class GetAllUsers {

		String username;

		public GetAllUsers(String u) {
			username = u;
		}

		public String[] fetchUsers() throws ClassNotFoundException, SQLException {
	   	   	Connection con = null;
   	   		Statement st = null;
   	   		ResultSet rs = null;
   	   		ArrayList<String> results = new ArrayList<String>();
   	   		String[] resultsArray = null;

   	   		if (sqlLoginCount() == 1) {
   	   			
   	   			// Load Driver
   	   			Class.forName("com.mysql.jdbc.Driver");
   	   			// Connect to the database
   	   			con = DriverManager.getConnection("jdbc:mysql://localhost:8888/webservices?user=apache&password=tomcat");

   	   			st = con.createStatement();
   	   			// Grab everyone except for the current user
   	   			rs = st.executeQuery("SELECT Username FROM Users WHERE Username <> \'" + username + "\'");

   	   			while (rs.next()) {
   	   				String temp = rs.getString(1);
   	   				results.add(temp);
   	   			}

   	   			if (results.size() != 0 && results != null) {
   	   				resultsArray = results.toArray(new String[results.size()]);
   	   			}

            st.close();
            rs.close();
            con.close();
   	   			return resultsArray;

   	   		} else {
   	   			// returns empty if the user isn't in the table or it's empty
   	   			return resultsArray;
   	   		}
		}

		public int sqlLoginCount() throws ClassNotFoundException, SQLException {

  	    	// Declare MySQL Variables
   	   		Connection con = null;
   	   		Statement st = null;
   	   		ResultSet rs = null;

   	   		int rowCount = 0;
   	   		// Load Driver
   	   		Class.forName("com.mysql.jdbc.Driver");
   	   		// Connect to the database
   	   		con = DriverManager.getConnection("jdbc:mysql://localhost:8888/webservices?user=apache&password=tomcat");

	   		st = con.createStatement();
	   		rs = st.executeQuery("SELECT * FROM Users WHERE Username=\'" + username + "\'");
	   		while(rs.next()){
         		rowCount++;
       		}

      		// Always close all SQL connections
      		rs.close();
      		st.close();
      		con.close();

      		return rowCount;
    	}
	}
}
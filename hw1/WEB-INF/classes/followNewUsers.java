import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/followNewUsers")
public class followNewUsers extends HttpServlet {
   
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    
    	String[] selectedUsers = request.getParameterValues("selectUsers");

    	HttpSession session = request.getSession(false);
        RequestDispatcher error = request.getRequestDispatcher("errLog");
        RequestDispatcher view = request.getRequestDispatcher("/viewAddedUsers.jsp");


          	// try to get the username from the session
            String user = null;
            try {
                user = session.getAttribute("user").toString();
            } catch (NullPointerException ex) {
                // this is thrown when session.getAttribute("user").toString() returns
                // empty, so redirect the user to the login form
                error.forward(request, response);
            }


            try {
            if (selectedUsers.length != 0 && selectedUsers != null) {
           		Follow fol = new Follow(user);
           		int check = fol.getUsers(selectedUsers);
           		if (check == 0) {
           			// inserting was successful
           			request.setAttribute("added_users", selectedUsers);
           			view.forward(request, response);

           		} else {
           			error.forward(request, response);
           		}
            }

        } catch (ClassNotFoundException | SQLException ex) {
        	Logger.getLogger(followNewUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


  	public class Follow {
  		String username;
  		int theUserID;
  		public Follow(String u) {
  			username = u;
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

    	public int getUsers(String[] users) throws ClassNotFoundException, SQLException {
    		  	    	// Declare MySQL Variables
   	   		Connection con = null;
   	   		Statement st = null;
   	   		Statement tempSt = null;
   	   		Statement insertSt = null;
   	   		ResultSet rs = null;
   	   		ResultSet tempRS = null;


	   		if (sqlLoginCount() == 1) {
	   			   	   		// Load Driver
   	   		Class.forName("com.mysql.jdbc.Driver");
   	   		// Connect to the database
   	   	  con = DriverManager.getConnection("jdbc:mysql://localhost:8888/webservices?user=apache&password=tomcat");
	   			int[] userIDs = new int[users.length];
	   			
          for (int i = 0; i < users.length; i++) {
	   				st = con.createStatement();
	   				rs = st.executeQuery("SELECT User_ID FROM Users WHERE Username=\'" + users[i] + "\'");
	   				rs.next();
	   				int temp = rs.getInt("User_ID");
	   				userIDs[i] = temp;
	   				st.close();
	   				rs.close();
	   			}

	   			st = con.createStatement();
	   			rs = st.executeQuery("SELECT User_ID FROM Users WHERE Username=\'" + username + "\'");
	   			rs.next();
	   			theUserID = rs.getInt("User_ID");
	   			st.close();
	   			rs.close();
	   			// check if all the unique combos arent there
	   			for (int i = 0; i < userIDs.length; i++) {
	   				int checkCount = 0;

	   				tempSt = con.createStatement();
	   				tempRS = tempSt.executeQuery("SELECT * FROM Following WHERE Follower=\'" + theUserID + "\' AND Followee=\'" + userIDs[i] + "\'");
	   			
	   				while(tempRS.next()){
	   					checkCount++;
	   				}
	   				
	   				
	   				// if so, add them. simple
	   				if (checkCount == 0) {
	   					insertSt = con.createStatement();
	   					insertSt.executeUpdate("INSERT INTO Following (Follower, Followee) VALUES ('" + theUserID + "', '" + userIDs[i] + "')");
	   					insertSt.close();
	   				}

	   				tempRS.close();
	   				tempSt.close();
	   			}

	   			// return good
	   			return 0;
	   		} else {
	   			// error with login or bad sql
	   			return 1;
	   		}
	   		
    	}

  	}

}
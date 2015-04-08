import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/removeFollowers")
public class removeFollowers extends HttpServlet {
   
   	@Override
   	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	String[] unfollow = request.getParameterValues("unfollowUsers");
    	HttpSession session = request.getSession(false);
        RequestDispatcher error = request.getRequestDispatcher("errLog");
        // RequestDispatcher view = request.getRequestDispatcher("/getAllFollowers");

        

        String user = null;
            try {
            user = session.getAttribute("user").toString();
            } catch (NullPointerException ex) {
                // this is thrown when session.getAttribute("user").toString() returns
                // empty, so redirect the user to the login form
                error.forward(request, response);
            }   

            try {
            	if (unfollow.length != 0 && unfollow != null) {
            		UpdateFollowers update = new UpdateFollowers(user);
            		int check = update.deleteFollowers(unfollow);
            		if (check == 0) {
            			// view.forward(request, response);

            			// this is used since the /getAllFollowers servlet answers with a GET
            			// This servelt handles forms via POST, so to go from POST to GET
            			// Theres a method called PRG or POST/Redirect/GET
            			// This is used since it will "reload" the unfollow page
            			// 
            			// http://en.wikipedia.org/wiki/Post/Redirect/Get
            			
            			response.sendRedirect(getServletContext().getContextPath()+"/getAllFollowers");
            		} else {
            			error.forward(request, response);
            		}
            	} else {
            		error.forward(request, response);
            	}
        	} catch (ClassNotFoundException | SQLException ex) {
        		Logger.getLogger(removeFollowers.class.getName()).log(Level.SEVERE, null, ex);
        	}

    }

    public class UpdateFollowers {

    	String username;

    	public UpdateFollowers(String u){
    		username = u;
    	}

    	// this is to check if the user that is has a session variable
        // actually has a record on the User table.
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
    
        public int deleteFollowers (String[] unfollowList) throws ClassNotFoundException, SQLException {

            // Declare MySQL Variables
            Connection con = null;
            Statement st = null;
            Statement tempSt = null;
            ResultSet tempRs = null;
            ResultSet rs = null;

            if (sqlLoginCount() == 1) {
            	int theUserID;
            	int[] unfollowIDs = new int[unfollowList.length];
				// Load Driver
            	Class.forName("com.mysql.jdbc.Driver");
            	// Connect to the database
            	con = DriverManager.getConnection("jdbc:mysql://localhost:8888/webservices?user=apache&password=tomcat");

            	st = con.createStatement();
            	rs = st.executeQuery("SELECT User_ID FROM Users WHERE Username=\'" + username + "\'");
            	rs.next();
            	theUserID = rs.getInt("User_ID");
            	st.close();
	   			rs.close();

            	// grab the userIDs of the selected users            	
            	for (int i = 0; i < unfollowList.length; i++) {
            		st = con.createStatement();
            		rs = st.executeQuery("SELECT User_ID FROM Users WHERE Username=\'" + unfollowList[i] + "\'");
            		rs.next();
            		int temp =	rs.getInt("User_ID");
            		unfollowIDs[i] = temp;
            		st.close();
	   				rs.close();
            	}

            	for (int i = 0; i < unfollowIDs.length; i++) {
            		int tempCheck = 0;
            		// check to see if the record you want to delete exists
            		tempSt = con.createStatement();
            		tempRs = tempSt.executeQuery("SELECT * FROM Following WHERE Follower=\'" + theUserID + "\' AND Followee=\'" + unfollowIDs[i] + "\'");

            		while (tempRs.next()){
            			tempCheck++;
            		}
            		
            		// this need to be one since you have to delete
            		// while inserting would be == 0
            		if (tempCheck == 1) {
            			st = con.createStatement();
            			st.executeUpdate("DELETE FROM Following WHERE Follower=\'" + theUserID + "\' AND Followee=\'" + unfollowIDs[i] + "\'");
            			st.close();
            		}

            		tempRs.close();
            		tempSt.close();
            	}

            	con.close();
				return 0;
            } else {
            	return 1;
            }
        }
    }
}
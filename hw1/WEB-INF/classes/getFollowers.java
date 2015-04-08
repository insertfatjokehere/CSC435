import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/getFollowers")
public class getFollowers extends HttpServlet {
   
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	HttpSession session = request.getSession(false);
        RequestDispatcher error = request.getRequestDispatcher("errLog");
        RequestDispatcher view = request.getRequestDispatcher("/viewFollowers.jsp");

            String user = null;
            try {
            user = session.getAttribute("user").toString();
            } catch (NullPointerException ex) {
                // this is thrown when session.getAttribute("user").toString() returns
                // empty, so redirect the user to the login form
                error.forward(request, response);
            }   

            try {
                GetFollowerList list = new GetFollowerList(user);
                String[] followerList = list.fetchList();


                    request.setAttribute("follower_list", followerList);
                    view.forward(request, response);
            } catch (ClassNotFoundException | SQLException ex) {
            	Logger.getLogger(getFollowers.class.getName()).log(Level.SEVERE, null, ex);
            }


    }

    public class GetFollowerList {

    	String username;
    	public GetFollowerList(String u) {
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

        public String[] fetchList() throws ClassNotFoundException, SQLException {
            // Declare MySQL Variables
            Connection con = null;
            Statement st = null;
            ResultSet rs = null;
            String[] results = null;
            ArrayList<String> getUsers = new ArrayList<String>();
			// Load Driver
			if (sqlLoginCount() == 1) {
            	Class.forName("com.mysql.jdbc.Driver");
            	// Connect to the database
            	con = DriverManager.getConnection("jdbc:mysql://localhost:8888/webservices?user=apache&password=tomcat");

            	st = con.createStatement();
            
            	rs = st.executeQuery("SELECT User_ID FROM Users WHERE Username=\'" + username + "\'");
            	rs.next();
            	int userID = rs.getInt("User_ID");
            	rs = st.executeQuery("SELECT Users.Username FROM Users, Following WHERE Users.User_ID = Following.Followee AND Following.Follower='" + userID + "'");
        		
        		while(rs.next()){
        			String temp = rs.getString("Username");
        			getUsers.add(temp);
        		}

        		st.close();
        		rs.close();
        		con.close();

        		if (getUsers.size() != 0 && getUsers != null) {
           			results = getUsers.toArray(new String[getUsers.size()]);
           		} 

           		return results;
        	} else {
        		// this returns null results
        		return results;
        	}

        }
   
    
    }
}
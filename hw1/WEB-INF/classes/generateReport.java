import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/generateReport")
public class generateReport extends HttpServlet {

@Override
   	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
               HttpSession session = request.getSession(false);
               RequestDispatcher error = request.getRequestDispatcher("errLog");
               RequestDispatcher view = request.getRequestDispatcher("/selectReport.jsp");
               String user = null;
               try {
               	user = session.getAttribute("user").toString();
               } catch (NullPointerException ex) {
               	error.forward(request, response);
               }

               WactchConnector wc = new WactchConnector(user);
               
               try {

				        String[] results = wc.getStocks();
				        request.setAttribute("view_stocks", results);
				        view.forward(request, response);

                } catch (ClassNotFoundException | SQLException ex) {
                	Logger.getLogger(retrieveWatchedStocks.class.getName()).log(Level.SEVERE, null, ex);
                }
    
    }

    public class WactchConnector {

    	String username;

    	public WactchConnector(String u){
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

    	public String[] getStocks() throws ClassNotFoundException, SQLException {
  	    	
    		ArrayList<String> results = new ArrayList<String>();
    		String[] returnResults = null;
  	    	// Declare MySQL Variables
   	   		Connection con = null;
   	   		Statement st = null;
   	   		ResultSet rs = null;

   	   		if (sqlLoginCount() == 1) {
   	   			// Load Driver
   	   			Class.forName("com.mysql.jdbc.Driver");
   	   			// Connect to the database
   	   			con = DriverManager.getConnection("jdbc:mysql://localhost:8888/webservices?user=apache&password=tomcat");

 
           		st = con.createStatement();
           		rs = st.executeQuery("SELECT WatchedCompanies.Symbol FROM WatchedCompanies, Users WHERE Users.User_ID = WatchedCompanies.User_ID AND Username=\'" + username + "\'");

           		while(rs.next()){
           			String temp = rs.getString(1);
           			results.add(temp);
           		}

           		st.close();
           		rs.close();
              con.close();
           		
              if (results.size() != 0 && results != null) {
           			returnResults = results.toArray(new String[results.size()]);
           		} 
    	
    			   return returnResults;
   	   		
   	   		} else {
            // returns empty if the user isn't in the table or it's empty
   	   			return returnResults;
   	   		}


    	}
    }

}
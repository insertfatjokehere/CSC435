import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/addStocks")
public class addStocks extends HttpServlet {
   
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

               // getParameterValues back an array while getParameter gives back
               // a string for one particular value.
               
               String[] requestedSymbols = request.getParameterValues("stockSymbol");
               HttpSession session = request.getSession(false);
               RequestDispatcher error = request.getRequestDispatcher("errLog");
               RequestDispatcher view = request.getRequestDispatcher("/viewAddedStocks.jsp");

               try {
                  // try to get the username from the session
                  String user = null;
                  try {
                     user = session.getAttribute("user").toString();
                  } catch (NullPointerException ex) {
                  // this is thrown when session.getAttribute("user").toString() returns
                  // empty, so redirect the user to the login form
                  error.forward(request, response);
                  }
                  
                  if (requestedSymbols != null && requestedSymbols.length != 0) {
                     // if the session is valid and the submitted array is not empty
                     addStocksConnector stock = new addStocksConnector(user);
                     int resultInsert = stock.connectAndAdd(requestedSymbols);
                     if (resultInsert == 0) {
                        // Inserting was successful. display results
                        request.setAttribute("added_stocks", requestedSymbols);
                        view.forward(request, response);
                     } else {
                        error.forward(request, response);
                     }
                  
                  }

               } catch (ClassNotFoundException | SQLException ex) {
                  Logger.getLogger(addStocks.class.getName()).log(Level.SEVERE, null, ex);
               }
   }

   public class addStocksConnector {

   		String username;

   		public addStocksConnector(String u) {
   			username = u;
   		}
   			public int connectAndAdd(String[] symbols) throws ClassNotFoundException, SQLException {
   				// Declare MySQL Variables
      			Connection con = null;
      			Statement st1 = null;
               Statement st2 = null;
               Statement stCheck = null;
      			ResultSet rs = null;
               ResultSet rsCheck = null;
               int checkCount = 0;
      			// Load Driver
      			Class.forName("com.mysql.jdbc.Driver");
      			// Connect to the database
      			con = DriverManager.getConnection("jdbc:mysql://localhost:8888/webservices?user=apache&password=tomcat");
               if (sqlLoginCount() == 1) {

                     // if ther username in the database exist, grab the userID and insert
                     st1 = con.createStatement();
                     rs = st1.executeQuery("SELECT User_ID FROM Users WHERE Username=\'" + username + "\'");
                     rs.next();
                     int userID = rs.getInt("User_ID");
                     rs.close();
                     st1.close();

            	  for (int i = 0; i < symbols.length; i++) {
                     // Because JDBC throws a wrapped SQLException with inserting
                     // into a table with an unique constraint. it is wise to check
                     // if this value exist of not. So count the number of times a
                     // the value matches. if the value is 0, then the unique
                     // value doesn't exist (which means it's safe to insert), else
                     // just ignore since it exists already.

                     checkCount = 0;
                     stCheck = con.createStatement();
                     rsCheck = stCheck.executeQuery("SELECT * FROM WatchedCompanies WHERE User_ID=\'" + userID + "\' AND Symbol=\'" + symbols[i] + "\'");


                     while(rsCheck.next()){
                        checkCount++;
                     }
                     
                     stCheck.close();
                     rsCheck.close();
                     

                     if (checkCount == 0) {
                        st2 = con.createStatement();
                        st2.executeUpdate("INSERT INTO WatchedCompanies (User_ID, Symbol) VALUES ('" + userID + "', '" + symbols[i] + "')");
                    }
      			   
                  }

      			   st2.close();
      			   con.close();
                  // executed statements were successful
                  return 0;
               } else {
                  // error with login information or queries.
                  return 1;
               }

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
   
   }

}
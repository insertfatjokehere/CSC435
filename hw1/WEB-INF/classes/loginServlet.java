import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
   
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      

      // Set the response MIME type of the response message
      response.setContentType("text/html;charset=UTF-8");

      // Allocate a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
  
      // Prepare for redirects / Forwards
      RequestDispatcher successfull = request.getRequestDispatcher("/successLogin");
      RequestDispatcher error = request.getRequestDispatcher("/errorLogin");

      // Get the user login values
      String user = request.getParameter("username");
      String pass = request.getParameter("password");

      try {
        
         
         int count = 0;
         Connector conn = new Connector(user, pass);
          try {
              count = conn.sqlLoginCount();
          } catch (ClassNotFoundException | SQLException ex) {
              Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
          }
         if (count == 1) {
            
            // If the username and password match to ONE and only ONE instance
            // in the User table (which is unique), the user typed in the correct 
            // username and password login credentials

            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            successfull.forward(request, response);

         } else {
            // Login was bad, either there was 0 matches or a sql error
            error.forward(request,response);  
         }

      } finally {
        out.close();  // Always close the output writer, resultSet, statement and connection
      }
    
    } // end of doGet

public class Connector {
    
    String un;
    String pw;
    
    public Connector(String u, String p){
       un = u;
       pw = p;
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
      rs = st.executeQuery("SELECT * FROM Users WHERE Username=\'" + un + "\' AND Password=\'" + pw + "\'");
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
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;

@WebServlet("/returnJSON")
public class returnJSON extends HttpServlet {
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
    // Output the response to a JSON format
    response.setContentType("application/json; charset=UTF-8");
    PrintWriter out = response.getWriter();

    try {
      // Object empty = null;
      // get the username info from the url
      GetUserInfo userInfo = new GetUserInfo(request.getParameter("username"));
      JSONObject results = new JSONObject();
      JSONArray symbols = null;
      JSONArray followers = null;

      if (userInfo.sqlLoginCount() != 1) {
        // if there was only less or more than one user
        // There is a null object with JSONObjects
        // http://www.json.org/javadoc/org/json/JSONObject.html#NULL
        results.put("username", results.NULL);
        out.print(results.toString());
      } else {
        results.put("username", userInfo.username);
        // out.println("Debug: Fetching results");
        String[] followerList = userInfo.getFollowers();                  
        // out.println("Debug: Following length: " + followerList.length);
        // out.println("Debug: Following: " + followerList.equals(null));
        
        if (followerList.length != 0 && followerList != null) {
          results.put("followerCount", followerList.length);
          followers = new JSONArray();
          for (int i = 0; i < followerList.length; i++) {
            followers.put(followerList[i]);
          }
          results.put("followers", followers);
        } else {
          results.put("followerCount", 0);
          followers = new JSONArray("[]");
          results.put("followers", followers);
        }

        String[] symbolList = userInfo.getStocks();

        if (symbolList.length != 0 && symbolList != null) {
          results.put("symbolCount", symbolList.length);
          symbols = new JSONArray();
          for (int i = 0; i < symbolList.length; i++) {
            symbols.put(symbolList[i]);
          }
          results.put("symbols", symbols);
        } else {
          results.put("symbolCount", 0);
          symbols = new JSONArray("[]");
          results.put("symbols", symbols);
        }
      
        out.print(results.toString());
      }


    } catch (JSONException | ClassNotFoundException | SQLException ex) {
        Logger.getLogger(returnJSON.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      out.close();
    }
    
  }

    public class GetUserInfo {
      String username;

      public GetUserInfo(String u) {
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
        

          while(rs.next()) {
            rowCount++;
          }


          // Always close all SQL connections
          rs.close();
          st.close();
          con.close();

          return rowCount;
      }

      public String[] getFollowers() throws ClassNotFoundException, SQLException {
          // Declare MySQL Variables
          Connection con = null;
          Statement st = null;
          ResultSet rs = null;
          String[] values = null;
          
          // Load Driver
          Class.forName("com.mysql.jdbc.Driver");
          // Connect to the database
          con = DriverManager.getConnection("jdbc:mysql://localhost:8888/webservices?user=apache&password=tomcat");

          st = con.createStatement();
          rs = st.executeQuery("SELECT User_ID FROM Users WHERE Username=\'" + username + "\'");
          rs.next();
          int id = rs.getInt("User_ID");

          st = con.createStatement();
          rs = st.executeQuery("SELECT Users.Username FROM Users, Following WHERE Following.Follower=\'" +
                                id + "\' AND Users.User_ID = Following.Followee");

          ArrayList<String> getUserFollowing = new ArrayList<String>();

          while(rs.next()){
            String temp = rs.getString("Username");
            getUserFollowing.add(temp);
          }
          
        
          values = getUserFollowing.toArray(new String[getUserFollowing.size()]);
          rs.close();
          st.close();
          con.close();
          return values;
      }


      public String[] getStocks() throws ClassNotFoundException, SQLException {
          Connection con = null;
          Statement st = null;
          ResultSet rs = null;
          String[] results = null;
          // Load Driver
          Class.forName("com.mysql.jdbc.Driver");
          // Connect to the database
          con = DriverManager.getConnection("jdbc:mysql://localhost:8888/webservices?user=apache&password=tomcat");

          st = con.createStatement();
          rs = st.executeQuery("SELECT User_ID FROM Users WHERE Username=\'" + username + "\'");
          rs.next();
          int id = rs.getInt("User_ID");

          st = con.createStatement();
          rs = st.executeQuery("SELECT Symbol FROM WatchedCompanies WHERE User_ID=\'" + id + "\'");
          ArrayList<String> list = new ArrayList<String>();
          while(rs.next()){
            String temp = rs.getString("Symbol");
            list.add(temp);
          }

          results = list.toArray(new String[list.size()]);
          rs.close();
          st.close();
          con.close();
          return results;
      }


    }
}
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;

@WebServlet("/getStockCategory")
public class getStockCategory extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

      // Set the response MIME type of the response message
      response.setContentType("text/html;charset=UTF-8");
      // Allocate a output writer to write the response message into the network socket
      RequestDispatcher view = request.getRequestDispatcher("/selectCategory2.jsp");

      try {
         
         // get query
         String url1 = "https://query.yahooapis.com/v1/public/yql?q=";
         String query = "select name from yahoo.finance.sectors";
         String url2 = "store://datatables.org/";
         String full = url1 + URLEncoder.encode(query, "UTF-8").replace("+", "%20") + 
                       "&format=json&diagnostics=false&env=" + URLEncoder.encode(url2, "UTF-8") + "alltableswithkeys&callback=";
         // String full =  "https://query.yahooapis.com/v1/public/yql?q=select%20name%20from%20yahoo.finance.sectors&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
         URL getQuery = new URL(full);
         InputStream input = getQuery.openStream();
         JSONObject getResults = new JSONObject(new JSONTokener(input));

         // JSON PARSER
         
         JSONObject jsonQuery = getResults.getJSONObject("query");
         String[] sectorNames = new String[jsonQuery.getInt("count")];
         // DEBUG
         // out.println("<p> Query: " + jsonQuery.toString() + "</p>\n");
         JSONObject results = jsonQuery.getJSONObject("results");

         // DEBUG
         // out.println("<p> Results " + results.toString() + "</p>\n");
         JSONArray list = results.getJSONArray("sector");

         for (int i = 0; i < list.length(); i++) {
            JSONObject temp = list.getJSONObject(i);
            sectorNames[i] = (String)temp.get("name");
         }
         
         request.setAttribute("sector_names" , sectorNames);
         request.setAttribute("query", full);
         view.forward(request, response);

      } catch (JSONException ex) {
         Logger.getLogger(getStockCategory.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
}
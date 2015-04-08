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

@WebServlet("/getStockIndustry")
public class getStockIndustry extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    RequestDispatcher view = request.getRequestDispatcher("/selectIndustry2.jsp");

    String url1 = "https://query.yahooapis.com/v1/public/yql?q=";
	String query = "select industry from yahoo.finance.sectors where name=\"" + request.getParameter("industry") + "\"";
	String url2 = "store://datatables.org/";
	String full = url1 + URLEncoder.encode(query, "UTF-8").replace("+", "%20") + 
                   "&format=json&diagnostics=false&env=" + URLEncoder.encode(url2, "UTF-8") + "alltableswithkeys&callback=";
    // Example with Basic Materials
	// String full =  "https://query.yahooapis.com/v1/public/yql?q=select%20industry%20from%20yahoo.finance.sectors%20where%20name%3D%22Basic%20Materials%22&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
    
    try {
        URL getQuery = new URL(full);
        InputStream input = getQuery.openStream();
        JSONObject getResults = new JSONObject(new JSONTokener(input));

        // JSON PARSER
         
        JSONObject jsonQuery = getResults.getJSONObject("query");
        String[] industryNames = new String[jsonQuery.getInt("count")];
        int[] industryID = new int[jsonQuery.getInt("count")];
        // DEBUG
        // <p> Query: " + jsonQuery.toString() + "</p>\n");
        JSONObject results = jsonQuery.getJSONObject("results");

        // DEBUG
        // <p> Results " + results.toString() + "</p>\n");
        JSONArray list = results.getJSONArray("sector");

        for (int i = 0; i < list.length(); i++) {
            JSONObject temp1 = list.getJSONObject(i);
            JSONObject temp2 = temp1.getJSONObject("industry");
            industryID[i] = temp2.getInt("id");
            industryNames[i] = (String)temp2.get("name");
        } 
         
        request.setAttribute("industry_name" , industryNames);
        request.setAttribute("industry_ID", industryID);
        request.setAttribute("selected_sector", request.getParameter("industry"));
        request.setAttribute("query", full);
        view.forward(request, response);

    } catch (JSONException ex) {
         Logger.getLogger(getStockIndustry.class.getName()).log(Level.SEVERE, null, ex);
    }

    }
}
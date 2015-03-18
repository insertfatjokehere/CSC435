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

@WebServlet("/getStockCompany")
public class getStockCompany extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    

    RequestDispatcher view = request.getRequestDispatcher("/selectCompany2.jsp");

    String url1 = "https://query.yahooapis.com/v1/public/yql?q=";
	String query = "select * from yahoo.finance.industry where id=\"" + request.getParameter("id") + "\"";
	String url2 = "store://datatables.org/";
	String full = url1 + URLEncoder.encode(query, "UTF-8").replace("+", "%20") + 
                   "&format=json&diagnostics=false&env=" + URLEncoder.encode(url2, "UTF-8") + "alltableswithkeys&callback=";
    // Example with ID = 110
	// String full = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.industry%20where%20id%3D%22110%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";

  	try {
 		URL getQuery = new URL(full);
    	InputStream input = getQuery.openStream();
    	JSONObject getResults = new JSONObject(new JSONTokener(input));

    	// JSON PARSER
         
    	JSONObject jsonQuery = getResults.getJSONObject("query");

    	// DEBUG
    	// <p> Query: " + jsonQuery.toString() + "</p>\n");
    	JSONObject results = jsonQuery.getJSONObject("results");

    	JSONObject ind = results.getJSONObject("industry");
    	// DEBUG
    	// <p> Results " + results.toString() + "</p>\n");
    	JSONArray list = ind.getJSONArray("company");

        String[] companyNames = new String[list.length()];
        String[] companySymbol = new String[list.length()];
        
    	for (int i = 0; i < list.length(); i++) {
    	JSONObject temp1 = list.getJSONObject(i);
    	companyNames[i] = (String)temp1.get("name");
    	companySymbol[i] = (String)temp1.get("symbol");
		}

		request.setAttribute("selected_industry", request.getParameter("industry"));
		request.setAttribute("selected_sector", request.getParameter("sector"));
		request.setAttribute("company_name", companyNames);
		request.setAttribute("company_symbol", companySymbol);
		request.setAttribute("query", full);

		view.forward(request, response);

  	} catch (JSONException ex) {
         Logger.getLogger(getStockCompany.class.getName()).log(Level.SEVERE, null, ex);
    }



    }
}
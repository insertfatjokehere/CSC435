package controllers;

import java.io.IOException;

import org.json.JSONException;

import play.*;
import play.mvc.*;
import play.mvc.Http.Session;
import play.data.Form.*;
import play.data.DynamicForm;
import views.html.*;
import play.libs.F.Option;

@SuppressWarnings("unused")
public class Application extends Controller {

	// Note Use return TODO for temporary use 
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    public static Result loginCheck() {
        String user = session("user");
        if(user != null) {
           //  return ok("Hello " + user);
           return (loginSuccess(user));
        } else {
            // session("user", "temp");
            return unauthorized(form.render("Oops, havent logged in"));
        }
    }

    public static Result logout() {
        session().clear();
        return ok("Logged Out");
    }
    
    public static Result loginSuccess(String username) {
        return ok(success.render("login successful", username));
    }
    
    public static Result login() {
        // change this later with database
        // add in error login
        DynamicForm df = play.data.Form.form().bindFromRequest();
        session("user", df.get("username"));
        String user = session("user");
        return (loginSuccess(user));
    }
    
    public static Result dash() {
        String user = session("user");
        return ok(dashboard.render(user));
    }
    
    public static Result getWatchedStocks() {
    	String user = session("user");
    	String[] stocks = new data.WatchedStocks().fetchStocks(user);
    	return ok(fetchedStocks.render(stocks));
    }
    
    public static Result generateReport(Option<String> stocksym, Option<String> time, Option<String> duration, Option<String> cType, Option<String> cScale) {
    	String user = session("user");
    	String[] stocks = new data.WatchedStocks().fetchStocks(user);
    	String base = "http://chart.finance.yahoo.com/z?s=";
    	String sym = request().getQueryString("stocksym");
    	if (sym != null) { base = base + sym; }
    	
    	return ok(reports.render(stocks, base));
    }
    
    public static Result sectors() {
    	String user = session("user");
    	return ok(sectors.render(user));
    }
    
    public static Result industry(String sec) {
    	String user = session("user");
    	int[] gint = new data.GetIndustries().getLinkId(sec);
    	String[] gstr = new data.GetIndustries().getLinkNames(sec);
    	return ok(industry.render(user, sec, gint, gstr));
    }
    
    public static Result companies(String sec, int indID) {
    	// select * from yahoo.finance.industry where id="110"
    	// https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.industry%20where%20id%3D%22110%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=
    	String user = session("user");
    	
    	String base = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.industry%20where%20id%3D%22";
    	String tail = "%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
    	String url = base + indID + tail;
    	data.GetCompanies get = new data.GetCompanies();
    	try {
			get.fetcher(url);
	    	String[] compNames = get.getCompNames();
	    	String[] compSymb = get.getCompSymb();
	    	
	    	if(compNames != null && compSymb != null){
	    		return ok(companies.render(user, compNames, compSymb, sec, indID));
	    	} else {
	    		return TODO;
	    	}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return TODO;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return TODO;
		}
    	
    }
}

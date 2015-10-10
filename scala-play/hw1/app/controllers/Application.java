package controllers;

import com.avaje.ebean.Ebean;
import data.UserJson;
import models.Following;
import models.Users;
import models.WatchedCompanies;
import org.json.JSONException;
import play.data.DynamicForm;
import play.libs.F.Option;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
        String pass = df.get("password");
        String user = df.get("username");
        
        Users check = Users.authenticate(user, pass);
        if(check != null){
        	session("user", user);
        	return (loginSuccess(user));
        	
        } else {
            // https://www.playframework.com/documentation/2.0/api/java/play/mvc/Results.html
        	return unauthorized(views.html.defaultpages.unauthorized.render());
        }
        
    }
    
    public static Result dash() {
        String user = session("user");
        if (user != null) {
            return ok(dashboard.render(user));
        } else {
            return unauthorized(views.html.defaultpages.unauthorized.render());
        }
    }
    
    public static Result getWatchedStocks() {
    	String user = session("user");
        if (user != null) {
            Users search = Users.find.where().eq("Username", user).findUnique();
            String[] stocks = new data.WatchedStocks().fetchStocks(search);
            return ok(fetchedStocks.render(stocks));
        } else {
            return unauthorized(views.html.defaultpages.unauthorized.render());
        }
    }
    
    public static Result generateReport(Option<String> stocksym, Option<String> time, Option<String> duration, Option<String> cType, Option<String> cScale) {

        String user = session("user");
        if (user != null) {
            Users search = Users.find.where().eq("Username", user).findUnique();
            String[] stocks = new data.WatchedStocks().fetchStocks(search);
            String base = "http://chart.finance.yahoo.com/z?s=";
            String sym = request().getQueryString("stocksym");
            if (sym != null) {
                base = base + sym;
            }

            return ok(reports.render(stocks, base));
        } else {
            return unauthorized(views.html.defaultpages.unauthorized.render());
        }
    }
    
    public static Result sectors() {
    	String user = session("user");
        if (user != null) {
            return ok(sectors.render(user));
        } else {
            return unauthorized(views.html.defaultpages.unauthorized.render());
        }
    }
    
    public static Result industry(String sec) {
    	String user = session("user");
        if (user != null) {
            int[] gint = new data.GetIndustries().getLinkId(sec);
            String[] gstr = new data.GetIndustries().getLinkNames(sec);
            return ok(industry.render(user, sec, gint, gstr));
        } else {
            return unauthorized(views.html.defaultpages.unauthorized.render());
        }
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
	    		return noContent();
	    	}
		} catch (JSONException e) {

			return TODO;
		} catch (IOException e) {

			return TODO;
		}
    	
    }

    public static Result addCompanies(){
        String user = session("user");
        if (user != null) {
            Users userName = Users.find.where().eq("Username", user).findUnique();
            Map<String, String[]> map = request().body().asFormUrlEncoded();
            String[] selected = map.get("stockSymbol"); // get selected topics
            if (selected == null || selected.length == 0) {
                return TODO;
            } else {
                for (String list : selected) {
                    new WatchedCompanies(userName, list).save();
                }
                return ok(addStocks.render(selected));
            }
        } else {
            return unauthorized(views.html.defaultpages.unauthorized.render());
        }
    }

    public static Result getOtherUsers(){
        String user = session("user");
        if (user != null) {
            List<Users> list = Ebean.find(Users.class).where().ne("Username", user).findList();
            if (list != null || list.size() == 0) {
                return ok(viewOtherUsers.render(user, list));
            } else {
                return TODO;
            }
        } else {
            return unauthorized(views.html.defaultpages.unauthorized.render());
        }
    }

    public static Result getFollowers() {
        String user = session("user");
        if (user != null) {
            Users search = Users.find.where().eq("Username", user).findUnique();
            // List<Following> userList = Ebean.find(Following.class).where().eq("follower", search).findList();
            List<Following> userList = search.followingList;
            ArrayList<String> userListNames = new ArrayList<String>();
            for (Following list : userList) {
                // debug
                // System.out.println(list.following);
                userListNames.add(list.following);
            }

            String[] list = userListNames.toArray(new String[userListNames.size()]);
            return ok(viewFollowing.render(user, list));
        } else {
            return unauthorized(views.html.defaultpages.unauthorized.render());
        }
    }

    public static Result removeFollowers() {
        String user = session("user");
        if (user != null) {
            Map<String, String[]> map = request().body().asFormUrlEncoded();
            String[] checkedVal = map.get("selectedUser"); // get selected topics
            Users userName = Users.find.where().eq("Username", user).findUnique();
            if (checkedVal != null || checkedVal.length == 0) {
                for (String list : checkedVal) {
                    Following follow = Following.find.where().eq("follower", userName).eq("following", list).findUnique();
                    Ebean.delete(follow);
                }
                return ok(remove.render(checkedVal));
            } else {
                return TODO;
            }
        } else {
            return unauthorized(views.html.defaultpages.unauthorized.render());
        }
    }

    public static Result addFollowers() {
        String user = session("user");
        if (user != null) {
            Map<String, String[]> map = request().body().asFormUrlEncoded();
            String[] checkedVal = map.get("userSelected"); // get selected topics
            Users userName = Users.find.where().eq("Username", user).findUnique();
            if (checkedVal != null || checkedVal.length == 0) {
                for (String list : checkedVal) {
                    Users temp = Users.find.where().eq("Username", list).findUnique();
                    new Following(userName, temp).save();
                }
                return ok(remove.render(checkedVal));
            } else {
                return TODO;
            }
        } else {
            return unauthorized(views.html.defaultpages.unauthorized.render());
        }
    }

    public static Result json(String username) {
        Users user = Users.find.where().eq("Username", username).findUnique();
        UserJson userCast = new UserJson(user);
        return ok(play.libs.Json.toJson(userCast));
    }


}

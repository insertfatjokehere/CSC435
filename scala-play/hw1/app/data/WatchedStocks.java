package data;
import java.util.ArrayList;
import java.util.List;

import models.*;

public class WatchedStocks {

	public String[] fetchStocks(Users user){
		// Make a proper fetch
		
		// Users search = Users.find.where().eq("Username", user).findUnique();
		// String[] results = {"ACU.L", "AIS.V", "AQR.AX" ,"ARG.TO"};
		List<WatchedCompanies> wc = user.watchedcompanies;
		ArrayList<String> tempList = new ArrayList<String>();
		
		for (WatchedCompanies watches : wc) {
			// System.out.println("Debug: " + watches.Symbol);
			tempList.add(watches.Symbol);
		}
		
		String[] results = tempList.toArray(new String[tempList.size()]);
		return results;
	}
}

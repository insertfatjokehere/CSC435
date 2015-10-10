import play.*;
import play.libs.*;
import com.avaje.ebean.Ebean;
import models.*;
import java.util.*;

public class Global extends GlobalSettings {
    @Override
    public void onStart(Application app) {
        // Check if the database is empty
        if (Users.find.findRowCount() == 0) {
        	Users apache = new Users("apache", "tomcat");
        	apache.save();
       
        	new WatchedCompanies(apache, "ACU.L").save();
        	new WatchedCompanies(apache, "AIS.V").save();
        	new WatchedCompanies(apache, "AQR.AX").save();

			Users user1 = new Users("user1", "one");
			user1.save();
        	Users user2 = new Users("user2", "two");
			user2.save();

			new Following(apache, user1).save();
			new Following(apache, user2).save();
			new Following(user1, user2).save();
        }
    }
}
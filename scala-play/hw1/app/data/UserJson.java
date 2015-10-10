package data;

import models.Following;
import models.Users;
import java.util.List;

public class UserJson {

    public Long User_ID;
    public String username;
    public String[] watchedCompanies;
    public String[] followingList;

    public UserJson(Users user){
        this.User_ID = user.User_ID;
        this.username = user.Username;

        try {
            this.watchedCompanies = new WatchedStocks().fetchStocks(user);
        } catch (NullPointerException e) {
            this.watchedCompanies = null;
        }



        try {
            List<Following> followTemp = user.followingList;
            this.followingList = new String[followTemp.size()];

            for (int i = 0; i < followTemp.size(); i++) {
                followingList[i] = followTemp.get(i).following;
            }

        } catch (NullPointerException e) {
            this.followingList = null;
        }

    }
}

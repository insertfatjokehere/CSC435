package models;

import java.util.List;



import play.db.ebean.*;
import play.data.validation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class Users extends Model  {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO) // auto increment
	public Long User_ID;
	
	@Constraints.Required
	@NotNull
	@Column(unique=true)
	public String Username;
	
	@Constraints.Required
	@NotNull
	public String Password;
	
	@OneToMany(mappedBy="user")
	public List<WatchedCompanies> watchedcompanies;


	@OneToMany(mappedBy = "follower")
	public List<Following> followingList;
	
	public Users(String user, String pass) {
		this.Username = user;
		this.Password = pass;
	}
	
	public static Finder<Long, Users> find = new Finder<Long, Users>(Long.class, Users.class);
	
	public static Users authenticate(String user, String pass) {
        return find.where().eq("Username", user)
            .eq("Password", pass).findUnique();
    }


}

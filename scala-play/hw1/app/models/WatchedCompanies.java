package models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import play.data.validation.Constraints;
import play.db.ebean.*;

@Entity
// @Table(uniqueConstraints={@UniqueConstraint(columnNames = {"user" , "Symbol"})})
public class WatchedCompanies extends Model {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long combo_ID;
	
	@ManyToOne
	@NotNull
	@Constraints.Required
	public Users user;

	@NotNull
	@Constraints.Required
	public String Symbol;
	
	public WatchedCompanies(Users user, String sym){
		this.user = user;
		this.Symbol = sym;

	}
	
	public static Finder<Long, WatchedCompanies> find = new Finder<Long, WatchedCompanies>(Long.class, WatchedCompanies.class);
}

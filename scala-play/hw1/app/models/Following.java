package models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import play.data.validation.Constraints;
import play.db.ebean.*;

@Entity
// @Table(uniqueConstraints={@UniqueConstraint(columnNames = {"follower" , "following"})})
public class Following extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long combo_ID;

    @ManyToOne
    @Constraints.Required
    @NotNull
    public Users follower;

    @Constraints.Required
    @NotNull
    public String following;

    public Following(Users userFollow, Users userFollowing){
        this.follower = userFollow;
        this.following = userFollowing.Username;

    }

    public static Finder<Long, Following> find = new Finder<Long, Following>(Long.class, Following.class);



}

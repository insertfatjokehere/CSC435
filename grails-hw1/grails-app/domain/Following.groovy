/**
 * Created by kgb on 4/23/15.
 */
class Following {

    Long id
    String followee
    static belongsTo = Users
    static hasMany = [user: Users]


    def contraints = {
        followee blank: false, nullable: false
    }

}

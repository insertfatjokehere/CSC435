/**
 * Created by kgb on 4/22/15.
 */
class WatchedCompanies {

    Long id
    String symbol
    static belongsTo = Users
    static hasMany = [user: Users]

    def contraints ={
        symbol blank: false, nullable: false
    }
}

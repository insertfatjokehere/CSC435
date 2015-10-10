
class Users {

    Long id
    String username
    String password
    static hasMany = [watched: WatchedCompanies, follow: Following]

    def contraints = {
        username blank: false, nullable: false, unique: true
        password blank: false, nullable: false
    }

    static mapping = {
        follow (cascade: "all-delete-orphan")
        watched(cascade: "all-delete-orphan")
    }

}

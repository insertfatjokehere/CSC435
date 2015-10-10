class BootStrap {

    def init = { servletContext ->
        new Users(username: "apache", password: "tomcat")
                .addToWatched(new WatchedCompanies(symbol: "ACU.L"))
                .addToWatched(new WatchedCompanies(symbol: "AIS.V"))
                .addToWatched(new WatchedCompanies(symbol: "AQR.AX"))
                .addToFollow(new Following(followee: "user1"))
                .addToFollow(new Following(followee: "user2"))
                .save()
        new Users(username: "user1", password: "one").save()
        new Users(username: "user2", password: "two").save()
    }
    def destroy = {
    }
}

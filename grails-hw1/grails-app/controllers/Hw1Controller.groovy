import data.Globals;
import data.GetCompanies
import grails.converters.JSON

class Hw1Controller {


    def index = {}

    def loginCheck() {
        def user = session["user"]
        if (user != null) {
            // goto sucessful
            render(view: 'successfulLogin.gsp')
        } else {
            // goto login
            render(view: 'form.gsp')
        }
    }

    def login = {
        // initial data is stored in grails-app/init/BootStrap
        def userCheck = Users.findByUsernameAndPassword(params['username'], params['password'])

        if (userCheck) {
            session["user"] = userCheck
            //  render "user: " + userCheck['username']
            render(view: 'successfulLogin.gsp')
        } else {
            // redirect(action: "some method")
            render(view: 'errorLogin.gsp')
        }
    }

    def dash = {
        if (session["user"]) {
            render(view: 'dash.gsp')
        } else {
            render(view: 'form.gsp')
        }
    }

    def logout = {
        session.invalidate()
        render(view: 'logout.gsp')
    }

    def selectCategory = {}

    def selectIndustry = {
        // http://www.groovyconsole.appspot.com/script/271001
        def val = params['industry']
        def list
        switch (val) {
            case "Basic Materials":
                list = [Globals.bml, Globals.bmn].transpose().collectEntries { it }
                break
            case "Consumer Goods":
                list = [Globals.cgl, Globals.cgn].transpose().collectEntries { it }
                break
            case "Financial":
                list = [Globals.fnl, Globals.fnn].transpose().collectEntries { it }
                break
            case "Healthcare":
                list = [Globals.hcl, Globals.hcn].transpose().collectEntries { it }
                break
            case "Industrial Goods":
                list = [Globals.igl, Globals.ign].transpose().collectEntries { it }
                break
            case "Services":
                list = [Globals.svl, Globals.svn].transpose().collectEntries { it }
                break
            case "Technology":
                list = [Globals.tcl, Globals.tcn].transpose().collectEntries { it }
                break
            case "Utilities":
                list = [Globals.utl, Globals.utn].transpose().collectEntries { it }
                render list
                break
            default:
                list = [null: null]
        }

        render(view: 'selectIndustry', model: [selected: val, mapList: list])

    }

    def selectCompany = {
        // select * from yahoo.finance.industry where id="110"
        // https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.industry%20where%20id%3D%22110%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=
        def base = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.industry%20where%20id%3D%22"
        def tail = "%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback="
        def url = base + params['id'] + tail
        def get = new GetCompanies()
        try {
            get.fetcher(url)

            def compName = get.compNames
            def compIDs = get.compSymb
            def mapList = [compIDs, compName].transpose().collectEntries { it }

            if (compIDs && compName) {
                // render mapList
                render(view: 'selectCompany', model: [mapList: mapList, sector: params['sector'], industry: params['industry'], id: params['id']])
            } else {
                render 'Error: Couldnt Retieve Industry Info'
            }
        } catch (Exception e) {
            render 'error'
        }

    }

    def followStocks = {
        // http://stackoverflow.com/questions/6567514/force-grails-to-treat-a-string-parameter-as-collection-of-string
        def user = Users.findByUsername(session.user['username'])
        if (params['stockSymbol'] && user) {
            // params.list() makes it so if there is only one selection, it makes
            // a list instead of a string
            def list = params.list('stockSymbol')
            for (stocks in list) {
                if (!user.watched['symbol'].contains(stocks))
                    user.addToWatched(new WatchedCompanies(symbol: stocks))
            }
            // render user.watched['symbol']
            render(view: 'followStocks', model: [list: list])
        } else {
            render 'You Did not select anything or you are not logged in'
        }
    }


    def viewStocks = {
        def user = Users.findByUsername(session.user['username'])
        if (user) {
            def list = []
            for (wc in user.watched) {
                list.add(wc.symbol)
            }
            render(view: 'viewStocks.gsp', model: [list: list])
        } else {
            render(view: 'errorLogin.gsp')
        }
    }

    def report = {
        def user = Users.findByUsername(session.user['username'])
        if (user) {
            def list = user.watched.symbol
            // render list
            render(view: 'report.gsp', model: [list: list])
        } else {
            render(view: 'errorLogin.gsp')
        }
    }

    def getAllFollowers = {
        def user = Users.findByUsername(session.user['username'])
        if (user) {
            def list = user.follow.followee
            // render list
            render(view: 'getAllFollowers.gsp', model: [list: list])
        } else {
            render(view: 'errorLogin.gsp')
        }
    }

    def viewUsers = {
        def user = Users.findAllByUsernameNotEqual(session.user['username'])
        if (user) {
            def list = user.username
            // render list
            render(view: 'viewUsers.gsp', model: [list: list])
        } else {
            render(view: 'errorLogin.gsp')
        }
    }

    def followNewUser = {
        def user = Users.findByUsername(session.user['username'])
        if (user) {
            if (params['selectUsers']) {
                def list = params.list('selectUsers')
                for (person in list) {
                    if (!user.follow['followee'].contains(person)) {
                        user.addToFollow(new Following(followee: person)).save(flush: true)
                    }
                }
                // render list
                render(view: 'addedUsers.gsp', model: [list: list])
            } else {
                render "No Users selected"
            }
        } else {
            render(view: 'errorLogin.gsp')
        }

    }

    def removeYourFollowers = {
        def user = Users.findByUsername(session.user['username'])
        if (user) {
            def list = params.list('unfollowUsers')
            if (list) {
                for (person in list) {
                    def temp = Following.findByFollowee(person)
                    user.removeFromFollow(temp).save(flush: true)
                }
                render(view: 'removedUsers.gsp', model: [list: list])
                // render list
            } else {
                render "No Users selected"
            }
        } else {
            render(view: 'errorLogin.gsp')
        }
    }

    def json = {
        def user = Users.findByUsername(params['username'])
        if (user) {
            def list1 = []
            for (stocks in user.watched) {
                list1.add(stocks.symbol)
            }

            def list2 = []
            for (person in user.follow) {
                list2.add(person.followee)
            }
            def result = [
                    'username'      : user.username,
                    'Watched Stocks': list1 ? list1 : null,
                    'Following'     : list2 ? list2 : null
            ]
            render result as JSON
        } else {
            def empty = [
                    'username': null
            ]
            render empty as JSON
        }

    }
}

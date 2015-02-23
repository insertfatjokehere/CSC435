
# CSC435 - Homework 1 - Kyle Bashford

## Assignment Details - Defined by Alex

  1. Think from the point of view of a human client who wants you to build a useful web application, and write an elaborate requirements specification document. Include as many details as you can.

    In addition, you should specify the following technical information:

    * Back-end functionality: explain what the logic of your project is. This is the part that does the useful work.

    * Points of contact of the back-end with the outside world: define the external interfaces to your logic. It will be best if you both consume external web services (weather, movie, etc.) and expose some of your application for external clients to consume.

    * Web tier: describe an interface to allow browsers to consume your back-end logic. You can make this as complex as you want, but I recommend you keep it simple. This is not a course in Web design; a beautiful browser interface is great, but not at the cost of functionality.

  2. Choose two of the end-points you identified in your project document, and build two Servlets (not JSPs) that represent them. One of those should be **stateful** (i.e., it should store/retrieve user information in an object associated with the session), and the other---**stateless**.

## Project Overview/Scope

The scope of this project will be based on the viewing and tracking of stocks. The project will retrieve stocks from [Yahoo! Finance](https://code.google.com/p/yahoo-finance-managed/wiki/YQLAPI) (via Yahoo! Query Language). Users would have to signin to the service, and all personal information, including 'watched/watching' stocks would be account bound. 

Example: 
     
    User Logins to the webservice.
    The login credentials is compared to a MySQL table.
    User views the stock he/she is watching.
    A table is pulled up with all the stocks that the user watches.
    The user can choose to view a graph of a companies stocks
    The server will compile an image and serve back to the user
    The service allows important queries to be watched

Retrieving this information is stateless since the servlet would have to request stocks every time to gain information. Generating the graphs would be stateless since the servlet would query to Yahoo! about the values, and serves the graph back to the user.

Where this project shares the statefullness is when the user logins to the service. The webservice also needs to be stateful if user accounts hold information about the user's watched stocks

## Setup of Project (as of Homework 1)

For the inital setup, I'll be using a local instance of [Apache Tomcat 8.0+](http://tomcat.apache.org/download-80.cgi), along with MySQL (This setup was done on a windows machine). This maybe temporary, but initally this is how the project will go. Later, I'll try to use my MySQL on the Moxie server to simulate a more accurate latency with the webservice. For the mean time, a local instance of [MySQL 5.6 Server](http://dev.mysql.com/downloads/file.php?id=455350) is fine.

Additional files that were needed for this project was the [Connector/J Driver](http://dev.mysql.com/downloads/file.php?id=454397) for the MySQL server. The other library needed to parse Yahoo Query Language in JSON format was the [java-json.jar](http://www.java2s.com/Code/Jar/j/Downloadjavajsonjar.htm), since parsing in XML would take longer with bandwidth. However, there will be copies for these .jar files in the .war file with the directory of (webapp/WEB-INF/lib)

Apache Tomcat is confirgured on windows by following these [instructions](http://www.ntu.edu.sg/home/ehchua/programming/howto/Tomcat_HowTo.html). And setting up MySQL server with these [instructions](http://www.ntu.edu.sg/home/ehchua/programming/sql/MySQL_HowTo.html)

To compile the project, 
      
    I stored the Connector/J (mysql-connector-java-5.1.34-bin.jar) and java-json.jar into `C:\tomcat\lib`. 
    I would change directory to where my webapp classes was: `cd C:\tomcat\webapps\hw1\WEB-INF\classes`. 
    Then I would run java compiler with `C:\tomcat\webapps\hw1\WEB-INF\classes>javac -cp .;C:\tomcat\lib\java-json.jar;C:\tomcat\lib\servlet-api.jar *.java`.

## Example Servlets for Submittion

The two examples I will use to demonstrate stateful and stateless of the service will be:

  1. A login page, where the user will sign in (validate with mysql user table), login to service. If the user is still in a session, if they start back at the welcome page, they would be redirected successfully

  2. The stateless servlet will come into play after someone has successfully logged into the service. After they go into the login page 

### Flow of the login servlets

These are the following files related to the login system for the Webservice (excluding web.xml) that encompass the statefull service of assignment.

  1. index.html
  2. loginCheckerServlet.java
  3. form.html
  4. loginServlet.java
  5. errorLogin.java
  6. successLogin.java

The user would start at the Welcome/Index page (localhost:9999/hw1) and send a POST request to the loginCheckServlet.java

The loginCheckerServlet (localhost:9999/hw1/loginCheck) would check if the user has a session, by getting the attribute of the HTTPSession. If the HTTPSession is not `NULL`, they would be redirected to the success page

In the case the user didn't have the HTTPSession, the loginChecker would redirect the user to ther form.html (localhost:9999/hw1/form.html) where they would have to login. The MySQL server for testing purposes only has one user `Apache` with the password of `Tomcat`. (source.sql in the hw1 folder has the temporary skeleton of the database).

After the user submits the form. The POST request is sent to the loginServlet (localhost:9999/hw1/login) where the credentials are checked against the MySQL table `Webservices.Users`. If the the results return 1 row, the login was successfull and is redirected to the success login page with a HTTPSession, else they go to the error login page. 

The errorLogin servlet is straight forward, the servlet generates a redirect page to the form.html

The Success loginpage generates a link for the user to go onto the other part of the assignment (stateless servlet).

### Stateless Servlet

The stateless servlet so far is only consisted of getStockCategory.java.

After the user has logged in the service, they can go to the main page (localhost:9999/hw1/selectCategory).

The page generated generates the [query](https://query.yahooapis.com/v1/public/yql?q=select%20name%20from%20yahoo.finance.sectors&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=) to the Yahoo Finance Table and retrieve the sectors/categories of industries in JSON format.

The page will display the time (from the JSON file) the query is executed and will list the sectors. The page maynot be exciting, but it shows how the java servlet is extracting the information from [JSON](http://www.json.org/javadoc/org/json/JSONObject.html) file. The project would parse a XML version of the query, so a JSON library doesnt need to be imported, but since XML require more bytes to generate, this will cause the webservice to be slower than using JSON. 

This servlet is stateless since the user can refresh the page, and the page doesnt check wether there is a session involved, and the time the query will change everytime the user refreshes.
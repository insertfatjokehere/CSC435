import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/dashboardServlet")
public class dashboardServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

	response.setContentType("text/html;charset=UTF-8");
	RequestDispatcher error = request.getRequestDispatcher("/errorLogin");

    PrintWriter out = response.getWriter();
 //    HttpSession session = request.getSession(false);
 //    String user = session.getAttribute("user").toString();

    try {

    HttpSession session = request.getSession(false);
    String user = session.getAttribute("user").toString();
     out.println("<!DOCTYPE html>");
     out.println("<html>");
     out.println("<head>");
     out.println("<meta charset=\"UTF-8\">");
     out.println("<title>" + user +" - Dashboard</title>");
     out.println("</head>");
     out.println("<body>");
     out.println("<div style=\"text-align:left;\"><a href=\"/hw1/dash\">Home</a> <span style=\"float:right;\"><a href=\"/hw1/logout.jsp\">logout</a></span></div><hr>\n");
     out.println("<ul>");
     out.println("<li><a href=\"/hw1/selectCategory\"> Add Company / Stocks to Watch </a></li>");
     out.println("<li><a href=\"/hw1/viewStocks\">View Watched Stocks</a></li>");
     out.println("<li><a href=\"/hw1/viewUsers\">Follow Other users</li>");
     out.println("<li><a href=\"/hw1/getAllFollowers\">Manage Your Followings</a></li>");
     out.println("<li><a href=\"/hw1/report\">Generate Report</a></li>");
     out.println("</ul>");
     out.println("</body>");
     out.println("</html>");

    } catch (NullPointerException ex) {
      error.forward(request, response);

    } finally {
    	out.close();
    }
  
  }

}
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/successLogin")
public class successLogin extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

      // Set the response MIME type of the response message
      response.setContentType("text/html;charset=UTF-8");
      // Allocate a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();

      // String user = request.getParameter("user");
      // String pass = request.getParameter("pass");
      HttpSession session = request.getSession(false);
      // String user = session.getAttribute("user").toString();
      try {
         out.println("<html>");
         out.println("<body>");
         // out.println("<p>User: " + user + "\n Pass: " + pass + "</p>");
         out.println("<p> Login was successfull </p>");
         out.println("<a href=\"/hw1/selectCategory\"> Click Here to view the stock quotes </a>");
         out.println("</body>");
         out.println("</html>");

      } finally {
		  out.close();  // Always close the output writer
      }
    }
}
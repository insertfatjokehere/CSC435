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
      String user = session.getAttribute("user").toString();

      try {
         out.println("<!DOCTYPE html>");
         out.println("<html>");
         out.println("<head>");
         out.println("<meta charset=\"UTF-8\">");
         out.println("<title>Successful Login</title>");
         out.println("</head>");
         out.println("<body>");
         // out.println("<p>User: " + user + "\n Pass: " + pass + "</p>");
         out.println("<p> Login was successfull </p>");
         // out.println("<form id=\"redirectDash\" method=\"post\" action=\"/hw1/dash\">");
         // out.println("<input type=\"hidden\" name=\"sub\" value=\"\" />");
         // out.println("<a onclick=\"document.getElementById(\'redirectDash\').submit();\"> <u> Click to go to dashboard </u> </a>");
         // out.println("</form>");

         out.println("<a href=\"/hw1/dash\"> Click to go to your dashboard</a>");
         out.println("</body>");
         out.println("</html>");

      } finally {
		  out.close();  // Always close the output writer
      }
    }
}
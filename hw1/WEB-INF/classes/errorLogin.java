import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/errorLogin")
public class errorLogin extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

      // Set the response MIME type of the response message
      response.setContentType("text/html;charset=UTF-8");
      // Allocate a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();

      try {
         out.println("<html>");
         out.println("<body>");
         out.println("<p> Error Page </p>");
         // out.println("<form method=\"link\" action=\"/hw1/form.html\">");
         // out.println("<input type=\"submit\" value=\"Login Again\"");
         // out.println("</form>");
         out.println("<a href=\"/hw1/form.html\">Login Again</a>");
         out.println("</body>");
         out.println("</html>");

      } finally {
		  out.close();  // Always close the output writer
      }
    }
}
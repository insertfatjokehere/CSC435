import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/loginCheckerServlet")

public class loginCheckerServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 

       HttpSession session = request.getSession(false);

       // Checking if session is null is not smart
       // Check for an attrubute instead.

       try {

        String user = session.getAttribute("user").toString();
        RequestDispatcher successfull = request.getRequestDispatcher("/successLogin");
        successfull.forward(request, response);
       
       } catch (NullPointerException ex) {

        response.sendRedirect("form.jsp");
        
       }


    } 
}
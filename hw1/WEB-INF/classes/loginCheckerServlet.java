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

       if (session != null) {

		 RequestDispatcher successfull = request.getRequestDispatcher("/successLogin");
		 successfull.forward(request, response);

       } else {

         // RequestDispatcher login = request.getRequestDispatcher("form.html");
         // login.forward(request, response);

       	 response.sendRedirect("form.html");
       
       }
    } 
}
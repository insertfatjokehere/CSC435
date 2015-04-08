import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/clearUser")
public class clearUser extends HttpServlet {
   
   	@Override
   	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	HttpSession session = request.getSession(false);
    	RequestDispatcher view = request.getRequestDispatcher("/logout.jsp");
    	session.invalidate();
    	view.forward(request, response);

    }
}
package vn.cmcti.richard.sample.peeper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PeeperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PeeperServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

	    PrintWriter out = response.getWriter();

	    out.println("<html>");
	    out.println("<head>");
	    out.println("<title>Peeper</title>");
	    out.println("</head>");
	    out.println("<body>");
	    out.println("<h1>Peeper!</h1>");

	    try {
	      List<String> allPeeps = PeeperServletContextListener.DATA_STORE.findAllPeeps();
	      for (String peep : allPeeps) {
	        out.println(peep);
	        out.print("<br/><br/>");
	      }
	    } catch (Exception e) {
	      throw new ServletException("Error listing peeps", e);
	    }

	    out.println("<form name=\"htmlform\" method=\"post\" action=\"peep\">");

	    out.println("Enter your peep: <input type=\"text\" name=\"peep\" maxlength=\"142\" size=\"30\"/>");

	    out.println("<input type=\"submit\" value=\"Peep!\"/>");
	    out.println("</form>");
	    out.println("</body>");
	    out.println("</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String peepText = request.getParameter("peep");

	    try {

	      PeeperServletContextListener.DATA_STORE.addPeep(peepText);
	      response.sendRedirect("peep");

	    } catch (Exception e) {
	      throw new ServletException("Error saving peep", e);
	    }
	}

}

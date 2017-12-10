

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Application;
import service.DuplicatedAppIDException;
import service.RegisterationService;

/**
 * Servlet implementation class RegistrationVerifyServlet
 */
@WebServlet("/appverify")
public class RegistrationVerifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationVerifyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.getWriter().print("<html><body>");
		response.getWriter().print("Don't support GET method");
		response.getWriter().print("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String appid = request.getParameter("appid");
		String token = request.getParameter("token");
		JsonBuilder json = new JsonBuilder();
		if(appid != null) {
			RegisterationService service = RegisterationService.getInstance();
			boolean login = service.verify(appid, token);
			if(login) {
				json.put("result", "1");
			}else {
				json.put("result", "-1");
			}
			
		}
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.getJson());
	}

}

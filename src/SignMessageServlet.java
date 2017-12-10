

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.RegisterationService;
import service.SignatureService;
import service.UnknownApplicationException;

/**
 * Servlet implementation class SignMessageServlet
 */
@WebServlet("/sign")
public class SignMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignMessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String appid = request.getParameter("appid");
		String token = request.getParameter("token");
		String message = request.getParameter("message");
		JsonBuilder json = new JsonBuilder();
		if(appid != null) {
			SignatureService service = SignatureService.getInstance();
			try {
				String sig = service.sign(appid, token, message);
				json.put("result", "1");
				json.put("signature", sig);
			} catch (UnknownApplicationException e) {
				json.put("result", "-1");
			}
		}
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.getJson());
	}

}

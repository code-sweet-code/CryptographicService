

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.SignatureService;
import service.UnknownApplicationException;

/**
 * Servlet implementation class SignVerifyServlet
 */
@WebServlet("/verify")
public class SignVerifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignVerifyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String appid = request.getParameter("appid");
		String signature = request.getParameter("signature");
		String message = request.getParameter("message");
		JsonBuilder json = new JsonBuilder();
		if(appid != null) {
			SignatureService service = SignatureService.getInstance();
			boolean verResult = service.verify(appid, message, signature);
			if(verResult) {
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

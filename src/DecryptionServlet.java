

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.EncryptedMessage;
import service.EncryptionService;
import service.UnknownApplicationException;

/**
 * Servlet implementation class DecryptionServlet
 */
@WebServlet("/decrypt")
public class DecryptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DecryptionServlet() {
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
		String tAppid = request.getParameter("targetAppID");
		String key = request.getParameter("key");
		String iv = request.getParameter("iv");
		JsonBuilder json = new JsonBuilder();
		if(appid != null) {
			EncryptionService service = EncryptionService.getInstance();
			try {
				String plainText = service.decrypt(appid, token, tAppid, message, key, iv);
				json.put("result", "1");
				json.put("message", plainText);
			} catch (UnknownApplicationException e) {
				json.put("result", "-1");
			}
		}
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.getJson());
	}

}

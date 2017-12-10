

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.EncryptedMessage;
import service.EncryptionService;
import service.SignatureService;
import service.UnknownApplicationException;

/**
 * Servlet implementation class EncryptionServlet
 */
@WebServlet("/encrypt")
public class EncryptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EncryptionServlet() {
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
		JsonBuilder json = new JsonBuilder();
		if(appid != null) {
			EncryptionService service = EncryptionService.getInstance();
			try {
				EncryptedMessage encryptedMsg = service.encrypt(appid, token, tAppid, message);
				json.put("result", "1");
				json.put("encryptedMessage", encryptedMsg.getContent());
				json.put("key", encryptedMsg.getKey());
				json.put("iv", encryptedMsg.getIV());
			} catch (UnknownApplicationException e) {
				json.put("result", "-1");
			}
		}
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.getJson());
	}

}

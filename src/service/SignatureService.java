package service;

public class SignatureService {
	private static final SignatureService instance = new SignatureService();
	private SignatureService() {}
	
	public static SignatureService getInstance() {
		return instance;
	}
	
	public String sign(String appID, String token, String message) throws UnknownApplicationException {
		Application app = new Application(appID);
		app.setToken(new Token(token));
		ApplicationMap appMap = new ApplicationMap(app);
		if(appMap.verify() == true) {
			app = appMap.extract();
			Message newMsg = new Message(message);
			Signer signer = new Signer(app);
			byte[] mac = signer.sign(newMsg);
			newMsg.setMAC(new MessageAuthenticationCode(mac));
			return newMsg.getMAC().getString();
		}else {
			throw new UnknownApplicationException();
		}
	}
	
	public boolean verify(String signerAppID, String msg, String signature) {
		ApplicationMap appMap = new ApplicationMap(signerAppID);
		if(appMap.exist()) {
			Application app = appMap.extract();
			Message message = new Message(msg);
			message.setMAC(new MessageAuthenticationCode(signature));
			Signer signer = new Signer(app);
			return signer.verify(message);
		}
		return false;
	}
}

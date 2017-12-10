package service;

import java.io.IOException;

public class RegisterationService {
	private static final RegisterationService instance = new RegisterationService();
	private RegisterationService() {}
	
	public static RegisterationService getInstance() {
		return instance;
	}
	
	public Application register(String appID) throws DuplicatedAppIDException {
		ApplicationMap appMap = new ApplicationMap(appID);
		if(!appMap.exist()) {
			Application newApp = new Application(appID);
			newApp.setToken(Token.generateToken());
			newApp.setKeyPair(KeyMaker.makeKeyPair());
			appMap.update(newApp);
			try {
				DataManager.getInstance().sync();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return newApp;
		}else {
			throw new DuplicatedAppIDException();
		}
	}
	
	public boolean verify(String appID, String token) {
		Application app = new Application(appID);
		app.setToken(new Token(token));
		ApplicationMap appMap = new ApplicationMap(app);
		return appMap.verify();
	}
}

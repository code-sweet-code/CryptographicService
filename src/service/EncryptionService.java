package service;

public class EncryptionService {
	private static final EncryptionService instance = new EncryptionService();
	private EncryptionService() {}
	
	public static EncryptionService getInstance() {
		return instance;
	}
	
	public EncryptedMessage encrypt(String appID, String token, String targetAppID, String message) throws UnknownApplicationException {
		Application myApp = new Application(appID);
		Application targetApp = new Application(targetAppID);
		myApp.setToken(new Token(token));
		ApplicationMap myAppMap = new ApplicationMap(myApp);
		ApplicationMap tAppMap = new ApplicationMap(targetApp);
		if(myAppMap.verify() == true && tAppMap.exist()) {
			myApp = myAppMap.extract();
			targetApp = tAppMap.extract();
			Encryptor encryptor = new Encryptor(myApp, targetApp);
			Message newMsg = new Message(message);
			return encryptor.encrypt(newMsg);
		}else {
			throw new UnknownApplicationException();
		}
	}
	
	public String decrypt(String appID, String token, String sourceAppID, String message, String key, String iv) throws UnknownApplicationException {
		Application myApp = new Application(appID);
		Application sourceApp = new Application(sourceAppID);
		myApp.setToken(new Token(token));
		ApplicationMap myAppMap = new ApplicationMap(myApp);
		ApplicationMap sAppMap = new ApplicationMap(sourceApp);
		if(myAppMap.verify() == true && sAppMap.exist()) {
			myApp = myAppMap.extract();
			sourceApp = sAppMap.extract();
			Encryptor encryptor = new Encryptor(myApp, sourceApp);
			EncryptedMessage eMsg = new EncryptedMessage(message, key, iv);
			Message plainMsg = encryptor.decrypt(eMsg);
			return plainMsg.getString();
		}else {
			throw new UnknownApplicationException();
		}
	}
}

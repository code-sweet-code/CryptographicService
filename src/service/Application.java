package service;

import java.security.KeyPair;

public class Application {
	private String appID;
	private Token token;
	private KeyPair keyPair;
	
	Application(String appID){
		this.appID = appID;
	}
	
	public void setToken(Token token) {
		this.token = token;
	}
	
	public Token getToken() {
		return token;
	}
	
	public String getID() {
		return appID;
	}

	public void setKeyPair(KeyPair keyPair) {
		this.keyPair = keyPair;
	}
	
	public KeyPair getKeyPair() {
		return keyPair;
	}
}

package service;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

public class ApplicationMap {
	private Application app;

	public ApplicationMap(String appID) {
		app = new Application(appID);
	}

	public ApplicationMap(Application app) {
		this.app = app;
	}

	public boolean exist() {
		DataManager manager = DataManager.getInstance();
		if(manager.searchApp(app.getID()) != null) {
			return true;
		}
		return false;
	}

	public void update(Application newApp) {
		if(newApp != null) {
			app = newApp;
		}
		List<String> attr = new LinkedList<String>();
		if(app.getToken() != null) {
			attr.add(app.getToken().getString());
		}
		KeyPair keyPair = app.getKeyPair();
		if(keyPair != null) {
			PublicKey pubKey = keyPair.getPublic();
			PrivateKey priKey = keyPair.getPrivate();
			attr.add(Base64.getEncoder().encodeToString(priKey.getEncoded()));
			attr.add(Base64.getEncoder().encodeToString(pubKey.getEncoded()));
		}
		DataManager manager = DataManager.getInstance();
		manager.updateApp(app.getID(), attr);
	}

	public boolean verify() {
		DataManager manager = DataManager.getInstance();
		List<String> attr = manager.searchApp(app.getID());
		if(attr != null && attr.size()>2) {
			String tokenStr = attr.get(0);
			if(tokenStr.equals(app.getToken().getString())) {
				return true;
			}
		}
		return false;
	}
	
	public Application extract() {
		DataManager manager = DataManager.getInstance();
		List<String> attr = manager.searchApp(app.getID());
		if(attr != null) {
			if(attr.size() == 1) {
				app.setToken(new Token(attr.get(0)));
			}else if(attr.size() == 3) {
				try {
					PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(attr.get(1)));
					X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(attr.get(2)));
					KeyFactory keyFactory;
					keyFactory = KeyFactory.getInstance("RSA");
					PrivateKey priKey = keyFactory.generatePrivate(priKeySpec);
					PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
					KeyPair keyPair = new KeyPair(pubKey, priKey);
					app.setKeyPair(keyPair);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return app;
	}

}

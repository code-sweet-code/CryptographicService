package service;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {
	private Application myApp;
	private Application targetApp;
	
	public Encryptor(Application myApp, Application targetApp) {
		this.myApp = myApp;
		this.targetApp = targetApp;
	}
	
	public EncryptedMessage encrypt(Message message) {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			SecretKey key = keyGen.generateKey();
			//Data cipherText = new Data(encryptMsg(message.getBinary(), key));
			Cipher msgCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			msgCipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptedMsg = msgCipher.doFinal(message.getBinary());
			Data cipherText = new Data(encryptedMsg);
			Data iv = new Data(msgCipher.getIV());
			Data encryptedKey = new Data(encryptKey(key));
			return new EncryptedMessage(cipherText, encryptedKey, iv);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	private byte[] encryptKey(SecretKey key) throws Exception {
		Cipher keyCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		keyCipher.init(Cipher.WRAP_MODE, targetApp.getKeyPair().getPublic());
		byte[] encryptedKey = keyCipher.wrap(key);
		return encryptedKey;
	}
	
	private SecretKey decryptKey(byte[] eKey) throws Exception{
		Cipher keyCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		keyCipher.init(Cipher.UNWRAP_MODE, myApp.getKeyPair().getPrivate());
		SecretKey key = (SecretKey) keyCipher.unwrap(eKey, "AES", Cipher.SECRET_KEY);
		
		return key;
	}
	
	private Message decryptMsg(byte[] eMsg, Key key, byte[] iv) throws Exception {
		Cipher msgCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		msgCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
		byte[] encryptedMsg = msgCipher.doFinal(eMsg);
		return new Message(new Data(encryptedMsg).getString("UTF-8"));
	}
	
	public Message decrypt(EncryptedMessage eMsg) {
		try {
			Data eKey = eMsg.getRawKey();
			SecretKey sKey = decryptKey(eKey.getBinary());
			Data iv = eMsg.getRawIV();
			Message decryptedMsg = decryptMsg(eMsg.getRawContent().getBinary(), sKey, iv.getBinary());
			return decryptedMsg;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

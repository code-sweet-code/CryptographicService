package service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.KeyGenerator;

public class Token implements DataOperator{
	private Data data;
	public Token(byte[] binary) {
		data = new Data(binary);
	}
	public Token(String token) {
		data = new Data(Base64.getDecoder().decode(token));
	}
	@Override
	public byte[] getBinary() {
		return data.getBinary();
	}
	@Override
	public String getString() {
		return data.getBase64();
	}

	public static Token generateToken() {
		Token newToken = new Token("");
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			Key key = keyGen.generateKey();
			byte[] byteKey = key.getEncoded();
			newToken = new Token(byteKey);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newToken;
	}
}

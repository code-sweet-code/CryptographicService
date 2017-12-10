package service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Digest implements DataOperator{
	private Data data;
	public Digest(byte[] binary) {
		data = new Data(binary);
	}
	
	public static Digest getDigest(String msg, String charset) {
		MessageDigest md;
		Digest digest = new Digest(new byte[1]);
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(msg.getBytes(charset));
			byte[] bDig = md.digest();
			digest = new Digest(bDig);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return digest;
	}

	@Override
	public byte[] getBinary() {
		return data.getBinary();
	}

	@Override
	public String getString() {
		return data.getBase64();
	}
}

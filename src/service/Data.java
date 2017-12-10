package service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Data {
	private byte[] binary;
	
	public Data(String data, String charset) {
		try {
			this.binary = data.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			this.binary = new byte[1];
			e.printStackTrace();
		}
	}
	
	public Data(byte[] bin) {
		if(bin == null) {
			this.binary = new byte[1];
		}
		this.binary = bin;
	}
	
	public byte[] getBinary() {
		return binary;
	}
	
	public String getString(String charset) {
		String str = "";
		try {
			str = new String(binary, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public String getBase64() {
		return Base64.getEncoder().encodeToString(binary);
	}
}

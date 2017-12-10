package service;

import java.security.Key;
import java.util.Base64;

public class EncryptedMessage {
	private Data eContent;
	private Data eKey;
	private Data iv;
	
	public EncryptedMessage(Data content, Data key, Data iv) {
		eContent = content;
		eKey = key;
		this.iv = iv;
	}
	
	public EncryptedMessage(String content, String key, String iv) {
		eContent = new Data(Base64.getDecoder().decode(content));
		eKey = new Data(Base64.getDecoder().decode(key));
		this.iv = new Data(Base64.getDecoder().decode(iv));
	}
	
	public String getContent() {
		return eContent.getBase64();
	}
	
	public String getKey() {
		return eKey.getBase64();
	}
	
	public String getIV() {
		return iv.getBase64();
	}
	
	public Data getRawContent() {
		return eContent;
	}
	
	public Data getRawKey() {
		return eKey;
	}
	
	public Data getRawIV() {
		return iv;
	}
}

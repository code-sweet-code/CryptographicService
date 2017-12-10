package service;

import java.util.Base64;

public class MessageAuthenticationCode implements DataOperator {
	private Data data;
	public MessageAuthenticationCode(byte[] binary) {
		data = new Data(binary);
	}
	public MessageAuthenticationCode(String sig) {
		data = new Data(Base64.getDecoder().decode(sig));
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

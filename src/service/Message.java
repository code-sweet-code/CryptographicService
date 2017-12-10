package service;

public class Message implements DataOperator{
	private Data data;
	private Digest digest;
	private MessageAuthenticationCode mac;
	
	public Message(String msg) {
		data = new Data(msg, "UTF-8");
	}
	
	public Digest getDigest() {
		if(digest == null) {
			digest = Digest.getDigest(getString(), "UTF-8");
		}
		return digest;
	}

	@Override
	public byte[] getBinary() {
		return data.getBinary();
	}

	@Override
	public String getString() {
		return data.getString("UTF-8");
	}
	
	public MessageAuthenticationCode getMAC() {
		return mac;
	}
	
	public void setMAC(MessageAuthenticationCode mac) {
		this.mac = mac;
	}
}

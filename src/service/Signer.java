package service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

public class Signer {
	private Application signer;
	private Signature sig;
	
	public Signer(Application app) {
		signer = app;
		try {
			sig = Signature.getInstance("SHA256withRSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte[] sign(Message msg) {
		byte[] signature = null;
		try {
			sig.initSign(signer.getKeyPair().getPrivate());
			sig.update(msg.getBinary());
			signature = sig.sign();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return signature;
	}
	
	public boolean verify(Message msg) {
		try {
			sig.initVerify(signer.getKeyPair().getPublic());
			sig.update(msg.getBinary());
			return sig.verify(msg.getMAC().getBinary());
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}

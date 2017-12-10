package service;

import java.security.KeyPair;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Test {

	public static void main(String[] args) {

		try {
			EncryptionService es = EncryptionService.getInstance();
			EncryptedMessage e = es.encrypt("zsbenq", "+oIIMW9zUZG11OjlftdNXA==", "zsbenq0418", "asdflakmfasdf");
			String p = es.decrypt("zsbenq0418", "BO+EQ12dYEBiZdDCRxDT+A==", "zsbenq", e.getContent(), e.getKey(), e.getIV());
			System.out.println(p);
			
		}catch(Exception e) {
			e.printStackTrace();
		} 
	}

}

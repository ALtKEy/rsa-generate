import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Rsa {
	//
	FileDao dao = null;
	
	public Rsa() {
		this.dao = new FileDao();
	}
	
	protected void encodePlainText(String plainText, String publicKeyPath) {
		//
		byte[] plainTextByte = plainText.getBytes();
		PublicKey publicKey = dao.readPublicKey(publicKeyPath);
		
		Cipher cipher = null;
		
		try {
			cipher = Cipher.getInstance("RSA");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			//
			e.printStackTrace();
		}
		
		byte[] encodeBytes = null;
		
		try {
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			encodeBytes = cipher.doFinal(plainTextByte);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			//
			e.printStackTrace();
		}
		
		dao.writeFile(encodeBytes, "D:/Temp/", "encodefile.dat");
		
	}
	
	protected byte[] decodeEncodingFile(String privateKeyPath) {
		//
		PrivateKey privateKey = dao.readPrivateKey(privateKeyPath);
		
		byte[] buffer = dao.readFile("D:/Temp/", "encodefile.dat");
		
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			//
			e.printStackTrace();
		}
		
		byte[] decodeBytes = null;
		
		try {
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			decodeBytes = cipher.doFinal(buffer);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			//
			e.printStackTrace();
		}
		
		return decodeBytes;
	}
}

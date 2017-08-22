import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * FileDao
 * 키를 읽고 쓰고를 담당합니다.
 * 
 * @author Kim Jung-tae (kim8907@naver.com)
 * @since 2017-08-22 022
 */
public class FileDao {
	//
	/**
	 * 키를 파일로 생성할때 사용합니다.
	 * 
	 * @param pair 키를 생성한 KeyPair 객체를 파라미터로 사용합니다.
	 * @throws IOException
	 */
	protected void writeKey(KeyPair pair) throws IOException {
		//
		// KeyPair 로 되어있는 부분을 각각 나눠줍니다.
		// 공개키(PublicKey) 말 그대로 공개를 하는 키 이 키로 암호화를 하고
		PublicKey publicKey = pair.getPublic();
		// 개인키(PrivateKey) 개인키로 복호화를 합니다.
		PrivateKey privateKey = pair.getPrivate();
		// 저장될 위치의 Path 테스트 하실때는 변경해서 사용
		String writePath = "D:/Temp/";
		// 폴더가 존재하지 않거나 파일이 중복될경우의 에러(예외)처리는 제외
		// FileOutputStream 을 이용하여 저장합니다.
		FileOutputStream publicFos = new FileOutputStream(writePath + "public.key");
		publicFos.write(publicKey.getEncoded());
		publicFos.close();
		// try catch 문을 생략하였기 때문에 close 가 조금 위험하지만 간단한 프로그램이므로 건너뛰었습니다.
		FileOutputStream privateFos = new FileOutputStream(writePath + "private.key");
		privateFos.write(privateKey.getEncoded());
		privateFos.close();
	}
	
	/**
	 * PublicKey 를 읽을때 사용합니다.
	 * 
	 * @param publicKeyPath 읽을 파일의 주소를 String 형태로 지정해서 사용합니다.
	 * @return PublicKey 를 반환합니다.
	 */
	protected PublicKey readPublicKey(String publicKeyPath) { 
		//
		// 읽을때는 FileInputStream 으로 사용했습니다.
		//  이번에는 throws 로 던지게 되면 너무 길어지니 try catch 문으로 묶었습니다.
		FileInputStream fileInputStream = null;
		// 리턴할 PublicKey 객체를 생성
		PublicKey returnPublicKey = null;
		try {
			fileInputStream = new FileInputStream(publicKeyPath);
			// 버퍼를 생성한다.
			byte[] buffer = null;
			// 버퍼에 들어갈 크기를 지정하기 위해 생성
			int buffBytes = 0;
			// byte를 세서 반환한뒤 buffByte에 넣습니다.
			buffBytes = fileInputStream.available();
			// 버퍼 배열을 초기화 한다
			buffer = new byte[buffBytes];
			// do ~ while 을 쓰기위해 임의의 count 변수를 생성
			int count = 0;
			do {
				// fileInputStream.read() 에는 반환이 INT형으로 반환이 되는데 -1이 될때 읽을게 없다라고 리턴합니다.
				count = fileInputStream.read(buffer);
				// 그러기때문에 count 에서는 0보다 초과할 경우를 생각하여 읽습니다.
			} while(count > 0);
			// byte로 읽은 내용을 인코딩을 하여 KeySpec에 담습니다.
			KeySpec keySpec = new X509EncodedKeySpec(buffer);
			// 키 펙토리를 생성한뒤에 초기화 합니다.
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			// 키 펙토리에 맞게 Public 키를 생성합니다.
			returnPublicKey = keyFactory.generatePublic(keySpec);
			
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException | NullPointerException e) {
			// 에러가 났을경우 print
			e.printStackTrace();
		} finally {
			try {
				if(fileInputStream != null) {
					// 이번에는 닫을때 에러를 예외처리를 해줬습니다.
					fileInputStream.close();
				}
			} catch (IOException e) {
				//
				System.out.println("파일을 닫는 도중 에러가 발생했습니다.");
				e.printStackTrace();
			}
		}
		// 생성된 공개키를 반환한다.
		return returnPublicKey;
	}
	
	/**
	 * PrivateKey 를 읽을때 사용합니다.
	 * 
	 * @param privateKeyPath 읽을 파일의 주소를 String 형태로 지정해서 사용합니다.
	 * @return PrivateKey 를 반환합니다.
	 */
	protected PrivateKey readPrivateKey(String privateKeyPath) {
		//
		// PublicKey 와 읽는 방식은 동일하지만 다른 부분만 주석처리
		FileInputStream fileInputStream = null;
		PrivateKey returnPrivateKey = null;
		try {
			fileInputStream = new FileInputStream(privateKeyPath);
			byte[] buffer = null;
			int buffBytes = 0;
			buffBytes = fileInputStream.available();
			buffer = new byte[buffBytes];
			int count = 0;
			do {
				count = fileInputStream.read(buffer);
			} while(count > 0);
			// 이번에는 X509가 아닌 PKCS8로 인코딩을 합니다.
			KeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			// generatePrivate 를 이용하여 생성하여 담습니다.
			returnPrivateKey = keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException | NullPointerException e) {
			// 에러가 났을경우 print
			e.printStackTrace();
		} finally {
			try {
				if(fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException e) {
				//
				System.out.println("파일을 닫는 도중 에러가 발생했습니다.");
				e.printStackTrace();
			}
		}
		return returnPrivateKey;
	}
}

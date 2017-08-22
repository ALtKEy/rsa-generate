import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

/**
 * RSA 를 생성하고 사용
 * 
 * @author Kim Jung-tae (kim8907@naver.com)
 * @since 2017-08-22 022
 */
public class RsaGenerate {
	//
	/**
	 * 키 생성을 담당한다
	 * @return KeyPair 객체를 반환
	 */
	private KeyPair keyGenerate() {
		//
		// 키 페어 제너레이터를 생성 로 RSA 할 예정이기에 ("RSA")로 지정했습니다.
		KeyPairGenerator generator = null;
		try {
			generator = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			//
			System.out.println("암호화 알고리즘을 찾을 수 없습니다.");
			e.printStackTrace();
		}
		// 난수 생성을 위해 SecureRandom 생성
		SecureRandom random = new SecureRandom();
		// 난수를 생성할 씨앗 값(숫자)을 입력
		random.setSeed(12);
		// RSA 키를 만들기위해 초기화
		generator.initialize(512, random);
		// 키 페어를 위해 생성한다 생성할때는 generator 에서 generateKeyPair로 생성하면 생성이 되어집니다.
		KeyPair pair = generator.generateKeyPair();
		
		return pair;
	}
	
	/**
	 * 스토리 형식의 확인 순으로 main 에서 진행
	 * @param args
	 */
	public static void main(String args[]) {
		//
		FileDao dao = new FileDao();
		RsaGenerate rsa = new RsaGenerate();
		// KeyPair 로 키를 생성합니다.
		KeyPair pair = rsa.keyGenerate();
		// dao 에서 writeKey 메소드를 호출해서 사용 구현에서 throw 로 했으므로 여기선
		// try catch 로 받았습니다.
		try {
			dao.writeKey(pair);
		} catch (IOException e) {
			//
			System.out.println("파일 생성시 에러가 났습니다.");
			e.printStackTrace();
		}
		// 이렇게 되면 생성까지 완료가 되었고 한번 읽어서 확인해 보도록 하겠습니다.
		// FileDao 에서는 파일 위치를 고정해 두었으니 그 위치로 읽도록 합니다.
		PublicKey publicKey = dao.readPublicKey("D:/Temp/public.key");
		PrivateKey privateKey = dao.readPrivateKey("D:/Temp/private.key");
		
		// toString 으로 읽는 의미는 없지만 값이 있다면 성공
		System.out.println(publicKey.toString());
		System.out.println(privateKey.toString());
	}
}

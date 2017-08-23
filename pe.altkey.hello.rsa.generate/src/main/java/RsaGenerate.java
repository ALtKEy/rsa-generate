import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
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
	protected KeyPair keyGenerate() {
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
}

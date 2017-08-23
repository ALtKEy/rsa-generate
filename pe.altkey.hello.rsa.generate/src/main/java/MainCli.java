import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class MainCli {
	//
	public static void main(String args[]) {
		//
		FileDao dao = new FileDao();
		RsaGenerate rsaGenerate = new RsaGenerate();
		// KeyPair 로 키를 생성합니다.
		KeyPair pair = rsaGenerate.keyGenerate();
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
		String publicKeyPath = "D:/Temp/public.key";
		String privateKeyPath = "D:/Temp/private.key";
		PublicKey publicKey = dao.readPublicKey(publicKeyPath);
		PrivateKey privateKey = dao.readPrivateKey(privateKeyPath);
		
		// toString 으로 읽는 의미는 없지만 값이 있다면 성공
		System.out.println("=====Public Key toString()=====");
		System.out.println(publicKey.toString());
		System.out.println("\n=====Private Key toString()=====");
		System.out.println(privateKey.toString());
		
		Rsa rsa = new Rsa();
		// 평문을 암호화 해보도록 하겠습니다.
		String plainText = "암호화할 평문";
		// 인코딩을 합니다.
		rsa.encodePlainText(plainText, publicKeyPath);
		// 생성된 파일을 메모장등으로 확인해봅니다.
		byte[] readFile = dao.readFile("D:/Temp/", "encodefile.dat");
		System.out.println("\n=====암호화된 파일 내용=====");
		String readString = null;
		try {
			readString = new String(readFile, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			//
			e.printStackTrace();
		}
		System.out.println(readString);
		
		// 이번에는 인코딩된 파일을 디코딩해보도록 하겠습니다.
		byte[] readDecode = rsa.decodeEncodingFile(privateKeyPath);
		System.out.println("\n=====복호화된 파일 내용=====");
		String decodeString = null;
		try {
			decodeString = new String(readDecode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			//
			e.printStackTrace();
		}
		System.out.println(decodeString);
		
	}
}

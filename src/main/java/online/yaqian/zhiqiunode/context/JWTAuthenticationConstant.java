package online.yaqian.zhiqiunode.context;

//import java.security.SecureRandom;
	/**
	* @author Young
	* @version 创建时间：2016年4月2日 下午10:13:01
	* 类说明：
	*/
public class JWTAuthenticationConstant {

	//使用以下函数生成随机密钥
	//Generate random 256-bit (32-byte) shared secret
    //SecureRandom random = new SecureRandom();
    //byte[] sharedSecret = new byte[32];
    //random.nextBytes(sharedSecret);
	//System.out.println(Arrays.toString(sharedSecret));
	
	/**
	 * MACVerifier,The secret as a UTF-8 encoded string. Must be at least 256 bits long and not null.
	 * JWT 签名密钥
	 */
	public static final String secretString = "your_secretString";
	
	/**
	 * issuerReference：您签发的JWT作用域，可在哪些站点使用？当前未启用
	 */
	public static final String issuerReference = "yaqian.online";
}

package online.yaqian.zhiqiunode.wechat.message.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import online.yaqian.zhiqiunode.wechat.context.WechatConstant;

/**
 * @author Young
 * @version 创建时间：2016年3月27日 下午9:54:13 类说明：
 */
/**
 * @author YoungZhu
 *
 */
public class Signature {
	
	public static Logger log = Logger.getLogger(Signature.class);

	/**
	 * 检查微信服务器发来的消息签名是否正确
	 * @param msg_signature
	 * @param timestamp
	 * @param nonce
	 * @param msg_encrypt
	 * @return
	 */
	public static boolean checkSHA1Signature(String msg_signature, String timestamp, String nonce, String msg_encrypt) {
		String[] strSet = new String[] { WechatConstant.TOKEN, timestamp, nonce ,msg_encrypt};
		// 对请求参数和自己的token进行排序，并连接排序后的结果为一个字符串
		java.util.Arrays.sort(strSet);
		StringBuffer total = new StringBuffer();
		for (String tempStr : strSet) {
			total = total.append(tempStr);
		}
			
		if (SHA1(total.toString()).equals(msg_signature)) { // 将加密的结果与请求参数中的signature比对
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查初始验证时服务器本事服务可用性
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSHA1Signature(String signature, String timestamp, String nonce) {
		String[] strSet = new String[] { WechatConstant.TOKEN, timestamp, nonce };
		// 对请求参数和自己的token进行排序，并连接排序后的结果为一个字符串
		java.util.Arrays.sort(strSet);
		StringBuffer total = new StringBuffer();
		for (String tempStr : strSet) {
			total = total.append(tempStr);
		}
			
		if (SHA1(total.toString()).equals(signature)) { // 将加密的结果与请求参数中的signature比对
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 对回复微信服务器的加密消息求其签名
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static String  getSHA1Signature(String encrypt, String timestamp, String nonce) {
		String[] strSet = new String[] { WechatConstant.TOKEN, timestamp, nonce ,encrypt};
		// 对请求参数和自己的token进行排序，并连接排序后的结果为一个字符串
		java.util.Arrays.sort(strSet);
		StringBuffer total = new StringBuffer();
		for (String tempStr : strSet) {
			total = total.append(tempStr);
		}
		return SHA1(total.toString());
	}
	/**
	 * JS-SDK 调用JS api时所需的签名算法，将对传入的jsapi_ticket与网页地址url进行SHA-1加密，返回加密时的时间戳、随机字符串及加密后的签名
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
	public static Map<String, String> wechatJSSignature(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		// 注意这里参数名必须全部小写，且必须有序
		StringBuffer tempStr = new StringBuffer("jsapi_ticket=").append(jsapi_ticket).append("&noncestr=").append(nonce_str).append("&timestamp=").append(timestamp).append("&url=").append(url);

		//ret.put("url", url);
		//ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", SHA1(tempStr.toString()));

		return ret;
	}

	
	/**
	 * SHA-1加密	
	 * @param content
	 * @return
	 */
	public static String SHA1(String content) {
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			sha1.reset();
			sha1.update(content.getBytes());
			byte[] codedBytes = sha1.digest();
			return new BigInteger(1, codedBytes).toString(16);// 将加密后的字节数组转换成字符串
		} catch (NoSuchAlgorithmException e) {
			log.error("SHA-1加密出错，无该加密方法:" + e.getLocalizedMessage());
			return "";
		}
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}

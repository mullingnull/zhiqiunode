package online.yaqian.zhiqiunode.util;

import java.util.Date;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import online.yaqian.zhiqiunode.context.JWTAuthenticationConstant;
import online.yaqian.zhiqiunode.model.WechatUser;

/**
	* @author Young
	* @version 创建时间：2016年4月3日 下午8:16:08
	* 类说明：
	*/
public class JWTUtil {
	
	/**
	 * @Title:getJWTToken
	 * @Description:
	 * @param wechatUser 微信用户的wid与uid
	 * @param millisecond 有效时间，以毫秒计，null或小于等于0时默认1个月
	 * @return
	 * @throws JOSEExceptionString
	 */
	public static String getJWTToken(WechatUser wechatUser,Long millisecond) throws JOSEException {
		// Create HMAC signer
		JWSSigner signer = new MACSigner(JWTAuthenticationConstant.secretString);

		// Prepare JWT with claims set 参考官方的构造法
		JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder();
		if (wechatUser.getUserId() == 0){
			claimsSet.claim("wid", wechatUser.getOpenId()).claim("uid", 0);
		}else {
			claimsSet.claim("uid", wechatUser.getUserId());
		}
		millisecond = millisecond==null?2592000000L:(millisecond<=0?2592000000L:millisecond);//默认有效期为1 Month = 30*24*60*60 = 2592000000L
		claimsSet.issuer(JWTAuthenticationConstant.issuerReference).issueTime(new Date()).notBeforeTime(new Date()).expirationTime(new Date(new Date().getTime() + millisecond));
		SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build(), claimsSet.build());
		// Apply the HMAC protection
		signedJWT.sign(signer);

		// Serialize to compact form, produces something like
		// eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
		return signedJWT.serialize();
	}
	
	/**
	 * @Title:getJWTToken
	 * @Description:
	 * @param userId 用户也即接收方ID
	 * @param vendorId 出让方用户ID
	 * @param millisecond 有效时间，以毫秒计，null或小于等于0时默认2小时
	 * @return
	 * @throws JOSEExceptionString
	 */
	public static String getJWTToken(int userId,int vendorId,Long millisecond) throws JOSEException {
		// Create HMAC signer
		JWSSigner signer = new MACSigner(JWTAuthenticationConstant.secretString);

		// Prepare JWT with claims set 参考官方的构造法
		JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder();
		claimsSet.claim("uid", userId);
		claimsSet.claim("vendorId", vendorId);
		millisecond = millisecond==null?7200000L:(millisecond<=0?7200000L:millisecond);//默认有效期为1 Hour = 2*60*60 = 7200000L
		claimsSet.issuer(JWTAuthenticationConstant.issuerReference).issueTime(new Date()).notBeforeTime(new Date()).expirationTime(new Date(new Date().getTime() + millisecond));
		SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build(), claimsSet.build());
		signedJWT.sign(signer);

		return signedJWT.serialize();
	}
}

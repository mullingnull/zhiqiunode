package online.yaqian.zhiqiunode.exception;

import org.springframework.security.core.AuthenticationException;

/**
	* @author Young
	* @version 创建时间：2016年4月2日 下午5:12:07
	* 类说明：
	*/
public class JWTAuthenticationException extends AuthenticationException {

		/**
		 * 
		 */
		private static final long serialVersionUID = -752377983230574814L;

		public JWTAuthenticationException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		
}

package online.yaqian.zhiqiunode.security;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.nimbusds.jwt.JWTParser;

import online.yaqian.zhiqiunode.exception.JWTAuthenticationException;

/**
	* @author Young
	* @version 创建时间：2016年4月1日 下午7:38:05
	* 类说明：
	*/
public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public JWTAuthenticationFilter() {
        super("/**");
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //String header = request.getHeader("Authorization");
    	String header = null;
    	Cookie[] c = request.getCookies();
    	if (c == null) {
    		 throw new JWTAuthenticationException("No JWT token found in request cookies");
		} else {

			for(Cookie cookie : c){
				if("Authorization".equals(cookie.getName())){
					header = cookie.getValue();
				}
			}	
			if (header == null || !header.startsWith("Bearer ")) {
				throw new JWTAuthenticationException("No JWT token found in request headers");
			}
			
			String authToken = header.substring(7);
			
			try {
				return getAuthenticationManager().authenticate(new JWTAuthenticationToken(JWTParser.parse(authToken)));
			} catch (ParseException e) {
				throw new JWTAuthenticationException("Invalid token");
			}
		}
        
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);

        // As this authentication is in HTTP header, after success we need to continue the request normally
        // and return the response as if the resource was not secured at all
        chain.doFilter(request, response);
    }
}
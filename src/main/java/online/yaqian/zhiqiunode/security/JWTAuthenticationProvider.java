package online.yaqian.zhiqiunode.security;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import com.nimbusds.jwt.SignedJWT;

import online.yaqian.zhiqiunode.context.JWTAuthenticationConstant;
import online.yaqian.zhiqiunode.exception.JWTAuthenticationException;

/**
	* @author Young
	* @version 创建时间：2016年4月1日 下午11:46:35
	* 类说明：
	*/
public class JWTAuthenticationProvider implements AuthenticationProvider {
    
    private JWSVerifier verifier;
    
    public JWTAuthenticationProvider() {
        try {
			this.verifier = new MACVerifier(JWTAuthenticationConstant.secretString);
		} catch (JOSEException e) {
			 throw new JWTAuthenticationException("MACVerifier failed");
		}
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JWTAuthenticationToken jwtToken = (JWTAuthenticationToken) authentication;
        JWT jwt = jwtToken.getJwt();
        
        // Check type of the parsed JOSE object
        if (jwt instanceof PlainJWT) {
            handlePlainToken((PlainJWT) jwt);
        } else if (jwt instanceof SignedJWT) {
            handleSignedToken((SignedJWT) jwt);
        } else if (jwt instanceof EncryptedJWT) {
            handleEncryptedToken((EncryptedJWT) jwt);
        }
        
        Date referenceTime = new Date();
        JWTClaimsSet claims = jwtToken.getClaims();
        
        Date expirationTime = claims.getExpirationTime();
        if (expirationTime == null || expirationTime.before(referenceTime)) {
            throw new JWTAuthenticationException("The token is expired");
        }
        
        Date notBeforeTime = claims.getNotBeforeTime();
        if (notBeforeTime == null || notBeforeTime.after(referenceTime)) {
            throw new JWTAuthenticationException("Not before is after sysdate");
        }
        
        
        String issuer = claims.getIssuer();
        if (!JWTAuthenticationConstant.issuerReference.equals(issuer)) {
            throw new JWTAuthenticationException("Invalid issuer");
        }
        
        jwtToken.setAuthenticated(true);
        return jwtToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthenticationToken.class.isAssignableFrom(authentication);
    }
    
    private void handlePlainToken(PlainJWT jwt) {
        throw new JWTAuthenticationException("catch PlainToken:Unsecured plain tokens are not supported");
    }
    
    private void handleSignedToken(SignedJWT jwt) {
        try {
            if (!jwt.verify(verifier)) {
                throw new JWTAuthenticationException("Signature validation failed");
            }
        } catch (JOSEException e) {
            throw new JWTAuthenticationException("Signature validation failed");
        }
    }
    
    private void handleEncryptedToken(EncryptedJWT jwt) {
        throw new JWTAuthenticationException("catch EncryptedToken:Unsupported token type");
    }
    
}

package online.yaqian.zhiqiunode.security;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;

/**
	* @author Young
	* @version 创建时间：2016年4月1日 下午11:43:08
	* 类说明：
	*/
public class JWTAuthenticationToken  implements Authentication {

    private static final long serialVersionUID = 1L;

    private JWT jwt;
    private final Collection<GrantedAuthority> authorities;
    private boolean authenticated;
    private JWTClaimsSet claims;
    
    public JWTAuthenticationToken(JWT jwt) throws ParseException {
        this.jwt = jwt;
        List<String> roles;
        try {
            roles = jwt.getJWTClaimsSet().getStringListClaim("roles");
        } catch (ParseException e) {
            roles = new ArrayList<>();
        }
        List<GrantedAuthority> tmp = new ArrayList<>();
        if (roles != null) {
            for (String role : roles) {
                tmp.add(new SimpleGrantedAuthority(role));
            }
        }
        this.authorities = Collections.unmodifiableList(tmp);
        this.claims = jwt.getJWTClaimsSet();
        authenticated = false;
    }
    
    public JWT getJwt() {
        return jwt;
    }

    public JWTClaimsSet getClaims() {
        return claims;
    }
    
    public int getUserId() {
    	return ((Long)claims.getClaim("uid")).intValue();
    }

    @Override
    public Object getCredentials() {
        return "";
    }
    
    @Override
    public Object getPrincipal() {
        return claims.getSubject();
    }

    @Override
    public String getName() {
        return claims.getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getDetails() {
        return claims.toJSONObject();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }
    
}
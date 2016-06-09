package online.yaqian.zhiqiunode.controller;

import java.io.IOException;
import java.text.ParseException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;

import online.yaqian.zhiqiunode.context.GlobalStatus;
import online.yaqian.zhiqiunode.context.JWTAuthenticationConstant;
import online.yaqian.zhiqiunode.exception.ViewException;
import online.yaqian.zhiqiunode.model.User;
import online.yaqian.zhiqiunode.model.WechatUser;
import online.yaqian.zhiqiunode.service.IMineService;
import online.yaqian.zhiqiunode.service.IUserService;
import online.yaqian.zhiqiunode.service.IWishService;
import online.yaqian.zhiqiunode.util.JWTUtil;
import online.yaqian.zhiqiunode.util.ValidateISBNUtil;
import online.yaqian.zhiqiunode.wechat.context.Ticket;
import online.yaqian.zhiqiunode.wechat.message.util.Signature;
import online.yaqian.zhiqiunode.wechat.service.IWechatUserService;

/**
 * @author Young
 * @version 创建时间：2016年4月3日 上午12:35:56 
 * 类说明：初始界面分派
 */

@Controller
@Scope(value = "prototype")
public class ViewDispatcher {

	public static Logger log = Logger.getLogger(ViewDispatcher.class);

	@Autowired
	@Qualifier("wechatUserService")
	private IWechatUserService wechatUserService;

	@Autowired
	@Qualifier("userService")
	private IUserService userService;

	@Autowired
	@Qualifier("mineService")
	private IMineService mineService;

	@Autowired
	@Qualifier("wishService")
	private IWishService wishService;

//	 @Autowired
//	 @Qualifier("bookInfoService")
//	 private IBookInfoService bookInfoService;

	@RequestMapping(value = "/jump", method = RequestMethod.GET)
	public void reception(@RequestParam(value = "bearer", defaultValue = "") String bearer,
			@RequestParam(value = "target", defaultValue = "") String target,
			@CookieValue(value = "Authorization", defaultValue = "") String authorization,
			@RequestParam(value="wcqrcode",defaultValue="") String wcqrcode,
			HttpServletResponse response) {
		target.toLowerCase();
		bearer = bearer.isEmpty() ? (authorization.startsWith("Bearer ") ? authorization.substring(7) : "") : bearer;
		if (bearer.isEmpty()) {
			throw new ViewException("参数错误", null);
		}

		try {
			SignedJWT signedJWT;
			JWSVerifier verifier = new MACVerifier(JWTAuthenticationConstant.secretString);
			signedJWT = SignedJWT.parse(bearer);
			if (signedJWT.verify(verifier)) {
				int uid = ((Long) signedJWT.getJWTClaimsSet().getClaim("uid")).intValue();
				if (0 == uid) {
					String openId = signedJWT.getJWTClaimsSet().getStringClaim("wid");// wechatopenid
					WechatUser wechatUser = wechatUserService.getWechatUser(openId);
					if (wechatUser.getUserId() == 0) {
						// redirect到主页&注册页面
						target = "user";
					} else {
						uid = wechatUser.getUserId();
						bearer = JWTUtil.getJWTToken(wechatUser,null);
							//用户登录
					}
				}
				
				if (target.equals("transfer")) {//返回传送页面，需配对
					Cookie c = new Cookie("Authorization", "Bearer " + bearer);
					c.setMaxAge(7080); // 2 hours = 2*60*59
					c.setHttpOnly(true);
					response.addCookie(c);
					response.sendRedirect("/transfer");
					
				}else if (target.equals("pair")) {//接受配对数据
					if (wcqrcode.length()==20) {
						User user = userService.getUserByWcQRCode(wcqrcode);
						if (user!=null &&user.getUserId()!=uid) {
							Cookie c = new Cookie("Authorization", "Bearer " + JWTUtil.getJWTToken(uid,user.getUserId(),null));
							c.setMaxAge(7080); // 2 hours = 2*60*59
							c.setHttpOnly(true);
							response.addCookie(c);
							response.setStatus(200);
						}else {
							//请求参数错误
							response.setStatus(404);
						}
						
					}else {
						response.setStatus(400);
					}
				} else {
					Cookie c = new Cookie("Authorization", "Bearer " + bearer);
					c.setMaxAge(849600); // 24 hours = 24*60*59
					c.setHttpOnly(true);
					response.addCookie(c);
					if (target.startsWith("seek")&&ValidateISBNUtil.validateISBN(target.substring(4))) {
						response.sendRedirect("/user/" + uid +"#seek?isbn="+target.substring(4)+"&page=1");
					}else {
						response.sendRedirect("/user/" + uid);
					}
				}

			}
		} catch (JOSEException e) {
			// log.error("生成MACVerifier时出现错误，请核验JWTAuthenticationConstant中secretString");
			throw new ViewException("登陆数据遭非法篡改", e);
		} catch (ParseException e) {
			// 非法数据，bearer被篡改过，数据格式不正确无法解析
			throw new ViewException("登陆数据遭非法篡改", e);
		} catch (IOException e) {
			// 重定向出错
			throw new ViewException("sorry，页面跳转出错，建议刷新重试", e);
		}
	}

	@RequestMapping(value = "/user/{uid}")
	public String interfaceDispatcher(@PathVariable("uid") int uid,
			@CookieValue(value = "Authorization", defaultValue = "") String authorization, ModelMap model) {

		if (authorization == "" || !authorization.startsWith("Bearer ")) {
			// 缺少认证信息，适当展示用户信息，展示主页，提供二维码以供关注
			// model.addAttribute("yaqian", );
			if (uid > 0) {
				// 返回系统消息,用户信息
				User u = userService.getUser(uid);
				if (u != null) {
					model.addAttribute("user", u);
					model.addAttribute("mine", mineService.getMinesByUserId(uid));
				}
				return "overview";
			} else {
				// 仅返回系统消息
				return "overview";
			}
		} else {
			try {
				JWSVerifier verifier = new MACVerifier(JWTAuthenticationConstant.secretString);
				SignedJWT signedJWT = SignedJWT.parse(authorization.substring(7));
				if (signedJWT.verify(verifier)) {
					if (uid == ((Long) signedJWT.getJWTClaimsSet().getClaim("uid")).intValue()) {
						if (uid == 0) {
							String openId = signedJWT.getJWTClaimsSet().getStringClaim("wid");// wechatopenid
							model.addAttribute("wechatuser", wechatUserService.getWechatUser(openId));
							model.addAttribute("wxconfig", Signature.wechatJSSignature(Ticket.getInstance().getJsApiTicket(), "http://xiaoyaqian.shaguanxi.com/user/0"));
							model.addAttribute("status", GlobalStatus.getInstance());
							// 主页&注册
							return "signup";
						} else {
							// 用户登录
							model.addAttribute("user", userService.getUser(uid));
							model.addAttribute("wxconfig",Signature.wechatJSSignature(Ticket.getInstance().getJsApiTicket(),"http://xiaoyaqian.shaguanxi.com/user/" + uid));

							return "mainpage";
						}
					} else {
						// pathParam修改过，折衷处理
						//访问的是别人的页面
					}
				}
			} catch (ParseException e) {
				// 非法数据，bearer被篡改过，数据格式不正确无法解析
				throw new ViewException("登陆数据遭非法篡改", e);
			} catch (JOSEException e) {
				// log.error("生成MACVerifier时出现错误，请核验JWTAuthenticationConstant中secretString");
				throw new ViewException("登陆数据遭非法篡改", e);
			}
		}
		return "exception";
	}

	@RequestMapping(value = "/transfer", method = RequestMethod.GET)
	public String transfer(@CookieValue(value = "Authorization", defaultValue = "") String authorization, ModelMap model) {
		
		try {
			JWSVerifier verifier = new MACVerifier(JWTAuthenticationConstant.secretString);
			SignedJWT signedJWT = SignedJWT.parse(authorization.substring(7));
			if (signedJWT.verify(verifier)) {
				int uid = ((Long) signedJWT.getJWTClaimsSet().getClaim("uid")).intValue();
					if (uid == 0) {
						String openId = signedJWT.getJWTClaimsSet().getStringClaim("wid");// wechatopenid
						model.addAttribute("wechatuser", wechatUserService.getWechatUser(openId));
						model.addAttribute("wxconfig", Signature.wechatJSSignature(Ticket.getInstance().getJsApiTicket(), "http://xiaoyaqian.shaguanxi.com/user/0"));
						// 未注册用户
						return "exception";
					} else {
						// 用户传送
						model.addAttribute("user", userService.getUser(uid));
						model.addAttribute("wxconfig",Signature.wechatJSSignature(Ticket.getInstance().getJsApiTicket(),"http://xiaoyaqian.shaguanxi.com/transfer"));

						return "transfer";
					}
				
			}
		} catch (ParseException e) {
			// 非法数据，bearer被篡改过，数据格式不正确无法解析
			throw new ViewException("登陆数据遭非法篡改", e);
		} catch (JOSEException e) {
			// log.error("生成MACVerifier时出现错误，请核验JWTAuthenticationConstant中secretString");
			throw new ViewException("登陆数据遭非法篡改", e);
		}
		

		return "exception";
	}
}

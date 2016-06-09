package online.yaqian.zhiqiunode.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;

import online.yaqian.zhiqiunode.dto.UserDetail;
import online.yaqian.zhiqiunode.model.User;
import online.yaqian.zhiqiunode.model.UserProfile;
import online.yaqian.zhiqiunode.model.WechatUser;
import online.yaqian.zhiqiunode.security.JWTAuthenticationToken;
import online.yaqian.zhiqiunode.service.IUserProfileService;
import online.yaqian.zhiqiunode.service.IUserService;
import online.yaqian.zhiqiunode.util.JWTUtil;
import online.yaqian.zhiqiunode.wechat.service.IWechatUserService;

/**
	* @author Young
	* @version 创建时间：2016年4月8日 上午12:30:26
	* 类说明：
	*/
@RestController
@RequestMapping(value="/api/users")
@Scope(value = "prototype")
public class RestfulUserAPIs {
	
	public static Logger log = Logger.getLogger(RestfulUserAPIs.class);
	
	@Autowired(required=true)
	@Qualifier("userService")
	private IUserService userService;
	
	@Autowired
	@Qualifier("wechatUserService")
	private IWechatUserService wechatUserService;
	
	@Autowired(required=true)
	@Qualifier("userProfileService")
	private IUserProfileService userProfileService;
	
	@RequestMapping(value="/{uid}",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Object getUserDetail(@PathVariable("uid") int uid) {
		JWTAuthenticationToken jWTAuth = (JWTAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		Map<String,Serializable> ret = new HashMap<String,Serializable>();
		if (uid <= 0) {
			ret.put("statuscode", 404);
			ret.put("errmsg", "Not Avaliable");
			return ret;
		} else if (uid == jWTAuth.getUserId()) {
			//String.format("%tY", Date);//4位年份    2008
			User u = userService.getUser(uid);
			UserProfile up = userProfileService.getUserProfile(u.getProfileId());
			if(up == null){
				return new UserDetail(u);
			}
			return new UserDetail(u,up);
		}else {
			User u = userService.getUser(uid);
			if(u == null){
				ret.put("statuscode", 404);
				ret.put("errmsg", "Not Found");
				return ret;
			}else {
				return u;
			}
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
	public Map<String, Serializable> addUser(@RequestBody UserDetail userDetail) {
		JWTAuthenticationToken jWTAuth = (JWTAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		Map<String,Serializable> ret = new HashMap<String,Serializable>();
		String wid = (String) jWTAuth.getClaims().getClaim("wid");
		if(wid == null){
			ret.put("statuscode", 403);
			ret.put("errmsg", "Forbidden");
			return ret;
		}
		WechatUser wechatUser = wechatUserService.getWechatUser(wid);
		int uid = 0;
		if (wechatUser.getUserId() == 0) {
			User newU = userDetail.toUser();
			//判断用户更新后的微信二维码是否已注册其他
			if (newU.getWcQrcode()!=null && userService.getUserByWcQRCode(newU.getWcQrcode())!=null) {
				ret.put("statuscode", 400);
				ret.put("errmsg", "该微信号已注册过，如需找回账号请给公众号留言");
				return ret;
			}
			uid = userService.saveUser(newU, wechatUser);
		}
		log.info(wid + "<<用户注册成功,其uid为：" + uid);
		ret.put("uid", uid);
		ret.put("statuscode", 201);
		ret.put("errmsg", "Created Successfully");
		return ret;
	}
	
	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
	public Object updateUser(@RequestBody UserDetail userDetail) {
		JWTAuthenticationToken jWTAuth = (JWTAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		Map<String,Serializable> ret = new HashMap<String,Serializable>();
		int uid = jWTAuth.getUserId();
		if (uid>0) {//当前只使用User类
				User newU = userDetail.toUser();
				//判断用户更新后的微信二维码是否已被注册其他账户
				if (newU.getWcQrcode()!=null) {
					User tmpU = userService.getUserByWcQRCode(newU.getWcQrcode());
					if (tmpU==null?false:tmpU.getUserId()!=uid) {
						ret.put("statuscode", 400);
						ret.put("errmsg", "该微信号已注册过，如需找回账号请给公众号留言或直接联系开发者");
						return ret;
					}
					tmpU = null;
				}
				Map<String,Object> result = new HashMap<String,Object>();
				newU.setUserId(uid);
				result.put("result", userService.updateUser(newU));
				result.put("statuscode", 200);
				result.put("errmsg", "OK");
				return result;

		}
		ret.put("statuscode", 401);
		ret.put("errmsg", "Unauthorized 鉴权失败！");

		return ret;
	}
	
	@RequestMapping(value="/{uid}",method = RequestMethod.DELETE,produces = "application/json;charset=UTF-8")
	public Map<String, Serializable> deleteUser(@PathVariable("uid") int uid) {
		JWTAuthenticationToken jWTAuth = (JWTAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		Map<String,Serializable> ret = new HashMap<String,Serializable>();
		
		if (uid == jWTAuth.getUserId()) {
			userService.deleteUser(uid);
			userProfileService.deleteUserProfileByUserId(uid);
			ret.put("statuscode", 200);
			ret.put("errmsg", "ok");
			//ret.put("statuscode", 403);
			//ret.put("errmsg", "forbidden now");
		}else {
			ret.put("statuscode", 401);
			ret.put("errmsg", "Unauthorized 鉴权失败！");
		}
		return ret;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Object searchUser(@RequestParam(value="wcqrcode",defaultValue="") String wcqrcode,@RequestParam(value="begintansfer",defaultValue="false")boolean beginTransfer) {
		JWTAuthenticationToken jWTAuth = (JWTAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		Map<String,Serializable> ret = new HashMap<String,Serializable>();
		int uid = jWTAuth.getUserId();
		if (uid==0) {
			ret.put("statuscode", 403);
			ret.put("errmsg", "Forbiden");
			return ret;
		}
		if (wcqrcode.length()==20 && beginTransfer) {
			User user = userService.getUserByWcQRCode(wcqrcode);
			if (user==null) {
				ret.put("statuscode", 404);
				ret.put("errmsg", "未找到该用户");
				return ret;
			} else if (uid == user.getUserId()) {
				ret.put("statuscode", 403);
				ret.put("errmsg", "自己跟自己玩，我是拒绝的");
				return ret;
			}else {
				try {
					String bearer = JWTUtil.getJWTToken(uid,user.getUserId(),null);
					ret.put("result", bearer);
					ret.put("statuscode", 200);
					ret.put("errmsg", "OK");
					return ret;
				} catch (JOSEException e) {
					// TODO
					
					ret.put("statuscode", 500);
					ret.put("errmsg", "服务器端错误,请刷新重试或联系开发者");
				}
			}
		}
		ret.put("errcode", 400);
		ret.put("errmsg", "Uknown Request");
		return ret;
	}
}

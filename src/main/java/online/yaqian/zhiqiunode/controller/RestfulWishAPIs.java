package online.yaqian.zhiqiunode.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import online.yaqian.zhiqiunode.dto.WishDetail;
import online.yaqian.zhiqiunode.model.Wish;
import online.yaqian.zhiqiunode.security.JWTAuthenticationToken;
import online.yaqian.zhiqiunode.service.IBookInfoService;
import online.yaqian.zhiqiunode.service.IMineService;
import online.yaqian.zhiqiunode.service.IWishService;
import online.yaqian.zhiqiunode.util.ValidateISBNUtil;

/**
	* @author Young
	* @version 创建时间：2016年4月14日 下午12:25:55
	* 类说明：
	*/
@RestController
@RequestMapping(value="/api/wishes")
@Scope(value = "prototype")
public class RestfulWishAPIs {

	public static Logger log = Logger.getLogger(RestfulWishAPIs.class);
	
	@Autowired(required=true)
	@Qualifier("wishService")
	private IWishService wishService;
	
	@Autowired(required=true)
	@Qualifier("bookInfoService")
	private IBookInfoService bookInfoService;
	
	@Autowired(required=true)
	@Qualifier("mineService")
	private IMineService mineService;
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Object getWishes(@RequestParam(value = "uid",defaultValue = "0") int uid,@RequestParam(value = "isbn",defaultValue = "") String isbn) {
		JWTAuthenticationToken jWTAuth = (JWTAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		Map<String,Serializable> ret = new HashMap<String,Serializable>();
		int userId = jWTAuth.getUserId();
		if(userId > 0) {//已注册用户
			uid = uid>0 ? uid : userId;//指定用户返回相应用户，否则返回其本人
			if (isbn.isEmpty()) {//返回请求用户WishDetail全集
				List<WishDetail> wishDetails = wishService.getWishDetailByUserId(uid);
				if (wishDetails == null) {
					ret.put("statuscode", 404);
					ret.put("errmsg", "not found");
					return ret;
				}
				for (WishDetail wish : wishDetails) {
					wish.setMineCount(mineService.countMinesByISBN(wish.getIsbn()));
				}
				
				return wishDetails;
				
			} else if (ValidateISBNUtil.validateISBN(isbn)) {//返回指定isbn的WishDetail
					Wish wish = wishService.getWishByUserIdAndISBN(uid, isbn);
					if (wish == null) {
						ret.put("statuscode", 404);
						ret.put("errmsg", "not found");
						return ret;
					}
					return new WishDetail(wish,bookInfoService.getBookInfo(wish.getIsbn()),mineService.countMinesByISBN(wish.getIsbn()));
			}
			ret.put("statuscode", 400);
			ret.put("errmsg", "Request Error:ISBN Error");
			return ret;
			//直接返回bookinfos,无法包含wish更新时间信息
//			ArrayList<String> isbns = new ArrayList<>();
//			for (Wish wish : wishes) {
//				isbns.add(wish.getIsbn());
//			}
//			List<BookInfo> bookInfos = bookInfoService.getBookInfo(isbns);
//			return bookInfos;
		}
		ret.put("statuscode", 403);
		ret.put("errmsg", "forbidden");
		return ret;

	}
	
	@RequestMapping(method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Object addWish(@RequestBody Wish wish) {
		JWTAuthenticationToken jWTAuth = (JWTAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		Map<String,Serializable> ret = new HashMap<String,Serializable>();
		int userId = jWTAuth.getUserId();
		if(userId > 0 && ValidateISBNUtil.validateISBN(wish.getIsbn())) {
			if (wishService.getWishByUserIdAndISBN(userId,wish.getIsbn()) == null) {
				wishService.addUserWish(userId,wish.getIsbn());
				ret.put("statuscode", 201);
				ret.put("errmsg", "created");
				return ret;			
			} else {
				ret.put("statuscode", 400);
				ret.put("errmsg", "Already Exists");
				return ret;
			}
		}
		ret.put("statuscode", 403);
		ret.put("errmsg", "forbidden");
		return ret;

	}
	
	@RequestMapping(method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	public Object deleteWish(@RequestParam(value = "isbn",defaultValue = "") String isbn,@RequestParam(value = "deleteall",defaultValue = "false") Boolean deleteall) {
		JWTAuthenticationToken jWTAuth = (JWTAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		Map<String,Serializable> ret = new HashMap<String,Serializable>();
		int userId = jWTAuth.getUserId();
		if(userId > 0 ) {
			if (deleteall) {
				wishService.deleteAllUserWishes(userId);
				ret.put("statuscode", 410);
				ret.put("errmsg", "gone");
				return ret;	
			} else if(!isbn.isEmpty()){
				if (ValidateISBNUtil.validateISBN(isbn)) {
					wishService.deleteUserWish(userId,isbn);
					ret.put("statuscode", 410);
					ret.put("errmsg", "gone");
					return ret;			
				}
				ret.put("statuscode", 400);
				ret.put("errmsg", "Request Error:ISBN Error");
				return ret;
			}
			ret.put("statuscode", 400);
			ret.put("errmsg", "Request Error");
			return ret;
		}
		ret.put("statuscode", 403);
		ret.put("errmsg", "forbidden");
		return ret;

	}
	
}

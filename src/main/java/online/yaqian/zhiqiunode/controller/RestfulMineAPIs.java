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

import online.yaqian.zhiqiunode.dto.MineDetail;
import online.yaqian.zhiqiunode.model.Mine;
import online.yaqian.zhiqiunode.security.JWTAuthenticationToken;
import online.yaqian.zhiqiunode.service.IBookInfoService;
import online.yaqian.zhiqiunode.service.IMineService;
import online.yaqian.zhiqiunode.service.IUserService;
import online.yaqian.zhiqiunode.service.IWishService;
import online.yaqian.zhiqiunode.util.ValidateISBNUtil;

/**
	* @author Young
	* @version 创建时间：2016年4月14日 下午12:25:30
	* 类说明：
	*/
@RestController
@RequestMapping(value="/api/mines")
@Scope(value = "prototype")
public class RestfulMineAPIs {

	public static Logger log = Logger.getLogger(RestfulMineAPIs.class);
	
	@Autowired(required=true)
	@Qualifier("mineService")
	private IMineService mineService;
	
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	
	@Autowired
	@Qualifier("bookInfoService")
	private IBookInfoService bookInfoService;
	
	@Autowired(required=true)
	@Qualifier("wishService")
	private IWishService wishService;
	
	
	
	/**
	 * @Title:getMines
	 * @Description:默认情况下返回用户本人的MineDetail集合，并计算各本书需求量,如指定uid则返回指定MineDetail集合,指定ISBN时返回指定uid或其本人的检索结果
	 * @param uid
	 * @param isbn
	 * @returnObject
	 */
	@RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Object getMines(@RequestParam(value = "uid",defaultValue = "0") int uid,@RequestParam(value = "isbn",defaultValue = "") String isbn) {
		JWTAuthenticationToken jWTAuth = (JWTAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		Map<String,Serializable> ret = new HashMap<String,Serializable>();
		int userId = jWTAuth.getUserId();
		
		if (userId > 0) {//已注册用户

			
			if(isbn.isEmpty()) {//返回其本人MineDetail
				
				 if (uid>0&&userId!=uid)	{//返回指定用户Mines
						return mineService.getMinesByUserId(userId);
					}
				List<MineDetail> mineDetails =  mineService.getMineDetailByUserId(userId);
				if (mineDetails == null) {
					ret.put("statuscode", 404);
					ret.put("errmsg", "Not Found");
					return ret;
				}
				for (MineDetail mine : mineDetails) {
					mine.setWishCount(wishService.countWishesByISBN(mine.getIsbn()));
				}
				return mineDetails;
			} else if (ValidateISBNUtil.validateISBN(isbn)) {//指定ISBN且有效时，返回对应Mine对象
				uid = uid > 0 ? uid : userId;
				Mine mine = mineService.getMineByUserIdAndISBN(uid, isbn);
				if (mine ==null) {
					ret.put("statuscode", 404);
					ret.put("errmsg", "Not Found");
					return ret;
				}
				return	new MineDetail(mine, bookInfoService.getBookInfo(isbn),wishService.countWishesByISBN(isbn));
				
			} 
			ret.put("statuscode", 400);
			ret.put("errmsg", "Request Error:ISBN Error");
			return ret;

		}
		ret.put("statuscode", 403);
		ret.put("errmsg", "Forbidden");
		return ret;
	}	
	
	@RequestMapping(method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Object addMine(@RequestBody Mine mine) {
		JWTAuthenticationToken jWTAuth = (JWTAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		Map<String,Serializable> ret = new HashMap<String,Serializable>();
		int userId = jWTAuth.getUserId();
		if(userId > 0 && ValidateISBNUtil.validateISBN(mine.getIsbn())) {
			Mine origin = mineService.getMineByUserIdAndISBN(userId, mine.getIsbn());
			mine.setMineId(null);
			if (origin == null) {
				mine.setUserId(userId);//不相信来自客户端的数据
				mine.setQty(mine.getQty()==null||mine.getQty()==0?1:mine.getQty());
				mineService.addMine(mine);
			} else {
				mineService.updateMine(mine, origin);
			}
			ret.put("statuscode", 201);
			ret.put("errmsg", "Created Successfully");
			return ret;			
		}
		ret.put("statuscode", 403);
		ret.put("errmsg", "Forbidden");
		return ret;

	}
	
	@RequestMapping(method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public Object updateMine(@RequestBody Mine mine) {
		JWTAuthenticationToken jWTAuth = (JWTAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		Map<String,Serializable> ret = new HashMap<String,Serializable>();
		int userId = jWTAuth.getUserId();
		if(userId > 0 && ValidateISBNUtil.validateISBN(mine.getIsbn())) {
			mine.setUserId(userId);//不相信来自客户端的数据
			mine.setMineId(null);
			Mine origin = mineService.getMineByUserIdAndISBN(userId, mine.getIsbn());
			mineService.updateMine(mine, origin);
			ret.put("statuscode", 201);
			ret.put("errmsg", "Updated Successfully");
			return ret;			
		}
		ret.put("statuscode", 403);
		ret.put("errmsg", "Forbidden");
		return ret;

	}
	
	@RequestMapping(method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	public Object deleteMine(@RequestParam(value = "isbn",defaultValue = "") String isbn) {
		JWTAuthenticationToken jWTAuth = (JWTAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		Map<String,Serializable> ret = new HashMap<String,Serializable>();
		int userId = jWTAuth.getUserId();
		if(userId > 0 ){
			if(ValidateISBNUtil.validateISBN(isbn)) {
				mineService.deleteMine(userId,isbn);
				ret.put("statuscode", 410);
				ret.put("errmsg", "Gone");
				return ret;			
			}
			ret.put("statuscode", 400);
			ret.put("errmsg", "Request Error:ISBN Error");
			return ret;
		}
		ret.put("statuscode", 403);
		ret.put("errmsg", "Forbidden");
		return ret;

	}
	
	
	
	
}

package online.yaqian.zhiqiunode.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import online.yaqian.zhiqiunode.security.JWTAuthenticationToken;
import online.yaqian.zhiqiunode.service.IBookInfoService;
import online.yaqian.zhiqiunode.service.IMineService;
import online.yaqian.zhiqiunode.service.IUserService;
import online.yaqian.zhiqiunode.util.ValidateISBNUtil;

/**
	* @author Young
	* @version 创建时间：2016年4月28日 下午2:47:00
	* 类说明：
	*/
@RestController
@RequestMapping(value="/api/resources/")
@Scope(value = "prototype")
public class RestfulResourceSearchAPIs {

public static Logger log = Logger.getLogger(RestfulResourceSearchAPIs.class);
	
	@Autowired(required=true)
	@Qualifier("mineService")
	private IMineService mineService;
	
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	
	@Autowired
	@Qualifier("bookInfoService")
	private IBookInfoService bookInfoService;
	
	
//	@TODO 优化搜索算法
	@RequestMapping(value="mines", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Object searchMines(@RequestParam(value="isbn", required = true) String isbn,
			@RequestParam(value = "gender",defaultValue = "9") int gender,
			@RequestParam(value = "college",defaultValue = "") String college,
			@RequestParam(value = "major",defaultValue = "") String major,
			@RequestParam(value = "minor",defaultValue = "") String minor,
			@RequestParam(value = "enrolYear",defaultValue = "") String enrolYear,
			@RequestParam(value = "page",defaultValue = "0") int page,
			@RequestParam(value = "per_page",defaultValue = "15") int per_page,
			@RequestParam(value = "sortby",defaultValue = "updatetime") String sortby,
			@RequestParam(value = "order",defaultValue = "desc") String order) {
		JWTAuthenticationToken jWTAuth = (JWTAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		Map<String,Serializable> ret = new HashMap<String,Serializable>();

		if (jWTAuth.getUserId() > 0) {
			if(ValidateISBNUtil.validateISBN(isbn)){
				if (gender>9 || college.length()>1 || major.length()>2 || minor.length()>2 || enrolYear.isEmpty()?false:!enrolYear.matches("^2\\d{3}")) {
					ret.put("statuscode", 400);
					ret.put("errmsg", "Request Error:Query Params Error");
					return ret;
				}
				Map<String, Object> result = new HashMap<String,Object>();
				result.put("results",mineService.searchMines(isbn, gender, college, major, minor, enrolYear, page, per_page, sortby, order));
				result.put("amount", mineService.countMinesByISBN(isbn));
				return result;
			}
			ret.put("statuscode", 400);
			ret.put("errmsg", "Request Error:ISBN Error");
			return ret;
		} 
		ret.put("statuscode", 403);
		ret.put("errmsg", "Forbbiden");
		return ret;
	}
	
//	@TODO 优化搜索算法
	@RequestMapping(value="users", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Object searchUsers(@RequestParam(value = "gender",defaultValue = "9") int gender,
			@RequestParam(value = "college",defaultValue = "") String college,
			@RequestParam(value = "major",defaultValue = "") String major,
			@RequestParam(value = "minor",defaultValue = "") String minor,
			@RequestParam(value = "enrolYear",defaultValue = "") String enrolYear,
			@RequestParam(value = "page",defaultValue = "0") int page,
			@RequestParam(value = "per_page",defaultValue = "15") int per_page,
			@RequestParam(value = "sortby",defaultValue = "updatetime") String sortby,
			@RequestParam(value = "order",defaultValue = "desc") String order) {
		JWTAuthenticationToken jWTAuth = (JWTAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		Map<String,Serializable> ret = new HashMap<String,Serializable>();

		if (jWTAuth.getUserId() > 0) {
			if (gender>9 || college.length()>1 || major.length()>2 || minor.length()>2 || enrolYear.isEmpty()?false:!enrolYear.matches("^2\\d{3}")) {
				ret.put("statuscode", 400);
				ret.put("errmsg", "Request Error:Query Params Error");
				return ret;
			}
			Map<String, Object> result = new HashMap<String,Object>();
			result.put("results",userService.searchUsers(gender, college, major, minor, enrolYear, page, per_page, sortby, order));
			return result;
		} 
		ret.put("statuscode", 403);
		ret.put("errmsg", "Forbbiden");
		return ret;
	}
	
}
	
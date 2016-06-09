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
import org.springframework.web.bind.annotation.RestController;

import online.yaqian.zhiqiunode.model.BookInfo;
import online.yaqian.zhiqiunode.security.JWTAuthenticationToken;
import online.yaqian.zhiqiunode.service.IBookInfoService;
import online.yaqian.zhiqiunode.util.ValidateISBNUtil;

/**
	* @author Young
	* @version 创建时间：2016年4月8日 上午12:32:38
	* 类说明：
	*/
@RestController
@RequestMapping(value="/api/books/isbn")
@Scope(value = "prototype")
public class RestfulBookInfoAPIs {

	public static Logger log = Logger.getLogger(RestfulBookInfoAPIs.class);
	
	@Autowired(required=true)
	@Qualifier("bookInfoService")
	private IBookInfoService bookInfoService;
	
	@RequestMapping(value="/{isbn}",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Object getBookInfo(@PathVariable("isbn") String isbn) {
		if (ValidateISBNUtil.validateISBN(isbn)) {
			return bookInfoService.getBookInfo(isbn);
		} else {
			Map<String,Serializable> ret = new HashMap<String,Serializable>();
			ret.put("statuscode", 400);
			ret.put("errmsg", "ISBN错误，请检查");
			return ret;
		}
		
	}
	
	@RequestMapping(value="/{isbn}",method = RequestMethod.PUT, consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
	public Map<String, Serializable> updateBookInfo(@RequestBody BookInfo bookInfo,@PathVariable("isbn") String isbn) {
		JWTAuthenticationToken jWTAuth = (JWTAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		Map<String,Serializable> ret = new HashMap<String,Serializable>();
		int uid = jWTAuth.getUserId();
		if (ValidateISBNUtil.validateISBN(isbn)) {
			if(bookInfo.getTitle() != null && bookInfoService.getBookInfo(isbn).getUpdatable()) {
				bookInfoService.updateBookInfo(uid, isbn, bookInfo);
				ret.put("statuscode", 200);
				ret.put("errmsg", "Update Book Information Successfully");
			}else if(bookInfo.getSummary() != null){
				bookInfoService.updateBookSummary(uid, isbn, bookInfo.getSummary());
				ret.put("statuscode", 200);
				ret.put("errmsg", "Update Summary Successfully");
			}else {
				ret.put("statuscode", 403);
				ret.put("errmsg", "Forbidden");
			}
		}else {
			ret.put("statuscode", 400);
			ret.put("errmsg", "ISBN错误，请检查");
		}
		return ret;
	}
}

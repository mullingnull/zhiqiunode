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

import online.yaqian.zhiqiunode.model.Order;
import online.yaqian.zhiqiunode.security.JWTAuthenticationToken;
import online.yaqian.zhiqiunode.service.IMineService;
import online.yaqian.zhiqiunode.service.IOrderService;
import online.yaqian.zhiqiunode.service.IWishService;
import online.yaqian.zhiqiunode.util.ValidateISBNUtil;

/**
	* @author Young
	* @version 创建时间：2016年5月2日 下午7:55:55
	* 类说明：
	*/
@RestController
@RequestMapping(value="/api/orders")
@Scope(value = "prototype")
public class RestfulOrderAPIs {

public static Logger log = Logger.getLogger(RestfulOrderAPIs.class);
	
	@Autowired(required=true)
	@Qualifier("orderService")
	private IOrderService orderService;

	@Autowired(required=true)
	@Qualifier("mineService")
	private IMineService mineService;
	
	@Autowired(required=true)
	@Qualifier("wishService")
	private IWishService wishService;
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Object getOrders(@RequestParam(value = "uid",defaultValue = "0") int uid,@RequestParam(value = "isbn",defaultValue = "") String isbn) {
	
		JWTAuthenticationToken jWTAuth = (JWTAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		Map<String,Serializable> ret = new HashMap<String,Serializable>();
		int userId = jWTAuth.getUserId();

		if (userId > 0) {//已注册用户
			userId = uid>0?uid:userId;
			if (ValidateISBNUtil.validateISBN(isbn)) {
				
			}
			HashMap<String, List<Order>> orders = new HashMap<String,List<Order>>();
			 orders.put("asvendor", orderService.getOrdersByUserIdAsVendor(userId));
			 orders.put("asvendee", orderService.getOrdersByUserIdAsVendee(userId));
			 return orders;
		}
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Object addOrder(@RequestBody Order order) {
		JWTAuthenticationToken jWTAuth = (JWTAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		Map<String,Serializable> ret = new HashMap<String,Serializable>();
		int userId = jWTAuth.getUserId();
		int vendorId = ((Long) jWTAuth.getClaims().getClaim("vendorId")).intValue();
		log.info(vendorId);
		log.info(ValidateISBNUtil.validateISBN(order.getIsbn()));
		if (vendorId > 0 && ValidateISBNUtil.validateISBN(order.getIsbn())) {
			Order o = orderService.getOrder(vendorId, userId, order.getIsbn());
			if (o == null) {
				order.setOrderId(null);
				order.setDatetime(null);
				order.setVendorId(vendorId);
				order.setVendeeId(userId);
				order.setQty(1);
				try {
					Map<String, Object> rest = new HashMap<String,Object>();
					orderService.saveOrUpdateOrder(order);
					rest.put("order",orderService.getOrder(vendorId, userId, order.getIsbn()));
					rest.put("statuscode", 201);
					rest.put("errmsg", "OK");
					return rest;
				} catch (Exception e) {
					ret.put("statuscode", 400);
					ret.put("errmsg", "对方好像并没有此藏书啊,继续下一本吧，btw 玩的开心啊");
					return ret;
				}
			} else {
				o.setQty(o.getQty()+1);
				try {
					Map<String, Object> rest = new HashMap<String,Object>();
					orderService.saveOrUpdateOrder(o);
					rest.put("order",orderService.getOrder(vendorId, userId, order.getIsbn()));
					rest.put("statuscode", 201);
					rest.put("errmsg", "OK");
					return rest;
				} catch (Exception e) {
					ret.put("statuscode", 400);
					ret.put("errmsg", "同学很厉害啊，对方的藏书中已经没有这本书了，差点又是个bug还好我思虑周全啊哈哈哈哈");
					return ret;
				}
			}
		}
		ret.put("statuscode", 400);
		ret.put("errmsg", "无效的请求参数");
		return ret;
	}
}

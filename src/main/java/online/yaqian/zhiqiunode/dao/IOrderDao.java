package online.yaqian.zhiqiunode.dao;

import java.util.List;

import online.yaqian.zhiqiunode.model.Order;

/**
	* @author Young
	* @version 创建时间：2016年5月2日 上午12:25:54
	* 类说明：
	*/
public interface IOrderDao extends IBaseDao<Order, Integer> {

	Integer countOrdersByISBN(String isbn);

	List<Order> getOrdersByUserIdAsVendor(int userId);

	List<Order> getOrdersByUserIdAsVendee(int userId);

	List<Order> getOrdersByISBN(String isbn);

	Integer countOrders();

	Integer countOrdersByUserIdAsVendor(int userId);

	Integer countOrdersByUserIdAsVendee(int userId);

	Order getOrder(int vendorId, int vendeeId, String isbn);

}

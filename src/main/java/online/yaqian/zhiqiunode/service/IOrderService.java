package online.yaqian.zhiqiunode.service;

import java.util.List;

import online.yaqian.zhiqiunode.model.Mine;
import online.yaqian.zhiqiunode.model.Order;

/**
	* @author Young
	* @version 创建时间：2016年5月2日 上午12:38:39
	* 类说明：
	*/
public interface IOrderService {

		List<Order> getOrdersByUserIdAsVendor(int userId);

		List<Order> getOrdersByUserIdAsVendee(int userId);

		List<Order> getOrdersByISBN(String isbn);

		Integer countOrders();

		Integer countOrdersByUserIdAsVendor(int userId);

		Integer countOrdersByUserIdAsVendee(int userId);

		Integer countOrdersByISBN(String isbn);

		Order getOrder(int orderId);

		Order getOrder(int vendorId, int vendeeId, String isbn);

		void saveOrUpdateOrder(Order order) throws Exception;

}

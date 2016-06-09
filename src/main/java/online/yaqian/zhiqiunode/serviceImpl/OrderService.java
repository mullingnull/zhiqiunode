package online.yaqian.zhiqiunode.serviceImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import online.yaqian.zhiqiunode.dao.IMineDao;
import online.yaqian.zhiqiunode.dao.IOrderDao;
import online.yaqian.zhiqiunode.dao.IWishDao;
import online.yaqian.zhiqiunode.model.Mine;
import online.yaqian.zhiqiunode.model.Order;
import online.yaqian.zhiqiunode.model.Wish;
import online.yaqian.zhiqiunode.service.IOrderService;

/**
 * @author Young
 * @version 创建时间：2016年5月2日 上午12:39:22 类说明：
 */
@Service("orderService")
public class OrderService implements IOrderService {

	public static Logger log = Logger.getLogger(OrderService.class);

	@Autowired(required = true)
	@Qualifier("orderDao")
	private IOrderDao orderDao;

	@Autowired(required=true)
	@Qualifier("mineDao")
	private IMineDao mineDao;
	
	@Autowired(required=true)
	@Qualifier("wishDao")
	private IWishDao wishDao;
	
	@Override
	@Transactional
	public void saveOrUpdateOrder(Order order) throws Exception {
		Mine mine = mineDao.getMineByUserIdAndISBN(order.getVendorId(), order.getIsbn());
		if (mine == null) {
			throw new Exception("未找到mine");
		}
		Wish wish = wishDao.getWishByUserIdAndISBN(order.getVendeeId(), order.getIsbn());
		if (order.getOrderId() == null) {
			orderDao.save(order);
		} else {
			orderDao.update(order);
		}
		if (mine.getQty()>1) {
			mine.setQty(mine.getQty()-1);
			mineDao.save(mine);
		}else {
			mineDao.delete(mine);
		}
		if (wish!=null) {
			wishDao.delete(wish);
		}
	}
	
	@Override
	public Order getOrder(int orderId) {
		return orderDao.findById(orderId);
	}
	@Override
	public Order getOrder(int vendorId,int vendeeId,String isbn) {
		return orderDao.getOrder(vendorId, vendeeId, isbn);
	}
	@Override
	public List<Order> getOrdersByUserIdAsVendor(int userId) {
		return orderDao.getOrdersByUserIdAsVendor(userId);
	}

	@Override
	public List<Order> getOrdersByUserIdAsVendee(int userId) {
		return orderDao.getOrdersByUserIdAsVendee(userId);
	}

	@Override
	public List<Order> getOrdersByISBN(String isbn) {
		return orderDao.getOrdersByISBN(isbn);
	}

	@Override
	public Integer countOrders() {
		return orderDao.countOrders();
	}
	@Override
	public Integer countOrdersByUserIdAsVendor(int userId) {
		return orderDao.countOrdersByUserIdAsVendor(userId);
	}
	
	@Override
	public Integer countOrdersByUserIdAsVendee(int userId) {
		return orderDao.countOrdersByUserIdAsVendee(userId);
			
	}
	
	@Override
	public Integer countOrdersByISBN(String isbn)  {
		return orderDao.countOrdersByISBN(isbn);
	}
}

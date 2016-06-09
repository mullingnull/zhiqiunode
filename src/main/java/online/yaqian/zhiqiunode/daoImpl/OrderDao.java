package online.yaqian.zhiqiunode.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import online.yaqian.zhiqiunode.dao.IOrderDao;
import online.yaqian.zhiqiunode.model.Order;

/**
	* @author Young
	* @version 创建时间：2016年5月2日 上午12:27:06
	* 类说明：
	*/
@Repository("orderDao")
public class OrderDao extends BaseDao<Order, Integer> implements IOrderDao {

	@Override
	public Order getOrder(int vendorId,int vendeeId,String isbn) {
		return super.findByHQL("FROM online.yaqian.zhiqiunode.model.Order order WHERE order.vendorId = ? AND order.vendeeId = ? AND order.isbn = ? ", vendorId,vendeeId,isbn);
	}
	
	@Override
	public List<Order> getOrdersByUserIdAsVendor(int userId) {
		return super.findListByHQL("FROM online.yaqian.zhiqiunode.model.Order order WHERE order.vendorId = ? ORDER BY order.datetime DESC", userId);
	}
	
	@Override
	public List<Order> getOrdersByUserIdAsVendee(int userId) {
		return super.findListByHQL("FROM online.yaqian.zhiqiunode.model.Order order WHERE order.vendeeId = ? ORDER BY order.datetime DESC", userId);
	}
	
	@Override
	public List<Order> getOrdersByISBN(String isbn) {
		return super.findListByHQL("FROM online.yaqian.zhiqiunode.model.Order order WHERE order.isbn = ? ORDER BY order.datetime DESC", isbn);
	}
	
	@Override
	public Integer countOrders() {
		return ((Long) super.getSession().createQuery("SELECT COUNT(*) FROM online.yaqian.zhiqiunode.model.Order").uniqueResult()).intValue();
	}
	
	@Override
	public Integer countOrdersByUserIdAsVendor(int userId) {
		Query query = super.getSession().createQuery("SELECT COUNT(*) FROM online.yaqian.zhiqiunode.model.Order order WHERE order.vendorId = ?");
		query.setInteger(0, userId);
		return ((Long) query.uniqueResult()).intValue();
	}
	
	@Override
	public Integer countOrdersByUserIdAsVendee(int userId) {
		Query query = super.getSession().createQuery("SELECT COUNT(*) FROM online.yaqian.zhiqiunode.model.Order order WHERE order.vendeeId = ?");
		query.setInteger(0, userId);
		return ((Long) query.uniqueResult()).intValue();
	}
	
	@Override
	public Integer countOrdersByISBN(String isbn)  {
		Query query = super.getSession().createQuery("SELECT COUNT(*) FROM online.yaqian.zhiqiunode.model.Order order WHERE order.isbn = ?");
		query.setString(0, isbn);
		return ((Long) query.uniqueResult()).intValue();
	}
}

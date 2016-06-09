package online.yaqian.zhiqiunode.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import online.yaqian.zhiqiunode.dao.IWishDao;
import online.yaqian.zhiqiunode.dto.WishDetail;
import online.yaqian.zhiqiunode.model.Wish;

/**
	* @author Young
	* @version 创建时间：2016年4月14日 上午1:09:45
	* 类说明：
	*/
@Repository("wishDao")
public class WishDao extends BaseDao<Wish, Integer> implements IWishDao {

	@Override
	public List<Wish> getWishesByUserId(int userId) {
		return super.findListByHQL("FROM online.yaqian.zhiqiunode.model.Wish wish WHERE wish.userId = ? ORDER BY wish.updateTime DESC", userId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WishDetail> getWishDetailByUserId(int userId) {
		Query query = super.getSession().createQuery("SELECT NEW online.yaqian.zhiqiunode.dto.WishDetail(w.userId, w.isbn, w.updateTime,b.title,b.author,b.publisher,b.page,b.price,b.summary,b.pubDate,b.updatable) FROM online.yaqian.zhiqiunode.model.Wish w,online.yaqian.zhiqiunode.model.BookInfo b WHERE w.userId = ? AND w.isbn = b.isbn ORDER BY w.updateTime DESC");
		query.setInteger(0, userId);
		return (List<WishDetail>) query.list();
	}

	
	@Override
	public Wish getWishByUserIdAndISBN(int userId,String isbn) {
		return super.findByHQL("FROM online.yaqian.zhiqiunode.model.Wish wish WHERE wish.userId = ? AND wish.isbn = ?", userId,isbn);
	}
	
	@Override
	public void deleteAllWishes(int userId) {
		Query query = super.getSession().createQuery("DELETE online.yaqian.zhiqiunode.model.Wish wish WHERE wish.userId = ?");
		query.setInteger(0, userId);
		query.executeUpdate();
		super.getSession().beginTransaction().commit();
	}
	
	@Override
	public Integer countWishes() {
		return ((Long) super.getSession().createQuery("SELECT COUNT(*) FROM online.yaqian.zhiqiunode.model.Wish").uniqueResult()).intValue();
	}
	
	@Override
	public Integer countUserWishes(int userId) {
		Query query = super.getSession().createQuery("SELECT COUNT(*) FROM online.yaqian.zhiqiunode.model.Wish wish WHERE wish.userId = ?");
		query.setInteger(0, userId);
		return ((Long) query.uniqueResult()).intValue();
	}
	
	@Override
	public Integer countWishesByISBN(String isbn) {
		Query query = super.getSession().createQuery("SELECT COUNT(*) FROM online.yaqian.zhiqiunode.model.Wish wish WHERE wish.isbn = ?");
		query.setString(0, isbn);
		return ((Long) query.uniqueResult()).intValue();
	}

}

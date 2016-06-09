package online.yaqian.zhiqiunode.serviceImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import online.yaqian.zhiqiunode.dao.IWishDao;
import online.yaqian.zhiqiunode.dto.WishDetail;
import online.yaqian.zhiqiunode.model.Wish;
import online.yaqian.zhiqiunode.service.IWishService;

/**
	* @author Young
	* @version 创建时间：2016年4月14日 上午1:15:05
	* 类说明：
	*/
@Service("wishService")
public class WishService implements IWishService {

	public static Logger log = Logger.getLogger(WishService.class);

	@Autowired(required=true)
	@Qualifier("wishDao")
	private IWishDao wishDao;
	
	@Override
	public List<Wish> getUserWishes(int userId) {
		return wishDao.getWishesByUserId(userId);
	}
	
	@Override
	public List<WishDetail> getWishDetailByUserId(int userId) {
		return wishDao.getWishDetailByUserId(userId);
	}
	
	
	@Override
	public Wish getWishByUserIdAndISBN(int userId, String isbn) {
		return wishDao.getWishByUserIdAndISBN(userId, isbn);
	}
	
	@Override
	public void addUserWish(int userId, String isbn) {
		if (wishDao.getWishByUserIdAndISBN(userId, isbn) == null) {
			wishDao.save(new Wish(userId,isbn));
		}
	}
	
	@Override
	public void deleteUserWish(int userId, String isbn) {
		Wish wish = wishDao.getWishByUserIdAndISBN(userId, isbn);
		if ( wish != null) {
			wishDao.delete(wish);
		}
	}
	
	@Override
	public void deleteAllUserWishes(int userId) {
		//wishDao.
	}
	
	@Override
	public Integer countWishes() {
		return wishDao.countWishes();
	}
	
	@Override
	public Integer countUserWishes(int userId) {
		return wishDao.countUserWishes(userId);
	}
	
	@Override
	public Integer countWishesByISBN(String isbn) {
		return wishDao.countWishesByISBN(isbn);
	}

	
}

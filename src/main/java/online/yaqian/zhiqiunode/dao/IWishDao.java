package online.yaqian.zhiqiunode.dao;

import java.util.List;

import online.yaqian.zhiqiunode.dto.WishDetail;
import online.yaqian.zhiqiunode.model.Wish;

/**
	* @author Young
	* @version 创建时间：2016年4月14日 上午1:08:43
	* 类说明：
	*/
public interface IWishDao extends IBaseDao<Wish, Integer> {

	List<Wish> getWishesByUserId(int userId);

	Wish getWishByUserIdAndISBN(int userId, String isbn);

	void deleteAllWishes(int userId);

	Integer countWishes();

	Integer countUserWishes(int userId);

	Integer countWishesByISBN(String isbn);

	List<WishDetail> getWishDetailByUserId(int userId);

}

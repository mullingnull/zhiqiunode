package online.yaqian.zhiqiunode.service;

import java.util.List;

import online.yaqian.zhiqiunode.dto.WishDetail;
import online.yaqian.zhiqiunode.model.Wish;

/**
	* @author Young
	* @version 创建时间：2016年4月14日 上午1:14:46
	* 类说明：
	*/
public interface IWishService {

		List<Wish> getUserWishes(int userId);

		void addUserWish(int userId, String isbn);

		void deleteUserWish(int userId, String isbn);

		void deleteAllUserWishes(int userId);

		Wish getWishByUserIdAndISBN(int userId, String isbn);

		Integer countWishes();

		Integer countUserWishes(int userId);

		Integer countWishesByISBN(String isbn);

		List<WishDetail> getWishDetailByUserId(int userId);
}

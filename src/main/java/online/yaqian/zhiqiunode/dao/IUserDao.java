package online.yaqian.zhiqiunode.dao;

import java.util.List;

import online.yaqian.zhiqiunode.model.User;

/**
	* @author Young
	* @version 创建时间：2016年4月8日 上午12:40:07
	* 类说明：
	*/
public interface IUserDao extends IBaseDao<User, Integer>{

	Integer countUsers();

	User getUserByWcQRCode(String wcQRCode);

	List<User> searchUsers(int gender, String college, String major, String minor, String enrolYear, int page,
			int per_page, String sortby, String order);

}
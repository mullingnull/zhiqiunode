package online.yaqian.zhiqiunode.service;

import java.util.List;

import online.yaqian.zhiqiunode.dto.UserDetail;
import online.yaqian.zhiqiunode.model.User;
import online.yaqian.zhiqiunode.model.WechatUser;

/**
	* @author Young
	* @version 创建时间：2016年4月8日 上午12:51:22
	* 类说明：
	*/
public interface IUserService {
	
	Integer saveUser(User user,WechatUser wechatUser);

	UserDetail updateUser(User user);

	/**
	 * @Title:getUser
	 * @Description:传入用户Id得到用户信息，注意返回用户信息中性别、学院、专业等信息均为代码表示，需自行进行转换操作
	 * @param uid
	 * @return User
	 */
	User getUser(Integer uid);

	void deleteUser(Integer userid);

	Integer countUser();

	User getUserByWcQRCode(String wcQRCode);

	List<User> searchUsers(int gender, String college, String major, String minor, String enrolYear, int page,
			int per_page, String sortby, String order);
}

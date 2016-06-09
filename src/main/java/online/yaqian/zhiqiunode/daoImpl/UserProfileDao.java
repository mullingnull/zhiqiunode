package online.yaqian.zhiqiunode.daoImpl;

import org.springframework.stereotype.Repository;

import online.yaqian.zhiqiunode.dao.IUserProfileDao;
import online.yaqian.zhiqiunode.model.UserProfile;

/**
	* @author Young
	* @version 创建时间：2016年4月11日 下午10:17:39
	* 类说明：
	*/
@Repository("userProfileDao")
public class UserProfileDao extends BaseDao<UserProfile, Integer> implements IUserProfileDao {

}

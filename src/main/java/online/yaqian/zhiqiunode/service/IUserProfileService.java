package online.yaqian.zhiqiunode.service;

import online.yaqian.zhiqiunode.model.UserProfile;

/**
	* @author Young
	* @version 创建时间：2016年4月11日 下午10:10:58
	* 类说明：
	*/
public interface IUserProfileService {

		Integer saveOrUpdateUserProfile(UserProfile up, int userId);

		UserProfile getUserProfile(int profileId);

		void deleteUserProfileByUserId(int userId);

}

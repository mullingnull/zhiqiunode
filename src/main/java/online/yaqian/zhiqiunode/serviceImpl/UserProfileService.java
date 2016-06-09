package online.yaqian.zhiqiunode.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import online.yaqian.zhiqiunode.dao.IUserDao;
import online.yaqian.zhiqiunode.dao.IUserProfileDao;
import online.yaqian.zhiqiunode.model.User;
import online.yaqian.zhiqiunode.model.UserProfile;
import online.yaqian.zhiqiunode.service.IUserProfileService;

/**
	* @author Young
	* @version 创建时间：2016年4月11日 下午10:12:39
	* 类说明：
	*/
@Service("userProfileService")
public class UserProfileService implements IUserProfileService {

	@Autowired(required=true)
	@Qualifier("userProfileDao")
	private IUserProfileDao userProfileDao;
	
	@Autowired
	@Qualifier("userDao")
	private IUserDao userDao;
	
	@Override
	@Transactional
	public Integer saveOrUpdateUserProfile(UserProfile up,int userId) {
		User u = userDao.findById(userId);
		Integer pid = u.getProfileId();
		if (pid == 0) {
			up.setProfileId(null);
			pid = (Integer) userProfileDao.save(up);
			u.setProfileId(pid);
			userDao.update(u);
		} else {
			up.setProfileId(pid);
			userProfileDao.update(up);
		}
		return pid;	
	}
	
	@Override
	public UserProfile getUserProfile(int profileId) {
		return userProfileDao.findById(profileId);
	}

	@Override
	@Transactional
	public void deleteUserProfileByUserId(int userId) {
		User tmp = userDao.findById(userId);
		if(tmp != null && tmp.getProfileId() > 0) {
			userProfileDao.deleteById(tmp.getProfileId());
			tmp.setProfileId(0);
			userDao.update(tmp);
		}
	}
}

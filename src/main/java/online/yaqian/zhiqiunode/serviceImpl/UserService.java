package online.yaqian.zhiqiunode.serviceImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import online.yaqian.zhiqiunode.util.CollegeUtil;
import online.yaqian.zhiqiunode.util.MajorUtil;
import online.yaqian.zhiqiunode.dao.IUserDao;
import online.yaqian.zhiqiunode.dao.IUserProfileDao;
import online.yaqian.zhiqiunode.dao.IWechatUserDao;
import online.yaqian.zhiqiunode.dto.UserDetail;
import online.yaqian.zhiqiunode.model.User;
import online.yaqian.zhiqiunode.model.WechatUser;
import online.yaqian.zhiqiunode.service.IUserService;

/**
	* @author Young
	* @version 创建时间：2016年4月8日 上午12:52:10
	* 类说明：
	*/
@Service("userService")
public class UserService implements IUserService {
	
	public static Logger log = Logger.getLogger(UserService.class);

	/**
	 * @Filed userDao : 自动注入IWechatUserDao接口的实现类
	 */
	@Autowired(required=true)
	@Qualifier("userDao")
	private IUserDao userDao;
	
	@Autowired()
	@Qualifier("userProfileDao")
	private IUserProfileDao userProfileDao;
	
	@Autowired
	@Qualifier("wechatUserDao")
	private IWechatUserDao wechatUserDao;
	
	@Override
	@Transactional
	public Integer saveUser(User user,WechatUser wechatUser) {
		user.setUserId(null);
		Integer uid = (Integer) userDao.save(user);
		wechatUser.setUserId(uid);
		wechatUserDao.update(wechatUser);
		return uid;
	}
	
	@Override
	public UserDetail updateUser(User update) {
		User user = userDao.findById(update.getUserId());
		if (user!=null) {
			log.info("update.getUserId" + update.getUserId());
			log.info("user.getUserId" + user.getUserId());
			user.merge(update);
			update = null;
			userDao.update(user);
			return new UserDetail(userDao.findById(user.getUserId()));
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see online.yaqian.zhiqiunode.service.IUserService#getUser(java.lang.Integer)
	 */
	@Override
	public User getUser(Integer uid) {
		return userDao.findById(uid);
//		try {
//			User user = userDao.findById(uid);
//			user.setCollege(CollegeUtil.getCollege2Full(user.getCollege()));
//			user.setMajor(MajorUtil.getMajor2Full(user.getMajor()));
//			user.setMinor(MajorUtil.getMajor2Full(user.getMinor()));
//			return user;
//		} catch (NullPointerException e) {
//			return null;
//		}
	}
	
	@Override
	public List<User> searchUsers(int gender,String college,String major,String minor,String enrolYear,int page,int per_page,String sortby,String order) {
		return userDao.searchUsers(gender, college, major, minor, enrolYear, page, per_page, sortby, order);
	}
	
	@Override
	@Transactional
	public void deleteUser(Integer userid) {
		User tmp = userDao.findById(userid);
		if (tmp != null) {
			if(tmp.getProfileId() > 0 ) {
				userProfileDao.deleteById(tmp.getProfileId());
			}
			userDao.deleteById(userid);
			
		} else {

		}
	}
	
	@Override
	public Integer countUser() {
		return userDao.countUsers();
	}
	
	@Override
	public User getUserByWcQRCode(String wcQRCode) {
		return userDao.getUserByWcQRCode(wcQRCode);
	}
}

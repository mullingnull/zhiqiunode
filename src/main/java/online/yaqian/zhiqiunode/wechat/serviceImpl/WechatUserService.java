package online.yaqian.zhiqiunode.wechat.serviceImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import online.yaqian.zhiqiunode.model.WechatUser;
import online.yaqian.zhiqiunode.dao.IWechatUserDao;
import online.yaqian.zhiqiunode.wechat.service.IWechatUserService;

	/**
	 * @ClassName: UserService
	 * @Description: IUserService的具体的实现类，该类仅做一个简单的演示，实际中service中应该是业务逻辑的核心（复杂）
	 * @author: Mervyn
	 * @Time: 2015年11月20日 下午2:27:02
	 */
@Service("wechatUserService")
public class WechatUserService implements IWechatUserService {
	
	public static Logger log = Logger.getLogger(WechatUserService.class);

		@Autowired(required=true)
		@Qualifier("wechatUserDao")
		private IWechatUserDao wechatUserDao;

		@Override
		@Transactional(readOnly=false,isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)  //使用声明式事物
		public WechatUser actWechatUser(String openId,Boolean activate) {
			
		try {
	 			WechatUser wechatUser = wechatUserDao.findById(openId);
					if (wechatUser.getIsActive() != activate) {
						wechatUser.setIsActive(activate);
						wechatUserDao.update(wechatUser);	
					}
					return wechatUser;
			} catch (NullPointerException e) {
				WechatUser wechatUser = new WechatUser(openId,0,activate);
				wechatUserDao.save(wechatUser);
				return wechatUser;
			}
	
		}

		@Override
		@Transactional(readOnly=false,isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
		public WechatUser getWechatUser(String openid) {
			WechatUser wechatUser = wechatUserDao.findById(openid);
			return (wechatUser == null) ? (actWechatUser(openid,true)) : (wechatUser);
		}
		
		
		@Override
		@Transactional
		public Boolean setWechatUserID(String openid,int id) {
			
			try {
				WechatUser wechatUser = wechatUserDao.findById(openid);
				wechatUser.setUserId(id);			
				wechatUserDao.update(wechatUser);
				return true;
			} catch (NullPointerException e) {
				return false;
			}
		}
		
		@Override
		public Integer countWechatUser() {
			return wechatUserDao.countWechatUsers().intValue();
		}
}

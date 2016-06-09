package online.yaqian.zhiqiunode.dao;

import online.yaqian.zhiqiunode.model.WechatUser;

/**
 * @ClassName: UserDao
 * @Description: UserDao接口
 * @author: Mervyn
 * @Time: 2015年11月18日 下午5:27:55
 */
public interface IWechatUserDao extends IBaseDao<WechatUser, String>{

	Integer countWechatUsers();

}
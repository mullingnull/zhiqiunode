package online.yaqian.zhiqiunode.wechat.service;

import online.yaqian.zhiqiunode.model.WechatUser;

/**
 * @ClassName: IUserService
 * @Description: 对UserService封装的接口，这里仅写一个save方法的接口，作为演示。项目中可添加实际的接口。
 * @author: Mervyn
 * @Time: 2015年11月20日 下午2:23:20
 */
/**
 * @author YoungZhu
 *
 */
public interface IWechatUserService {

	/**
	 * @Title: save
	 * @Description: 保存用户
	 * @param: @param user
	 * @return: WechatUser
	 * @throws:
	 */
	WechatUser actWechatUser(String openid, Boolean activate);

	WechatUser getWechatUser(String wechaopenid);

	Boolean setWechatUserID(String openid, int id);

	Integer countWechatUser();

}
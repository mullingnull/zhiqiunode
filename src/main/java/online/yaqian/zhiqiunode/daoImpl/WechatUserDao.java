package online.yaqian.zhiqiunode.daoImpl;

import online.yaqian.zhiqiunode.model.WechatUser;
import online.yaqian.zhiqiunode.dao.IWechatUserDao;

import org.springframework.stereotype.Repository;

@Repository("wechatUserDao")
public class WechatUserDao extends BaseDao<WechatUser, String> implements  IWechatUserDao{

	@Override
	public Integer countWechatUsers() {
		return ((Long) super.getSession().createQuery("SELECT COUNT(*) FROM online.yaqian.zhiqiunode.model.WechatUser").uniqueResult()).intValue();
		}
}
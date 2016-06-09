package online.yaqian.zhiqiunode.dao;

import online.yaqian.zhiqiunode.model.IdentifyCode;

/**
	* @author Young
	* @version 创建时间：2016年5月2日 上午12:30:30
	* 类说明：
	*/
public interface IIdentifyCodeDao extends IBaseDao<IdentifyCode, Integer> {

	IdentifyCode getIdCodeByUserId(int vendorId);

}

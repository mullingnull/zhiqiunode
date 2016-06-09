package online.yaqian.zhiqiunode.daoImpl;

import org.springframework.stereotype.Repository;

import online.yaqian.zhiqiunode.dao.IIdentifyCodeDao;
import online.yaqian.zhiqiunode.model.IdentifyCode;

/**
	* @author Young
	* @version 创建时间：2016年5月2日 上午12:31:54
	* 类说明：
	*/
@Repository("identifyCodeDao")
public class IdentifyCodeDao extends BaseDao<IdentifyCode, Integer> implements IIdentifyCodeDao {

	@Override
	public IdentifyCode getIdCodeByUserId(int vendorId) {
		return super.findByHQL("FROM online.yaqian.zhiqiunode.model.IdentifyCode idCode WHERE idCode.vendorId = ?", vendorId);
	}
	
	
}

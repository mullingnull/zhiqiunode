package online.yaqian.zhiqiunode.service;
	/**
	* @author Young
	* @version 创建时间：2016年5月2日 上午12:36:51
	* 类说明：
	*/
public interface IIdentifyCodeService {

		String getIdCode(int vendorId);

		void deleteExpiredIdCode();

}

package online.yaqian.zhiqiunode.serviceImpl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import online.yaqian.zhiqiunode.dao.IIdentifyCodeDao;
import online.yaqian.zhiqiunode.model.IdentifyCode;
import online.yaqian.zhiqiunode.service.IIdentifyCodeService;

/**
	* @author Young
	* @version 创建时间：2016年5月2日 上午12:37:40
	* 类说明：
	*/
@Service("identifyCodeService")
public class IdentifyCodeService implements IIdentifyCodeService {

	public static Logger log = Logger.getLogger(IdentifyCodeService.class);
	private static final String STR_FORMAT = "00000"; 

	@Autowired(required=true)
	@Qualifier("identifyCodeDao")
	private IIdentifyCodeDao idCodeDao;
	
	@Override
	@Transactional(noRollbackForClassName="ConstraintViolationException")
	public String getIdCode(int vendorId) {
		IdentifyCode idCode = idCodeDao.getIdCodeByUserId(vendorId);
		if (idCode != null) {
			idCodeDao.deleteById(idCode.getCodeId());
			//idCodeDao.flush();
		} 
		try {
			idCode = new IdentifyCode(vendorId,getRandomInt());
			//idCode = new IdentifyCode(vendorId,"23333");
			//idCodeDao.save(idCode);
		}catch (RuntimeException e) {
//		    Throwable t = e.getCause();
//		    while ((t != null) && !(t instanceof ConstraintViolationException)) {
//		        t = t.getCause();
//		    }
//		    if (t instanceof ConstraintViolationException) {
//		        // Here you're sure you have a ConstraintViolationException, you can handle it
		    	return "0";
//		    }
		}
		return idCode.getIdentifyCode();
//		return idCodeDao.findById((Integer) idCodeDao.save(newIdentifyCode(vendorId))).getIdentifyCode();

	}
	
	
	String getRandomInt() {
		return new DecimalFormat(STR_FORMAT).format(ThreadLocalRandom.current().nextInt(100000));
	}
	
	boolean isIdCodeExpired(IdentifyCode idCode) {
		//return idCode.getDatetime().after(new Date());
		return (new Date().getTime() - idCode.getDatetime().getTime()) / (60000L) < 120; //1min = 60*1000 = 60000
	}
	
	//在数据库中用定时任务的形式实现
	@Override
	public void deleteExpiredIdCode() {
		
	}
	
}

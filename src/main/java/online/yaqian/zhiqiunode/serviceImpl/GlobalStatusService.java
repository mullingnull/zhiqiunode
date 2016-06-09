package online.yaqian.zhiqiunode.serviceImpl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import online.yaqian.zhiqiunode.context.GlobalStatus;
import online.yaqian.zhiqiunode.dao.IBookInfoDao;
import online.yaqian.zhiqiunode.dao.IMineDao;
import online.yaqian.zhiqiunode.dao.IOrderDao;
import online.yaqian.zhiqiunode.dao.IUserDao;
import online.yaqian.zhiqiunode.dao.IWechatUserDao;
import online.yaqian.zhiqiunode.dao.IWishDao;
import online.yaqian.zhiqiunode.service.IGlobalStatusService;

/**
	* @author Young
	* @version 创建时间：2016年5月5日 上午4:39:52
	* 类说明：
	*/
@Service("globalStatusService")
@Lazy(false)
public class GlobalStatusService implements IGlobalStatusService {
	
	public static Logger log = Logger.getLogger(GlobalStatusService.class);
	
	@Autowired(required = true)
	@Qualifier("bookInfoDao")
	private IBookInfoDao bDao;
	
	@Autowired(required=true)
	@Qualifier("mineDao")
	private IMineDao mDao;
	
	@Autowired(required = true)
	@Qualifier("orderDao")
	private IOrderDao oDao;
	
	@Autowired(required=true)
	@Qualifier("userDao")
	private IUserDao uDao;
	
	@Autowired(required=true)
	@Qualifier("wishDao")
	private IWishDao wDao;
	
	@Autowired(required=true)
	@Qualifier("wechatUserDao")
	private IWechatUserDao wUDao;
	
	
	@Scheduled(initialDelay = 50000L,fixedDelay=654000) //每十分钟左右唤醒一次
	public void getGlobalStatus() {
		GlobalStatus.getInstance().setBookInfoCount(bDao.countBookInfos());
		GlobalStatus.getInstance().setMineCount(mDao.countMines());
		GlobalStatus.getInstance().setMineQtyCount(mDao.countMineQty());
		GlobalStatus.getInstance().setOrderCount(oDao.countOrders());
		GlobalStatus.getInstance().setUserCount(uDao.countUsers());
		GlobalStatus.getInstance().setWechatUserCount(wUDao.countWechatUsers());
		GlobalStatus.getInstance().setWishCount(wDao.countWishes());
		GlobalStatus.getInstance().setDate(new Date());
	}
}

package online.yaqian.zhiqiunode.serviceImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import online.yaqian.zhiqiunode.dao.IMineDao;
import online.yaqian.zhiqiunode.dto.MineDetail;
import online.yaqian.zhiqiunode.model.Mine;
import online.yaqian.zhiqiunode.service.IMineService;

/**
	* @author Young
	* @version 创建时间：2016年4月14日 上午1:12:17
	* 类说明：
	*/
@Service("mineService")
public class MineService implements IMineService{

	public static Logger log = Logger.getLogger(MineService.class);

	@Autowired(required=true)
	@Qualifier("mineDao")
	private IMineDao mineDao;
	
	@Override
	public List<Mine> getMinesByUserId(int userId) {
		return mineDao.getMinesByUserId(userId);
	}
	
	@Override
	public List<MineDetail> getMineDetailByUserId(int userId) {
		return mineDao.getMineDetailByUserId(userId);
	}
	
	
	@Override
	public List<Mine> getMinesByISBN(String isbn) {
		return mineDao.getMinesByISBN(isbn);
	}
	
	@Override
	public List<MineDetail> searchMines(String isbn,int gender,String college,String major,String minor,String enrolYear,int page,int per_page,String sortby,String order) {
		return mineDao.searchMines(isbn, gender, college, major, minor, enrolYear, page, per_page, sortby, order);
	}
	
	@Override
	public Mine getMineByUserIdAndISBN(int userId,String isbn) {
		return mineDao.getMineByUserIdAndISBN(userId, isbn);
	}
	
	@Override
	public void addMine(Mine mine) {
			mineDao.save(mine);
	}
	
	@Override
	public void updateMine(Mine update,Mine origin) {
		if ( update.getQty()>0 && update.getQty()< 10 ) {
			origin.setQty(update.getQty());
		}
		origin.setPrefer(update.getPrefer());
		mineDao.update(origin);
	}

	@Override
	public void deleteMine(int userId, String isbn) {
		Mine mine = mineDao.getMineByUserIdAndISBN(userId, isbn);
		if (mine != null) {
			mineDao.delete(mine);
		}
	}
	
	@Override
	public Integer countMines() {
		return mineDao.countMines();
	}
	
	@Override
	public Integer countUserMines(int userId) {
		return mineDao.countUserMines(userId);
	}
	
	@Override
	public Integer countMinesByISBN(String isbn) {
		return mineDao.countMinesByISBN(isbn);
	}
}

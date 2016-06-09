package online.yaqian.zhiqiunode.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import online.yaqian.zhiqiunode.dao.IMineDao;
import online.yaqian.zhiqiunode.dto.MineDetail;
import online.yaqian.zhiqiunode.model.Mine;
import online.yaqian.zhiqiunode.util.CollegeUtil;
import online.yaqian.zhiqiunode.util.MajorUtil;

/**
	* @author Young
	* @version 创建时间：2016年4月14日 上午1:07:22
	* 类说明：
	*/
@Repository("mineDao")
public class MineDao extends BaseDao<Mine, Integer> implements IMineDao {

	@Override
	public List<Mine> getMinesByUserId(int userId) {
		return super.findListByHQL("FROM online.yaqian.zhiqiunode.model.Mine mine WHERE mine.userId = ? ORDER BY mine.updateTime DESC", userId);
	}
	
	@Override
	public List<Mine> getMinesByISBN(String isbn) {
		return super.findListByHQL("FROM online.yaqian.zhiqiunode.model.Mine mine WHERE mine.isbn = ? ORDER BY mine.updateTime DESC", isbn);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MineDetail> getMineDetailByUserId(int userId) {
		Query query = super.getSession().createQuery("SELECT NEW online.yaqian.zhiqiunode.dto.MineDetail(m.isbn, m.prefer, m.qty, m.updateTime,b.title,b.author,b.publisher,b.page,b.price,b.summary,b.pubDate,b.updatable) FROM online.yaqian.zhiqiunode.model.Mine m,online.yaqian.zhiqiunode.model.BookInfo b WHERE m.userId = ? AND m.isbn = b.isbn ORDER BY m.updateTime DESC");
		query.setInteger(0, userId);
		return (List<MineDetail>) query.list();
	}
	
	@Override
	public Mine getMineByUserIdAndISBN(int userId,String isbn) {
		return super.findByHQL("FROM online.yaqian.zhiqiunode.model.Mine mine WHERE mine.userId = ? AND mine.isbn = ?", userId,isbn);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MineDetail> searchMines(String isbn,int gender,String college,String major,String minor,String enrolYear,int page,int per_page,String sortby,String order) {
		StringBuffer hql = new StringBuffer("SELECT NEW online.yaqian.zhiqiunode.dto.MineDetail(m.userId, m.prefer, m.qty, m.updateTime,u.nickName,u.gender,u.major,u.am,u.college,u.enrolYear,u.wcQrcode,u.minor) FROM online.yaqian.zhiqiunode.model.Mine m,online.yaqian.zhiqiunode.model.User u WHERE m.userId = u.userId AND m.isbn = ").append(isbn);
		if (gender<2) {
			hql.append(" AND u.gender = '").append(gender).append("'");
		}
		if (MajorUtil.validateShortMajorForm(major)) {
			hql.append(" AND u.major = '").append(major).append("'");
		}else if (CollegeUtil.validateShortCollegeForm(college)) {
			hql.append(" AND u.college = '").append(college).append("'");
		}
//		if (MajorUtil.validateShortMajorForm(minor)) {
//			hql.append(" AND u.minor = '").append(minor).append("'");
//		}
		if (enrolYear.matches("^2\\d{3}")) {
			hql.append(" AND u.enrolYear = '").append(enrolYear).append("'");
		}
		if ("enrolYear".equalsIgnoreCase(sortby)) {
			hql.append(" ORDER BY u.enrolYear");
		} else {
			hql.append(" ORDER BY m.updateTime");
		}
		if ("asc".equalsIgnoreCase(order)) {
			hql.append(" ASC");
		} else {
			hql.append(" DESC");
		}
		Query query = super.getSession().createQuery(hql.toString());
		per_page = per_page > 35 ? 35 : per_page;
		if (page>1) {
			query.setFirstResult((page-1)*per_page);
		} else {
			query.setFirstResult(0);
		}
		query.setMaxResults(per_page);
		List<MineDetail> mDetails = (List<MineDetail>)query.list();
		for (MineDetail mDetail : mDetails) {
			mDetail.setClues(super.getSession().createQuery("SELECT mine.isbn FROM online.yaqian.zhiqiunode.model.Mine mine WHERE mine.userId = ?").setInteger(0, mDetail.getUserId()).list());
		}
		return mDetails;
	}
	
	@Override
	public Integer countMines() {
		return ((Long) super.getSession().createQuery("SELECT COUNT(*) FROM online.yaqian.zhiqiunode.model.Mine").uniqueResult()).intValue();
	}
	
	@Override
	public Integer countMineQty() {
		return ((Long) super.getSession().createQuery("SELECT COALESCE(SUM(m.qty),0) FROM online.yaqian.zhiqiunode.model.Mine m").uniqueResult()).intValue();
	}
	
	@Override
	public Integer countUserMines(int userId) {
		Query query = super.getSession().createQuery("SELECT COUNT(mine.mineId) FROM online.yaqian.zhiqiunode.model.Mine mine WHERE mine.userId = ?");
		query.setInteger(0, userId);
		return ((Long) query.uniqueResult()).intValue();
	}
	
	@Override
	public Integer countMinesByISBN(String isbn) {
		Query query = super.getSession().createQuery("SELECT COUNT(mine.mineId) FROM online.yaqian.zhiqiunode.model.Mine mine WHERE mine.isbn = ?");
		query.setString(0, isbn);
		return ((Long) query.uniqueResult()).intValue();
	}
	
	@Override
	public Integer countMineQtyByISBN(String isbn) {
		Query query = super.getSession().createQuery("SELECT COALESCE(SUM(m.qty),0) FROM online.yaqian.zhiqiunode.model.Mine m WHERE m.isbn = ?");
		query.setString(0, isbn);
		return ((Long) query.uniqueResult()).intValue();
	}

}

package online.yaqian.zhiqiunode.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import online.yaqian.zhiqiunode.dao.IUserDao;
import online.yaqian.zhiqiunode.model.User;
import online.yaqian.zhiqiunode.util.CollegeUtil;
import online.yaqian.zhiqiunode.util.MajorUtil;

/**
	* @author Young
	* @version 创建时间：2016年4月8日 上午12:44:40
	* 类说明：
	*/
@Repository("userDao")
public class UserDao extends BaseDao<User, Integer> implements IUserDao {
	
	@Override
	public Integer countUsers() {
		return ((Long) super.getSession().createQuery("SELECT COUNT(*) FROM online.yaqian.zhiqiunode.model.User").uniqueResult()).intValue();
		
	}
	
	@Override
	public User getUserByWcQRCode(String wcQRCode) {
		return super.findByHQL("FROM online.yaqian.zhiqiunode.model.User u WHERE u.wcQrcode = ? ", wcQRCode);
	}
	
	/**
	 * @Title:searchUsers
	 * @Description:根据条件检索出立flag的用户
	 * @param gender
	 * @returnList<User>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> searchUsers(int gender,String college,String major,String minor,String enrolYear,int page,int per_page,String sortby,String order) {
		StringBuffer hql = new StringBuffer("FROM online.yaqian.zhiqiunode.model.User u WHERE u.flag=true");
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
		return query.list();
	}
}

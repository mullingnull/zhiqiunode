package online.yaqian.zhiqiunode.dao;

import java.util.List;

import online.yaqian.zhiqiunode.dto.MineDetail;
import online.yaqian.zhiqiunode.model.Mine;

/**
	* @author Young
	* @version 创建时间：2016年4月14日 上午1:06:20
	* 类说明：
	*/
public interface IMineDao extends IBaseDao<Mine, Integer> {

	List<Mine> getMinesByUserId(int userId);

	List<Mine> getMinesByISBN(String isbn);

	Mine getMineByUserIdAndISBN(int userId, String isbn);

	List<MineDetail> searchMines(String isbn, int gender, String college, String major, String minor, String enrolYear,
			int page, int per_page, String sortby, String order);

	Integer countMines();

	Integer countUserMines(int userId);

	Integer countMinesByISBN(String isbn);

	List<MineDetail> getMineDetailByUserId(int userId);

	Integer countMineQty();

	Integer countMineQtyByISBN(String isbn);

}

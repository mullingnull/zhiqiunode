package online.yaqian.zhiqiunode.service;

import java.util.List;

import online.yaqian.zhiqiunode.dto.MineDetail;
import online.yaqian.zhiqiunode.model.Mine;

/**
	* @author Young
	* @version 创建时间：2016年4月14日 上午1:11:05
	* 类说明：
	*/
public interface IMineService {

		List<Mine> getMinesByUserId(int userId);

		List<Mine> getMinesByISBN(String isbn);

		void addMine(Mine mine);

		Mine getMineByUserIdAndISBN(int userId, String isbn);

		void updateMine(Mine update, Mine origin);

		void deleteMine(int userId, String isbn);

		List<?> searchMines(String isbn, int gender, String college, String major, String minor, String enrolYear,
				int page, int per_page, String sortby, String order);

		Integer countMines();

		Integer countUserMines(int userId);

		Integer countMinesByISBN(String isbn);

		List<MineDetail> getMineDetailByUserId(int userId);

}

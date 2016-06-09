package online.yaqian.zhiqiunode.dao;

import online.yaqian.zhiqiunode.model.BookAlterHistory;
import online.yaqian.zhiqiunode.model.BookInfo;

/**
	* @author Young
	* @version 创建时间：2016年4月13日 下午5:59:12
	* 类说明：
	*/
public interface IBookAlterHistoryDao extends IBaseDao<BookAlterHistory, Integer> {

	void saveAlterSummaryHistory(int userid, String isbn, String summary);

	void saveAlterBookInfoHistory(int userid, String isbn, BookInfo origin, BookInfo modified);

	void saveAlterBookInfoHistory(String isbn, BookInfo fromDouBan);

}

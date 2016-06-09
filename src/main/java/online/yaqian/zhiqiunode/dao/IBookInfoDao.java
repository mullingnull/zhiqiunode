package online.yaqian.zhiqiunode.dao;

import java.util.ArrayList;
import java.util.List;

import online.yaqian.zhiqiunode.model.BookInfo;

public interface IBookInfoDao extends IBaseDao<BookInfo, Integer>{

	BookInfo getBookInfo(String isbn);

	List<BookInfo> getBookInfo(ArrayList<String> isbns);

	Integer countBookInfos();

}

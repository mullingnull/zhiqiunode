package online.yaqian.zhiqiunode.service;

import java.util.ArrayList;
import java.util.List;

import online.yaqian.zhiqiunode.model.BookInfo;

public interface IBookInfoService {

	BookInfo getBookInfo(String isbn);

	List<BookInfo> getBookInfo(ArrayList<String> isbns);

	void updateBookInfo(int uid, String isbn, BookInfo bookInfo);

	void updateBookSummary(int uid, String isbn, String summary);

	
}

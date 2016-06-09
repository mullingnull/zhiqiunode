package online.yaqian.zhiqiunode.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import online.yaqian.zhiqiunode.model.BookInfo;
import online.yaqian.zhiqiunode.dao.IBookAlterHistoryDao;
import online.yaqian.zhiqiunode.dao.IBookInfoDao;
import online.yaqian.zhiqiunode.service.IBookInfoService;
import online.yaqian.zhiqiunode.util.GetBookInfoFromDouBan;

@Service("bookInfoService")
public class BookInfoService implements IBookInfoService {

	
	public static Logger log = Logger.getLogger(BookInfoService.class);
		/**
		 * @Filed userDao : 自动注入IBookInfoDao接口的实现类
		 */
		@Autowired(required = true)
		@Qualifier("bookInfoDao")
		private IBookInfoDao bookInfoDao;

		@Autowired(required = true)
		@Qualifier("bookAlterHistoryDao")
		private IBookAlterHistoryDao bookAltHisDao;
		// 批量检索书籍情况仅出现在对在库图书信息的查询，故不会出现空数据情况
		@Override
		public List<BookInfo> getBookInfo(ArrayList<String> isbns) {
			return bookInfoDao.getBookInfo(isbns);
		}
	
		@Override
		@Transactional
		public BookInfo getBookInfo(String isbn) {

			BookInfo bookInfo = bookInfoDao.getBookInfo(isbn);
			if (bookInfo == null) {
				bookInfo = GetBookInfoFromDouBan.getBookInfo(isbn);
				if(bookInfo.getTitle()==null || bookInfo.getAuthor()==null || bookInfo.getPublisher()==null || bookInfo.getPage()==null) {
					bookInfo.setUpdatable(true);
					log.error("书目信息不全，ISBN为" + isbn);
					bookAltHisDao.saveAlterBookInfoHistory(isbn, bookInfo);
				}else{
					bookInfo.setUpdatable(false);
				}
				bookInfoDao.save(bookInfo);
				return bookInfoDao.getBookInfo(isbn);
			} else {
				return bookInfo;
			}
		}

		@Override
		@Transactional
		public void updateBookInfo(int userid,String isbn,BookInfo bookInfo) {
			BookInfo pbInfo = bookInfoDao.getBookInfo(isbn);
			pbInfo.merge(bookInfo);
			bookInfoDao.update(pbInfo);
			bookAltHisDao.saveAlterBookInfoHistory(userid, isbn, pbInfo, bookInfo);
		}
		
		@Override
		@Transactional
		public void updateBookSummary(int userid,String isbn,String summary) {
			BookInfo pbInfo = bookInfoDao.getBookInfo(isbn);
			pbInfo.setSummary(summary);
			bookInfoDao.save(pbInfo);
			bookAltHisDao.saveAlterSummaryHistory(userid, isbn, summary);
		}		
		
}

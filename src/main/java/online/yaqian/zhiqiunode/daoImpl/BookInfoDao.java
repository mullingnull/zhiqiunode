package online.yaqian.zhiqiunode.daoImpl;

import java.util.ArrayList;
import java.util.List;

import online.yaqian.zhiqiunode.model.BookInfo;
import online.yaqian.zhiqiunode.dao.IBookInfoDao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;


@Repository("bookInfoDao")
public class BookInfoDao extends BaseDao<BookInfo, Integer> implements  IBookInfoDao{
	
	public static Logger log = Logger.getLogger(BookInfoDao.class);
	
	@Override
	public BookInfo getBookInfo(String isbn) {
		return super.findByHQL("FROM online.yaqian.zhiqiunode.model.BookInfo bookinfo WHERE bookinfo.isbn = ?", isbn);
} 

	
	//批量检索书籍情况仅对已在库图书信息查询，不会出现未命中空数据情况
	@Override
	public List<BookInfo> getBookInfo(ArrayList<String> isbns) {
		return super.findListByHQL("FROM online.yaqian.zhiqiunode.model.BookInfo bookinfo WHERE bookinfo.isbn = ?", isbns);
	}
	
	@Override
	public Integer countBookInfos() {
		return ((Long) super.getSession().createQuery("SELECT COUNT(*) FROM online.yaqian.zhiqiunode.model.BookInfo").uniqueResult()).intValue();
		
	}
}
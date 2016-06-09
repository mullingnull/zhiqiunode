package online.yaqian.zhiqiunode.daoImpl;

import org.springframework.stereotype.Repository;

import online.yaqian.zhiqiunode.dao.IBookAlterHistoryDao;
import online.yaqian.zhiqiunode.model.BookAlterHistory;
import online.yaqian.zhiqiunode.model.BookInfo;

/**
	* @author Young
	* @version 创建时间：2016年4月13日 下午6:00:15
	* 类说明：
	*/
@Repository("bookAlterHistoryDao")
public class BookAlterHistoryDao extends BaseDao<BookAlterHistory, Integer> implements IBookAlterHistoryDao {

	@Override
	public void saveAlterSummaryHistory(int userid,String isbn,String summary){
		super.save(new BookAlterHistory(userid,isbn,summary));
	}
	
	@Override
	public void saveAlterBookInfoHistory(int userid,String isbn,BookInfo origin,BookInfo modified){
		BookAlterHistory bookAltHis = new BookAlterHistory(userid,isbn);
		bookAltHis.setAuthor(origin.getAuthor() == modified.getAuthor()?null:modified.getAuthor());
		bookAltHis.setPage(origin.getPage() == modified.getPage()?null:modified.getPage());
		bookAltHis.setPrice(origin.getPrice() == modified.getPrice()?null:modified.getPrice());
		bookAltHis.setPubDate(origin.getPubDate() == modified.getPubDate()?null:modified.getPubDate());
		bookAltHis.setPublisher(origin.getPublisher() == modified.getPublisher()?null:modified.getPublisher());
		bookAltHis.setSummary(origin.getSummary() == modified.getSummary()?null:modified.getSummary());
		bookAltHis.setTitle(origin.getTitle() == modified.getTitle()?null:modified.getTitle());
		super.save(bookAltHis);
	}
	
	@Override
	public void saveAlterBookInfoHistory(String isbn,BookInfo fromDouBan){
		BookAlterHistory bookAltHis = new BookAlterHistory(-1,isbn);
		bookAltHis.setAuthor(fromDouBan.getAuthor() == null?"未找到":fromDouBan.getAuthor());
		bookAltHis.setPage(fromDouBan.getPage() == null ? 0 :fromDouBan.getPage());
		bookAltHis.setPrice(fromDouBan.getPrice() == null?null:fromDouBan.getPrice());
		bookAltHis.setPubDate(fromDouBan.getPubDate() == null?null:fromDouBan.getPubDate());
		bookAltHis.setPublisher(fromDouBan.getPublisher() == null?"未找到":fromDouBan.getPublisher());
		bookAltHis.setSummary(fromDouBan.getSummary() == null?"未找到":fromDouBan.getSummary());
		bookAltHis.setTitle(fromDouBan.getTitle() == null?"未找到":fromDouBan.getTitle());
		super.save(bookAltHis);
	}
}

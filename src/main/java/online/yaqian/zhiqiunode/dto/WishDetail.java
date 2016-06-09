package online.yaqian.zhiqiunode.dto;

import java.math.BigDecimal;
import java.util.Date;

import online.yaqian.zhiqiunode.model.BookInfo;
import online.yaqian.zhiqiunode.model.Wish;

/**
	* @author Young
	* @version 创建时间：2016年4月27日 下午7:23:43
	* 类说明：
	*/
public class WishDetail {
		
	private Integer wishId;
	private Integer userId;
	private String isbn;
	private Date updateTime;
	
	//private Integer bookId;
	//private String isbn;
	private String title;
	private String author;
	private String publisher;
	private Integer page;
	private BigDecimal price;
	private String summary;
	private Date pubDate;
	private Boolean updatable;
	
	private int mineCount;
	
	public WishDetail(int userId, String isbn, Date updateTime, String title, String author,
			String publisher, Integer page, BigDecimal price, String summary, Date pubDate, Boolean updatable) {
		this.userId = userId;
		this.isbn = isbn;
		this.updateTime = updateTime;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.page = page;
		this.price = price;
		this.summary = summary;
		this.pubDate = pubDate;
		this.updatable = updatable;
	}
	
	public WishDetail(int userId, String isbn, Date updateTime, String title, String author,
			String publisher, Integer page, BigDecimal price, String summary, Date pubDate, Boolean updatable,int mineCount) {
		this.userId = userId;
		this.isbn = isbn;
		this.updateTime = updateTime;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.page = page;
		this.price = price;
		this.summary = summary;
		this.pubDate = pubDate;
		this.updatable = updatable;
		this.mineCount = mineCount;
	}
	
	public WishDetail(Wish wish,BookInfo bookInfo,int mineCount) {

		this.isbn = wish.getIsbn();
		this.updateTime = wish.getUpdateTime();
		
		this.title = bookInfo.getTitle();
		this.author = bookInfo.getAuthor();
		this.publisher = bookInfo.getPublisher();
		this.page = bookInfo.getPage();
		this.price = bookInfo.getPrice();
		this.summary = bookInfo.getSummary();
		this.pubDate = bookInfo.getPubDate();
		this.updatable = bookInfo.getUpdatable();
		this.mineCount = mineCount;
	}

	
	
	
	
	public Integer getWishId() {
		return wishId;
	}
	public void setWishId(Integer wishId) {
		this.wishId = wishId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	public Boolean getUpdatable() {
		return updatable;
	}
	public void setUpdatable(Boolean updatable) {
		this.updatable = updatable;
	}

	public int getMineCount() {
		return mineCount;
	}

	public void setMineCount(int mineCount) {
		this.mineCount = mineCount;
	}
	
	
}

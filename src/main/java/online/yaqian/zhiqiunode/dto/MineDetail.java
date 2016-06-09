package online.yaqian.zhiqiunode.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import online.yaqian.zhiqiunode.model.BookInfo;
import online.yaqian.zhiqiunode.model.Mine;
import online.yaqian.zhiqiunode.model.User;

/**
	* @author Young
	* @version 创建时间：2016年4月15日 下午5:21:46
	* 类说明：
	*/
public class MineDetail {
	
	private Integer mineId;
	private Integer userId;
	private String isbn;
	private String prefer;
	private Integer qty;
	private Date updateTime;
	
	//private Integer userId;
	private String nickName;
	private Integer gender;
	private String major;
	private String am;
	private String college;
	private Integer enrolYear;
	private Integer profileId;
	private String wcQrcode;
	private String minor;
	
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
	
	private Integer wishCount;
	
	private List<String> clues;
	
	
	
	/**
	 * @Title:MineDetail
	 * @Description:用于指定ISBN时，传入Mine和User相关的参数
	 * @param userId
	 * @param prefer
	 * @param qty
	 * @param updateTime
	 * @param nickName
	 * @param gender
	 * @param major
	 * @param am
	 * @param college
	 * @param enrolYear
	 * @param wcQrcode
	 * @param minor
	 */
	public MineDetail(int userId, String prefer, Integer qty, Date updateTime,
			String nickName, Integer gender, String major, String am, String college, Integer enrolYear,
			String wcQrcode, String minor) {
		
		this.userId = userId;
		this.prefer = prefer;
		this.qty = qty;
		this.updateTime = updateTime;
		this.nickName = nickName;
		this.gender = gender;
		this.major = major;
		this.am = am;
		this.college = college;
		this.enrolYear = enrolYear;
		this.wcQrcode = wcQrcode;
		this.minor =minor;
	}

	/**
	 * @Title:MineDetail
	 * @Description:用于指定UserId时，传入Mine和BookInfo相关的参数
	 * @param isbn
	 * @param prefer
	 * @param qty
	 * @param updateTime
	 * @param title
	 * @param author
	 * @param publisher
	 * @param page
	 * @param price
	 * @param summary
	 * @param pubDate
	 * @param updatable
	 * 
	 */
	public MineDetail(String isbn, String prefer, Integer qty, Date updateTime, String title, String author,
			String publisher, Integer page, BigDecimal price, String summary, Date pubDate, Boolean updatable) {

		this.isbn = isbn;
		this.prefer = prefer;
		this.qty = qty;
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

	/**
	 * @Title:MineDetail
	 * @Description:用于指定ISBN时，传入Mine和User
	 * @param mine
	 * @param user
	 */
	public MineDetail(Mine mine,User user) {
		this.mineId = mine.getMineId();
		this.userId = mine.getUserId();
		this.isbn = mine.getIsbn();
		this.prefer = mine.getPrefer();
		this.qty = mine.getQty();
		this.updateTime = mine.getUpdateTime();
		
		this.nickName = user.getNickName();
		this.gender = user.getGender();
		this.major = user.getMajor();
		this.am = user.getAm();
		this.college = user.getCollege();
		this.enrolYear = user.getEnrolYear();
		this.profileId = user.getProfileId();
		this.wcQrcode = user.getWcQrcode();
		this.minor = user.getMinor();
	}
	
	/**
	 * @Title:MineDetail
	 * @Description:用于指定ISBN时，传入Mine和BookInfo
	 * @param mine
	 * @param bookInfo
	 */
	public MineDetail(Mine mine,BookInfo bookInfo) {

		this.mineId = mine.getMineId();
		this.userId = mine.getUserId();
		this.isbn = mine.getIsbn();
		this.prefer = mine.getPrefer();
		this.qty = mine.getQty();
		this.updateTime = mine.getUpdateTime();
	
		this.title = bookInfo.getTitle();
		this.author = bookInfo.getAuthor();
		this.publisher = bookInfo.getPublisher();
		this.page = bookInfo.getPage();
		this.price = bookInfo.getPrice();
		this.summary = bookInfo.getSummary();
		this.pubDate = bookInfo.getPubDate();
		this.updatable = bookInfo.getUpdatable();
	}
	
	/**
	 * @Title:MineDetail
	 * @Description:用于指定ISBN时，传入Mine和BookInfo,wishCount
	 * @param mine
	 * @param bookInfo
	 * @param wishCount
	 */
	public MineDetail(Mine mine,BookInfo bookInfo,int wishCount) {

		this.mineId = mine.getMineId();
		this.isbn = mine.getIsbn();
		this.prefer = mine.getPrefer();
		this.qty = mine.getQty();
		this.updateTime = mine.getUpdateTime();
	
		this.title = bookInfo.getTitle();
		this.author = bookInfo.getAuthor();
		this.publisher = bookInfo.getPublisher();
		this.page = bookInfo.getPage();
		this.price = bookInfo.getPrice();
		this.summary = bookInfo.getSummary();
		this.pubDate = bookInfo.getPubDate();
		this.updatable = bookInfo.getUpdatable();
		this.wishCount = wishCount;
	}
	
	public Integer getMineId() {
		return mineId;
	}
	public void setMineId(Integer mineId) {
		this.mineId = mineId;
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
	public String getPrefer() {
		return prefer;
	}
	public void setPrefer(String prefer) {
		this.prefer = prefer;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getAm() {
		return am;
	}
	public void setAm(String am) {
		this.am = am;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public Integer getEnrolYear() {
		return enrolYear;
	}
	public void setEnrolYear(Integer enrolYear) {
		this.enrolYear = enrolYear;
	}
	public Integer getProfileId() {
		return profileId;
	}
	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}
	public String getWcQrcode() {
		return wcQrcode;
	}
	public void setWcQrcode(String wcQrcode) {
		this.wcQrcode = wcQrcode;
	}
	public String getMinor() {
		return minor;
	}
	public void setMinor(String minor) {
		this.minor = minor;
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
	public Integer getWishCount() {
		return wishCount;
	}
	public void setWishCount(Integer wishCount) {
		this.wishCount = wishCount;
	}
	public List<String> getClues() {
		return clues;
	}
	public void setClues(List<String> clues) {
		this.clues = clues;
	}
}

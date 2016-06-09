package online.yaqian.zhiqiunode.model;
// default package
// Generated 2016-4-13 17:52:55 by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * BookAlterHistory generated by hbm2java
 */
@Entity
@Table(name = "book_alter_history", catalog = "zhiqiunode")
@DynamicInsert(true)
@DynamicUpdate(true)
public class BookAlterHistory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1793149813242685268L;
	private Integer alterId;
	private int userId;
	private String isbn;
	private String summary;
	private String title;
	private String author;
	private String publisher;
	private Integer page;
	private BigDecimal price;
	private Date pubDate;
	private Date datetime;

	public BookAlterHistory() {
	}

	public BookAlterHistory(int userId, String isbn) {
		this.userId = userId;
		this.isbn = isbn;
	}
	
	public BookAlterHistory(int userId, String isbn, String summary) {
		this.userId = userId;
		this.isbn = isbn;
		this.summary = summary;
	}

	public BookAlterHistory(int userId, String isbn, String summary, String title, String author, String publisher,
			Integer page, BigDecimal price, Date pubDate) {
		this.userId = userId;
		this.isbn = isbn;
		this.summary = summary;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.page = page;
		this.price = price;
		this.pubDate = pubDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "alter_id", unique = true, nullable = false)
	public Integer getAlterId() {
		return this.alterId;
	}

	public void setAlterId(Integer alterId) {
		this.alterId = alterId;
	}

	@Column(name = "user_id", nullable = false)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "isbn", nullable = false, length = 13)
	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Column(name = "summary", length = 225)
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "title", length = 25)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "author", length = 25)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "publisher", length = 20)
	public String getPublisher() {
		return this.publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Column(name = "page")
	public Integer getPage() {
		return this.page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	@Column(name = "price", precision = 5)
	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "pub_date", length = 10)
	public Date getPubDate() {
		return this.pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datetime", length = 19,updatable = false)
	public Date getDatetime() {
		return this.datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

}

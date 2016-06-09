package online.yaqian.zhiqiunode.context;

import java.util.Date;

/**
	* @author Young
	* @version 创建时间：2016年5月4日 下午10:53:07
	* 类说明：
	*/
public class GlobalStatus {
	
	private int wechatUserCount;
	private int userCount;
	private int bookInfoCount;
	private int mineCount;
	private int wishCount;
	private int orderCount;
	private int mineQtyCount;
	private Date date;
	
	
	
	 static class GlobalStatusHolder {
	      static GlobalStatus instance = new GlobalStatus();
	  }   
	  
	  public static GlobalStatus getInstance() {
	      return GlobalStatusHolder.instance;
	  }

	public int getWechatUserCount() {
		return wechatUserCount;
	}

	public void setWechatUserCount(int wechatUserCount) {
		this.wechatUserCount = wechatUserCount;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getBookInfoCount() {
		return bookInfoCount;
	}

	public void setBookInfoCount(int bookInfoCount) {
		this.bookInfoCount = bookInfoCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public void setMineCount(int mineCount) {
		this.mineCount = mineCount;
	}

	public int getWishCount() {
		return wishCount;
	}

	public void setWishCount(int wishCount) {
		this.wishCount = wishCount;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public int getMineQtyCount() {
		return mineQtyCount;
	}

	public void setMineQtyCount(int mineQtyCount) {
		this.mineQtyCount = mineQtyCount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

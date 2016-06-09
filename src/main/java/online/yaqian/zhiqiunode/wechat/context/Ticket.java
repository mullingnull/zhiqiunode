package online.yaqian.zhiqiunode.wechat.context;

import java.util.Date;

/**
 * @author YoungZhu
 *
 * 存储access_token和jsapi_ticket
 */
public class Ticket {
	
	  private String accessToken;//接口访问凭据
	  
	  private long acessTokenCreateTime;//接口访问凭据创建时间，理论上是2小时后过期
	  
	  private String jsApiTicket;//jssdk使用凭据
	  
	  private long jsApiTicketCreateTime;//jssdkTicket创建时间，理论上是2小时后过期
	  
	  static class WeChatInteractionContextHolder {
	      static Ticket instance = new Ticket();
	  }   
	  
	  public static Ticket getInstance() {
	      return WeChatInteractionContextHolder.instance;
	  }   
	  
	  //AceessToken是否过期
	  public boolean isAceessTokenNearlyExpired() {
	      long time = new Date().getTime();
	      //如果当前记录时间为0
	      if(this.acessTokenCreateTime <= 0) {
	          return true;
	      }
	      //判断记录时间是否超过3600s
	      if(this.acessTokenCreateTime/1000 + 3600 < time/1000) {
	          return true;
	      }
	      return false;
	  }
	      //记录接口访问凭证
	  public void saveLocalAccessonToke(String accessToken) {
	      this.accessToken = accessToken;
	      this.acessTokenCreateTime = new Date().getTime();
	  }
	  public void setAccessToken(String accessToken) {
	      this.accessToken = accessToken;
	  }
	  /**
	 * @Title:getAccessToken
	 * @Description:返回当前调用微信接口时需提供的AccessToken
	 * @return String
	 */
	public String getAccessToken() {
	      return accessToken;
	  }   
	  public void setAccessTokenCreateTime(long createTime) {
	      this.acessTokenCreateTime = createTime;
	  }
	  public long getAccessTokenCreateTime() {
	      return acessTokenCreateTime;
	  }
	  
	//是否JsApiTicket过期
	  public boolean isJsApiTicketNearlyExpired() {
	      long time = new Date().getTime();
	      //如果当前记录时间为0
	      if(this.jsApiTicketCreateTime <= 0) {
	          return true;
	      }
	      //判断记录时间是否超过3600s
	      if(this.jsApiTicketCreateTime/1000 + 3600 < time/1000) {
	          return true;
	      }
	      return false;
	  }
	      //记录接口访问凭证
	  public void saveLocalJsApiTicket(String jsApiTicket) {
	      this.jsApiTicket = jsApiTicket;
	      this.jsApiTicketCreateTime = new Date().getTime();
	  }
	  public void setJsApiTicket(String jsApiTicket) {
	      this.jsApiTicket = jsApiTicket;
	  }
	  /**
	 * @Title:getJsApiTicket
	 * @Description:返回当前微信JS-SDK的JsApiTicket
	 * @return String
	 */
	public String getJsApiTicket() {
	      return jsApiTicket;
	  }   
	  public void setJsApiTicketCreateTime(long jsApiTicketCreateTime) {
	      this.jsApiTicketCreateTime = jsApiTicketCreateTime;
	  }
	  public long getJsApiTicketCreateTime() {
	      return jsApiTicketCreateTime;
	  }
	}
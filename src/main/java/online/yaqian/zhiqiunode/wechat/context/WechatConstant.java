package online.yaqian.zhiqiunode.wechat.context;

public class WechatConstant {
	
	
	/**
	 * 申请到的微信APPID
	 */
	public static final String APPID = "your_APPID";
	
	/**
	 * 微信端定义的TOKEN
	 */
	public static final String TOKEN = "your_TOKEN";
	
	/**
	 * 微信端定义的APPSECRET
	 */
	public static final String APPSECRET = "your_APPSECRET";
	
	/**
	 * 微信端定义的消息加解密EncodingAESKey
	 */
	public static final String EncodingAESKey = "your_EncodingAESKey";
	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	/**
	 * 回复消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	/**
	 * 回复消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";  
	/**
	 * 请求消息类型：事件
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(关注)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消关注)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";
	
	/**
	 * 获取access_token的接口地址（GET） 限200（次/天）
	 */
	public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	/**
	 * 获取access_token的接口地址（GET） 限200（次/天）
	 */
	public static final String GET_JSAPI_TOCKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
}

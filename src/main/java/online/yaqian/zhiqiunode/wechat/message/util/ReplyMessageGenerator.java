package online.yaqian.zhiqiunode.wechat.message.util;

/**
 * @author Young
 * @version 创建时间：2016年3月29日 下午4:31:10 
 * 类说明：微信回复消息生成工具
 */
public class ReplyMessageGenerator {

	String replyMsg = "";

	public String getReplyMsg() {
		return replyMsg;
	}

	public void setReplyMsg(String replyMsg) {
		this.replyMsg = replyMsg;
	}

	public ReplyMessageGenerator() {
	}

	/**
	 * 构造回复图片消息
	 * 
	 * @param toUser
	 *            接收方帐号（收到的OpenID）
	 * @param fromUser
	 *            开发者微信号
	 * @param createTime
	 *            消息创建时间 （整型）
	 * @param media_id
	 *            回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
	 * @return
	 */
	public void generateImageMsg(String toUser, String fromUser, String createTime, String media_id) {
		StringBuffer imageMsg = new StringBuffer();
		replyMsg = imageMsg.append("<xml>\n<ToUserName><![CDATA[").append(toUser)
				.append("]]></ToUserName>\n<FromUserName><![CDATA[").append(fromUser)
				.append("]]></FromUserName>\n<CreateTime>").append(createTime)
				.append("</CreateTime>\n<MsgType><![CDATA[image]]></MsgType>\n<Image>\n<MediaId><![CDATA[")
				.append(media_id).append("]]></MediaId>\n</Image>\n</xml>").toString();
	}

	/**
	 * 构造回复文本消息
	 * 
	 * @param toUser
	 *            接收方帐号（收到的OpenID）
	 * @param fromUser
	 *            开发者微信号
	 * @param createTime
	 *            消息创建时间 （整型）
	 * @param content
	 *            回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
	 * @return
	 */
	public void generateTextMsg(String toUser, String fromUser, String createTime, String content) {
		StringBuffer textMsg = new StringBuffer();
		replyMsg = textMsg.append("<xml>\n<ToUserName><![CDATA[").append(toUser)
				.append("]]></ToUserName>\n<FromUserName><![CDATA[").append(fromUser)
				.append("]]></FromUserName>\n<CreateTime>").append(createTime)
				.append("</CreateTime>\n<MsgType><![CDATA[text]]></MsgType>\n<Content><![CDATA[").append(content)
				.append("]]></Content>\n</xml>").toString();
	}

	/**
	 * 构造回复图文消息
	 * 
	 * @param toUser
	 *            接收方帐号（收到的OpenID）
	 * @param fromUser
	 *            开发者微信号
	 * @param createTime
	 *            消息创建时间 （整型）
	 * @param articleCount
	 *            图文消息个数，限制为10条以内
	 * @param item
	 *            generateArticleItem合成的图文消息
	 * @return
	 */
	public void generateNewsMsg(String toUser, String fromUser, String createTime, String articleCount) {
		StringBuffer newsMsg = new StringBuffer();
		replyMsg = newsMsg.append("<xml>\n<ToUserName><![CDATA[").append(toUser)
				.append("]]></ToUserName>\n<FromUserName><![CDATA[").append(fromUser)
				.append("]]></FromUserName>\n<CreateTime>").append(createTime)
				.append("</CreateTime>\n<MsgType><![CDATA[news]]></MsgType>\n<ArticleCount>").append(articleCount)
				.append("</ArticleCount>\n<Articles>\n").append(replyMsg).append("</Articles>\n</xml>").toString();
	}

	/**
	 * @param title
	 *            图文消息标题
	 * @param description
	 *            图文消息描述
	 * @param picurl
	 *            图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
	 * @param url
	 *            点击图文消息跳转链接
	 * @return
	 */
	public void addArticleItem(String title, String description, String picurl, String url) {
		StringBuffer articleItem = new StringBuffer(replyMsg);
		replyMsg = articleItem.append("<item>\n<Title><![CDATA[").append(title)
				.append("]]></Title>\n<Description><![CDATA[").append(description)
				.append("]]></Description>\n<PicUrl><![CDATA[").append(picurl).append("]]></PicUrl>\n<Url><![CDATA[")
				.append(url).append("]]></Url>\n</item>\n").toString();
	}
}

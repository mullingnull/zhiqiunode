package online.yaqian.zhiqiunode.wechat.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import online.yaqian.zhiqiunode.wechat.service.IWechatUserService;
import online.yaqian.zhiqiunode.model.BookInfo;
import online.yaqian.zhiqiunode.model.WechatUser;
import online.yaqian.zhiqiunode.service.IBookInfoService;
import online.yaqian.zhiqiunode.service.IIdentifyCodeService;
import online.yaqian.zhiqiunode.service.IMineService;
import online.yaqian.zhiqiunode.util.JWTUtil;
import online.yaqian.zhiqiunode.util.ValidateISBNUtil;
import online.yaqian.zhiqiunode.wechat.context.Ticket;
import online.yaqian.zhiqiunode.wechat.context.WechatConstant;
import online.yaqian.zhiqiunode.wechat.message.util.AESException;
import online.yaqian.zhiqiunode.wechat.message.util.Signature;
import online.yaqian.zhiqiunode.wechat.message.util.WXBizMsgCrypt;
import online.yaqian.zhiqiunode.wechat.message.util.ReplyMessageGenerator;

/**
 * @author YoungZhu
 * 类说明：微信消息收发交互
 */
@RestController
@RequestMapping("/api/wechat/reception")
@Scope(value = "prototype")
public class MessageReception {
	
	public static Logger log = Logger.getLogger(MessageReception.class);
	
	@Autowired(required=true)
	@Qualifier("wechatUserService")
	private IWechatUserService wechatUserService;
	String reply = "";
	
	@Autowired
	@Qualifier("identifyCodeService")
	private IIdentifyCodeService idCodeService;
	
	@Autowired(required=true)
	@Qualifier("bookInfoService")
	private IBookInfoService bookInfoService;
	
	@Autowired
	@Qualifier("mineService")
	private IMineService mineService;

	/**
	 * @Title:checkSignature
	 * @Description:微信后台服务器签名校验
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @returnString
	 */
	@RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String checkSignature(@RequestParam(value = "signature") String signature,
			@RequestParam(value = "timestamp") String timestamp, @RequestParam(value = "nonce") String nonce,
			@RequestParam(value = "echostr") String echostr) {
		if (Signature.checkSHA1Signature(signature, timestamp, nonce)) {
			log.info("收到微信校验服务器信息，成功完成校验");
			reply = echostr;
		}
		return reply;

	}

	/**
	 * @Title:messageReply
	 * @Description:微信消息的处理
	 * @param encrypt_type
	 * @param msg_signature
	 * @param timestamp
	 * @param nonce
	 * @param requestmsg
	 * @return
	 * @throws UnsupportedEncodingExceptionString
	 */
	@RequestMapping(method = RequestMethod.POST, produces = "application/xml;charset=UTF-8")
	public String messageReply(@RequestParam(value = "encrypt_type") String encrypt_type,
			@RequestParam String msg_signature, @RequestParam String timestamp, @RequestParam String nonce,
			@RequestBody String requestmsg) throws UnsupportedEncodingException {

		/* 消息的接收、处理、响应,安全模式下只响应加密后的消息 */
		if ("aes".equalsIgnoreCase(encrypt_type)) {
			try {
				WXBizMsgCrypt wbmc = new WXBizMsgCrypt();
				ReplyMessageGenerator replyMsg = new ReplyMessageGenerator();

				// 将解析结果存储在HashMap中
				Map<String, String> requestMsgMap = new HashMap<String, String>();

				Document document = DocumentHelper
						.parseText(wbmc.decryptMsg(msg_signature, timestamp, nonce, requestmsg));
				// 得到xml根元素
				Element root = document.getRootElement();
				// 得到根元素的所有子节点

				@SuppressWarnings("unchecked")
				List<Element> elementList = root.elements();

				// 遍历所有子节点
				for (Element e : elementList) {
					requestMsgMap.put(e.getName(), e.getText());
				}
				String fromUserName = requestMsgMap.get("FromUserName");
				String toUserName = requestMsgMap.get("ToUserName");
				String msgType = requestMsgMap.get("MsgType");
				WechatUser wUser = wechatUserService.getWechatUser(fromUserName);

				switch (msgType) {
				case WechatConstant.REQ_MESSAGE_TYPE_TEXT:
					// 接收用户发送的文本消息内容
					String content = requestMsgMap.get("Content");

					log.info("收到文本消息:" + content + ";来自" + fromUserName);

					switch (content.toLowerCase()) {
					case "debug": {
						StringBuffer contentMsg = new StringBuffer("你好，这里是debug信息，请知悉：\n");
						contentMsg.append("您的openid为：" + fromUserName).append("\n");
						contentMsg.append("我们的微信Message Id为：" + toUserName).append("\n");
						contentMsg.append("当前Access-Token为：" + Ticket.getInstance().getAccessToken()).append("\n");
						contentMsg.append("当前JSAPI-Ticket为：" + Ticket.getInstance().getJsApiTicket()).append("\n");
						contentMsg.append("<a href=\"").append(getURL(wUser))
		            	.append("\">如需注册或登陆请点我</a>\n");
						replyMsg.generateTextMsg(fromUserName, toUserName, Long.toString(System.currentTimeMillis()),
								contentMsg.toString());
					}
						break;
					case "hello" :
					case "登陆": {

						replyMsg.addArticleItem("点我登陆", "秘制入口",
								"http://tankr.net/s/medium/GY1U.jpg",
								getURL(wUser));
						replyMsg.addArticleItem("使用介绍 ", "FAQ", "http://tankr.net/s/medium/YNFW.jpg",
								"http://xiaoyaqian.shaguanxi.com/");
						replyMsg.generateNewsMsg(fromUserName, toUserName, Long.toString(System.currentTimeMillis()),
								"2");
					}
						break;
					case "give me five" :
					case "wu" :
					//case "hello" :
					case "five":
					case "五":
					case "5": {
						StringBuffer contentMsg = new StringBuffer();
						if (wUser.getUserId()==0) {
							contentMsg.append("你还没扫码分享过教材呢，还是先上传几本书后再试吧");
						} else {
							contentMsg.append("你好，您的对接验证码为:").append(idCodeService.getIdCode(wUser.getUserId())).append("两个小时以内有效,为什么是俩小时呢？我也不知道");
						}
						replyMsg.generateTextMsg(fromUserName, toUserName, Long.toString(System.currentTimeMillis()),
		            			contentMsg.toString());
						
					}	
						break;
					case "transfer" :
					case "pair" :
					case "传送":
					case "传送门":
					case "传输":
					case "交接":
					case "交易": {
						StringBuffer contentMsg = new StringBuffer();
						if (wUser.getUserId()==0) {
							contentMsg.append("你还没注册呢，还是先配置一下个性化信息再试吧");
						} else {
							contentMsg.append("你好，指令收到，").append("<a href=\"").append(getTransferURL(wUser))
					            	.append("\">点击开启传送门</a>");
						}
						replyMsg.generateTextMsg(fromUserName, toUserName, Long.toString(System.currentTimeMillis()),
		            			contentMsg.toString());
						
					}	
						break;
					default: {
						if (content.length()==13 && ValidateISBNUtil.validateISBN(content)) {
							BookInfo b = bookInfoService.getBookInfo(content);
							StringBuffer contentMsg = new StringBuffer("你好，您搜索的图书\n");
							contentMsg.append("书名:").append(b.getTitle()).append("\n作者:").append(b.getAuthor()).append("\n出版方:").append(b.getPublisher()).append("\n我们于数据库中为您检索到 ");
							contentMsg.append(mineService.countMinesByISBN(content)).append("本").append("<a href=\"").append(getURL(wUser)).append("&target=seek").append(content).append("\">点击这里查看详情</a>");
							replyMsg.generateTextMsg(fromUserName, toUserName, Long.toString(System.currentTimeMillis()),
			            			contentMsg.toString());
			            }else {
							
			            	StringBuffer contentMsg = new StringBuffer();
			            	//contentMsg.append(msgType).append("消息已收到，不过系统目前无法理解您的消息，如果有任何问题或意见均可给我们留言，我们会尽快给您回复：\n").append("<a href=\"").append(getURL(wechatUserService.getWechatUser(fromUserName))).append("\">如需登陆或注册请点我</a>\n");
							contentMsg.append(msgType).append("系统已经收到您的消息，不过我完全不能理解您的意思，有任何问题或意见均可留言，我的主人会尽快给您回复：\n").append("<a href=\"").append(getURL(wechatUserService.getWechatUser(fromUserName))).append("\">如需登陆或注册请点我</a>\n");
			            	replyMsg.generateTextMsg(fromUserName, toUserName, Long.toString(System.currentTimeMillis()),contentMsg.toString());
							break;
						}
					}
						break;
					}
					break;
				case WechatConstant.REQ_MESSAGE_TYPE_EVENT:
					// 事件类型
					String eventType = requestMsgMap.get("Event");

					switch (eventType) {
					case WechatConstant.EVENT_TYPE_SUBSCRIBE: {
						wechatUserService.actWechatUser(fromUserName,true);
						log.info("用户:" + fromUserName + "关注");
						replyMsg.addArticleItem("欢迎关注小牙签@浙理，点击这里开始使用吧", "您好，欢迎关注小牙签@浙理",
								"http://tankr.net/s/medium/VJVI.jpg",
								getURL(wechatUserService.getWechatUser(fromUserName)));
						replyMsg.addArticleItem("使用介绍 ", "FAQ", "http://tankr.net/s/medium/Q4ZB.jpg",
								"http://xiaoyaqian.shaguanxi.com/");
						replyMsg.generateNewsMsg(fromUserName, toUserName, Long.toString(System.currentTimeMillis()),
								"2");
					}
						break;
					case WechatConstant.EVENT_TYPE_UNSUBSCRIBE: {
						// 取消关注,用户接受不到我们发送的消息了，可以在这里记录用户取消关注的日志信息
						wechatUserService.actWechatUser(fromUserName,false);
						log.info("用户:" + fromUserName + "取消关注");
					}
						break;
					case WechatConstant.EVENT_TYPE_CLICK: {
						{
							StringBuffer contentMsg = new StringBuffer();
							// 事件KEY值，与创建自定义菜单时指定的KEY值对应
							String eventKey = requestMsgMap.get("EventKey");

							// 自定义菜单点击事件
							if ("11".equals(eventKey)) {
								contentMsg.append("11菜单项被点击！");
							} else if ("12".equals(eventKey)) {
								contentMsg.append("12 菜单项被点击！");
							}
							replyMsg.generateTextMsg(fromUserName, toUserName,
									Long.toString(System.currentTimeMillis()), contentMsg.toString());
						}
					}
						break;
					default:
						break;
					}
					break;
				default://其他类型的消息
					log.info("收到" + msgType + "消息,来自" + fromUserName);
					StringBuffer contentMsg = new StringBuffer("您好，您的");
					//contentMsg.append(msgType).append("消息已收到，不过系统目前无法理解您的消息，如果有任何问题或意见均可给我们留言，我们会尽快给您回复：\n").append("<a href=\"").append(getURL(wechatUserService.getWechatUser(fromUserName))).append("\">如需登陆或注册请点我</a>\n");
					contentMsg.append(msgType).append("系统已经收到您的消息，不过我完全不能理解您的意思，有任何问题或意见均可留言，我的主人会尽快给您回复：\n").append("<a href=\"").append(getURL(wechatUserService.getWechatUser(fromUserName))).append("\">如需登陆或注册请点我</a>\n");
	            	replyMsg.generateTextMsg(fromUserName, toUserName, Long.toString(System.currentTimeMillis()),contentMsg.toString());
					break;
				}
				log.info(replyMsg.getReplyMsg());
				reply = wbmc.encryptMsg(replyMsg.getReplyMsg(), timestamp, nonce);
			} catch (AESException e) {
				log.error("加、解密消息时出错：" + e.getMessage());

			} catch (DocumentException e) {
				log.error("解析xml时出错：" + e.getLocalizedMessage());

			} catch (JOSEException e) {
				log.error("生成登陆参数中的JWT部分时出错：" + e.getLocalizedMessage());
			} catch (Exception e) {
				log.error("其他错误：" + e.getLocalizedMessage());
			}
		} else {//如果消息未使用AES加密，则不处理，若需处理可在此处实现

		}
		return reply;
	}
	
	
	
	String getURL(WechatUser wechatUser) throws JOSEException {
		return "http://xiaoyaqian.shaguanxi.com/jump?bearer=" + JWTUtil.getJWTToken(wechatUser,null);
	}
	
	String getTransferURL(WechatUser wechatUser) throws JOSEException {
		return "http://xiaoyaqian.shaguanxi.com/jump?target=transfer&bearer=" + JWTUtil.getJWTToken(wechatUser,7080000L);
	}
	
}

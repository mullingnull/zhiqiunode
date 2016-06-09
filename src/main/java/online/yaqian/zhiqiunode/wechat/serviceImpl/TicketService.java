package online.yaqian.zhiqiunode.wechat.serviceImpl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONException;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import online.yaqian.zhiqiunode.util.MyX509TrustManager;
import online.yaqian.zhiqiunode.wechat.context.WechatConstant;
import online.yaqian.zhiqiunode.wechat.context.Ticket;
import online.yaqian.zhiqiunode.wechat.service.ITicketService;

/**
 * @author YoungZhu
 * @version 创建时间：2015年12月20日 下午3:27:02
 * 类说明：微信接口调用所需凭证定时获取
 */
@Service("ticketService")
@Lazy(false)
public class TicketService implements ITicketService{
	
	
	public static Logger log = Logger.getLogger(TicketService.class);
	
	
	
	
	@Scheduled(initialDelay = 30000L,fixedDelay=1800000L) //每三十分钟唤醒一次，查看剩余时间，剩余少于一小时即进行更换
	public static void getAccessToken() {
		if (Ticket.getInstance().isAceessTokenNearlyExpired()) {
			String requestUrl = WechatConstant.GET_ACCESS_TOKEN_URL.replace("APPID", WechatConstant.APPID).replace(
					"APPSECRET", WechatConstant.APPSECRET);
			JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
			// 如果请求成功
			if (null != jsonObject) {
				try {
					 Ticket.getInstance().saveLocalAccessonToke(jsonObject.getString("access_token"));
					 log.info("获取AccessToken成功:" + Ticket.getInstance().getAccessToken());
				} catch (JSONException e) {
					// 获取ticket失败
					log.fatal("获取AccessToken失败 errcode:" + jsonObject.getIntValue("errcode")
							+ "，errmsg:" + jsonObject.getString("errmsg"));
				}
			}
   		}
	}
	
	@Scheduled(initialDelay=40000L,fixedDelay=1800000L) //每三十分钟唤醒一次，查看剩余时间，剩余少于一小时即进行更换
	public static void getJsApiTicket() {
		if (Ticket.getInstance().isJsApiTicketNearlyExpired() && (Ticket.getInstance().getAccessToken() != null)) {
			String requestUrl = WechatConstant.GET_JSAPI_TOCKET_URL.replace("ACCESS_TOKEN", Ticket.getInstance().getAccessToken());
			JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
			// 如果请求成功
			if (null != jsonObject) {
				try {
					 Ticket.getInstance().saveLocalJsApiTicket((jsonObject.getString("ticket")));
					 log.info("获取JsApiTicket成功:" + Ticket.getInstance().getJsApiTicket());
				} catch (JSONException e) {
					// 获取ticket失败
					log.fatal("获取JsApiTicket失败 errcode:" + jsonObject.getIntValue("errcode")
							+ "errmsg:" + jsonObject.getString("errmsg"));
				}
				
			}
		} 		
		//return accessToken;
	}
	
	
	
	
	public static JSONObject httpsRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:", e);
		}
		return jsonObject;
	}

	
}

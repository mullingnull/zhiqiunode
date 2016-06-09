package online.yaqian.zhiqiunode.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import online.yaqian.zhiqiunode.model.BookInfo;
import online.yaqian.zhiqiunode.util.MyX509TrustManager;

/**
 * @author YoungZhu
 * @version 创建时间：2016年1月8日 下午7:17:38
 * 类说明：从豆瓣开放API拉取书籍信息
 */
public class GetBookInfoFromDouBan {
	
	public static Logger log = Logger.getLogger(GetBookInfoFromDouBan.class);

	
	public static final String GET_BOOKINFO_FROM_DOUBAN_URL = "https://api.douban.com/v2/book/isbn/ISBN13?fields=isbn13,title,author,publisher,pubdate,price,pages,summary";
	
	public static BookInfo getBookInfo(String isbn13) {
		StringBuffer buffer = new StringBuffer();
		BookInfo bookInfo = new BookInfo(isbn13);
		JSONObject dbBook = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(GET_BOOKINFO_FROM_DOUBAN_URL.replace("ISBN13", isbn13));
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setRequestMethod("GET");

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
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

			dbBook = JSONObject.parseObject(buffer.toString());

			//转化为BookInfo对象
			String title = dbBook.getString("title");
			bookInfo.setTitle(title.isEmpty()?null:(title.length()>25)?title.substring(0,24):title);
			
			String author = dbBook.getJSONArray("author").toString().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");
			bookInfo.setAuthor(author.isEmpty()?null:(author.length()>25)?author.substring(0,24):author);
			
			int pages = dbBook.getInteger("pages");
			bookInfo.setPage(pages>9999?9999:pages);
			
			String publisher = dbBook.getString("publisher");
			bookInfo.setPublisher(publisher.isEmpty()?null:publisher.length()>20?publisher.substring(0, 19):publisher);
			
			bookInfo.setPrice(new BigDecimal(dbBook.getString("price").replace("元", "")));
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			try {
				bookInfo.setPubDate(sdf.parse(dbBook.getString("pubdate")));
			} catch (ParseException | NullPointerException e) {
				//若出版时间为null或空，交由数据库填充默认值
				bookInfo.setPubDate(null);
			}

			String summary = dbBook.getString("summary").replaceAll("\\n", "");
			bookInfo.setSummary(summary.isEmpty()?null:(summary.length()>224)?summary.substring(0,200)+"...":summary);
		
		} catch (ConnectException ce) {
			log.error("从豆瓣拉取图书信息出现错误，错误原因：server connection timed out." + ",isbn13:" + isbn13);
		}catch (FileNotFoundException e) {
			log.error("从豆瓣拉取图书信息出现错误,未找到图书信息，ISBN为：" + isbn13);
			//log.error("从豆瓣拉取图书信息出现错误，错误码"+"jsonObject.getString(\"code\")"+"错误原因：" + "jsonObject.getString(\"msg\")" + ",isbn13:" + isbn13);
		} catch (Exception e) {
			log.error("从豆瓣拉取图书信息出现错误，" + "出错ISBN:" + isbn13 + ",错误原因:", e);
		}
	return bookInfo;	
	}

}



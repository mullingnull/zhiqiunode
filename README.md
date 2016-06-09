# 小牙签@浙理（zhiqiunode）

小牙签，取“牙签万轴，予取予求”之意，应该不算掉书袋吧

“牙签万轴裹红绡，玉粲书同付火烧”，李后主一千多年一句诗，而今毕业3毛一斤卖掉四年积攒的教材时，读来还是有所感触的。能做的有限，但是我尽力而为。

项目的目标在于为在校大学生提供一个教材再利用信息流通平台，解决因信息不对称带来的（巨大？待考证）教材浪费问题。

这里给出的是自己写的浙理节点（zhiqiunode）的实现，系统以微信订阅号+WebAPP形式运行，欢迎fork到自己学校，如果感兴趣欢迎使用其他语言实现。如果你也和我一样觉得本项目工程Bug遍地走，欢迎和我一起完善代码，如对本项目工程有更多想法，欢迎您的重构或重新开发。

### **一言以蔽之：一个教材信息流通平台，换换书，交交朋友，但尽人事，莫许前程**

### 系统核心逻辑简介：

1. 捐赠方使用微信扫一扫扫描有意捐赠教材封底的ISBN码，选填留言等信息，上传保存至数据库Mine表中
2. 其他人以ISBN码为关键字检索Mine表，如未检索到心宜结果可将当前书籍信息保存至心愿单Wish表中，下次直接点击发起检索
3. 检索到心宜记录后可通过捐赠方预留的微信号通过微信与之交流互动、商讨捐赠事宜
4. 捐赠意向达成双方交接时，由接收方扫描所接收教材ISBN码，删除后端系统中相应的捐赠方待捐赠教材记录

## 系统功能演示与公众号
markdown中不便演示，您可以[点此查看](https://github.com/mullingnull/zhiqiunode/系统功能演示.png)PNG图片格式系统功能演示文档

![小牙签-微信公众号](http://o7650r1ld.bkt.clouddn.com/images/qrcode_yaqian_large.jpg)

您可以使用微信扫一扫扫描以上二维码关注微信公众号【小牙签@浙理】亲身体验

## 系统技术实现

### 系统细节
* [Spring Framwork](https://github.com/spring-projects/spring-framework) 4.2，含Spring MVC，[Spring Security](https://github.com/spring-projects/spring-security)
* [Nimbus JOSE+JWT](https://bitbucket.org/connect2id/nimbus-jose-jwt/) 4.12
* [Hibernate ORM](https://github.com/hibernate/hibernate-orm) 5.1
* [Druid 连接池](https://github.com/alibaba/druid)，[fastjson](https://github.com/alibaba/fastjson)
* [MariaDB](https://mariadb.org) 10.1
* [WeUI](https://github.com/weui/weui)
* [QRCode.js](https://github.com/davidshimjs/qrcodejs)

### 后端系统主要功能与相应实现
* 响应用户通过微信发送的消息、事件推送(MessageReception.java)
* 定时获取、更新微信AccessToken、JsApiTicket(TicketService.java)
* 用户HTTP请求拦截、身份验证(JWTAuthenticationFilter.java，JWTAuthenticationProvider.java，JWTAuthenticationToken.java等)
* 用户登陆，未注册用户返回注册页面、已注册用户返回主页面(ViewDispatcher.java)
* 用户个人信息更新、心愿单维护(RestfulUserAPIs.java， RestfulWishAPIs.java)
* 检索待捐赠书籍记录(RestfulResourceAPIs.java)
* 用户添加、变更待捐赠书籍信息(RestfulMineAPIs.java)
* 达成捐赠、删除相关记录、生成成交信息请求(RestfulOrderAPIs.java)
* 从豆瓣数据库中拉取书籍名等信息(GetBookInfoFromDouBan.java)
* 系统状态轮询（GlobalStatusService.java）
* 用户请求异常处理（ViewException.java）

### 前台尚在开发中，暂不列出

### 部署时注意事项
#### 开发环境
* jdk 1.8 （必须）
* Eclipse Java EE IDE for Web Developers，Mars.2 Release（参考）

#### 运行环境
* jsk 1.8 （必须）或jdk 1.8
* tomcat 8.0.18 （参考）

#### 需配置的参数
src/main/java/online/yaqian/zhiqiunode/context/JWTAuthenticationConstant.java中secretString（JWT签名密钥）

src/main/java/online/yaqian/zhiqiunode/wechat/context/WechatConstant.java中APPID（您申请到的微信APPID），TOKEN（微信端定义的TOKEN），APPSECRET（微信端定义的APPSECRET），EncodingAESKey（微信端定义的消息加解密EncodingAESKey）

src/main/resources/jdbc.properties中jdbc连接属性，注意数据库连接密码需加密

src/main/resources/log4j.properties中配置日志输出目录、等级等

另需注意微信AES加密通信密钥长度问题，详情请参阅[JCE无限制权限策略](http://mp.weixin.qq.com/wiki/17/3d8857290ae300a3c54439df2d2fd629.html)之“异常java.security.InvalidKeyException:illegal Key Size的解决方案”

## TODO
* 后端SQL语句优化、ehcache
* 文档完善、方法命名规范化
* 身份验证码重复值规避
* API访问频次控制
* mainpage页 
 * 用户卡片效果优化
 * 用户个性化信息更新
 * 用户账号注销、删除
 * 微信分享到朋友圈等文案编写
 * 图书信息（BookInfo）元数据公共编辑
 * Bug反馈、问答系统
* overview 页
 * 页面布局设计
 * 对已登陆用户展示双方供求信息、匹配度
* signup页
 * 页面布局优化，交互优化
 * 注册引导、内容完善
* exception页
 * 返回细粒度错误信息

## TOTOTOTODO（有生之年）
* 其他闲置物品信息流通
* 美工支援
* 好多。。。一言难尽

## 安全性
撇开实际需求谈安全性就是耍流氓。

项目本身未涉及财务往来，考虑到移动端使用体验将来应该也不会考虑在线支付，且项目设计之初就竭力避免获取用户的敏感信息，因此相对于安全性更值得关注的是项目工程的的健壮性

当前项目与微信服务器间的通信已启用AES加密，可保障与用户间的消息交互（包括用于身份验证的JWT）不被窃取。

不过囿于现有资源等，目前项目WEB服务器未能使用HTTPS协议，在用户访问过程中有被截取JWT的风险，HTTP明文传输亦存在通信内容泄露的风险

将来条件允许会尽快补足，如实际应用时条件不具备，将缩短JWT生命期（由当前的1个月缩减到2小时）。牺牲便捷性换取安全性。我尽力 XD

## 许可证
[BSD 3-clause "New" or "Revised" License](https://opensource.org/licenses/BSD-3-Clause "The BSD 3-Clause License")，您可使用本项目源码于各种用途，相信您能在条件允许的范围内通过反馈Bug等协助项目的完善

## 其他
* 感谢开源项目的开发者、贡献者，致敬开源精神
* 感谢Google、StackOverflow、GitHub以及诸多技术博客作者
* 感谢[小白脸](https://github.com/NotA1994)的PS支持，感谢寝室长的良胸XD
* 感谢大家的奉献

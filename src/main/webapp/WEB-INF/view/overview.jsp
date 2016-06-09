<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>	
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String scdPath = "http://o7650r1ld.bkt.clouddn.com/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">
	<title>小牙签@浙理</title>
</head>
<body>
用户信息：
用户名：${user.nickName}
性别：${user.gender}
主修：${user.major}
自我介绍：${user.desc}
学院：${user.college}
入学年份：${user.enrolYear}
系统信息：
<div class="status">
				<div>当前系统运行状态：截至${status.date}<br></div>
				<div>微信关注数：${status.wechatUserCount}<br></div>
				<div>用户数：${status.userCount}<br></div>
				<div>图书种类：${status.bookInfoCount}种<br></div>
				<div>用户藏书：${status.mineCount}种<br></div>
				<div>用户藏书合计：${status.mineQtyCount}册<br></div>
				<div>用户心愿单：${status.wishCount}册<br></div>
				<div>成交：${status.orderCount}册<br></div>
			</div>
copy and paste
<p ><img alt="扫一扫微信关注小牙签@浙理" src="<%=scdPath%>images/qrcode_yaqian_samll.jpg" width="344" height="344"></p>
<p >长按关注小牙签@浙理</p>
<script type="text/javascript" >

</script>
</body>
</html>
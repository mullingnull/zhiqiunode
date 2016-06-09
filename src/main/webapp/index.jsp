<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>	
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String scdPath = "http://o7650r1ld.bkt.clouddn.com/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<style>
h1
{
text-shadow: 5px 5px 5px #FF0000;
text-align:center;
}
p { text-align:center;}
</style>
<title>欢迎您的访问</title>
</head>
<body>

<h1 >小牙签@浙理，敬请期待</h1>
<p ><img alt="扫一扫微信关注小牙签@浙理" src="<%=scdPath%>images/qrcode_yaqian_large.jpg" width="430" height="430"></p>
<p >扫一扫微信关注小牙签@浙理</p>
</body>
</html>
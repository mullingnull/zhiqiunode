<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
String scdPath = "http://o7650r1ld.bkt.clouddn.com/";
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=0">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/styles.css">
<link rel="stylesheet" href="<%=basePath%>css/weui.css" />
<script type="text/javascript"	src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="<%=scdPath%>js/qrcode.min.js"></script>
<style>
/*CSS*/
.code-bg {
	position: relative;
	height: 200px;
	width: 200px;
	margin: 0px auto; /*此处为了居中*/
	text-align: center;
	background: url(http://www.iaxure.com/share/ewsm/images/index/u3.png)
		center top no-repeat;
}

.code-bg img {
	position: absolute;
	top: 10px;
	left: 20px;
	z-index: 1;
	height: 160px;
	width: 160px;
}

.line {
	position: absolute;
	top: 5px;
	left: -40px;
	z-index: 2;
	height: 3px;
	width: 360px;
	background:
		url(http://www.iaxure.com/share/ewsm/images/index/line_u9.png)
		no-repeat;
	/*动画效果*/
	-webkit-animation: myScan 1s infinite alternate;
			animation: myScan 1s infinite alternate;
}
@-webkit-keyframes  myScan {

	from { top:5px;
	}
	to {
	top: 170px;
	}
}

@keyframes  myScan {
	from { top:5px;
	}
	to {
	top: 170px;
	}
}
</style>
<title>对接中~~~小牙签@浙理</title>
</head>
<body>

	<div class="page" style="background: #fbf9fe;">


		<div class="msg">
			<div class="weui_msg">
				<div class="code-bg">
					<div class="line"></div>
					<img src="<%=basePath%>images/qrcode_yaqian_samll.jpg" alt="">
				</div>

					<div class="weui_text_area" style="margin-top: 11vh;">
						<h2 class="weui_msg_title">Vendor to Vendee</h2>
						<p class="weui_msg_desc"
							style="margin-top: 4vh; text-align: left; margin-left: 13vw; line-height: 2em; font-weight: 800;">
							$:对接准备完毕.<br>$:准备开启传送门[=========] 100%<br>$:等待扫描对方微信二维码中..
						</p>
					</div>
					<div class="weui_opr_area">
						<p class="weui_btn_area">
							<a href="javascript:;" id="scanWechatQRCode" style="width: 80%;"
								class="weui_btn weui_btn_plain_primary">扫码</a>
						</p>
					</div>
					<div class="weui_extra_area">
						<a href="">点我使用验证码方式</a>
					</div>
				</div>
			</div>
			<div class="button_sp_area"
				style="width: 60%; top: 56vh; left: 20%; position: absolute;">


			</div>
		</div>

		<!--BEGIN dialog-->
		<div class="weui_dialog_confirm" id="dialog" style="display: none;">
			<div class="weui_mask"></div>
			<div class="weui_dialog">
				<div class="weui_dialog_hd" id="dialoghd">
					<strong class="weui_dialog_title">通知</strong>
				</div>
				<div class="weui_dialog_bd" id="dialogbd">通知内容</div>
				<div class="weui_dialog_ft">
					<a href="javascript:pureView('dialog');" id="dialogcancel"
						class="weui_btn_dialog default">取消</a> <a
						class="weui_btn_dialog primary" id="dialogsubmit">确定</a>
				</div>
			</div>
		</div>

		<div class="weui_dialog_alert" id="dialogonly" style="display: none;">
			<div class="weui_mask"></div>
			<div class="weui_dialog">
				<div class="weui_dialog_hd" id="dialogonlyhd">
					<strong class="weui_dialog_title">Sorry</strong>
				</div>
				<div class="weui_dialog_bd" id="dialogonlybd">出错了(´◔ ‸◔')</div>
				<div class="weui_dialog_ft">
					<a href="javascript:pureView('dialogonly');"
						class="weui_btn_dialog primary">好吧(◔ ౪◔)</a>
				</div>
			</div>
		</div>

		<!--END dialog-->

<template type="text/html" id="tpl_transfer">
<div class="header">
	<a href="javascript:scanISBN();" id="scanISBN"
			class="weui_btn weui_btn_plain_primary" style="color: #FBF9FE;border: 1px solid #FBF9FE;top: 50%;width: 80%;">扫描ISBN进行传送</a>

	<div class="tips-container" style="text-align: center;padding-top: 52px;">对接成功！开始传送吧！</div>
</div>
<div id="container" class="container">
	<div id="mine_panel" class="weui_panel weui_panel_access">
		<div id="mine_panel_hd" class="weui_panel_hd">已传送成功 0 本</div>
		<div id="mine_panel_bd" class="weui_panel_bd"></div>
		<a id="mine_panel_ft" class="weui_panel_ft"
			href="javascript:void(0);">That's All</a>
	</div>
</div>
</template>

		<script type="text/javascript">
var orders = [] ;
var orderIndex =[];
window.onload = function(){

	
}


wx.ready(function () {
	wx.hideOptionMenu();
	document.querySelector('#scanWechatQRCode').onclick = function () {
    wx.scanQRCode({
      needResult: 1,
      scanType: "qrCode",
      desc: 'scanQRCode desc',
      success: function (res) {
    	  checkQRCodeFun(res.resultStr);
      }
    });
  };
})

//检查扫码结果之格式
function checkQRCodeFun(qrcodeStr) {
	//if (qrcodeStr.startsWith('http://weixin.qq.com/r/') && qrcodeStr.length == 43) {
	if (qrcodeStr.length == 43) {
		beginTransfer(qrcodeStr.substring(23));
	}else {
		document.getElementById("dialogonly").style.display = "block";
		document.getElementById("dialogonlyhd").innerHTML="二维码格式不正确";
		document.getElementById("dialogonlybd").innerHTML="很抱歉您提供的二维码不正确，扫码结果为：" + qrcodeStr;
	}
}

function beginTransfer(wechatqrcode) {
	var xmlhttp;
    if(window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest();
        xmlhttp.open('GET', '<%=basePath%>jump?target=pair&wcqrcode='+wechatqrcode, true);
        xmlhttp.send(null);
        xmlhttp.onreadystatechange = function(){
            if(xmlhttp.readyState == 4){
            	if(xmlhttp.status == 200){
				  //重新授予cookies
				  document.querySelector('.page').innerHTML = document.getElementById("tpl_transfer").innerHTML;
				  document.title ='对接中~~~小牙签@浙理';
            } else {
				dialogOnly(xmlhttp.status,"抱歉，您扫描二维码不正确，系统未找到该用户，请重试");
			}
          }
        }
    } 	
}

function convertEnrolYear(year) {
	switch (year) {
	case 2011:
		return '大五';
		break;
	case 2012:
		return '大四';
		break;
	case 2013:
		return '大三';
		break;
	case 2014:
		return '大二';
		break;
	case 2015:
		return '大一';
		break;

	default:
		return '未知';
		break;
	}
}


function scanISBN() {//扫描ISBN
wx.scanQRCode({
      needResult: 1,
      scanType: "barCode",
      desc: '扫描添加图书，仅支持图书哦',
      success: function (res) {
    	iSBNtoPost = res.resultStr.split(',')[1];
		if (validateISBN(iSBNtoPost)) {
			postOrder(iSBNtoPost);
		}else {
			dialogOnly(400,'ISBN错误，仅接受97开头13位图书类ISBN');
		}
      },
     cancle:function () {
    	 alert('cancled');
     }
});
}

function postOrder(iSBNtoPost) {
	var order = {isbn:iSBNtoPost,count:1};
	ajaxRequest('api/orders', 'POST', JSON.stringify(order), function(responseText) {
		var resJson = eval("(" + responseText + ")");
		  if (resJson.statuscode == 201) {
			  pureView('dialog');
			  //旋转加载和对号动画
			if (orderIndex.indexOf(iSBNtoPost)<0) {
				orders.unshift(resJson.order)
				orderIndex.unshift(iSBNtoPost);
			}else{
				//删除并更新数组
				orders.pop(orderIndex.indexOf(iSBNtoPost));
				orders.unshift(resJson.order);
			}
			  listOrders();
				scanISBN();

		  }else {
			  dialogOnly(resJson.statuscode,resJson.errmsg);
		  }
	});
}

function listOrders() {
	var ft = document.getElementById("mine_panel_ft");
	if (ft==null) {
		return;
	}
	var inner ='';
 	var ordercounts = 0;
	if (orders.length == 0) {
	ft.innerHTML ="还没有哦";
	ft.setAttribute("href",'');
	}else {//遍历所有，显示所有
	  orders.forEach(function (order, index, array) {
		  orderIndex[index] = order.isbn;
		  ordercounts += order.count;
		  inner += buildOrderBdInner(index,order);
	  });
	  ft.innerHTML ="That's All!";
  } 
  	document.getElementById('mine_panel_hd').innerHTML = '当前共传送图书合计' + orders.length + '种,共' + ordercounts + '本';
	document.getElementById('mine_panel_bd').innerHTML = inner;
}

function buildOrderBdInner(index,order) {
	//return '<a href="javascript:" class="weui_media_box weui_media_appmsg"><div class="weui_media_hd"><img class="weui_media_appmsg_thumb" src="<%=scdPath%>images/icon_book.png" alt=""></div><div class="weui_media_bd"><h4 class="weui_media_title">' + order.title + '</h4><p class="weui_media_desc">' + order.prefer + '</p></div></a>' + '<ul class="weui_media_info mine"><li class="weui_media_info_meta mine">数量：' + order.qty+ '</li><li class="weui_media_info_meta mine">' + formateDate(order.dateTime) + '</li><li class="weui_media_info_meta weui_media_info_meta_extra mine">' + order.isbn + '</li></ul>';
	return '<a href="javascript:" class="weui_media_box weui_media_appmsg"><div class="weui_media_hd"><img class="weui_media_appmsg_thumb" src="<%=scdPath%>images/icon_book.png" alt=""></div><div class="weui_media_bd"><h4 class="weui_media_title">' + order.isbn + '</h4></div></a>' + '<ul class="weui_media_info mine"><li class="weui_media_info_meta mine">数量：' + order.qty+ '</li><li class="weui_media_info_meta mine">' + formateDate(order.dateTime) + '</li></ul>';
}



function getBookInfo(isbn,callback) {
	ajaxRequest('api/books/isbn/'+isbn,'GET',null,function(responseText){
	  var resJson = eval("(" + responseText + ")");
	  if (resJson.statuscode == undefined) {
		  callback(resJson);
		}else {
		  dialogOnly(resJson.statuscode,resJson.errmsg);
		}
	  });
}

function dialog(dialoghd,dialogbd,cancelFun,confirmFun) {
	document.getElementById("dialoghd").innerHTML = dialoghd; 
	document.getElementById("dialogbd").innerHTML = dialogbd;
	if (cancelFun != null) {
	document.getElementById("dialogcancel").setAttribute("href", href);
	}
	document.getElementById("dialogsubmit").setAttribute("href", 'javascript:' + confirmFun);
	document.getElementById("dialog").style.display = "block";
	
}


function formateDate(strTime) {
	var date = new Date(strTime);
	return date.getFullYear().toString().slice(2, 4) + '/' + (date.getMonth()+1) + '/' +date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes();
}

function dialogOnly(statuscode,errmsg) {
	document.getElementById("dialogonly").style.display = "block";
	document.getElementById("dialogonlyhd").innerHTML="真抱歉，访问异常，状态码" + statuscode;
	document.getElementById("dialogonlybd").innerHTML="状态信息：" + errmsg;
}


function closeScanBar() {
	scanBarActive = false;
	document.querySelector('#scanbar').className ='scan-bar';
}

function validateISBN(isbn) {
	if (isbn.match(/^97[0-9]{11}$/g)) {
		return true;
	}else {
		return false;
	}
}

function ajaxRequest(url, method, data, callback) {
	url = '<%=basePath%>' + url;
    var xmlhttp;
    if(window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest();
        xmlhttp.open(method, url, true);
        if(method === 'POST' || method === 'PUT' || method === 'PATCH'){
            xmlhttp.setRequestHeader("Content-type","application/json");
        }
        xmlhttp.send(data);
        xmlhttp.onreadystatechange = function(){
            if(xmlhttp.readyState == 4){
            	if(xmlhttp.status == 200){
                callback(xmlhttp.responseText);
            } else {
				dialogOnly(xmlhttp.status,"抱歉，网络访问故障");
			}
          }
        }
    }
}



function convertGender(gender) {
	switch (gender) {
	case 1:
		return "♂";
	case 2:
		return "♀";
	default:
		return "◎";
	}
}

var subject_a = ["aa:材料成型及控制工程","ab:材料成型及控制工程(3+2)","ac:测控技术与仪器","ad:测控技术与仪器(3+2)","ae:电气工程及其自动化","af:电气类","ag:工业工程","ah:过程装备与控制工程","ai:过程装备与控制工程(3+2)","aj:机械电子工程","ak:机械类","al:机械设计制造及其自动化","am:机械设计制造及其自动化(全英文)","an:自动化"],
subject_b = ["ba:传播学","bb:汉语言文学","bc:汉语言文学(3+2)"],
subject_c = ["ca:包装工程","cb:材料科学与工程","cc:材料类","cd:纺织工程","ce:纺织工程(3+2)","cf:纺织工程(卓越计划)","cg:纺织类","ch:纺织品设计","ci:非织造材料与工程","cj:轻化工程","ck:轻化工程(3+2)","cl:轻化工程(纺织贸易与检测全英文)","cm:轻化工程(染整工艺与设计)","cn:轻化工程(生态纺织化学品)","co:轻化工程(造纸方向)","cp:轻化工程(卓越计划)"],
subject_f = ["fa:法学","fb:公共管理类","fc:公共事业管理","fd:公共事业管理(3+2)","fe:行政管理","ff:行政管理(3+2)","fg:社会工作","fh:社会工作(3+2)"],
subject_g = ["ga:电子商务","gb:电子商务类","gc:工商管理","gd:工商管理(3+2)","ge:工商管理(全英文)","gf:工商管理类","gg:管理科学与工程类","gh:国际经济与贸易","gi:国际经济与贸易(全英文)","gj:会计学","gk:金融学","gl:经济统计学","gm:经济学","gn:经济与贸易类","go:人力资源管理","gp:市场营销","gq:统计学","gr:信息管理与信息系统"],
subject_j = ["ja:风景园林","jb:风景园林(3+2)","jc:工程管理","jd:建筑环境与能源应用工程","je:建筑环境与能源应用工程(3+2)","jf:建	筑环境与设备工程","jg:建筑学","jh:土木工程"],
subject_l = ["la:材料化学","lb:化学类","lc:数学类","ld:数学与应用数学","le:信息与计算科学","lf:信息与计算科学(3+2)","lg:应用化学","lh:应用物理学","li:应用心理学","lj:应用心理学(3+2)"],
subject_p = ["pp:未知专业"],
subject_q = ["qa:材化生实验班","qb:机电实验班","qc:经济管理实验班","qd:理工实验班","qe:信息电子实验班","cb:材料科学与工程","ae:电气工程及其自动化","xa:电子信息工程","xb:电子信息科学与技术","ag:工业工程","gh:国际经济与贸易","gj:会计学","aj:机械电子工程","al:机械设计制造及其自动化","xd:计算机科学与技术","gk:金融学","gm:经济学","cj:轻化工程","sa:生物技术","se:生物制药","xg:通信工程","jh:土木工程","lg:应用化学"],
subject_s = ["sa:生物技术","sb:生物技术(全英文)","sc:生物技术(生物工程)","sd:生物技术(生物制药)","se:生物制药"],
subject_w = ["wa:日语","wb:英语"],
subject_x = ["xa:电子信息工程","xb:电子信息科学与技术","xc:电子信息类","xd:计算机科学与技术","xe:计算机科学与技术(全英文)","xf:数字媒体技术","xg:通信工程"],
subject_y = ["ya:产品设计","yb:动画","yc:工业设计","yd:广告学","ye:环境设计","yf:美术学","yg:设计学类","yh:视觉传达设计","yi:视觉传达设计(广告设计)","yj:艺术设计(产品设计)","yk:艺术设计(环境艺术设计)","yl:艺术设计(家具设计)","ym:艺术设计(视觉传达设计)"],
subject_z = ["za:表演(人物形象设计)","zb:表演(时装表演艺术)","zc:产品设计(纺织品艺术设计)","zd:服装设计与工程","ze:服装设计与工程(全英文)","zf:服装设计与工程(中美合作)","zg:服装设计与工程(卓越计划)","zh:服装与服饰设计","zi:服装与服饰设计(服饰品设计)","zj:服装与服饰设计(服装设计中美合作 )","zk:服装与服饰设计(服装艺术设计)","zl:服装与服饰设计(人物形象设计)","zm:服装与服饰设计(设计与营销)","zn:服装与服饰设计(设计与营销)(3+2)","zo:服装与服饰设计(设计与营销中美合作)","zp:艺术设计(服饰品设计与营销)","zq:艺术设计(服装设计中美合作)","zr:艺术设计(服装艺术设计)","zs:艺术设计(染织艺术设计)","zt:艺术设计(人物造型)","zu:艺术设计(人物造型设计)","zv:艺术设计(设计与营销中美合作)","zw:艺术设计(时装表演及营销)"];

var collegeArr = [];
function convertMajor(mj) {
	var subject_mj = eval('subject_' +mj.slice(0,1)).valueOf();
	for( var i = 0; i < subjectArr.length; i++ ){
		if(subject_mj[i].split(":")[0] == mj)
		return subject_mj[i].split(":")[1];
	}
	return '未透露专业';
}

function convertCollege(college) {
	switch (college) {
	case "a":return "机械与自动控制学院";case "b":return "文化传播学院";case "c":return "材料与纺织学院";case "f":return "法政学院";case "g":	return "经济管理学院";case "j":return "建筑工程学院";case "l":return "理学院";case "q":return "启新学院";case "s":return "生命科学学院";case "x":	return "信息学院";case "w":return "外国语学院";case "y":return "艺术与设计学院";case "z":return "服装学院";
	default:return "未透露学院";
	}
}

function hasClass(obj, cls) {    
return obj.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));    
}    
    
function addClass(obj, cls) {    
    if (!this.hasClass(obj, cls)) obj.className += " " + cls;    
}    
    
function removeClass(obj, cls) {    
    if (hasClass(obj, cls)) {    
        var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');    
        obj.className = obj.className.replace(reg, '');    
    }    
}    
    
function toggleClass(obj,cls){    
    if(hasClass(obj,cls)){    
        removeClass(obj, cls);    
    }else{    
        addClass(obj, cls);    
    }    
}  

function pureView(elementid){
	document.getElementById(elementid).style.display = "none";
}

wx.config({
    debug: false,                    
    appId: "wx368c4b0fd8b8007f", 
    timestamp: ${wxconfig.timestamp}, 
    nonceStr: '${wxconfig.nonceStr}', 
    signature:'${wxconfig.signature}',
    jsApiList: ['checkJsApi',
                'hideMenuItems',
                'showMenuItems',
                'hideAllNonBaseMenuItem',
                'showAllNonBaseMenuItem',
                'hideOptionMenu',
                'showOptionMenu',
                'closeWindow',
                'scanQRCode']
})

wx.ready(function () {
	wx.hideMenuItems({
	      menuList: [
	        'menuItem:readMode', // 阅读模式
	        'menuItem:copyUrl', // 复制链接
	        'menuItem:share:QZone'  //分享到 QQ 空间
	      ]
	    });
	wx.onMenuShareTimeline({
	    title: '哇哈哈哈哈哈', // 分享标题
	    link: '', // 分享链接
	    imgUrl: '', // 分享图标
	    success: function () { 
	        // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	    }
	});
	wx.onMenuShareAppMessage({
	    title: '天下第一帅牙签', // 分享标题
	    desc: '', // 分享描述
	    link: '', // 分享链接
	    imgUrl: '', // 分享图标
	    type: '', // 分享类型,music、video或link，不填默认为link
	    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
	    success: function () { 
	        // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	    }
	});
	wx.onMenuShareQQ({
	    title: '', // 分享标题
	    desc: '', // 分享描述
	    link: '', // 分享链接
	    imgUrl: '', // 分享图标
	    success: function () { 
	       // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	       // 用户取消分享后执行的回调函数
	    }
	});
})
</script>
</body>
</html>
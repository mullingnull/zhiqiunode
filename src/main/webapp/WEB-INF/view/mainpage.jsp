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
	<meta http-equiv="cache-control" content="no-cache"> 
	<meta http-equiv="expires" content="0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport"	content="width=device-width, initial-scale=1, user-scalable=0">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/styles.css">
	<link rel="stylesheet" type="text/css" href="https://res.wx.qq.com/open/libs/weui/0.4.1/weui.min.css"/>
	<script type="text/javascript"	src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript" src="<%=scdPath%>js/qrcode.min.js"></script>
	<style>
	#hello {
		position: relative;
		z-index: 4;
		top: 40%;
		text-align: center;
	}
	
	#hello h1 {
		display: inline;
		font-size: 2.7em;
		color: rgb(255, 255, 255);
	}
	</style>
	<title>小牙签@浙理</title>
</head>
<body onhashchange = "router();">
<div id="paper-back">
	<nav>
		<div class="close"></div>
		<a href="#hide">Hide</a> <a href="#seek">Seek</a> <a href="#more">More</a>
	</nav>
</div>
<div id="paper-window">
	<div id="paper-front">
		<div class="hamburger">
			<span></span>
		</div>
		<div id="#" class="page">载入中，稍候
		</div>
	</div>
</div>
<!--BEGIN dialog-->
<div class="weui_dialog_confirm" id="dialog" style="display: none;">
    <div class="weui_mask"></div>
    <div class="weui_dialog">
        <div class="weui_dialog_hd" id="dialoghd"><strong class="weui_dialog_title">通知</strong></div>
        <div class="weui_dialog_bd" id="dialogbd">通知内容</div>
        <div class="weui_dialog_ft">
            <a href="javascript:pureView('dialog');" id="dialogcancel" class="weui_btn_dialog default">取消</a>
            <a class="weui_btn_dialog primary" id="dialogsubmit">确定</a>
        </div>
    </div>
</div>

<div class="weui_dialog_alert" id="dialogonly" style="display: none;">
    <div class="weui_mask"></div>
    <div class="weui_dialog">
        <div class="weui_dialog_hd" id="dialogonlyhd"><strong class="weui_dialog_title">Sorry</strong></div>
        <div class="weui_dialog_bd" id="dialogonlybd">出错了(´◔ ‸◔')</div>
        <div class="weui_dialog_ft">
            <a href="javascript:pureView('dialogonly');" class="weui_btn_dialog primary">好吧(◔ ౪◔)</a>
        </div>
    </div>
</div>

<!--END dialog-->
<!-- BEGIN tipcard -->
<div class="user_card" id="usercard" style="display: none;">
    <div class="card_mask"></div>
	<div class="card_container">
		<div class="card_hd" id="cardhd"><strong class="weui_dialog_title">Sorry</strong></div>
		<div class="card_bd flipped" id="cardbd">
			<div class="front">Click to flip</div>
			<div class="back">Yo, what up?</div>
		</div>
   </div>
</div>
<!-- END tipcard -->
<template type="text/html" id="tpl_hide">
	<div class="header">
	<div style="top: 24vh; position: absolute; font-weight: 800; right: 55vw; font-size: 16px; ">
	批量扫描（伪）→
	</div>
	<div class="checkbox">
		<input id="batch" type="checkbox" /> <label></label>
	</div>
	<div id="scanbar" class="scan-bar active">
		<div class="input-holder">
			<input id="isbntohide" type="number" class="type-in"
				onkeyup="value=value.replace(/[^0-9]/g,'')"
				placeholder="Type ISBN-13 Here" />
			<button class="scan-icon" onclick="scanIconClick();">
				<span></span>
			</button>
		</div>
		<span class="zip" onclick="closeScanBar();"></span>
		<div class="tips-container">Or Click to SCAN Directly↑</div>
	</div>
	<div class="borderline">------hidden before------</div>
	</div>
	<div id="container" class="container">
	<div id="mine_panel" class="weui_panel weui_panel_access">
		<div id="mine_panel_hd" class="weui_panel_hd"></div>
		<div id="mine_panel_bd" class="weui_panel_bd"></div>
		<a id="mine_panel_ft" class="weui_panel_ft" href="javascript:listMines(0);">展开所有</a>

	</div>
	</div>
</template>
<template type="text/html" id="tpl_seek">
	<div class="header">
		<div id="hello">
			<h1>Hello,${user.nickName}</h1>
		</div>
		<div id="bookinfo" class="bookinfo">
		</div>
	</div>
	<div class="reel">
	  <input type="checkbox" class="reel__cb" id="menu-cb"/>
	  <div class="reel__content">
	    <div class="reel__items">
	      <div class="reel__item">
	        <select id="gender" class="reel__item-text"  onchange="changeFilter('gender')">
				<option value="false">性别</option>
				<option value="1">男</option>
				<option value="2">女</option>
				<option value="0">保密</option>
	        </select>
	      </div>
		  <div class="reel__item">
	        <select id="college"  class="reel__item-text"  onchange="changeFilter('college')">
				<option value="false">学院</option>
				<option value="c">材织</option>
				<option value="f">法政</option>
				<option value="z">服院</option>
				<option value="a">机控</option>
				<option value="j">建工</option>
				<option value="g">经管</option>
				<option value="l">理学院</option>
				<option value="q">启新</option>
				<option value="s">生科</option>
				<option value="w">外国语</option>
				<option value="b">文传</option>
				<option value="x">信息</option>
				<option value="y">艺设</option>
				<option value="p">其它</option>
	        </select>
	      </div>
	      <div class="reel__item">
	        <select id="major" class="reel__item-text" onchange="changeFilter('major')">
	          <option value="false">专业</option>
	        </select>
	      </div>
	      <div class="reel__item">
	        <select id="enrolYear" class="reel__item-text" onchange="changeFilter('year')">
				<option value="false">年级</option>
				<option value="2015">2015</option>
				<option value="2014">2014</option>
				<option value="2013">2013</option>
				<option value="2012">2012</option>
	        </select>
	      </div>
	    </div>
	  </div>
	  <label class="reel__btn" for="menu-cb" onclick="changeFilter('close')"></label>
	</div>
	<div class="searchbar">
		<input id="seekinput" type="number" name="searchinput" class="input"  min="0" inputmode="numeric" pattern="[0-9]*" title="Non-negative integral number"
			onkeyup="value=value.replace(/![0-9]/g,'')" onkeydown="if(event.keyCode==9 || event.keyCode==13) {submitWishISBN()}" >
		<button id="seekbutt" type="submit" class="search" onclick="toggleSearchBar(this)"></button>
	</div>
	<div id="container" class="container loading">
		<div id="wish_panel" class="weui_panel weui_panel_access">
			<div id="wish_panel_hd" class="weui_panel_hd"></div>
			<div id="wish_panel_bd" class="weui_panel_bd"></div>
			<a id="wish_panel_ft" href="javascript:listWishes(0);" class="weui_panel_ft">查看所有</a>
			<a href="javascript:alterUser();" class="weui_panel_ft">那些粗♂暴的学长学姐们...XD</a>
		</div>
		<div class="loading">
			<div class="line"></div>
			<div class="line"></div>
			<div class="line"></div>
			<div class="line"></div>
		</div>
	</div>
</template>
<template type="text/html" id="tpl_more">	
	<div class="header">
	<div id="myinfo" class="myinfo">
	
	</div>
	</div>
	<div id="minelist" class="weui_cells weui_cells_access">
		<a class="weui_cell" href="javascript:alterUser();">
			<div class="weui_cell_hd">
				<img src="" alt=""
					style="width: 20px; margin-right: 5px; display: block">
			</div>
			<div class="weui_cell_bd weui_cell_primary">
				<p>个人信息</p>
			</div>
			<div class="weui_cell_ft">更新个人信息</div>
		</a> 
		<a class="weui_cell" href="javascript:alterUser();">
			<div class="weui_cell_hd">
				<img src="" alt=""
					style="width: 20px; margin-right: 5px; display: block">
			</div>
			<div class="weui_cell_bd weui_cell_primary">
				<p>BUG</p>
			</div>
			<div class="weui_cell_ft">提交BUG</div>
		</a> 
		<a class="weui_cell" href="javascript:alterUser();">
			<div class="weui_cell_hd">
				<img src="" alt=""
					style="width: 20px; margin-right: 5px; display: block">
			</div>
			<div class="weui_cell_bd weui_cell_primary">
				<p>FAQ</p>
			</div>
			<div class="weui_cell_ft">前往FAQ</div>
		</a>
		<a class="weui_cell" href="javascript:alterUser();">
			<div class="weui_cell_hd">
				<img src="" alt=""
					style="width: 20px; margin-right: 5px; display: block">
			</div>
			<div class="weui_cell_bd weui_cell_primary">
				<p>About Us</p>
			</div>
			<div class="weui_cell_ft">Read More</div>
		</a> 
	</div>
	<div style="padding-bottom: 3vh;padding-left:36vw;color: #7A9FCF;">2016毕设作品</div>
</template>
<script type="text/javascript">

//用户本人信息

var myInfo = {nickName:'${user.nickName}',gender:${user.gender},college:'${user.college}',major:'${user.major}',enrolYear:${user.enrolYear},am:'${user.am}',flag:${user.flag}};

window.onload = function(){
	if (!minesCalled) {
		getMines();
	}
	if (!wishesCalled) {
		getWishes();
	}
	var paperwindow = document.querySelector('#paper-window');
	var hamburger = document.querySelector('.hamburger');
	var seekbutt = document.querySelector('#seekbutt');
	var seekinput = document.querySelector('#seekinput');
	hamburger.onclick = function() {
		toggleClass(paperwindow,'tilt');
	};
	document.querySelector('.close').onclick = function () {
    	paperwindow.className = '';
	};
	var cardbd = document.querySelector('.card_bd');
	cardbd.onclick = function() {
		toggleClass(cardbd,'flipped');
	};
	document.querySelector('.card_mask').onclick = function () {
		pureView('usercard');
	};
}

function alterUser() {
	dialogOnly(404, "前方道路施工中...稍候");
}

function showMyInfo(item) {
	if (item==='hide') {
		var para = document.createElement("a");
	    para.setAttribute("id", "mine_panel_bl");
	    para.setAttribute("class", "weui_panel_ft");
	    para.setAttribute("href", "javascript:alterMyInfo('aboutFlag');");
		if (myInfo.flag) {
	    	para.appendChild(document.createTextNode("暴力模式已启用，修改-->"));
		}else{
		    para.appendChild(document.createTextNode("来不及发车了？试试暴力模式"));
		}
		document.getElementById('mine_panel').appendChild(para);
	} else {
		document.getElementById('myinfo').innerHTML = '<img src="<%=scdPath%>favicon.ico" alt=""><ul><li>用户名：'+ myInfo.nickName + convertGender(myInfo.gender)+'</li><li>学院：'+convertCollege(myInfo.college)+'</li><li>专业：'+convertMajor(myInfo.major)+'</li><li>'+myInfo.enrolYear+'级</li><li>自我介绍：'+myInfo.am+'</li></ul>';
	}
	
}

function alterMyInfo(item) {//更改用户信息等
	var i = mineIdx.indexOf(isbnToHide);
	if (item==='aboutFlag') {
		var bdinner = '<div class="weui_cells_title">&nbsp;&nbsp;&nbsp;&nbsp;暴力模式将暴露您的学院、专业等信息给学弟学妹，需要您对教材等进行笼统介绍</div><div class="weui_cells weui_cells_form"><div class="weui_cell weui_cell_switch"><div class="weui_cell_hd weui_cell_primary">暴力模式</div><div class="weui_cell_ft"><input class="weui_switch" id="myflag" type="checkbox"></div></div></div><div class="weui_cells_title">一句话简述教材等信息</div><div class="weui_cells weui_cells_form"><div class="weui_cell"><div class="weui_cell_bd weui_cell_primary"><textarea class="weui_textarea" id="myam" placeholder="举个栗子,工业工程全套教材，大四，急转，电15757123482" rows="3">'+ myInfo.am+'</textarea><div class="weui_textarea_counter"><span>0</span>/45</div></div></div></div>';
		dialog('暴力模式', bdinner, null, "putMyInfo('aboutFlag');");
		document.getElementById("myflag").checked = myInfo.flag;
	} else {
		getBookInfo(isbnToHide, function(bookInfo) {
			  var bdinner = '<div class="weui_cells"><div class="weui_cell"><div class="weui_cell_bd weui_cell_primary"><p>作者:</p></div><div class="weui_cell_ft">'+ bookInfo.author +'</div></div><div class="weui_cell"><div class="weui_cell_bd weui_cell_primary"><p>出版社:</p></div><div class="weui_cell_ft">'+ bookInfo.publisher +'</div></div><div class="weui_cell weui_cell_select weui_select_after"><div class="weui_cell_hd">数量:</div><div class="weui_cell_bd weui_cell_primary"><select id="mineqty" class="weui_select" name="select2"><option value="1" selected=true>1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option></select></div></div><div class="weui_cells weui_cells_form"><div class="weui_cell"><div class="weui_cell_bd weui_cell_primary"><textarea class="weui_textarea" id="myprefer" placeholder="说出想说的话吧" rows="3"></textarea><div class="weui_textarea_counter"><span>0</span>/45</div></div></div></div></div>';
				
				dialog(bookInfo.title, bdinner, null, "postMine(\'" + bookInfo.isbn + "\');");
			
		  });
		}
}

function putMyInfo(item) {
	pureView('dialog');
	var changed = false;
	if (item==='aboutFlag') {
		var newAm = document.getElementById("myam").value.replace(inputPattern,"").trim().slice(0,44);
		var newFlag = document.getElementById("myflag").checked;
		if (newAm != myInfo.am) {
			myInfo.am = newAm;
			changed = true;
		}
		if (newFlag != myInfo.flag) {
			myInfo.flag = newFlag;
			changed = true;
		}
	} else {

	}
	
	if (changed) {
		ajaxRequest('api/users', 'PUT', JSON.stringify(myInfo), function(responseText) {
			var resJson = JSON.parse(responseText);
			  if (resJson.statuscode == 200) {
				myInfo = resJson.result;
			  }else {
				  dialogOnly(resJson.statuscode,resJson.errmsg);
			  }
		});
	}
}

//！animation variables
var homepage = 'seek';
var mines = [];

//默认显示的藏书数
var mineLine = 5;
var mineIdx = [];
var scanBarActive = true;
var isbnToHide ='';
var minesCalled = false;

var wishes = [];
var wishIdx =[];
var wishesCalled = false;

var bookInfos = [];
//三维数组，依次分别为ISBN、页码和用户
var searchResults = [];
//依据ISBN为searchResults提供索引
var resultIdx = [];
var resultAmount = [];
//当前ISBN下多个页面集合
var resultSet = [];

var limitGender = 'false';
var limitCollege = 'false';
var limitMajor = 'false';
var limitEnrolYear = 'false';

var perpage=15;
var inputPattern = new RegExp("[`#$^*()=|{}':;',\\n\\[\\].<>/@#￥&*&mdash;—|{}【】‘]");

function reelReel() {
	var item = document.getElementById("gender");
	//gender
	for (var i = 0; i < 4; i++) {
		if (item.options[i].value==limitGender) {
			item.options[i].selected =true;
			break;
		}
	}
	//college
	item = document.getElementById("college");
	if (limitCollege != 'false') {
		for (i = 0; i < 16; i++) {
			if (item.options[i].value==limitCollege) {
				item.options[i].selected =true;
				break;
			}
		}
		cascadeSubject('major');
		//major
		item = document.getElementById("major");
		for (i = 0; i < item.length; i++) {
			if (item.options[i].value==limitMajor) {
				item.options[i].selected =true;
				break;
			}
		}
	}
	//enrolYear
	item = document.getElementById("enrolYear");
	for (i = 0; i < 5; i++) {
		if (item.options[i].value==limitEnrolYear) {
			item.options[i].selected =true;
			break;
		}
	}
	
}
var tempG='false',tempC='false',tempM='false',tempY='false';
function changeFilter(item) {
	var changed = false;
	if (item=='gender') {
		tempG = document.getElementById("gender").options[document.getElementById("gender").options.selectedIndex].value;
	} else if (item=='college') {
		tempC = document.getElementById("college").options[document.getElementById("college").options.selectedIndex].value;
		if (tempC != 'false') {
		cascadeSubject('major');
		}else {
			document.getElementById('major').options.length = 0;
			document.getElementById('major').options[0] = new Option("专业","false");
		}
	}else if (item=='major'){
		tempM = document.getElementById("major").options[document.getElementById("major").options.selectedIndex].value;
	}else if (item=='year') {
		tempY = document.getElementById("enrolYear").options[document.getElementById("enrolYear").options.selectedIndex].value;	
	}else if (item=='close') {
		if (limitCollege != tempC) {
			limitCollege = tempC;
			limitMajor='false';
			changed = true;
		}
		if (limitGender!=tempG) {
			limitGender = tempG;
			changed = true;
		}
		if (limitEnrolYear!=tempY) {
			limitEnrolYear=tempY;
			changed = true;
		}
		if (limitMajor!=tempM) {
			limitMajor=tempM;
			changed = true;
		}
		if (changed) {
			searchResults = [];
			resultIdx = [];
			resultAmount = [];
			resultSet = [];	
			try {
				var para = window.location.href.split('#')[1].split('?')[1];
				var isbn=para.split('&')[0].split('=')[1];
				var page = para.split('&')[1].split('=')[1];
				if (isbn!==undefined) {
					if (page==1||page===undefined) {
						seekBook(isbn, 1);
					} else {
						window.location.href=window.location.href.split('?')[0]+'?isbn='+isbn+'&page=1';
					}
				}
			} catch (e) {
				// TODO: handle exception
			}
		}
		
		
	}
}

function router() {
	var pageEnum = ['hide','seek','more'];
	var hash,isbn,map,topage;hash=isbn=topage=undefined;
	try {
		hash = window.location.href.split('#')[1];
		map=hash.split('?')[1];
		isbn=map.split('&')[0].split('=')[1];
		topage=map.split('&')[1].split('=')[1];
		hash = hash.split('?')[0];
	} catch (e) {
		// TODO: handle exception
	}
	
	var page = document.querySelector('.page');
	if (document.querySelector('#paper-window').className == ' tilt') {//关闭页面旋转
	document.querySelector('#paper-window').className = '';
	}

	if (page.id == hash ) {//DOM已加载完成，渲染部分即可
		if (hash == 'seek') {//分流seek请求
			if (isbn===undefined) {
				listWishes(-1);
				} else {
				seekBook(isbn,topage);
				}
		}
	}else {//加载整个界面
		if (pageEnum.indexOf(hash)<0) {
			hash = homepage;
		}
		if (hash == 'seek') {
			page.innerHTML = document.getElementById("tpl_seek").innerHTML;
			reelReel();
			if (map===undefined ||map[0].split('=')[1]==='') {
				listWishes(-1);
				} else {
					seekBook(isbn,topage);
				}
		} else if(hash == 'hide'){
			page.innerHTML = document.getElementById("tpl_hide").innerHTML;
			listMines(-1);
			showMyInfo('hide');
		} else {
			page.innerHTML = document.getElementById("tpl_more").innerHTML;
			showMyInfo('more');
		}
		page.id = hash;
	}

}

function seekBook(isbn,page) {
	if (!validateISBN(isbn)) {
		dialogOnly(666, 'ISBN错误，请检查');
		return;
	}
	loadContainer(true);
	if (!(minesCalled&&wishesCalled)) {
		return;
	}
    if(wishes.length>wishIdx.length){
        wishes.forEach(function (wish, index, array) {
  			wishIdx[index] = wish.isbn;
		});
    }
    if(mines.length>mineIdx.length){
       mines.forEach(function (mine, index, array) {
			  mineIdx[index] = mine.isbn;
		 });
    }
	var bookInfo;
	var w = wishIdx.indexOf(isbn);
	var m = mineIdx.indexOf(isbn);
	if (w>=0) {
		bookInfo = wishes[w];
		showBookInfo(bookInfo);
	} else if (m>=0) {
		bookInfo = mines[m];
		notify('had hidden');
		//plusOneSecond();
		showBookInfo(bookInfo);
	} else{
		getBookInfo(isbn, function (bookInfo) {
			showBookInfo(bookInfo);
		});
	}
	page = page===undefined?1:page<=0?1:page;
	var i = resultIdx.indexOf(isbn);
	if (i<0) {//未搜寻过的书籍
			searchMine(isbn, page, function(result) {
				
				i=resultIdx.push(isbn)-1;
				resultSet[page-1] = result.results;
				searchResults[i]=resultSet;
				resultAmount[i] = result.amount;
				listSearchResult(result.results,isbn,page,result.amount);
		
		});
	} else {//已搜寻过的书籍
		if (searchResults[i][page-1]===undefined) {//请求未缓存过的页面
			if (resultAmount[i]>page-1*perpage) {
				searchMine(isbn, page, function(result) {
					resultSet[page-1] = result.results;
					searchResults[i]=resultSet;
					resultAmount[i] = result.amount;
					listSearchResult(result.results,isbn,page,result.amount);
				});
				
			} else {
				dialogOnly(404, '没能找到更多,等养肥了再试试？');
			}
		} else {//本地已缓存
			resultSet=searchResults[i];
			listSearchResult(searchResults[i][page-1],isbn,page,resultAmount[i]);
		}
	}
}

function searchMine(isbn,page,callback) {
	var url = 'api/resources/mines?isbn='+ isbn + '&page=' +page;
	if (limitGender!='false') {
		url=url+'&gender='+limitGender;
	}
	if (limitMajor!='false') {
		url=url+'&major='+limitMajor;	
	}else if (limitCollege!='false') {
		url=url+'&college='+limitCollege;
	}
	if (limitEnrolYear!='false') {
		url=url+'&enrolYear='+limitEnrolYear;
	}
	ajaxRequest(url, 'GET', null,function(responseText) {
				var resJson = JSON.parse(responseText);
				if (resJson.statuscode === undefined) {
					callback(resJson);	
					
				} else {
					loadContainer(false);
					//window.history.go(-1);
					dialogOnly(resJson.statuscode, resJson.errmsg);
				}
	});
}

function showBookInfo(bookInfo) {
	var hello = document.getElementById('hello');
	if (hello !== null) {
	hello.parentNode.removeChild(hello);
	}
	var inner = '<img src="<%=scdPath%>images/icon_book.png" alt=""><ul><li>'+bookInfo.title+'</li><li>'+bookInfo.author+'</li><li>'+ bookInfo.publisher+'</li><li>'+bookInfo.page+'页</li>';
	if (wishIdx.indexOf(bookInfo.isbn)<0) {
		inner+='<li onclick="postWish(\''+bookInfo.isbn+'\')">添加到心愿单</li></ul>';
	} else {
		inner+='</ul>';
	}
	document.getElementById('bookinfo').innerHTML = inner;
}

function listSearchResult(results,isbn,currentpage,totalAmount) {
	var ft = document.getElementById("wish_panel_ft");
	if (ft===null) {
		return;
	}
	var inner = '';
	if (results.length===0) {
		if (totalAmount===0) {
			
			if (wishIdx.indexOf(isbn)<0) {
				inner = '数据库中未检索到该书，需要加入心愿单中吗？。？<a href="javascript:postWish(\''+isbn+'\')">放入心愿单</a>';
				
			} else {
			inner = '没能找到,等养肥了再试试？so sad';
			}
		} else {
			
			inner='That\'s all,没有更多了,you can <a href="javascript:window.history.go(-1)">fanhui 前页</a>';
		}
		document.getElementById('wish_panel_hd').innerHTML = '糟糕，没找到！';
	}else {
  		results.forEach(function (result, index, array) {

			inner += buildSearchBdInner(currentpage,index,result);
			});

	  	if (totalAmount<=perpage*currentpage) {
	  		 ft.innerHTML ="That's All!";
			 ft.setAttribute("href", 'javascript:void(0);');
		} else {
	  		ft.innerHTML ="下一页<s>可能有也可能没有</s>";
	  		ft.setAttribute("href", '#seek?isbn='+isbn+'&page='+(currentpage+1));

		}
		document.getElementById('wish_panel_hd').innerHTML = '该书在库总计'+totalAmount+'本,显示'+(currentpage-1)*perpage +'~' + ((currentpage-1)*perpage+results.length)+ '条结果,点击可查看结果详情';
	}
		document.getElementById("wish_panel_bd").innerHTML = inner;
		loadContainer(false);
}

function buildSearchBdInner(currentpage,index,result) {
	return '<a href="javascript:showUserCard('+currentpage+','+ index +');" class="weui_media_box weui_media_appmsg"><div class="weui_media_hd"><img class="weui_media_appmsg_thumb" src="<%=scdPath%>favicon.ico" alt=""></div><div class="weui_media_bd"><h4 class="weui_media_title">' + result.nickName + '&nbsp;'+ convertGender(result.gender)+'&nbsp;|<b style="padding-left:15px;">'+ convertEnrolYear(result.enrolYear) +'</b></h4><p class="weui_media_desc">留言' + result.prefer + '</p></div></a>' + '<ul class="weui_media_info mine"><li class="weui_media_info_meta mine">hid in ' + formateDate(result.updateTime).slice(0, 7) + '</li><li style="color: #328421;" class="weui_media_info_meta weui_media_info_meta_extra mine">匹配:' + wishIdx.filter(function(v) {return result.clues.includes(v)}).length+ '本</li><li style="color: #328421;" class="weui_media_info_meta weui_media_info_meta_extra mine">' + convertCollege4Short(result.college) + '</li><li class="weui_media_info_meta mine">' + convertMajor(result.major) + '</li></ul>';
	
}

function showUserCard(currentpage,index) {
	var user = resultSet[currentpage-1][index];
	var cardbd = document.getElementById("cardbd");
    var intersection = wishIdx.filter(function(v) {return user.clues.includes(v)});
    var inner = '';
	document.getElementById("usercard").style.display = "block";
	document.getElementById("cardhd").innerHTML=user.nickName + '&nbsp;'+ convertGender(user.gender)+'&nbsp;|<b style="padding-left:15px;">'+intersection.length+'本</b>';
	cardbd.querySelector('.back').innerHTML='<div id="qrcode"></div>';
    var qrcode = new QRCode(document.getElementById("qrcode"), {width : 250,height : 250});
	qrcode.makeCode('http://weixin.qq.com/r/'+ user.wcQrcode);
	intersection.forEach(function (isbn, index, array) {
			inner +='<li>'+ wishes[wishIdx.indexOf(isbn)].title+'</li>';
			});
	cardbd.querySelector('.front').innerHTML='自我介绍：'+user.am+'<br/>心愿单匹配书籍列表，点击查看二维码<br/><ul>'+inner+'</ul';
	//cardbd.className = 'card_bd';
	toggleClass(cardbd,'flipped');
}

function toggleSearchBar() {
	var seekbutt = document.querySelector('#seekbutt');
	var seekinput = document.querySelector('#seekinput');
	toggleClass(seekbutt,'zip');
	toggleClass(seekinput,'square');
	if (hasClass(seekbutt,'zip')) {
		seekinput.focus();
	  } else {
		seekinput.blur();
		seekinput.value = '';
	  }
}

function loadContainer(bool){
	var conl = document.querySelector('#container');
	if (bool && hasClass(conl, 'container')) {
		addClass(conl,'loading');
	} else if (hasClass(conl, 'container loading')) {
		removeClass(conl,'loading');
	}
}

function submitWishISBN() {
	var seekinput = document.querySelector('#seekinput');
	var iSBNtoSeek = seekinput.value;
	if (iSBNtoSeek === "") {
		seekinput.focus;
	} else if (validateISBN(iSBNtoSeek)) {
		window.location.href='#seek?isbn=' + iSBNtoSeek + '&page=1';
		toggleSearchBar();
	} else {
		dialogOnly(666, 'ISBN错误,劳驾重新确认一下ISBN');
		seekinput.focus;
	}
}

function getWishes() {
ajaxRequest('api/wishes','GET',null,function(responseText){
	var resJson = JSON.parse(responseText);
	if (resJson.statuscode === undefined) {
		wishes = JSON.parse(responseText);
	}else if (resJson.statuscode != 404) {
		dialogOnly(resJson.statuscode,resJson.errmsg + '拉取心愿单失败');
	}
	wishesCalled = true;
	//listWishes(-1);
	router();
	});
}

function listWishes(i) {
	var ft = document.getElementById("wish_panel_ft");
	if (!wishesCalled) {
		//getWishes();
		return;
	}
	if (ft===null) {
		return;
	}
	var inner = '';
	if (wishes.length === 0) {
	inner = '';
	ft.innerHTML ="还没有哦";
	ft.setAttribute("href",'javascript:void(0);');
	} else if (i > 0) {
  		
  	} else if (i === 0) {//遍历所有，显示所有
  		wishes.forEach(function (wish, index, array) {
  			wishIdx[index] = wish.isbn;
			inner += buildWishBdInner(wish);
			});
  		
  		ft.innerHTML ="That's All!点击收起";
		ft.setAttribute("href", 'javascript:listWishes(-1);');
  	} else {//遍历所有，显示部分
	  wishes.forEach(function (wish, index, array) {
		  	wishIdx[index] = wish.isbn;
			if (index < mineLine) {
		  	inner += buildWishBdInner(wish);
			}
	  });
	  if (mineLine>=wishes.length) {
		  ft.innerHTML ="That's All!";
		  ft.setAttribute("href", 'javascript:void(0);');
		}
	}
	loadContainer(false);
	document.getElementById('wish_panel_hd').innerHTML = '心愿单,共计' + wishes.length + '本';
	document.getElementById("wish_panel_bd").innerHTML = inner;
}

function buildWishBdInner(wish) {
	return '<div class="weui_media_box weui_media_text"><a href="#seek?isbn=' + wish.isbn + '&page=1"><h4 class="weui_media_title">' + wish.title + '</h4><p class="weui_media_desc">简介：'+ wish.summary + '</p></a><ul class="weui_media_info"><li class="weui_media_info_meta">约' + wish.mineCount + '本</li><li class="weui_media_info_meta">'+ formateDate(wish.updateTime) + '</li><li onclick="confirmDeleteWish(\'' + wish.isbn + '\')" class="weui_media_info_meta weui_media_info_meta_extra">删除</li></ul></div>';
}

function postWish(ISBNtoSeek) {
	if (wishIdx.indexOf(ISBNtoSeek)<0) {
	var wish = {isbn:ISBNtoSeek};
	ajaxRequest('api/wishes', 'POST', JSON.stringify(wish), function(responseText) {
		var resJson = JSON.parse(responseText);
		  if (resJson.statuscode == 201 || resJson.statuscode == 400) {//创建成功或已存在
			  notify('+1');
			  ajaxRequest('api/wishes?isbn=' + ISBNtoSeek, 'GET', null, function(responseText) {
				  var resJson = JSON.parse(responseText);
				  if (resJson.statuscode === undefined) { 
					  wishes.unshift(resJson);
					  wishIdx.unshift(resJson.isbn);
					  //listWishes(0);//搜索页面添加时覆盖搜索结果，交给调用其的函数决定是否要执行
				  }});
		  }else {
			  dialogOnly(resJson.statuscode,resJson.errmsg);
		  }
	});	
	}
}

function confirmDeleteWish(ISBNtoSeek) {
	dialog('确定删除？？', 'R U SURE???', null, 'deleteWish(\'' + ISBNtoSeek + '\')');
}

function deleteWish(ISBNtoSeek) {
	pureView('dialog');
	var i = wishIdx.indexOf(ISBNtoSeek);
	if (i>=0) {
		ajaxRequest('api/wishes?isbn='+ ISBNtoSeek, 'DELETE', null, function(responseText) {
			var resJson = JSON.parse(responseText);
			  if (resJson.statuscode == 410 ) {//删除成功
				  wishes.splice(i, 1);
			  	  wishIdx.splice(i, 1);	
				  listWishes(0);
			  }else {	
				  dialogOnly(resJson.statuscode,resJson.errmsg);
			  }
		});	
	}
}

function convertEnrolYear(year) {
	switch (year) {
	case 2011:
		return '大五';
	case 2012:
		return '大四';
	case 2013:
		return '大三';
	case 2014:
		return '大二';
	case 2015:
		return '大一';
	default:
		return '未知';
	}
}

function getMine(isbnToHide) {
  ajaxRequest('api/mines?isbn=' + isbnToHide, 'GET', null, function(responseText) {
	  var resJson = JSON.parse(responseText);
	  if (resJson.statuscode === undefined) { 
		mines.push(resJson);
	  }
});
}

function getMines() {
ajaxRequest('api/mines','GET',null,function(responseText){
	  var resJson = JSON.parse(responseText);
	  if (resJson.statuscode === undefined) {
		  mines = JSON.parse(responseText);
		}else if (resJson.statuscode != 404) {
		  dialogOnly(resJson.statuscode,resJson.errmsg);
		}
	  minesCalled = true;
	  //listMines(-1);
	  router();
	  });
}

function listMines(i) {
	var ft = document.getElementById("mine_panel_ft");
	if (!minesCalled) {
		//getMines();由router负责调用
		return;
	}
	if (ft===null) {
		return;
	}
	var inner ='';
 	var mineQty = 0;
	if (mines.length === 0) {
	ft.innerHTML ="还没有哦";
	ft.setAttribute("href",'javascript:void(0)');
	} else if (i < 0) {//遍历所有，显示部分
	  mines.forEach(function (mine, index, array) {
		  mineIdx[index] = mine.isbn;
		  mineQty += mine.qty;
		if (index < mineLine) {
			 inner += buildMineBdInner(index,mine) ;
		}
		if (mineLine>=mines.length) {
			  ft.innerHTML ="That's All!";
		  		ft.setAttribute("href", 'javascript:void(0)');
			}
	  });

  } else if (i === 0) {//遍历所有，显示所有
	  mines.forEach(function (mine, index, array) {
		  mineIdx[index] = mine.isbn;
		  mineQty += mine.qty;
		  inner += buildMineBdInner(index,mine) ;
	  });
	  ft.innerHTML ="That's All!点击收起";
	  ft.setAttribute("href", 'javascript:listMines(-1)');
  } 
  	document.getElementById('mine_panel_hd').innerHTML = '已藏图书合计' + mines.length + '种,共' + mineQty + '本';
	document.getElementById('mine_panel_bd').innerHTML = inner;
}

function buildMineBdInner(index,mine) {
	return '<a href="javascript:alterMine('+ index +');" class="weui_media_box weui_media_appmsg"><div class="weui_media_hd"><img class="weui_media_appmsg_thumb" src="<%=scdPath%>images/icon_book.png" alt=""></div><div class="weui_media_bd"><h4 class="weui_media_title">' + mine.title + '</h4><p class="weui_media_desc">' + mine.prefer + '</p></div></a>' + '<ul class="weui_media_info mine"><li class="weui_media_info_meta mine">数量：' + mine.qty+ '</li><li class="weui_media_info_meta mine">' + formateDate(mine.updateTime) + '</li><li class="weui_media_info_meta weui_media_info_meta_extra mine">约' + mine.wishCount + '人在找</li></ul>';
	
}

function alterMine(index) {
	var mine = mines[index];
	if ( mine !== undefined) {
		var dialogbd = '<div class="weui_cells"><div class="weui_cell"><div class="weui_cell_bd weui_cell_primary"><p>ISBN:</p></div><div class="weui_cell_ft">'+ mine.isbn +'</div></div><div class="weui_cell"><div class="weui_cell_bd weui_cell_primary"><p>更新于:</p></div><div class="weui_cell_ft">'+ formateDate(mine.updateTime) +'</div></div><div class="weui_cell weui_cell_select weui_select_after"><div class="weui_cell_hd">数量:</div><div class="weui_cell_bd weui_cell_primary"><select id="mineqty" class="weui_select" name="select2"><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option></select></div></div><div class="weui_cell"><div class="weui_cell_bd weui_cell_primary"><textarea class="weui_textarea" id="myprefer" placeholder="说出想说的话吧" rows="3">'+ mine.prefer+'</textarea><div class="weui_textarea_counter"><span>0</span>/45</div></div></div></div>';
		dialog(mine.title, dialogbd, null, "putMine(" + index + ");");
		document.getElementById('mineqty').options[mine.qty - 1].selected = true;
	}
}

function putMine(index) {
	pureView('dialog');
	var mine = mines[index];
	var changed = false;
	var newprefer = document.getElementById("myprefer").value.replace(inputPattern,"").trim().slice(0,44);
	var newQty = document.getElementById("mineqty").options.selectedIndex + 1;
	if (newprefer != mine.prefer) {
		mine.prefer = newprefer;
		changed = true;
	}
	if (newQty != mine.qty) {
		mine.qty = newQty;
		changed = true;
	}
	if (changed) {
		ajaxRequest('api/mines', 'PUT', JSON.stringify(mine), function(responseText) {
			var resJson = JSON.parse(responseText);
			  if (resJson.statuscode == 201) {
				mines[index] = mine;
				listMines(0);
			  }else {
				  dialogOnly(resJson.statuscode,resJson.errmsg);
			  }
		});
	}
}
/* 扫码 或  展开 searchbar */
function scanIconClick() {//判别是否需要扫描
	if (scanBarActive){
		isbnToHide = document.getElementById("isbntohide").value;
		if (isbnToHide.length === 0 || isbnToHide.length == 2) {//未输入或默认输入
			document.getElementById("isbntohide").value = "";
			scanISBN();
		}else {
			document.getElementById("isbntohide").value = "97";
			if (validateISBN(isbnToHide)) {
				previewMine(isbnToHide);
			}else {
				dialogOnly(400,'ISBN错误，仅接受97开头13位图书类ISBN');
			}
		}
	} else {
		document.querySelector('#scanbar').className ='scan-bar active';
	}	
}

function scanISBN() {//扫描ISBN
wx.scanQRCode({
      needResult: 1,
      scanType: "barCode",
      desc: '扫描添加图书，仅支持图书哦',
      success: function (res) {
    	isbnToHide = res.resultStr.split(',')[1];
		if (validateISBN(isbnToHide)) {
			previewMine(isbnToHide);
		}else {
			dialogOnly(400,'ISBN错误，仅接受97开头13位图书类ISBN');
		}
      },
     cancle:function () {
    	 alert('cancled');
     }
});
}

function previewMine(isbnToHide) {//预览书籍信息，输入留言
	var i = mineIdx.indexOf(isbnToHide);
	if (i >= 0) {
		alterMine(i);
		document.getElementById('mineqty').options[mines[i].qty].selected = true;
		notify('+1');
		//plusOneSecond();
	} else {
		getBookInfo(isbnToHide, function(bookInfo) {
			  var bdinner = '<div class="weui_cells"><div class="weui_cell"><div class="weui_cell_bd weui_cell_primary"><p>作者:</p></div><div class="weui_cell_ft">'+ bookInfo.author +'</div></div><div class="weui_cell"><div class="weui_cell_bd weui_cell_primary"><p>出版社:</p></div><div class="weui_cell_ft">'+ bookInfo.publisher +'</div></div><div class="weui_cell weui_cell_select weui_select_after"><div class="weui_cell_hd">数量:</div><div class="weui_cell_bd weui_cell_primary"><select id="mineqty" class="weui_select" name="select2"><option value="1" selected=true>1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option></select></div></div><div class="weui_cells weui_cells_form"><div class="weui_cell"><div class="weui_cell_bd weui_cell_primary"><textarea class="weui_textarea" id="myprefer" placeholder="说出想说的话吧" rows="3"></textarea><div class="weui_textarea_counter"><span>0</span>/45</div></div></div></div></div>';
				
				dialog(bookInfo.title, bdinner, null, "postMine(\'" + bookInfo.isbn + "\');");
			
		  });
		}
}

function getBookInfo(isbn,callback) {
	ajaxRequest('api/books/isbn/'+isbn,'GET',null,function(responseText){
	  var resJson = JSON.parse(responseText);
	  if (resJson.statuscode === undefined) {
		  callback(resJson);
		}else {
		  dialogOnly(resJson.statuscode,resJson.errmsg);
		}
	  });
}

function dialog(dialoghd,dialogbd,cancelFun,confirmFun) {
	document.getElementById("dialoghd").innerHTML = dialoghd; 
	document.getElementById("dialogbd").innerHTML = dialogbd;
	if (cancelFun !== null) {
	document.getElementById("dialogcancel").setAttribute("href",  'javascript:' + cancelFun);
  }else{
    document.getElementById("dialogcancel").setAttribute("href",  "javascript:pureView('dialog');");
  }
	document.getElementById("dialogsubmit").setAttribute("href", 'javascript:' + confirmFun);
	document.getElementById("dialog").style.display = "block";
	
}

function postMine(isbnToHide) {
	pureView('dialog');
	var prefers = document.getElementById("myprefer").value.replace(inputPattern,"").trim().slice(0,44);
	var c = document.getElementById("mineqty").options.selectedIndex + 1;
	var mine = {isbn:isbnToHide,prefer:prefers,qty:c};
	ajaxRequest('api/mines', 'POST', JSON.stringify(mine), function(responseText) {
		var resJson = JSON.parse(responseText);
		  if (resJson.statuscode == 201) {

			ajaxRequest('api/mines?isbn=' + isbnToHide, 'GET', null, function(responseText) {
				  var resJson = JSON.parse(responseText);
				  if (resJson.statuscode === undefined) { 
					mines.unshift(resJson);
					listMines(-1);
				  }});
			if (document.getElementById("batch").checked && (document.getElementById("isbntohide").value.length === 0)) {//批量扫描时
				scanISBN();
			}
		  }else {
			  dialogOnly(resJson.statuscode,resJson.errmsg);
		  }
	});
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
        };
    }
}

function convertGender(gender) {
	switch (gender) {
	case 0:
	case '0':
		return '<img src="<%=scdPath%>images/gender_icon_female.png" alt="female" style="padding: 5px;height: 18px;width: 18px;"/>';
	case 1:
	case '1':
		return '<img src="<%=scdPath%>images/gender_icon_male.png" alt="male" style="padding: 5px;height: 18px;width: 18px;"/>';
	default:
		return '<img src="<%=scdPath%>images/gender_icon_bigender.png" alt="bigender" style="padding: 5px;height: 18px;width: 18px;"/>';
	}
}

var subject_a = ["aa:材料成型及控制工程","ab:材料成型及控制工程(3+2)","ac:测控技术与仪器","ad:测控技术与仪器(3+2)","ae:电气工程及其自动化","af:电气类","ag:工业工程","ah:过程装备与控制工程","ai:过程装备与控制工程(3+2)","aj:机械电子工程","ak:机械类","al:机械设计制造及其自动化","am:机械设计制造及其自动化(全英文)","an:自动化"],
subject_b = ["ba:传播学","bb:汉语言文学","bc:汉语言文学(3+2)"],
subject_c = ["ca:包装工程","cb:材料科学与工程","cc:材料类","cd:纺织工程","ce:纺织工程(3+2)","cf:纺织工程(卓越计划)","cg:纺织类","ch:纺织品设计","ci:非织造材料与工程","cj:轻化工程","ck:轻化工程(3+2)","cl:轻化工程(纺织贸易与检测全英文)","cm:轻化工程(染整工艺与设计)","cn:轻化工程(生态纺织化学品)","co:轻化工程(造纸方向)","cp:轻化工程(卓越计划)"],
subject_f = ["fa:法学","fb:公共管理类","fc:公共事业管理","fd:公共事业管理(3+2)","fe:行政管理","ff:行政管理(3+2)","fg:社会工作","fh:社会工作(3+2)"],
subject_g = ["ga:电子商务","gb:电子商务类","gc:工商管理","gd:工商管理(3+2)","ge:工商管理(全英文)","gf:工商管理类","gg:管理科学与工程类","gh:国际经济与贸易","gi:国际经济与贸易(全英文)","gj:会计学","gk:金融学","gl:经济统计学","gm:经济学","gn:经济与贸易类","go:人力资源管理","gp:市场营销","gq:统计学","gr:信息管理与信息系统"],
subject_j = ["ja:风景园林","jb:风景园林(3+2)","jc:工程管理","jd:建筑环境与能源应用工程","je:建筑环境与能源应用工程(3+2)","jf:建筑环境与设备工程","jg:建筑学","jh:土木工程"],
subject_l = ["la:材料化学","lb:化学类","lc:数学类","ld:数学与应用数学","le:信息与计算科学","lf:信息与计算科学(3+2)","lg:应用化学","lh:应用物理学","li:应用心理学","lj:应用心理学(3+2)"],
subject_p = ["pp:未知专业"],
subject_q = ["qa:材化生实验班","qb:机电实验班","qc:经济管理实验班","qd:理工实验班","qe:信息电子实验班","cb:材料科学与工程","ae:电气工程及其自动化","xa:电子信息工程","xb:电子信息科学与技术","ag:工业工程","gh:国际经济与贸易","gj:会计学","aj:机械电子工程","al:机械设计制造及其自动化","xd:计算机科学与技术","gk:金融学","gm:经济学","cj:轻化工程","sa:生物技术","se:生物制药","xg:通信工程","jh:土木工程","lg:应用化学"],
subject_s = ["sa:生物技术","sb:生物技术(全英文)","sc:生物技术(生物工程)","sd:生物技术(生物制药)","se:生物制药"],
subject_w = ["wa:日语","wb:英语"],
subject_x = ["xa:电子信息工程","xb:电子信息科学与技术","xc:电子信息类","xd:计算机科学与技术","xe:计算机科学与技术(全英文)","xf:数字媒体技术","xg:通信工程"],
subject_y = ["ya:产品设计","yb:动画","yc:工业设计","yd:广告学","ye:环境设计","yf:美术学","yg:设计学类","yh:视觉传达设计","yi:视觉传达设计(广告设计)","yj:艺术设计(产品设计)","yk:艺术设计(环境艺术设计)","yl:艺术设计(家具设计)","ym:艺术设计(视觉传达设计)"],
subject_z = ["za:表演(人物形象设计)","zb:表演(时装表演艺术)","zc:产品设计(纺织品艺术设计)","zd:服装设计与工程","ze:服装设计与工程(全英文)","zf:服装设计与工程(中美合作)","zg:服装设计与工程(卓越计划)","zh:服装与服饰设计","zi:服装与服饰设计(服饰品设计)","zj:服装与服饰设计(服装设计中美合作 )","zk:服装与服饰设计(服装艺术设计)","zl:服装与服饰设计(人物形象设计)","zm:服装与服饰设计(设计与营销)","zn:服装与服饰设计(设计与营销)(3+2)","zo:服装与服饰设计(设计与营销中美合作)","zp:艺术设计(服饰品设计与营销)","zq:艺术设计(服装设计中美合作)","zr:艺术设计(服装艺术设计)","zs:艺术设计(染织艺术设计)","zt:艺术设计(人物造型)","zu:艺术设计(人物造型设计)","zv:艺术设计(设计与营销中美合作)","zw:艺术设计(时装表演及营销)"];

function convertMajor(mj) {
	try {
		var subject_mj = eval('subject_' + mj.slice(0,1)).valueOf();
		for( var i = 0; i < subject_mj.length; i++ ){
			if(subject_mj[i].split(":")[0] == mj)
			return subject_mj[i].split(":")[1];
		}
		return '未透露专业';
	} catch (e) {
		return mj;
	}
}

//根据学院动态更新专业
function cascadeSubject(majormin){
	if ( majormin === "major"){//主修专业选择
		var subjectArr = eval("subject_" + document.getElementById("college").options[document.getElementById("college").options.selectedIndex].value);	
		var subArr = [];
		document.getElementById('major').options.length = 0;
		document.getElementById('major').options[0] = new Option("专业","false"); 
		for( var i = 0; i < subjectArr.length; i++ ){
			subArr = subjectArr[i].split(":");
	        document.getElementById('major').options[i+1] = new Option(subArr[1],subArr[0]); 
	    }
		}else {//辅修专业选择
			document.getElementById('minor').options.length = 0;
			document.getElementById('minor').options[0] = new Option("--专业--",""); 
			var minorCollege = document.getElementById("minorCollege").options[document.getElementById("minorCollege").options.selectedIndex].value;
			if (minorCollege !== "nothanks"){
			var subjectArr = eval("subject_" + minorCollege);	
			var subArr = [];	
			for( var i = 0; i < subjectArr.length; i++ ){
				subArr = subjectArr[i].split(":");
		        document.getElementById('minor').options[i+1] = new Option(subArr[1],subArr[0]); 
		    }
			}
		}
}

function convertCollege(college) {
	switch (college) {
	case "a":return "机械与自动控制学院";case "b":return "文化传播学院";case "c":return "材料与纺织学院";case "f":return "法政学院";case "g":	return "经济管理学院";case "j":return "建筑工程学院";case "l":return "理学院";case "q":return "启新学院";case "s":return "生命科学学院";case "x":	return "信息学院";case "w":return "外国语学院";case "y":return "艺术与设计学院";case "z":return "服装学院";
	default:return "未透露学院";
	}
}

function convertCollege4Short(college) {
	switch (college) {
	case "a":return "机控";case "b":return "文传";case "c":return "材纺";case "f":return "法政";case "g":	return "经管";case "j":return "建工";case "l":return "理学院";case "q":return "启新";case "s":return "生科";case "x":return "信息";case "w":return "外国语";case "y":return "艺设";case "z":return "服院";
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


//Array.includes方法实现
if (!Array.prototype.includes) {
  Array.prototype.includes = function(searchElement /*, fromIndex*/ ) {
    'use strict';
    var O = Object(this);
    var len = parseInt(O.length) || 0;
    if (len === 0) {
      return false;
    }
    var n = parseInt(arguments[1]) || 0;
    var k;
    if (n >= 0) {
      k = n;
    } else {
      k = len + n;
      if (k < 0) {k = 0;}
    }
    var currentElement;
    while (k < len) {
      currentElement = O[k];
      if (searchElement === currentElement ||
         (searchElement !== searchElement && currentElement !== currentElement)) { // NaN !== NaN
        return true;
      }
      k++;
    }
    return false;
  };
}


function notify(message) {
    if (plusOneSecEnabled == false) {
        return;
    }
    enabled = false;
    
    var para = document.createElement("div");
    //para.appendChild(node);
    para.setAttribute("id", "notify");
    para.setAttribute("style", "left:50%;top:50%;position:fixed;z-index:99;");
    para.innerHTML=message;
    document.querySelector('.page').parentNode.appendChild(para);

    ctrl = document.getElementById("notify");
    t = 0;
    ctrl.style.opacity = 1;
    size = 20;
    //pos = 220;
    anima();
}

function anima() {
    pos -= 1;
    //ctrl.style.top = pos.toString() + "px";
    ctrl.style.opacity = ctrl.style.opacity - 0.05
    size += 0.7;
    ctrl.style.fontSize = size.toString() + "px";
    if (t < d) {
        t++;
        setTimeout(anima, 20);
    }
    else {
        ctrl.parentNode.removeChild(ctrl);
        enabled = true;
    }
}

//plus one second animation variables
var d = 20, t = 0;
var pos = 220, size = 20.0;
var ctrl;
var plusOneSecEnabled = true;
function plusOneSecond() {
    if (plusOneSecEnabled == false) {
        return;
    }
    enabled = false;
    var para = document.createElement("div");
    var node = document.createTextNode("+1");
    para.appendChild(node);
    para.setAttribute("id", "added");
    //para.setAttribute("class", "divsstyle");
    para.setAttribute("style", "left:50%;top:50%;position:fixed;z-index:99;");
    var element=document.querySelector('.page');
    element.parentNode.appendChild(para);

    ctrl = document.getElementById("added");
    //alert(ctrl.innerText);
    t = 0;
    ctrl.style.opacity = 1;
    size = 20;
    //pos = 220;
    anima();
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
	    title: '天下第一帅的牙签', // 分享标题
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
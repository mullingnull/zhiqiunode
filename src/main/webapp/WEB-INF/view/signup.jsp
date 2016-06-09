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
  	<link rel="stylesheet" href="<%=basePath%>css/weui.css"/>
  	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
  	<script src="<%=scdPath%>js/qrcode.min.js"></script>
	<title>新用户登入--小牙签@浙理</title>
	<style type="text/css">
	.page_title {
	    text-align: center;
	    font-size: 34px;
	    color: #135705;
	    font-weight: 800;
	    margin: 0 15%;
	    margin-top: 2vh;
	}
	.page {
	    height: 100vh;
	    overflow-x: hidden;
	}
	.status {
		font-weight: 600;
	    margin-left: 15vw;
	    margin-top: 7vh;
	    line-height: 2em;
	    font-size: 1.2em;
	    color: #8C652B;
	}
	/*CSS*/
	.code-bg{
	    position: relative; 
	    height: 200px; width: 200px; 
	    margin: 0px auto;/*此处为了居中*/
	    text-align: center; 
	    background: url(http://www.iaxure.com/share/ewsm/images/index/u3.png) center top no-repeat; 
	}
	.code-bg img{ 
	    position: absolute;
	    top:  10px; left: 20px; 
	    z-index: 1;
	    height: 160px; width: 160px; 
	}
	.line{ 
	    position: absolute; 
	    top: 5px; left: -40px; 
	    z-index: 2; 
	    height: 3px; width: 360px; 
	    background: url(http://www.iaxure.com/share/ewsm/images/index/line_u9.png) no-repeat; 
	    /*动画效果*/
	    animation: myScan 1s infinite alternate; 
	    -webkit-animation: myScan 1s infinite alternate; 
	}
	@keyframes  myScan{
	    from { top:5px; }
	    to { top: 170px; }
	}
	@-webkit-keyframes  myScan{
	    from { top:5px; }
	    to { top: 170px; }
	}
	</style>
</head>
<body>
    
		<div id="home" class="page" id="section-overview">
			<h1 class="page_title">小牙签@浙理</h1>
				<div class="status">
					<div>当前系统运行状态：截至${status.date}<br></div>
					<div>微信关注数：${status.wechatUserCount}<br></div>
					<div>用户数：${status.userCount}<br></div>
					<div>图书种类：${status.bookInfoCount}种<br></div>
					<div>用户藏书：${status.mineCount}种<br></div>
					<div>用户藏书合计：${status.mineQtyCount}册<br></div>
					<div>用户心愿单：${status.wishCount}册<br></div>
					<div>成交：${status.orderCount}册<br></div>
					<button class="weui_btn weui_btn_plain_primary" onclick="pureView('home');" style="width: 80%;">下一步</button><!-- window.location.href='#scanqrcode'; -->
				</div>
		</div>
		<div id="ua" class="page section-ua">
			<h1 class="page_title">用户协议：我也不清楚这到底算不算用户协议但既然我觉得算那就算吧</h1>
			<dir>
			<h3 class="title">1、很开心能得到您的关注与支持，如果有任何问题与意见都可以与我联系，也可以给订阅号留言或在BUG板块中提出</h3>
			<h3 class="title">2、希望大家在这里友善互助,避免过激言论与不当行为</h3>
			<h3 class="title">3、订阅号每天都有一次的消息推送机会，但请放心我们不会频繁而无意义骚扰大家，一盏清茶喜相逢，最重要的是开心<s>我懒起来我自己都怕</s></h3>
			<h3 class="title">4、本着审慎负责的态度，我们尽量避免收集个人尤其是隐私信息，诸如对微信二维码的要求实属难免，介意的话可以跳过</h3>
			<h3 class="title">5、请不要骂我们的美工，因为我<S>们</S>压根就没有美工，觉得UI丑的同学，嗯我觉得也挺丑的,不过如果你见过我们写给自己用的软件。。。<br></h3>
			</dir>
			<button class="weui_btn weui_btn_plain_primary" onclick="pureView('ua');" id="userAgreement">非常同意</button>
		</div>

		<div id="scanqrcode"class="page section-qrcode">
			<h1 class="page_title">个性化配置|识别微信名片 <br></h1>
			<div class="code-bg">
		    		<div class="line"></div>
		    		<img id="qrcode" src="<%=scdPath%>images/qrcode_yaqian_samll.jpg" alt="">
				</div>

			<div class="weui_cells_title" style="margin-top: 1.77em;margin-bottom: 2.3em;padding-left: 15vw;padding-right: 10vw;color: #242D24;font-size: 1em;font-weight: 400;line-height: 1.7em;">学弟学妹们对你提供的教材感兴趣时将扫描此二维码与你联系</div>
			<button class="weui_btn weui_btn_plain_primary" id="scanWechatQRCode" style="width: 80%;">识别我的二维码名片</button>
			<button class="weui_btn weui_btn_plain_primary" onclick="pureView('scanqrcode');"  style="width: 80%;">下一步</button>
		</div>
			
		<div id="final" class="page section-detail">
            <div class="hd">
                <h1 class="page_title">个性化配置|Sign Up</h1>
            </div>
            <div class="bd">
            <div class="weui_cells_title">以及一丢丢其它个性化信息</div>
                <div class="weui_cells weui_cells_form">
                    <div class="weui_cell">
                        <div class="weui_cell_hd"><label class="weui_label">昵称:</label></div>
                        <div class="weui_cell_bd weui_cell_primary">
                            <input class="weui_input" id="nickName" type="text" onkeyup="value=value.replace(/[`~!@#$^&*()=|{}':;',\[\].<>/?~！……&*（）&mdash;—|{}【】‘；：”“']/g,'')" placeholder="红红火火恍恍惚惚" onchange='checkNameInput();'/>
                        </div>
                    </div>
                 <div class="weui_cells">
                 	<div class="weui_cell weui_cell_select weui_select_after">
                        <div class="weui_cell_hd">性别:</div>
                        <div class="weui_cell_bd weui_cell_primary">
                            <select class="weui_select" id="gender">
                                <option value="0">女</option>
                                <option value="1">男</option>
                                <option value="2">保密</option>
                            </select>
                        </div>
                    </div>
                    <div class="weui_cell weui_cell_select weui_select_after">
                        <div class="weui_cell_hd">年级:</div>
                        <div class="weui_cell_bd weui_cell_primary">
                            <select class="weui_select" id="enrolYear">
                                <option value="2015">2015</option>
                                <option value="2014">2014</option>
                                <option value="2013">2013</option>
                                <option value="2012">2012</option>
                                <option value="2011">2011</option>
                            </select>
                        </div>
                    </div>
                    <div class="weui_cell weui_cell_select weui_select_after">
                        <div class="weui_cell_hd">学院:</div>
                        <div class="weui_cell_bd weui_cell_primary">
                            <select class="weui_select" id="college" onchange='cascadeSubject("major");'>
									<option value="c">材料与纺织学院</option>
									<option value="f">法政学院</option>
									<option value="z">服装学院</option>
									<option value="a">机械与自动控制学院</option>
									<option value="j">建筑工程学院</option>
									<option value="g">经济管理学院</option>
									<option value="l">理学院</option>
									<option value="q">启新学院</option>
									<option value="s">生命科学学院</option>
									<option value="w">外国语学院</option>
									<option value="b">文化传播学院</option>
									<option value="x">信息学院</option>
									<option value="y">艺术与设计学院</option>
									<option value="p">魔法学院</option>
								</select>
                        </div>
                    </div> 
                    <div class="weui_cell weui_cell_select weui_select_after">
                        <div class="weui_cell_hd">专业:</div>
                        <div class="weui_cell_bd weui_cell_primary">
                            <select class="weui_select" id="major">	
								</select>
                        </div>
                    </div> 
                     <div class="weui_cell weui_cell_select weui_select_before">
                      	<div class="weui_cell_hd">辅修:</div>
                        <div class="weui_cell_bd weui_cell_primary">
                            <select class="weui_select" id="minorCollege" onchange='cascadeSubject("minor");'>
                            		<option value="nothanks">--学院--</option>
									<option value="c">材料与纺织学院</option>
									<option value="f">法政学院</option>
									<option value="z">服装学院</option>
									<option value="a">机械与自动控制学院</option>
									<option value="j">建筑工程学院</option>
									<option value="g">经济管理学院</option>
									<option value="l">理学院</option>
									<option value="q">启新学院</option>
									<option value="s">生命科学学院</option>
									<option value="w">外国语学院</option>
									<option value="b">文化传播学院</option>
									<option value="x">信息学院</option>
									<option value="y">艺术与设计学院</option>
								</select>
                            <select class="weui_select" id="minor" onchange='checkMinor();'>
                            		<option value="">--专业--</option>
								</select>
                        </div>
                    </div> 
                 </div>
                </div>
                <div class="weui_cells_tips">确认无误后即可提交</div>
                <div class="weui_btn_area">
                    <a href="javascript:signupFun();" id="submit" class="weui_btn weui_btn_plain_primary">提交</a>
                </div>
            </div>
		</div>

<!--BEGIN toast-->
<div id="toast" style="display: none;">
    <div class="weui_mask_transparent"></div>
    <div class="weui_toast">
        <i class="weui_icon_toast"></i>
        <p class="weui_toast_content">跳转中/ - \ |</p>
    </div>
</div>
<div id="loadingToast" class="weui_loading_toast" style="display:none;">
    <div class="weui_mask_transparent"></div>
    <div class="weui_toast">
        <div class="weui_loading">
            <div class="weui_loading_leaf weui_loading_leaf_0"></div>
            <div class="weui_loading_leaf weui_loading_leaf_1"></div>
            <div class="weui_loading_leaf weui_loading_leaf_2"></div>
            <div class="weui_loading_leaf weui_loading_leaf_3"></div>
            <div class="weui_loading_leaf weui_loading_leaf_4"></div>
            <div class="weui_loading_leaf weui_loading_leaf_5"></div>
            <div class="weui_loading_leaf weui_loading_leaf_6"></div>
            <div class="weui_loading_leaf weui_loading_leaf_7"></div>
            <div class="weui_loading_leaf weui_loading_leaf_8"></div>
            <div class="weui_loading_leaf weui_loading_leaf_9"></div>
            <div class="weui_loading_leaf weui_loading_leaf_10"></div>
            <div class="weui_loading_leaf weui_loading_leaf_11"></div>
        </div>
        <p class="weui_toast_content">数据递送中</p>
    </div>
</div>
<!--END toast-->
<!--BEGIN dialog-->
<div class="weui_dialog_alert" id="dialogonly" style="display: none;">
    <div class="weui_mask"></div>
    <div class="weui_dialog">
        <div class="weui_dialog_hd" id="dialogonlyhd"><strong class="weui_dialog_title">Sorry</strong></div>
        <div class="weui_dialog_bd" id="dialogonlybd">出错了(´◔ ‸◔')</div>
        <div class="weui_dialog_ft">
            <a href="javascript:pureView('dialogonly');" class="weui_btn_dialog primary">(◔ ౪◔)</a>
        </div>
    </div>
</div>

<!--END dialog-->
</div>     
       
<script type="text/javascript" >

window.onload = initSelect;
var namepattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）&mdash;—|{}【】‘；：”“'。，、？]");
var qrcode = new QRCode(document.getElementById("qrcode"), {width : 244,height : 244});
var wechatqrcode;

function initSelect(){
	cascadeSubject("major");
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

//根据学院动态更新专业
function cascadeSubject(majormin){
	var subject_a = ["aa:材料成型及控制工程","ab:材料成型及控制工程(3+2)","ac:测控技术与仪器","ad:测控技术与仪器(3+2)","ae:电气工程及其自动化","af:电气类","ag:工业工程","ah:过程装备与控制工程","ai:过程装备与控制工程(3+2)","aj:机械电子工程","ak:机械类","al:机械设计制造及其自动化","am:机械设计制造及其自动化(全英文)","an:自动化"],
	subject_b = ["ba:传播学","bb:汉语言文学","bc:汉语言文学(3+2)"],
subject_c = ["ca:包装工程","cb:材料科学与工程","cc:材料类","cd:纺织工程","ce:纺织工程(3+2)","cf:纺织工程(卓越计划)","cg:纺织类","ch:纺织品设计","ci:非织造材料与工程","cj:轻化工程","ck:轻化工程(3+2)","cl:轻化工程(纺织贸易与检测全英文)","cm:轻化工程(染整工艺与设计)","cn:轻化工程(生态纺织化学品)","co:轻化工程(造纸方向)","cp:轻化工程(卓越计划)"],
subject_f = ["fa:法学","fb:公共管理类","fc:公共事业管理","fd:公共事业管理(3+2)","fe:行政管理","ff:行政管理(3+2)","fg:社会工作","fh:社会工作(3+2)"],
subject_g = ["ga:电子商务","gb:电子商务类","gc:工商管理","gd:工商管理(3+2)","ge:工商管理(全英文)","gf:工商管理类","gg:管理科学与工程类","gh:国际经济与贸易","gi:国际经济与贸易(全英文)","gj:会计学","gk:金融学","gl:经济统计学","gm:经济学","gn:经济与贸易类","go:人力资源管理","gp:市场营销","gq:统计学","gr:信息管理与信息系统"],
subject_j = ["ja:风景园林","jb:风景园林(3+2)","jc:工程管理","jd:建筑环境与能源应用工程","je:建筑环境与能源应用工程(3+2)","jf:建筑环境与设备工程","jg:建筑学","jh:土木工程"],
subject_l = ["la:材料化学","lb:化学类","lc:数学类","ld:数学与应用数学","le:信息与计算科学","lf:信息与计算科学(3+2)","lg:应用化学","lh:应用物理学","li:应用心理学","lj:应用心理学(3+2)"],
subject_p = ["pp:面条神学"],
subject_q = ["qa:材化生实验班","qb:机电实验班","qc:经济管理实验班","qd:理工实验班","qe:信息电子实验班","cb:材料科学与工程","ae:电气工程及其自动化","xa:电子信息工程","xb:电子信息科学与技术","ag:工业工程","gh:国际经济与贸易","gj:会计学","aj:机械电子工程","al:机械设计制造及其自动化","xd:计算机科学与技术","gk:金融学","gm:经济学","cj:轻化工程","sa:生物技术","se:生物制药","xg:通信工程","jh:土木工程","lg:应用化学"],
subject_s = ["sa:生物技术","sb:生物技术(全英文)","sc:生物技术(生物工程)","sd:生物技术(生物制药)","se:生物制药"],
subject_w = ["wa:日语","wb:英语"],
subject_x = ["xa:电子信息工程","xb:电子信息科学与技术","xc:电子信息类","xd:计算机科学与技术","xe:计算机科学与技术(全英文)","xf:数字媒体技术","xg:通信工程"],
subject_y = ["ya:产品设计","yb:动画","yc:工业设计","yd:广告学","ye:环境设计","yf:美术学","yg:设计学类","yh:视觉传达设计","yi:视觉传达设计(广告设计)","yj:艺术设计(产品设计)","yk:艺术设计(环境艺术设计)","yl:艺术设计(家具设计)","ym:艺术设计(视觉传达设计)"],
subject_z = ["za:表演(人物形象设计)","zb:表演(时装表演艺术)","zc:产品设计(纺织品艺术设计)","zd:服装设计与工程","ze:服装设计与工程(全英文)","zf:服装设计与工程(中美合作)","zg:服装设计与工程(卓越计划)","zh:服装与服饰设计","zi:服装与服饰设计(服饰品设计)","zj:服装与服饰设计(服装设计中美合作 )","zk:服装与服饰设计(服装艺术设计)","zl:服装与服饰设计(人物形象设计)","zm:服装与服饰设计(设计与营销)","zn:服装与服饰设计(设计与营销)(3+2)","zo:服装与服饰设计(设计与营销中美合作)","zp:艺术设计(服饰品设计与营销)","zq:艺术设计(服装设计中美合作)","zr:艺术设计(服装艺术设计)","zs:艺术设计(染织艺术设计)","zt:艺术设计(人物造型)","zu:艺术设计(人物造型设计)","zv:艺术设计(设计与营销中美合作)","zw:艺术设计(时装表演及营销)"];
	if ( majormin === "major"){//主修专业选择
		var subjectArr = eval("subject_" + document.getElementById("college").options[document.getElementById("college").options.selectedIndex].value);	
		var subArr = [];
		document.getElementById('major').options.length = 0;
		for( var i = 0; i < subjectArr.length; i++ ){
			subArr = subjectArr[i].split(":");
	        document.getElementById('major').options[i] = new Option(subArr[1],subArr[0]); 
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

//检查辅修专业是否与主修重复
function checkMinor() {
	var minor = document.getElementById("minor").options[document.getElementById("minor").options.selectedIndex].value;
	var major = document.getElementById("major").options[document.getElementById("major").options.selectedIndex].value;
	if (minor === major) {
		document.getElementById("dialogonly").style.display = "block";
		document.getElementById("dialogonlyhd").innerHTML="NO!NO!NO!";
		document.getElementById("dialogonlybd").innerHTML="与主修专业重复了哦！不必再选择啦";
		document.getElementById('minor').options[0].selected = true;
	}
}

function checkNameInput() {
	var userName = eval(document.getElementById("nickName")).value.replace(/[s　]*/g,"");//删除字符串的全部空格（包括全角空格）
	if (userName.length > 20) {
		document.getElementById("dialogonly").style.display = "block";
		document.getElementById("dialogonlyhd").innerHTML="昵称太长";
		document.getElementById("dialogonlybd").innerHTML="20字符以内好的吧，不好也得好，不然我截了啊";
	}
}

//提交注册信息
function signupFun() {
	var userName = document.getElementById("nickName").value.replace(/[s　]*/g,"").trim().replace(namepattern,"").slice(0,20);
	if (userName.length === 0) {
		document.getElementById("dialogonly").style.display = "block";
		document.getElementById("dialogonlyhd").innerHTML="请填写昵称";
		document.getElementById("dialogonlybd").innerHTML="比如，八百标兵奔北坡北坡八百炮兵炮标兵怕碰炮兵炮炮兵怕把标兵碰...";
		return;
	}
	document.getElementById("loadingToast").style.display = " block";
	var userGender = document.getElementById("gender").options[document.getElementById("gender").options.selectedIndex].value;
	var userMajor = document.getElementById("major").options[document.getElementById("major").options.selectedIndex].value;
	var userMinor = document.getElementById("minor").options[document.getElementById("minor").options.selectedIndex].value;
	var userCollege = document.getElementById("college").options[document.getElementById("college").options.selectedIndex].value;
	var userAdmisyr = document.getElementById("enrolYear").options[document.getElementById("enrolYear").options.selectedIndex].value;
	var jsonUser = {isSaveUser:true,nickName:userName,gender:userGender,major:userMajor,minor:userMinor,college:userCollege,enrolYear:userAdmisyr,wcQrcode:wechatqrcode}; 
	var strUser = JSON.stringify(jsonUser); 
	var xmlhttp;
	if(window.XMLHttpRequest){      
		    xmlhttp = new XMLHttpRequest();
            xmlhttp.open('POST',"<%=basePath%>api/users", true);
			xmlhttp.setRequestHeader("Content-type","application/json");
            xmlhttp.send(strUser);
            xmlhttp.onreadystatechange = function(){
	                if(xmlhttp.readyState == 4  && xmlhttp.status == 200){		
							var resJson = eval("(" + xmlhttp.responseText + ")");
							if (resJson.statuscode == 201) {
								document.getElementById("loadingToast").style.display = "none";
								document.getElementById("toast").style.display = "block";
								window.location.replace("<%=basePath%>jump");
							}else{
								document.getElementById("loadingToast").style.display = "none";
								document.getElementById("dialogonly").style.display = "block";
								document.getElementById("dialogonlyhd").innerHTML="Sorry,";
								document.getElementById("dialogonlybd").innerHTML="很抱歉,错误码：" + resJson.statuscode + "错误详情：" + resJson.errmsg;
							}
		                }
	            } 
		}
}
//检查扫码结果之格式
function checkQRCodeFun(qrcodeStr) {
	if (qrcodeStr.match(/^http:\/\/weixin.qq.com\/r\/[\w-_]{20}$/g)) {
		wechatqrcode = qrcodeStr.substring(23);
		//qrcode.clear();
		//qrcode.makeCode(qrcodeStr);
		window.location.href='#final';
		pureView('scanqrcode');
	}else {
		document.getElementById("dialogonly").style.display = "block";
		document.getElementById("dialogonlyhd").innerHTML="二维码格式不正确";
		document.getElementById("dialogonlybd").innerHTML="很抱歉您提供的二维码不正确，扫码结果为：" + qrcodeStr;
	}
}

function pureView(elementid){
	document.getElementById(elementid).style.display = "none";
}
</script>
</body>
</html>
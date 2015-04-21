<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ebk" uri="/tld/lvmama-ebk-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title >驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
</head>

<body id="body_index" class="ebooking_index">	
	<jsp:include page="../common/head.jsp"></jsp:include>
<!--首页内容开始-->
<div class="breadcrumb">
	<ul class="breadcrumb_nav">
		<li class="home">首页</li>
    </ul>
    <div class="nodealTips">
    	<p class="msgWarn">网页提醒：<span id="flash">
    	    <img id="flashIMG" src="http://pic.lvmama.com/img/ebooking/shakeOn.gif" /><i id="flashSPAN" class="msgOn">已开启</i>（<a id="flashA" href="javascript:turnOff()"  >关闭</a>）
    	</span></p> 
    	<p class="msgWarn">弹窗提醒：<span id="flash">
    	    <img id="flashIMGTipShow" src="http://pic.lvmama.com/img/ebooking/shakeOn.gif" /><i id="flashSPANTipShow" class="msgOn">已开启</i>（<a id="flashATipShow" href="javascript:turnTipShowOff()"  >关闭</a>）
    	</span></p> 
        <p class="soundWarn" style="display: none;">声音提醒：<span><img src="http://pic.lvmama.com/img/ebooking/soundff.gif" /><i class="soundOff"></i>未开启（<a href="javascript:void(0)">开启</a>）</span></p>
    </div>
    <div class="clearBoth"></div>
</div>
<!--smallNavEND-->

<!--您需要处理的订单-->

<div class="middle">
   <ebk:perm permissionId="8" >
	<div class="middle_l">
		<ebk:perm permissionId="13,14" >
	    	<div class="frame">
	           	<h3><b>线路类信息</b></h3>
	            <div class="middle_l_b">
<%-- 			         <ebk:perm permissionId="13" >
		                <ul class="middle_l_b_ul_l">
		                    <li class="ul_word"><b>酒店类</b></li>
		                    <a href="${contextPath }/ebooking/task/confirmTaskList.do?certificateStatus=CONFIRM"><li class="ul_bottom">
		                        <span>待处理订单<b>${confirmHotelOrderCount }</b>笔</span>
		                    </li></a>
		                </ul>
	                 </ebk:perm> --%>
		             <ebk:perm permissionId="14" >
	                	<!-- <div class="middle_l_b_div"></div> 
		                <ul class="middle_l_b_ul_r">-->
		                <ul class="middle_l_b_ul_l">
		                    <li class="ul_word"><b>线路类</b></li>
		                    <a href="${contextPath }/ebooking/task/confirmRouteTaskList.do?certificateStatus=CONFIRM"><li class="ul_bottom">
		                        <span>待处理订单<b>${confirmRouteOrderCount }</b>笔</span>
		                    </li></a>
		                </ul>
		             </ebk:perm>
	           	</div>
	        </div>
        </ebk:perm>
      	<ebk:perm permissionId="3" >
	        <div class="frame">
	        	<h3><b>门票类信息</b></h3>
	            <div class="middle_l_b2">
	            	<h4>今日概况:</h4>
	                <ul class="middle_l_b2_ul1">
	                	<li>订购票数：<span><b>${tongJI[0]}</b></span></li>
	                    <li>已取票数：<span><b>${tongJI[1]}</b></span></li>
	                    <li>待取票数：<span><b>${tongJI[2]}</b></span></li>
	                </ul>
	                <div class="middle_l_b_div"></div>
	                <ul class="middle_l_b2_ul2">
	                	<li >预计游玩人数：<span><b>${tongJI[3]}</b></span></li>
	                    <li >&nbsp;&nbsp;已游玩人数：<span><b>${tongJI[4]}</b></span></li>
	                    <li >&nbsp;&nbsp;未游玩人数：<span><b>${tongJI[5]}</b></span></li>
	                </ul>
	            </div>
	        </div>
      	</ebk:perm>
    </div>
    </ebk:perm>
	<ebk:perm permissionId="11" >
    <div class="middle_r">
    	<h3><b>公告</b><a href="${contextPath }/announcement/ebkAnnouncementList.do">更多>></a></h3>
        <ul>
	     <s:iterator value="ebkAnnouncementList" var="item">
        	<li>[<s:date name="beginDate" format="yyyy-MM-dd"/>] <a title='<s:property value="title" />' href="${contextPath}/announcement/ebkAnnouncementList.do"><s:property value="title" /></a></li>
        </s:iterator>
        </ul>
    </div>
    </ebk:perm>	
</div>
<!--公共尾部-->
<div class="clearBoth"></div>
	<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>
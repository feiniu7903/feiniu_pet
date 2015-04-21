<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>

</head>
<body id="body_ggxx">
<jsp:include page="../common/head.jsp"></jsp:include>
<div class="breadcrumb">
	<ul class="breadcrumb_nav">
		<li class="home">首页</li>
    	<li>公告信息</li>
    </ul>
</div>
<!--以上是公用部分-->
<!--订单处理开始-->
<dl class="order_nav order_nav_js">
	<dt>公告列表</dt>
</dl>
<div class="gonggao">
    <ul class="gonggao_list">
         <s:iterator value="ebkAnnouncementPage.items" var="item">
			<li>
				<div class="gonggao_list_top">
                	<h5 class="gonggao_list_l"><s:property value="title" /></h5>
               	 	<span class="gonggao_list_r dj_bg_1"><s:date name="beginDate" format="yyyy-MM-dd HH:mm"/></span>
            	</div>
            	<div class="gonggao_main">
            		<p>${item.content}</p>
           	 		<s:if test="haveAttachment">
           	 			<b>普通附件</b><span class="c_gray"> （已通过杀毒引擎扫描）</span>
            		<h6><s:property value="attachmentName" /></h6>
                	<a class="download" href="${contextPath}/announcement/downloadAnnouncementAttachment.do?attachment=<s:property value="attachment"/>">下载</a>
           	 		</s:if>	
           	 	</div>
			</li>
	  </s:iterator>
    </ul>
    <div>
    	<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pageSupplier(ebkAnnouncementPage,'')"/>
    </div>
</div>
<!--公用底部-->
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>

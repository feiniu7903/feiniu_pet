<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="#request.hideCss==null">
<link href="<%=request.getContextPath()%>/style/houtai.css" rel="stylesheet" type="text/css" />
</s:if>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ui/jquery-ui-1.8.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.jsonSuggest.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/remoteUrlLoad.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.showLoading.min.js"></script>
<link href="<%=request.getContextPath()%>/themes/base/showLoading.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/suggest/jquery.suggest.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/base/jquery.ui.all.css" rel="stylesheet" type="text/css" />

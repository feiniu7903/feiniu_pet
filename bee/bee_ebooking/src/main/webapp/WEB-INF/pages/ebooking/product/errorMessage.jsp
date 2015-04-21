<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
<div id="errorMessageDiv"  class="cuowu_box">
    <h2><s:if test="null!=errorMessage"><s:property value="errorMessage"/></s:if><s:else>系统异常，无法加载信息</s:else></h2>
</div>
<script type="text/javascript">
	$("#errorMessageDiv").live("load",function(){$(this).removeClass("cuowu_box");});
</script>
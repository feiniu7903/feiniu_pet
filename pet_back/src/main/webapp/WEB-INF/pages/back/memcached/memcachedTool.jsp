<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/perm/perm.css" />
	<title>memcached工具</title>
	<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
	<link rel="stylesheet" type="text/css" href="${basePath}/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="${basePath}/themes/default/icon.css" />
	<script type="text/javascript" src="${basePath}/js/base/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath}/js/base/jquery.form.js"></script>
</head>
<body>

<form method="post" action="<%=request.getContextPath() %>/pub/memcachedTool!clearMemcached.do" id="clearMemcached">
<ul class="gl_top">
	<li><a>《缓存管理》</a></li>
	<li>请输入缓存key：<input name="memcachedKey" value="" size="50"/></li>
	<li><input name="submit" value="清除缓存" type="submit" > 非专业人士请勿擅动</li> 			
</ul>
<ul class="gl_top">
	<li>&nbsp;</li>
</ul>
</form>
<ul class="gl_top">
	<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
	<form method="post" action="${basePath}/pub/memcachedTool!deleteSeckillMecached.do">
		<li>请输入秒杀缓存key:<input name="memcachedKey" value="" size="50"/></li>
		<li><input name="submit" value="清除秒杀缓存" type="submit"/></li>
	</form>
</ul>
<div class="tab_top"></div>
<table class="gl_table" cellspacing="0" cellpadding="0">
 	     <ul class="gl_top">
 		<li>频道：
			<select id="preKey" >
				<option value="">请选择频道</option>
				<s:iterator value="memcachedPrefixKeyPindaoMap" var="var" status="st">
				  <option value="${value}">${key}</option>
				</s:iterator>
 			 </select> 分站:
 			<select id="placeId" >
				<option value="">请选择分站</option>
				<s:iterator value="placeIdMap" var="var" status="st">
				  <option value="${value}">${key}</option>
				</s:iterator>
 			 </select>
		<li><Button  onclick='deleteMem($("#preKey").val(),$("#placeId").val())' >清除缓存</Button></li> 			
		</ul>
 </table>
<p></p>
<p></p>
<script type="text/javascript">
function clearMem(key){
	if(key!=""||key!=null){
	 if(!confirm("是否将此缓存删除?")){
		return false;
	 }
	var param = {"memcachedKey":key};
			$.ajax({type:"POST", url:"<%=request.getContextPath() %>/pub/memcachedTool!clearMemcached.do", data:param, dataType:"json", success:function (json) {
			 		alert("已经执行删除操作");
			 		if(json.flag=="true"){
			 			window.location.reload(true);
					}else{
			 			window.location.reload(true);
			 		}
			}});
	}else{
		alert("请输入缓存数据的key");
	}
}


function deleteMem(prekey,placeid){
	if(prekey!=""&&prekey!=null&&placeid!=""&&placeid!=null){
	 if(!confirm("是否将此缓存删除?")){
		return false;
	 }
	 var key=prekey+placeid;
	var param = {"memcachedKey":key};
			$.ajax({type:"POST", url:"<%=request.getContextPath() %>/pub/deleteMemcached.do", data:param, dataType:"json", success:function (json) {
			 		if(json.flag=="true"){
			 			alert("已经执行删除操作");
					}else if(json.flag=="false"){
						alert("执行删除操作失败");
			 		}else{
			 			alert("这个"+key+"值为null");
			 		}
			}});
	}else{
		alert("请选择频道或者分站!");
	}
}
</script>
</body>
</html>

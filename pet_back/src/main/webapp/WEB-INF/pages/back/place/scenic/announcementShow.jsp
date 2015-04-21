<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>公告管理</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script type="text/javascript" src="${basePath}/js/base/jquery.form.js"></script>
<script charset="utf-8" src="${basePath}/kindeditor-4.0.2/kindeditor-min.js"></script>
<script charset="utf-8" src="${basePath}/kindeditor-4.0.2/lang/zh_CN.js"></script>
</head>
<body>
<div id="win_activi" style="display: none"></div>

<table class="p_table">
 <tr>
    <th>活动主题</th>
    <th>开始时间</th>
    <th>结束时间</th>
    <th>操作</th>
  </tr>
  <s:iterator value="placeActivityList">
   <tr>
    <td><s:property value="title"/></td>
    <td><s:date name="startTime" format="yyyy-MM-dd hh:mm:ss"/></td>
    <td><s:date name="endTime" format="yyyy-MM-dd hh:mm:ss"/></td>
    <td><a href="javascript:placeActivityAdd('${basePath}/place/placeActivityEdit.do?placeActivityId=${placeActivityId}&stage=${stage}&placeId=${placeId}');">编辑</a>&nbsp;&nbsp;
    <a href="javascript:void(0)" onclick="javascript:delActivity('${placeActivityId}');">删除</a></td>
  </tr>
  </s:iterator>
</table>
    <s:if test='flag=="Y"'>
    	<p class="tc mt10">
    		<input type="button" id="newActivity" class="btn btn-small w3" onclick="javascript:placeActivityAdd('${basePath}/place/placeActivityAdd.do?placeId=${placeId}&stage=${stage}');" value="新增" />
    	</p>
    </s:if>
    <s:else>
    	<p class="tc mt10">
    		<input type="button" disabled="true" id="newActivity" class="btn btn-small w3" value="活动数已经达到最大数3个，不能添加！" />
    	</p>
    </s:else>

	<div id="activityAdd" style="display: none"></div>    
</body>

	<script type="text/javascript">
	function reloadActivity(placeId){
		$("#popDiv").html("");
		$("#popDiv").load("${basePath}/place/placeActivity.do?placeId="+placeId+"&stage=${stage}" + "&math=" + Math.random(), function() {
			$( "#popDiv" ).dialog( "close" );
			$( "#popDiv" ).dialog( "open" );
	     });
	};
	function delActivity(id){
		if(confirm("确认要删除此活动吗!")){
			$("#popDiv").html("");
			$("#popDiv").load("${basePath}/place/placeActivityDel.do?placeActivityId="+id+"&placeId=${placeId}&stage=${stage}" + "&math=" + Math.random(), function() {
				$( "#popDiv" ).dialog( "close" );
				$( "#popDiv" ).dialog( "open" );
		     });
		}
	}
	function placeActivityAdd(url){
		 
		$("#activityAdd").html("");
		$("#activityAdd").load(url + "&math=" + Math.random(), function() {
		$("#activityAdd").show();              
	     });
	};
	
 </script>
</head>
</html>
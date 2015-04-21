<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>科捷文字管理</title>
		<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
		
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
</head>
<body>

		<div id="popDiv" style="display: none"></div>
		<div class="ui_title">
			<ul class="ui_tab">
				<li class="active"><a href="#">科捷文字管理</a></li>
			</ul>
		</div>
		
		<div class="iframe-content">
			<div class="p_box">
				<form action="<%=basePath%>/pub/kejet/index.do" method="post" id="searchForm">
					<table class="p_table form-inline" width="100%">
						<tr>
							<td class="p_label">标识号:</td>
							<td>
								<s:textfield name="comKeJetWord.id"/>
							</td>
							<td class="p_label">描述:</td>
							<td>
								<s:textfield name="comKeJetWord.description"/>	
							</td>
							
						</tr>														
					</table>
					<p class="tc mt20">
						<button class="btn btn-small w5" type="button" onclick="search()">查询</button>
						<button class="btn btn-small w5" type="button" onclick="add()">新增</button>
					</p>
				</form>
				<br/>
				<form method="post">
					<table class="p_table form-inline" width="100%">
						<tr>
							<td class="p_label">缓存的键值:</td>
							<td>
								<s:textfield name="comKeJetWordKey"/>
							</td>
							<td>
								<button class="btn btn-small w5" type="button" onclick="refreshMemKey()">清空缓存</button>
								<button class="btn btn-small" type="button" onclick="refreshAllMem()">清空所有科捷广告系统的缓存</button>	
							</td>
						</tr>														
					</table>
				</form>				
			</div>
			<div class="p_box">
				<table class="p_table table_center">
					<tr>
						<th>标识号</th>
						<th>代码</th>
						<th>描述</th>
						<th width="325">操作</th>
					</tr>										
					<s:iterator value="comKeJetWordList" var="word">
						<tr>
							<td>${id }</td>
							<td><s:property value="code" /></td>
							<td>${description }</td>
							<td>
								<button class="btn btn-small w5" type="button" onclick="edit(${id})">修改</button>
								<button class="btn btn-small w5" type="button" onclick="del(${id})">删除</button>
								<button class="btn btn-small w5" type="button" onclick="refreshMem(${id})">清空缓存</button>
							</td>	
						</tr>
					</s:iterator>	
				</table>
			</div>
		</div>
		<script type="text/javascript">
			function search() {
		  		$("#comKeJetWord_id").val($.trim($("#comKeJetWord_id").val()));
		  		if ($("#comKeJetWord_id").val() != "" && isNaN($("#comKeJetWord_id").val())) {
		  			alert("请输入正确的标识号");
		  			$("#comKeJetWord_id").focus();
		  			return; 
		  		}
		  		$("#searchForm").submit();
		  	}
			
			function add() {
				$("#popDiv").load("<%=basePath%>/pub/kejet/edit.do?_=" + (new Date).getTime(), function() {
					$(this).dialog({
		            	modal:true,
		            	title:"新增广告描述文字",
		            	width:550,
		            	height:400
		            });
		        });		  
			}
			
			function edit(id) {
				$("#popDiv").load("<%=basePath%>/pub/kejet/edit.do?_=" + (new Date).getTime() + "&comKeJetWord.id=" + id, function() {
					$(this).dialog({
		            	modal:true,
		            	title:"编辑广告描述文字",
		            	width:550,
		            	height:400
		            });
		        });		  
			}
			
			function del(id) {
				if (confirm("您真的确认删除此广告描述文字吗?")) {
					$.ajax({
		    	 		url: "<%=basePath%>/pub/kejet/del.do",
						type:"post",
		    	 		data: {
								"comKeJetWord.id":id,
								"date":(new Date).getTime()
							},
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
		    	 		dataType:"json",
		    	 		success: function(result) {
							if (result.success) {
								alert("删除成功");
								document.location.reload();
							} else {
								alert("数据丢失，操作失败");
							}
		    	 		}
		    		});					
				}  
			}
			
			function refreshMem(id) {
				$.ajax({
	    	 		url: "<%=basePath%>/pub/kejet/refreshMem.do",
					type:"post",
	    	 		data: {
							"comKeJetWord.id":id,
							"date":(new Date).getTime()
						},
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
	    	 		dataType:"json",
	    	 		success: function(result) {
						if (result.success) {
							alert("清空成功");
						} else {
							//alert("数据丢失，操作失败");
						}
	    	 		}
	    		});				
			}
			
			function refreshMemKey() {
				$("#comKeJetWordKey").val($.trim($("#comKeJetWordKey").val()));
		  		if ($("#comKeJetWordKey").val() == "") {
		  			alert("请输入正确的缓存的键值");
		  			$("#comKeJetWordKey").focus();
		  			return; 
		  		}
				$.ajax({
	    	 		url: "<%=basePath%>/pub/kejet/refreshMemKey.do",
					type:"post",
	    	 		data: {
							"refreshMemKey":$("#comKeJetWordKey").val(),
							"date":(new Date).getTime()
						},
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
	    	 		dataType:"json",
	    	 		success: function(result) {
						if (result.success) {
							alert("清空成功");
						} else {
							//alert("数据丢失，操作失败");
						}
	    	 		}
	    		});
			}
			
			function refreshAllMem() {
				if (confirm("您真的确认要清空所有科捷广告系统?请在迫不得已情况下使用，否则会导致系统崩溃!")) {
					$.ajax({
		    	 		url: "<%=basePath%>/pub/kejet/refreshAllMem.do",
						type:"post",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
		    	 		dataType:"json",
		    	 		success: function(result) {
							if (result.success) {
								alert("清空成功");
							} else {
								//alert("数据丢失，操作失败");
							}
		    	 		}
		    		});					
				}	
			}
		</script>
	</body>
</html>

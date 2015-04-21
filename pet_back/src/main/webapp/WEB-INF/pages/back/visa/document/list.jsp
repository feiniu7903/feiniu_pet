<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>签证材料列表</title>
		<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
		<s:include value="/WEB-INF/pages/pub/suggest.jsp" />
		<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
		
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
	</head>
	<body>
		<div id="addVisaApplicationDocumentDiv" style="display: none"></div>
		<div id="viewVisaApplicationDocumentDiv" style="display: none"></div>
		<div id="copyVisaApplicationDocumentDiv" style="display: none"></div>
		<div class="ui_title">
			<ul class="ui_tab">
				<li class="active"><a href="#">签证所需材料列表</a></li>
			</ul>
		</div>
		
		<div class="iframe-content">
			<div class="p_box">
				<form action="<%=basePath%>/visa/document/index.do" method="post">
					<table class="p_table form-inline" width="100%">
						<tr>
							<td class="p_label">国家</td>
							<td>
								<input type="text" name="country" id="country"/>	
							</td>
							<td class="p_label">签证类型：</td>
							<td>
								<s:select name="visaType"  list="#{'':'-- 请选择  --','GROUP_LEISURE_TORUS_VISA':'团体旅游签证','PERSONAL_VISA':'个人旅游签证','BUSINESS_VISA':'商务签证','VISIT_VISA':'探亲访友签证','STUDENT_VISA':'留学签证','REGISTER_VISA':'签注','MATCH_VISA':'赛事签证'}"/>
							</td>
							<td class="p_label">送签城市：</td>
							<td>
								<s:select name="city" list="#{'':'-- 请选择  --','SH_VISA_CITY':'上海送签','BJ_VISA_CITY':'北京送签','GZ_VISA_CITY':'广州送签','CD_VISA_CITY':'成都送签','TJ_VISA_CITY':'天津送签','WH_VISA_CITY':'武汉送签','SY_VISA_CITY':'沈阳送签','REGION':'户籍所在地'}"/>
							</td>
							<td class="p_label">所属人群：</td>
							<td>
								<s:select name="occupation" list="#{'':'-- 请选择  --','VISA_FOR_EMPLOYEE':'在职人员','VISA_FOR_RETIRE':'退休人员','VISA_FOR_STUDENT':'在校学生','VISA_FOR_PRESCHOOLS':'学龄前儿童','VISA_FOR_FREELANCE':'自由职业者','VISA_FOR_ALL':'适用所有人员'}"/>
							</td>	
						</tr>			
					</table>
					<p class="tc mt20"><button class="btn btn-small w5" type="submit">查询</button>　<button id="addVisaApplicationDocumentBtn" class="btn btn-small w5" type="button">创建新材料</button></p>
				</form>
			</div>
			<div class="p_box">
				<table class="p_table table_center">
					<tr>
						<th>国家</th>
						<th>签证类型</th>
						<th>送签城市</th>
						<th>人群</th>
						<th>操作</th>
					</tr>
					<s:iterator value="pagination.items" var="document">
						<tr>
							<td>${document.country}</td>
							<td>${document.cnVisaType}</td>
							<td>${document.cnCity}</td>
							<td>${document.cnOccupation}</td>
							<td>
								<a href="javascript:viewDocument(${document.documentId});">明细</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="javascript:deleteDocument(${document.documentId});">删除</a>&nbsp;&nbsp;&nbsp;&nbsp; 
								<a href="javascript:copy(${document.documentId});">复制</a>&nbsp;&nbsp;&nbsp;&nbsp;  
								<a href="javascript:void(0)" class="showLogDialog" param="{'objectId':'${document.documentId}','objectType':'VISA_APP_DOCUMENT_TARGET'}">查看日志</a>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</s:iterator>
					<tr>
     					<td colspan="2" align="right">总条数：<s:property value="pagination.totalResultSize"/></td>
						<td colspan="3" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td>
    				</tr>
				</table>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		  $(function(){
		  	$("#country").jsonSuggest({
				url : basePath + "/visa/queryVisaCountry.do",
				maxResults : 10,
				width : 300,
				emptyKeyup : false,
				minCharacters : 1,
				onSelect : function(item) {
					$("#country").val(item.id);
				}
			}).change(function() {
				if ($.trim($(this).val()) == "") {
					$("#country").val("");
				}
			});
			  
			$("#addVisaApplicationDocumentBtn").click(function(){
				$("#addVisaApplicationDocumentDiv").load("<%=basePath%>/visa/document/add.do",function() {
            		$(this).dialog({
            			modal:true,
            			title:"新增签证所需材料",
            			width:550,
            			height:250
                	});
            	});
			});
		  });
		
		function viewDocument(documentId) {
			$("#viewVisaApplicationDocumentDiv").load("<%=basePath%>/visa/document/view.do?date=" + (new Date).getTime() + "&documentId=" + documentId, function() {
				$(this).dialog({
            		modal:true,
            		title:"编辑签证所需材料明细",
            		width:750,
            		height:650
                });
            });			
		}

		function deleteDocument(documentId){
			var flag = window.confirm("您确定需要删除此签证资料(包含此资料下的所有资料明细)吗？此操作将不可恢复。"); 	
			if (flag) {
				$.ajax({
        	 		url: "<%=basePath%>/visa/document/del.do",
					type:"post",
        	 		data: {"documentId":documentId},
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
        	 		dataType:"json",
        	 		success: function(result) {
        	 			document.location.reload();
        	 		}
        		});
			}
		}

		function copy(documentId) {
			$("#addVisaApplicationDocumentDiv").load("<%=basePath%>/visa/document/preCopy.do?documentId=" + documentId,function() {
				$(this).dialog({
            		modal:true,
            		title:"复制签证所需材料",
            		width:550,
            		height:400
                });
            });	
		}
	</script>
</html>

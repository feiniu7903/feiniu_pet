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
		<title>签证产品列表</title>
		<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
		<s:include value="/WEB-INF/pages/pub/suggest.jsp" />
		<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
		
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
	</head>
	<body>
		<div id="addVisaProductDiv" style="display: none"></div>
		<div id="viewVisaApplicationDocumentDiv" style="display: none"></div>
		<div class="ui_title">
			<ul class="ui_tab">
				<li class="active"><a href="#">签证销售产品列表</a></li>
			</ul>
		</div>
		
		<div class="iframe-content">
			<div class="p_box">
				<form action="<%=basePath%>/visa/document/index.do" method="post">
					<table class="p_table form-inline" width="100%">
						<tr>
							<td class="p_label">产品名称</td>
							<td>
								<input type="text" />	
							</td>
							<td class="p_label">产品ID</td>
							<td>
								<input type="text" />	
							</td>
							<td class="p_label">产品编号</td>
							<td>
								<input type="text" />	
							</td>
						</tr>
						<tr>	
							<td class="p_label">所属公司</td>
							<td>
								<s:select list="#{'':'所有','BJ_FILIALE':'北京分部','CD_FILIALE':'成都分部','GZ_FILIALE':'广州分部','HS_FILIALE':'黄山办事处','HZ_FILIALE':'杭州分部','SH_FILIALE':'上海总部','SY_FILIALE':'三亚分部','XM_FILIALE':'厦门办事处'}" key="insurance.filialeName"></s:select>	
							</td>
							<td class="p_label">上下线状态</td>
							<td>
								<s:select list="#{'':'所有','Y':'上线','N':'下线'}" />	
							</td>
							<td class="p_label"></td>
							<td>
								
							</td>
						</tr>
						<tr>	
							<td class="p_label">关联材料：</td>
							<td colspan="5">
								<input type="text" name="country" id="country"/>
								<s:select key="visaType" list="visaTypeList"/>
								<s:select key="occupation" list="visaOccupationList"/>
							</td>	
						</tr>			
					</table>
					<p class="tc mt20"><button class="btn btn-small w5" type="submit">查询</button>　<button id="addVisaProductBtn" class="btn btn-small w5" type="button">新增销售产品</button></p>
				</form>
			</div>
			<div class="p_box">
				<table class="p_table table_center">
					<tr>
						<th>产品ID</th>
						<th>产品编号</th>
						<th width="30%">产品名称</th>
						<th>签证类型</th>
						<th>送签城市</th>
						<th>上下线状态</th>
						<th>操作</th>
					</tr>
					<s:iterator value="pagination.items" var="document">
						<tr>
							<td>${document.country}</td>
							<td>${document.cnVisaType}</td>
							<td>${document.cnCity}</td>
							<td>${document.cnOccupation}</td>
							<td>
								<a href="javascript:view(${document.documentId});">明细</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="javascript:del(${document.documentId});">删除</a>&nbsp;&nbsp;&nbsp;&nbsp; 
								<a href="javascript:copy(${document.documentId});">复制</a>&nbsp;&nbsp;&nbsp;&nbsp;  
								预览
								<a href="javascript:void(0)" class="showLogDialog" param="{'objectId':'${document.documentId}','objectType':'VISA_APP_DOCUMENT_TARGET'}">查看日志</a>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</s:iterator>
					<tr>
     					<td colspan="2" align="right">总条数：<s:property value="pagination.totalResultSize"/></td>
						<td colspan="5" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td>
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
			  
			$("#addVisaProductBtn").click(function(){
				$("#addVisaProductDiv").load("<%=basePath%>/prod/visa/add.do",function() {
            		$(this).dialog({
            			modal:true,
            			title:"新增签证销售产品",
            			width:850,
            			height:550
                	});
            	});
			});
		  });
	</script>
</html>

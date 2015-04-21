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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>点评列表</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/mis.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base/back_base.css">
<jsp:include page="/WEB-INF/pages/pub/jquery.jsp" />
<jsp:include page="/WEB-INF/pages/pub/suggest.jsp" />
<jsp:include page="/WEB-INF/pages/pub/timepicker.jsp" />
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
</head>
<body>
<div id="editCommentDiv" style="display: none"></div>
<div id="addPointDiv" style="display: none"></div>
<div id="showUpdatePlaceCommentTopicDiv" style="display: none"></div>

	<div class="main main02">
		<div class="row1">
			<h3 class="newTit">点评列表</h3>
			<form action="<%=basePath%>/commentManager/queryCommentList.do" method="post" id="commentListForm">
                <input type="hidden" value="" id="lastest3day" name="lastest3day"/>
                <input type="hidden" id="page" name="page" value="${page}">
                <input type="hidden" id="scrollTopHight" name="scrollTopHight" value="${scrollTopHight}">
                
				<table border="1" cellspacing="0" cellpadding="0" class="search_table" width="100%">
					<tr>
						<td>点评编号：</td>
						<td>
							<s:textfield id="commentId" name="commentId" cssClass="newtext1"/>
						</td>
						
						<td>主题名称：</td>
						<td>
							<s:textfield id="subjectName" name="subjectName" cssClass="newtext1"/>
						</td>
						
						<td>精华状态：</td>
						<td>
							<s:select id="isBest" name="isBest" list="#{'':'全部','Y':'精华','N':'非精华'}"></s:select>
						</td>
							
					</tr>
					<tr>
						<td>用户名：</td>
						<td>
							<s:textfield id="userName" name="userName" cssClass="newtext1"/>
						</td>
						
						<td>主题类型：</td>
						<td>
							<s:select id="stage" name="stage" list="#{'':'全部','1':'目的地','2':'景点','3':'酒店'}"></s:select>
							<s:select id="productType" name="productType" list="#{'':'全部','ROUTE':'线路产品','TICKET':'门票产品','HOTEL':'酒店产品'}"></s:select>
                            <s:select list="subProductTypeList" name="subProductType" listKey="code" listValue="name"></s:select>						</td>
						
						
						<td>审核状态：</td>
						<td>
							<s:select id="auditStatus" name="auditStatus" list="#{'AUDIT_ALL':'全部','AUDIT_GOING':'待审核','AUDIT_SUCCESS':'审核通过','AUDIT_FAILED':'审核未通过'}"></s:select>
						</td>
					
					</tr>
					<tr>
						<td>发表时间：</td>
						<td><input id="startDate" type="text" class="newtext1"
							name="startDate" value="${startDate}"/> ~<input id="endDate" type="text"
							class="newtext1" name="endDate" value="${endDate}"/></td>

                       	<td>点评类型：</td>
						<td>
							<s:select id="cmtType" name="cmtType" list="#{'':'全部','EXPERIENCE':'体验点评','COMMON':'普通点评'}"></s:select>
						</td>
						<td>排序：</td>
						<td>
							<s:select id="order" name="order" list="#{'':'请选择','createTime321':'时间新>>旧',
							'createTime123':'时间旧>>新','replyCount123':'回复数少>>多','replyCount321':'回复数多>>少'
							,'usefulCount321':'有用数多>>少','usefulCount123':'有用数少>>多'}"></s:select>
						</td>
					</tr>
					
					<tr>
						<td>产品ID：</td>
						<td>
							<s:textfield id="productId" name="productId" cssClass="newtext1"/>
						</td>
						<td>是否有图片：</td>
						<td>
							<s:select id="hasPicture" name="hasPicture" list="#{'':'全部','Y':'有','N':'没'}"></s:select>
						</td>
						<td>来源渠道：</td>
						<td>
							<s:select id="channel" name="channel" list="#{'':'全部','FRONTEND':'前台','BACKEND':'后台','CLIENT':'客户端'}"></s:select>
						</td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td>内容：</td>
						<td>
							<s:textfield id="content" name="content" cssClass="newtext1" />
						</td>
						<td>点评分类：</td>
						<td>
							<s:select id="cmtEffectType" name="cmtEffectType" list="#{'':'全部','NORMAL':'常规','PROPOSAL':'建议','COMPLAINT':'投诉'}"></s:select>
						</td>
					</tr>
					
					<tr>
					    <td colspan="7"><input type="button" class="button" value="近3天待审核记录"  onClick="threeDaySearch()"/>&nbsp;&nbsp;
					    <input type="button" class="button" value="批量审核通过" onClick="batchOperator('isAudit')"/>&nbsp;&nbsp;
					    <input type="button" class="button" value="批量审核不通过" onClick="batchOperator('isNAudit')"/>&nbsp;&nbsp;
					    <input type="button" class="button" value="批量通过并隐藏" onClick="batchOperator('isAuditAndHide')"/>&nbsp;&nbsp;
					    <input type="button" class="button" value="导出" onClick="doExport()"/>&nbsp;&nbsp;
					    <input type="button" class="button" value="查询"  onClick="search('manual')"/>
					    &nbsp;&nbsp;&nbsp;&nbsp;
					    <input type="button" class="button" value="修改景区点评主题"  onClick="showEditScienceCommentTopic()"/></td>
					</tr>
				</table>
			</form>
		</div>
		<div class="row2">
			<table border="1" cellspacing="0" cellpadding="0" class="gl_table" width="100%">
				<tr>
					<td colspan="3"> 总条数：<s:property value="pagination.totalResultSize"/> </td>
     				<td colspan="13" align="left">
     					<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination)"/>
     				</td>
     			</tr>
				<tr>
				    <th><input type="checkbox" id="allCommentSelectHead" onClick="selectAll('allCommentSelectHead')"/></th>
					<th>编号</th>
					<th>用户名</th>
					<th>主题类型</th>
					<th>主题名称</th>
					<th width="45%">内容</th>
					<th>点评类型</th>
					<th>是否精华</th>
					<th>前台隐藏</th>
					<th>回复数</th>
					<th>图片数</th>
					<th>有用数</th>
					<th>审核时间</th>
					<th width="14px">发表时间</th>
					<th>审核状态</th>
					<th>操作</th>
				</tr>
				<s:iterator value="commonCmtCommentVOList" var="commonCmtComment" status="c">
					<tr>
					    <td><input type="checkbox" id="checkBox<s:property value="#c.index"/>"  value="${commonCmtComment.commentId}"/></td>
						<td><a href="http://www.lvmama.com/comment/${commonCmtComment.commentId}" target="_blank">${commonCmtComment.commentId}</a></td>
						<td>${commonCmtComment.userName}</td>
						<s:if test='subjectType != null && subjectName != null'>
							<td>${commonCmtComment.subjectType}</td>
							<td>${commonCmtComment.subjectName}</td>
					    </s:if>
					    <s:else>
						    <td></td>
						    <td></td>
					    </s:else>
						<td>${commonCmtComment.contentDelEnter}</td>
						<td>${commonCmtComment.chCmtType}</td>
						<td>${commonCmtComment.chIsBest}</td>
						<td>${commonCmtComment.chIsHide}</td>
						<td>${commonCmtComment.replyCount}</td>
						<td>${commonCmtComment.pictureCount}</td>
						<td>${commonCmtComment.usefulCount}</td>
						<td>${commonCmtComment.formatterAuditTime}</td>
						<td>${commonCmtComment.formatterCreatedTime}</td>
						<td>${commonCmtComment.chAudit}</td>
                        <td>
	                        <input type="button" class="button" value="审核通过" onClick="operatorFun('isAudit',${commonCmtComment.commentId})"/><br/>
	                        <input type="button" class="button" value="通过并精华" onClick="operatorFun('isAuditAndIsBest',${commonCmtComment.commentId})"/><br/>
	                        <input type="button" class="button" value="精华" onClick="operatorFun('isBest',${commonCmtComment.commentId})"/><br/>
	                        <input type="button" class="button" value="审核不过" onClick="operatorFun('isNAudit',${commonCmtComment.commentId})"/><br/>
	                        <input type="button" class="button" value="通过并隐藏" onClick="operatorFun('isAuditAndHide',${commonCmtComment.commentId})"/><br/>
	                        <input type="button" class="button" value="隐藏" onClick="operatorFun('hide',${commonCmtComment.commentId})"/><br/>
	                        <input type="button" class="button" value="显示" onClick="operatorFun('show',${commonCmtComment.commentId})"/><br/>
	                        <input type="button" class="button" value="查看编辑" onClick="openEditCommentWindow(${commonCmtComment.commentId})"/><br/>
	                        <input type="button" class="button" value="加积分" onClick="openAddPointWindow(${commonCmtComment.commentId})"/><br/>
	                        <input type="button" class="button" value="建议" <s:if test='cmtEffectType =="PROPOSAL"'>disabled</s:if> onClick="operatorFun('PROPOSAL',${commonCmtComment.commentId})"/>
	                        <input type="button" class="button" value="投诉" <s:if test='cmtEffectType =="COMPLAINT"'>disabled</s:if> onClick="operatorFun('COMPLAINT',${commonCmtComment.commentId})"/><br/>
                        </td>
					</tr>
				</s:iterator>
				<tr>
					<td colspan="2"> <input type="checkbox" id="allCommentSelectFoot" onClick="selectAll('allCommentSelectFoot')"/></td>
     				<td colspan="2"> 总条数：<s:property value="pagination.totalResultSize"/> </td>
     				<td colspan="12" align="left">
     					<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination)"/>
     				</td>
    			</tr>
    			<tr>
					<td colspan="16">
						<input type="button" class="button" value="近3天待审核记录"  onClick="threeDaySearch()"/>&nbsp;&nbsp;
					    <input type="button" class="button" value="批量审核通过" onClick="batchOperator('isAudit')"/>&nbsp;&nbsp;
					    <input type="button" class="button" value="批量审核不通过" onClick="batchOperator('isNAudit')"/>&nbsp;&nbsp;
					    <input type="button" class="button" value="批量通过并隐藏" onClick="batchOperator('isAuditAndHide')"/>&nbsp;&nbsp;
					    <!-- <input type="button" class="button" value="修改点评主题为空问题"  onClick="showUpdatePlaceCommentTopic()"/></td> -->
					</td>
				</tr>
				<tr>
     				 <td colspan="16"></td>
    			</tr>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">
        $(function(){
            $('#startDate').datepicker({dateFormat: 'yy-mm-dd'});
            $('#endDate').datepicker({dateFormat: 'yy-mm-dd'});
            $(document).scrollTop($("#scrollTopHight").val());
        });
        
        //近3天待审核记录
        function threeDaySearch()
        {
        	$("#lastest3day").val("true");
        	$("#auditStatus").val("AUDIT_GOING");
        	$("#commentListForm").submit();
        }
        
        //操作单条记录 
        function operatorFun(operator, batchCommentIds)
        {
			if(confirm("是否确认操作？")){
				$("#scrollTopHight").val($(document).scrollTop());
	          	 var url = "<%=basePath%>/commentManager/batchOperator.do";
	        	 $.ajax({
	        	 	url: url,
	        	 	data: {"operator":operator,"batchCommentIds":batchCommentIds},
	        	 	dataType:"json",
	        	 	success: function(result) {
		        		if (result.success) {
		        			//location.href = location.href?;
		        			search('auto');
		        		} else {
		        			alert(result.errorMessage);
		        		}
	        	 	}
	        	 });
			}
        }
        
        //批量操作多条记录
        function batchOperator(operator)
        {
        	var batchCommentIds = "";
    		for(var i = 0; i < 30; i++)
    		{
    			if($("#checkBox"+i) != null && $("#checkBox"+i).attr("checked")==true)
    			{
    				batchCommentIds += $("#checkBox"+i).val() + ",";
    			}
    		}
    		operatorFun(operator, batchCommentIds);
        }
        
        //选择所有记录
        function selectAll(id)
        {
        	if($("#" + id).attr("checked")==true)
        	{
        		for(var i = 0; i < 30; i++)
        		{
        			if($("#checkBox"+i) != null)
        			{
        				$("#checkBox"+i).attr("checked",true);
        			}
        		}
        	}
        	else if($("#" + id).attr("checked")==false)
        	{
        		for(var i = 0; i < 30; i++)
        		{
        			if($("#checkBox"+i) != null)
        			{
        				$("#checkBox"+i).attr("checked",false);
        			}
        		}
        	}
        }
        
        function openEditCommentWindow(commentId)
        {
        	$("#editCommentDiv").load("<%=basePath%>/commentManager/openEditComment.do?commentId=" + commentId,function() {
        		$(this).dialog({
        			modal:true,
        			title:"点评编辑",
        			width:950,
        			height:650
            	});
        	});
        }
        
        function openAddPointWindow(commentId)
        {
        	$("#addPointDiv").load("<%=basePath%>/commentManager/openAddPoint.do?commentId=" + commentId,function() {
        		$(this).dialog({
        			modal:true,
        			title:"增加积分",
        			width:950,
        			height:650
            	});
        	});
        }
        
        function search(operation)
        {
        	if(operation == 'manual'){
        		$("#scrollTopHight").val(10);
        	}
        	 
        	if($("#subjectName").val() != null && $("#subjectName").val() != "")
        	{
        		if(($("#stage").val() == null || $("#stage").val() == "") && ($("#productType").val() == null || $("#productType").val() == ""))
        		{
        			alert("查找主题名称时，请至少选择一个主题类型！");
        			return;
        		}
        	} 
        	$("#commentListForm").submit();
        }
        
        function doExport(){
        	if(!confirm("确定导出吗(该功能最多导出900笔体验点评记录)")){
				return false;
			}
        	var arr = [];
			$("#commentListForm").find("textarea,input:text,select").each(function(){
			    arr.push(this.name+'='+this.value);
			});
			var data = arr.join("&");
			var url = "<%=basePath%>/commentManager/doExport.do?" + data;
			window.location = url;
         }
        function showEditScienceCommentTopic() {
			  $("#editCommentDiv").load("<%=basePath%>/commentManager/showScienceCommentTopic.do",function() {
					$(this).dialog({
	            		modal:true,
	            		title:"修改景区的点评主题",
	            		width:550,
	            		height:400
	                });
	            });	
        }
        function showUpdatePlaceCommentTopic() {
			  $("#showUpdatePlaceCommentTopicDiv").load("<%=basePath%>/commentManager/showUpdatePlaceCommentTopic.do",function() {
					$(this).dialog({
	            		modal:true,
	            		title:"修改点评主题为空问题",
	            		width:550,
	            		height:400
	                });
	            });	
      }
    </script>
</html>



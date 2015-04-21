<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>点评回复信息管理</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<s:include value="/WEB-INF/pages/pub/validate.jsp"/>
<link rel="stylesheet" type="text/css" href="${basePath}/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${basePath}/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${basePath}/themes/mis.css">
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/back_base.css">
<style>
#show_comment_parent{border:1px solid #666;position:fixed;_position:absolute; left:50%;top:120px; width:700px; background:#fff; overflow:hidden; padding:0px; margin-left:-350px;z-index:2005;padding:4px;}
#close_button{color:red;}

</style>
<jsp:include page="/WEB-INF/pages/pub/timepicker.jsp" />
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
</head>
<body>
<div><form action="${basePath}/commentManager/listReply.do" method="post">
	<table border="0" cellspacing="0" cellpadding="0" class="search_table">
			<tr>
				<td>用户名：</td>
				<td>
					<input type="text" class="newtext1" name="userName" 	value="<s:property value="userName"/>" />
				</td>
				<td>
					点评编号：
				</td>
				<td><input type="text" class="newtext1" name="commentId" 	value="<s:property value="commentId"/>" /></td>
			 </tr>
			 <tr>
				<td>审核状态：</td>
				<td>
					<s:select name="isAudit" list="#{'':'全部','AUDIT_GOING':'待审核','AUDIT_SUCCESS':'审核通过','AUDIT_FAILED':'审核未通过'}"></s:select>
				</td>
				<td>发表时间：</td>
				<td><input id="startDate" type="text" class="newtext1"
							name="startDate" value="<s:date name="startDate" format="yyyy-MM-dd"/>"/> ~<input id="endDate" type="text"
							class="newtext1" name="endDate" value="<s:date name="endDate" format="yyyy-MM-dd"/>"/></td>
			 </tr>
			 <tr>
			 	<td>排序：</td>
			 	<td><s:select name="orderBy" list="#{'':'请选择','DESC':'时间新>>旧','ASC':'时间旧>>新'}"></s:select>
			 	</td>
			 	<td>
			 	<input type="button" value="查询" class="button" id="queryButton"/>&nbsp;&nbsp;
			 	<input type="button" value="批量审核通过" class="button batchAduit"  batch="all" auditStatus="AUDIT_SUCCESS"/>&nbsp;&nbsp;
			 	<input type="button" value="批量审核不通过" class="button batchAduit"  batch="all" auditStatus="AUDIT_FAILED"/>
			 </td>
			 </tr>
	</table>
</form>
</div>
<div>
	<table border="0" cellspacing="0" cellpadding="0" class="gl_table">
		<tr>
			<th width="50" align="left"><input type="checkbox" id="allcheckBox" /></th>
			<th width="50" >回复编号</th>
			<th width="50">点评编号</th>
			<th width="100">用户名/IP</th>
			<th width="400">内容</th>
			<th width="100">发表时间</th>
			<th width="50">审核状态</th>
			<th width="150">操作</th>
		</tr>
		<s:iterator value="cmtReplyList" var="cmtReply" status="cmt">			
		<tr>
			<td><input type="checkbox" value="<s:property value="replyId"/>" reply_index="${cmt.index}"/></td>
			<td><s:property value="replyId"/></td>
			<td><a href="javascript:void(0)" current_url ="${basePath }/commentManager/openEditComment.do" data="${commentId}" class="showDetail"><s:property value="commentId"/></a></td>
			<td><s:property value="userName"/></td>
			<td><s:property value="content"/></td>
			<td><s:date name="#cmtReply.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			<td audit_status_index="${replyId}"><s:property value="chIsAudit"/></td>
			<td>
				<mis:checkPerm permCode="1403">
					<a href="javascript:void(0)" width="85px" data="${replyId}" auditStatus="AUDIT_SUCCESS" class="batchAduit" id="batchAduit" batch="one">审核通过</a>|
				</mis:checkPerm>
				<mis:checkPerm permCode="1404">
					<a href="javascript:void(0)" width="85px" data="${replyId}" auditStatus="AUDIT_FAILED"  class="batchAduit" id="batchAduit" batch="one">审核不通过</a>
				</mis:checkPerm>
			</td>					
		</tr>	
		</s:iterator>
		<tr>
			<td>
				总条数：<s:property value="pagination.totalResultSize"/>
			</td>
			<td colspan="8" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td>
		</tr>
	</table>
</div>
<div id="show_comment_parent" style="display:none;">
	<div style="text-align:right"><span id="close_button">关闭</span></div>
	<div  id="show_comment_detail"/>
</div>
</body>
<script type="text/javascript" charset="utf-8">
$(function(){
	$('#startDate').datepicker({dateFormat: 'yy-mm-dd'});
    $('#endDate').datepicker({dateFormat: 'yy-mm-dd'});
	$("#queryButton").click(function(){
		document.forms[0].submit();
	});
	$("#allcheckBox").click(function(){
		$("input:checkbox").attr("checked",$(this).attr("checked"));
	});
	$(".batchAduit").click(function(){
		var replyId = "";
		var batch = $(this).attr("batch");
		var auditStatus = $(this).attr("auditStatus");
		var auditShow = "审核通过";
		if(auditStatus == "AUDIT_FAILED"){auditShow = "审核不通过";}
		if("all"==batch){
			if($(":input:checkbox:checked[reply_index]").size()==0){
				alert("请选择要审核的回复");
			}
			$(":input:checkbox:checked[reply_index]").each(function(){
				replyId+=($(this).val())+",";
			});
		}else{
			replyId = $(this).attr("data");
		}
		$.ajax( {
			url : "${basePath}/commentManager/batchAuditReply.do",
			data:{"replyStr":replyId,"auditStatus":auditStatus},
			type: "POST",
			success : function(data) {
					if(data.success){
						$(replyId.split(",")).each(function(){
							$("td[audit_status_index='"+this+"']").text(auditShow);
						});
						alert("审核成功");
						window.location.reload();
						$(":input:checkbox:checked[reply_index]").removeAttr("checked");
					}else{
						var replyArray = $(replyId.split(",")).not(data.errorText.split(","));
						replyArray.each(function(){
							$("td[audit_status_index='"+this+"']").text(auditShow);
						});
						alert("回复号："+data.errorText+" 审核失败");
					}
				}
			});
	});
	$(".showDetail").click(function(){
		$.ajax({
			url : $(this).attr("current_url"),
			data: {commentId:$(this).attr("data")},
			type:"POST",
			dataType:"html",
			success:function(data){
				$("#show_comment_parent").show().find("#show_comment_detail").html(data);
				$("#show_comment_detail").find(".search_table tr").last().remove();
				$("#show_comment_detail").find("#editContent").attr("disabled",true);
			}
		});
	});
	$("#close_button").click(function(){
		$("#show_comment_parent").hide();
	});
});
</script>
</html>
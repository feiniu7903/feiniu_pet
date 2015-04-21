<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()+"/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>点评回复--内容审核</title>
<s:include value="/WEB-INF/pages/pub/reviewHeadJs.jsp" />
<script type="text/javascript">
 var reviewStatus = '${reviewStatus}';
$("#reviewStatus").val(reviewStatus);

$(function(){
    $("#form1").validate({
        rules: {    
           /*  "startDate":{
                required:true
            },
            "endDate":{
                required:true
            }, */
            "reviewStatus":{
                required:true
            } 
        }, 
        messages: {    
             /* "startDate":{
                 required:"请选择开始时间"
             },
             "endDate":{
                 required:"请选择结束时间"
             }, */
             "reviewStatus":{
                 required:"请选择状态"
             } 
        }
    });
});
</script>
</head>
<body>

	<div id="popDiv" style="display: none"></div>
	<div class="iframe-content">
		<div class="p_box">
			<form action="${basePath}keyword/cmtReply.do" method="post"
				id="form1">
				<table class="p_table form-inline" width="100%">
					<tr>
						<td class="p_label">内容日期:</td>
						<td><input   name="startDate" id="startDate"
							value='<s:property value="startDate"/>' type="text" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" /> 至&nbsp;<input  
							name="endDate" id="endDate" value="<s:property value="endDate"/>"
							type="text" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})" />
						</td>
						<td class="p_label">安全等级:</td>
						<td><s:select name="reviewStatus" list="ReviewStatusList" theme="simple" listKey="elementCode" listValue="elementValue"></s:select></td>
					</tr>
				</table>
				<p class="tc mt20">
					<input type="submit" class="btn btn-small w5" id="allSelect"
						value="查&nbsp;&nbsp;询" />
				</p>

			</form>
		</div>

		<div class="p_box">
			<table class="p_table table_center">
				<tr>
					<th width="100">点评回复链接</th>
					<th>内容全文</th>
				</tr>

				<s:if test="pagination.allItems!=null ">
					<input id="itemsLength" type="hidden"
						value="<s:property value='pagination.allItems.size()'/>" />
					<s:iterator value="pagination.allItems" var="var" status="st">
						<tr>

							<td><a href="http://www.lvmama.com/comment/${commentId}"
								target="_blank">${replyId}</a></td>
							<td>${content}</td>
						</tr>
						<tr>
							<td class="gl_cz"><a class="showLogDialog"
								param="{'parentType':'cmtReply','parentId':${replyId}}"
								href="#log">操作日志</a></td>
							<td><input type="hidden"
								id="id<s:property value='#st.count'/>" value="${replyId}" /> 
                                <input type="radio" name="reviewstatus<s:property value='#st.count'/>"
								value="1" checked="checked" <s:if test='reviewStatus==1||reviewStatus==4||reviewStatus==5' > checked="checked"</s:if> />白色&nbsp;&nbsp; 
                                <input type="radio" name="reviewstatus<s:property value='#st.count'/>" value="2" <s:if test='reviewStatus==2' > checked="checked"</s:if>/>黑色&nbsp;&nbsp;
								<input type="radio"  name="reviewstatus<s:property value='#st.count'/>" value="3"  <s:if test='reviewStatus==3' > checked="checked"</s:if> />灰色&nbsp;&nbsp;

								<a>创建时间:<s:date name="createTime" format="yyyy-MM-dd HH:mm:SS" /></a></td>
						</tr>
						<tr>
                            <td colspan="2" class="p_label">&nbsp;</td>
                        </tr>
					</s:iterator>
				</s:if>
				<tr>
					<td colspan="1" align="right">总【<s:property
							value="pagination.totalResultSize" />】条
					</td>
					<td colspan="1">
						<div style="text-align: left;">
							每页显示<input id="pageSize" class="btn btn-small w1" type="text"
								value="${pagination.pageSize}" /> <input
								class="btn btn-small w5" type="button" value=" GO "
								onclick="javascript:return ajaxPageSize('${basePath}//review/pagesize.do','${memcachPageSizeKey}');">
						</div>
						<div style="text-align: right;">
							<s:property escape="false"
								value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)" />
						</div>
					</td>
				</tr>

				<tr>
					<td></td>
					<td><input class="btn btn-small w5" type="button" value="提交"
						onclick="javascript:return ajaxSumitPage('${basePath}/keyword/doCmtReplyUpdate.do','${reviewStatus}');">
				</tr>

			</table>
		</div>
	</div>
</body>
</html>
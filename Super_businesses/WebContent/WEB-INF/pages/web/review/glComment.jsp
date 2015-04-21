<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>攻略点评</title>
<s:include value="/WEB-INF/pages/pub/reviewHeadJs.jsp" />
</head>
<script type="text/javascript">
$(function(){
    var datebegin = '${datebegin}';
    $("#datebeginID").val(datebegin);
      var dateend = '${dateend}';
        $("#dateendID").val(dateend);
    var  sid='${statusId}';
    $("#statusId").val(sid);
});

$(function(){
    $("#form1").validate({
        rules: {    
            /* "datebegin":{
                required:true
            },
            "dateend":{
                required:true
            }, */
            "statusId":{
                required:true
            } 
        }, 
        messages: {    
            /*  "datebegin":{
                 required:"请选择开始时间"
             },
             "dateend":{
                 required:"请选择结束时间"
             }, */
             "statusId":{
                 required:"请选择状态"
             } 
        }
    });
});
</script>
<body>
	<div id="popDiv" style="display: none"></div>
	<div class="iframe-content">
		  <div class="p_box">
            <form action="${ basePath}/glComment/query.do" method="post" id="form1">
                <table class="p_table form-inline" width="80%">
                    <tr>
                         <td class="p_label">日期:</td>
                         <td><input    name="datebegin"
                             id="datebeginID"  value='<s:property value="datebegin"/>' 
                             type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
                                                                                                          至&nbsp;
                           <input      name="dateend" id="dateendID"  value="<s:property value="dateend"/>" 
                           type="text"  class="Wdate"
                            onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'datebeginID\')}'})" />
                        </td>
                        <td class="p_label">状态:</td>
                        <td><select name="statusId" id="statusId">
                                 <s:iterator value="statusIds" var="var" status="st">
                                    <option value="${key}">${value}</option>
                                </s:iterator>
                        </select></td>
                    </tr>
                </table>
                <p class="tc mt20">
                    <input type="submit" class="btn btn-small w5"
                        value="查&nbsp;&nbsp;询" />
                </p>
            </form>
        </div>

		<div class="p_box">
			<table class="p_table ">
				<tr>
					<th width="100">攻略内容链接</th>
					<th>内容</th>
				</tr>
				<s:if test="pagination.allItems!=null ">
					<!-- 内容部分 -->
					<input id="itemsLength" type="hidden" value="<s:property value='pagination.allItems.size()'/>" />
                    
					<s:iterator value="pagination.allItems" var="var" status="st">
						<tr>
							<td><a target="_blank" href="${url}">${commentid}</a></td>
							<td>${content}</td>
						</tr>
						<tr>
							<td><a class="showLogDialog" 	param="{'parentId':${commentid},'parentType':'glcomment'}"	href="#log">操作日志</a></td>
							<td><input type="hidden" id="id<s:property value='#st.count'/>" value="${commentid}" /> 
                            <input name="reviewstatus<s:property value='#st.count'/>" type="radio" value="1" <s:if test='statusId==1||statusId==4||statusId==5' > checked="checked"</s:if>  /> 白色 
                            <input name="reviewstatus<s:property value='#st.count'/>" type="radio" value="2" <s:if test='statusId==2' > checked="checked"</s:if>  /> 黑色 &nbsp; 
                            <input name="reviewstatus<s:property value='#st.count'/>" type="radio" value="3" <s:if test='statusId==3' > checked="checked"</s:if>  /> 灰色
                            &nbsp; &nbsp; <a>创建时间:<s:date  name="createdate" format="yyyy-MM-dd HH:mm:SS" /></a></td>
						</tr>
						<tr>
                            <td colspan="2" class="p_label">&nbsp;</td>
                        </tr>
					</s:iterator>
					<!-- 内容部分 -->
				</s:if>
				<tr>
					<td colspan="1" align="right">总条数：<s:property
							value="pagination.totalResultSize" /></td>
					<td colspan="1">
						<div style="text-align: left;"> 
							每页显示<input id="pageSize" class="btn btn-small w1" type="text" value="${pagination.pageSize}" /> <input
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
					<td><input class="btn btn-small w5" type="button" value="提交" onclick="javascript:return ajaxSumitPage('${basePath}//glComment/update.do',${statusId});">
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()+"/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>扫描管理</title>
<s:include value="/WEB-INF/pages/pub/reviewHeadJs.jsp" />
<script type="text/javascript">
$(function(){
	$("#form1").validate({
        rules: {    
            "reviewSendEmail.keyWordStartDate":{
                required:true
            },
            "reviewSendEmail.keyWordEndDate":{
                required:true
            },
            "reviewSendEmail.contentStartDate":{
                required:true
            },
            "reviewSendEmail.contentEndDate":{
                required:true
            },
            "reviewSendEmail.reviewChannel":{
                 required:true 
             }, 
            "reviewSendEmail.reviewStatus":{
                 required:true
            } 
        }, 
        messages: {    
        	"reviewSendEmail.keyWordStartDate":{
                required:"请选择日期"
            },
            "reviewSendEmail.keyWordEndDate":{
                required:"请选择日期"
            },
            "reviewSendEmail.contentStartDate":{
                required:"请选择日期"
            },
            "reviewSendEmail.contentEndDate":{
                required:"请选择日期"
            },
            "reviewSendEmail.reviewChannel":{
                 required:"请选择内容来源" 
             }, 
            "reviewSendEmail.reviewStatus":{
                 required:"请选择安全等级"
            } 
        }
    });
});

</script>
</head>
<body>
	<div class="iframe-content">
		<div class="p_box">
			<form action="${basePath}keyword/reviewSendEmail.do" method="post"   
				id="form1">
				<table class="p_table table_center" width="80%">
					<tr>
						<td class="p_label">关键字日期:</td>
						<td><input  name="reviewSendEmail.keyWordStartDate" id="keyWordStartDate"
							value='<s:property value="reviewSendEmail.keyWordStartDate"/>' type="text"  
							class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',
								   maxDate : '#F{$dp.$D(\'keyWordEndDate\')}'
							})"  />
                        </td> <td>   
							至&nbsp;<input   name="reviewSendEmail.keyWordEndDate" id="keyWordEndDate"   
							value="<s:property value="reviewSendEmail.keyWordEndDate"/>" type="text"
							class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'keyWordStartDate\')}'})" />
						</td>
					</tr>
					<tr>
						<td class="p_label">&nbsp;内容日期:</td>
						<td><input   name="reviewSendEmail.contentStartDate" id="contentStartDate"
							value='<s:property value="reviewSendEmail.contentStartDate"/>' type="text"
							class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
							</td> <td> 至&nbsp;<input   name="reviewSendEmail.contentEndDate" id="contentEndDate"
							value="<s:property value="reviewSendEmail.contentEndDate"/>" type="text"
							class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'contentStartDate\')}'})" />
						</td>
					</tr>
					<tr>
						<td class="p_label">&nbsp;内容来源:</td>
						<td  colspan="2"><select id="reviewChannel"  name="reviewSendEmail.reviewChannel">
								<option value="1">论坛帖子和主题列表</option>
								<option value="2">点评及评论</option>
								<option value="3">攻略及评论</option>
								<option value="4">资讯及评论</option>
						</select></td>
					</tr>
					<tr>
						<td class="p_label">&nbsp;安全等级:</td>
						<td colspan="2"><select id="reviewStatus"  name="reviewSendEmail.reviewStatus">
 							    <option value="3" selected="selected">灰色</option>
								<option value="5" >浅灰色</option>
						</select></td>
					</tr>
					<tr> <td class="p_label"></td>
						<td colspan="3"  >
							<button class="btn btn-small w5" type="submit">扫描</button>
						</td>
					</tr>

				</table>
			</form>
		</div>

		<div class="p_box">
			<table class="p_table table_center" width="80%">
				<thead>
					<tr>
						<th>操作内容</th>
					</tr>
				</thead>

				<tbody>
					<s:if test="null != ReviewContentList">
						<s:iterator value="ReviewContentList" var="ReviewSendEmail" status="st">
							<tr>
								<td><a>操作时间：<b>${rseDateToString}</b></a><br />${rseContent }</td>
							</tr>
						</s:iterator>
					</s:if>
					<tr><td align="right">总【<s:property value="pagination.totalResultSize"/>】条</tr>
					<tr>
	                    <td ><div style="text-align: right;"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></div></td>
				    </tr>
				</tbody>
			</table>
		</div>
    </div>
</body>
</html>
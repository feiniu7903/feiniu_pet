<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath() + "/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>储值卡日志</title>
<s:include value="/WEB-INF/pages/pub/lvmamacard.jsp" />

</head>
<body>
<script type="text/javascript">
    $(function() {
    });
    $(function(){ 	
        	
    });
</script>
    <div id="popDiv" style="display: none">
    	
    </div>
    <div class="iframe-content">
        <div class="p_box">
            <form action="${basePath}log/logStorageCard.do" method="post" id="form1">
                <table class="p_table form-inline" width="100%">
                       <tr>
                           <td class="p_label">操作日志:</td>
                            <td><select name="logType">
                                <s:iterator value="logStatusList" var="var" status="st">
                                <s:if test="logType == key">
                             		<option selected="selected" value="${key}">${value}</option>   
                                </s:if>
 								<s:else>
 									<option value="${key}">${value}</option>
 								</s:else>                            
                                </s:iterator>
                            </td>
                            <td class="p_label">用户id:</td>
                            <td><input type="text" name="operatorName" value="${operatorName}" />
                            </td>
                       </tr>
                       <tr>
                            <td colspan="4"><input type="submit" class="btn btn-small w5" value="查&nbsp;&nbsp;询" /></td>
                    </tr>
                </table>
            </form>
        </div>
        <div class="p_box">
        
            <table class="p_table ">
                <tr>
                    <th width="200">发生时间</th>
                    <th>操作人</th>
                    <th>操作内容</th>
                    <th>备注</th>
                </tr>
			<s:iterator value="comLogList" var="log">
				<tr>
					<td><s:date name="#log.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><span style="font-weight:bold">${log.operatorName}</span></td>
					<td>${log.content}</td>
					<td>&nbsp;</td>
				</tr>
			</s:iterator>
			<tr>
					<td colspan="3">总<s:property value="pagination.totalResultSize" />条</td>
					<td>
						<div style="text-align: right;">
							<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)" />
						</div>
					</td>
				</tr>
            </table>
        </div>
    </div>
</body>
</html>
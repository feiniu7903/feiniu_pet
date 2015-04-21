<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()+"/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title> </title>
<s:include value="/WEB-INF/pages/pub/reviewHeadJs.jsp" />
</head>
<script type="text/javascript">
$(function(){
	var fid='${fromId}';
 	$("#fromId").val(fid);
	var  sid='${statusId}';
	$("#statusId").val(sid);
});
</script>
<body>
    <div id="popDiv" style="display: none"></div>
    <div class="iframe-content">
        <div class="p_box">
         <form action="${ basePath}/review/query.do" method="post" id="form1">
                <table class="p_table form-inline" width="100%">
                    <tr>
                        <td class="p_label">内容来源</td>
                        <td><select name="fromId" id="fromId">
                                <option value="0">请选择内容来源</option>
                                <s:iterator value="fromIds" var="var" status="st">
                                    <option value="${key}">${value}</option>
                                </s:iterator>
                        </select></td>
                        <td class="p_label">状态:</td>
                        <td><select name="statusId" id="statusId">
                                <option value="0">请选择状态</option>
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
   </div>
        
</body>
</html>
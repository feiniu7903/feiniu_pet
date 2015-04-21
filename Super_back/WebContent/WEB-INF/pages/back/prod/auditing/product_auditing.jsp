<%--
  Created by IntelliJ IDEA.
  User: troy-kou
  Date: 14-1-20
  Time: 下午5:05
  Email:kouhongyu@163.com
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld" %>
<html>
<head>
    <title></title>

</head>
<body>
<jsp:include page="/WEB-INF/pages/back/prod/auditing/auditing_product_menu.jsp"></jsp:include>

<iframe name="myframe" id="myframe" src="" width="100%" height="600px" frameborder="0"></iframe>

<br>
<br>
<div style="text-align: center;">
   <s:if test="auditAble">
	    <input type="button" value="审核通过" onclick="auditing(<s:property value="productId"/>,'true')">
	    <input type="button" value="审核不通过" onclick="auditing(<s:property value="productId"/>,'false')">
	    <input type="button" value="取消" onclick="closeAuditing()">
	</s:if>
	<s:else>
		<input type="button" value="关闭" onclick="closeAuditing()">
	</s:else>
</div>

<script>
    $("#baseA").click();
</script>
</body>
</html>
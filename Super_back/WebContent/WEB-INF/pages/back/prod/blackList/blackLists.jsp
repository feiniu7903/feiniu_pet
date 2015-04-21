<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()+"/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>黑名单列表</title>
<s:include value="/WEB-INF/pages/back/base/jquery.jsp"/>
</head>
<body>
<div class="main main02" style="width: 820px;">
    <div class="row1">
    	<form action="${basePath}/prodblack/query.do" method="post">
    		请输入查询手机号码:
			<input type="text" name="phone" value="<s:property value="phone"/>"/> 
			<input type="hidden" name="productId" value="<s:property value="productId"/>">
			<input type="submit" value="查询"/>
    	</form>
    </div>

    <div class="row2">
        <table border="0" cellspacing="0" cellpadding="0" class="newTable" style="text-align: center;width: 820px;">
            <tr class="newTableTit">
                <td>序号</td>
                <td>被限制手机号</td>
	          	<td>限制条件</td>
	          	<td>限制开始时间</td>
	          	<td>限制结束时间</td>
	          	<td>限制周期/时间内可购买次数</td>
	          	<td>限制原因</td>
	          	<td>操作</td>
            </tr>
            <s:iterator value="pagination.records" status="index">
                <tr>
                <td><s:property value="#index.index+1"/></td>
		  		<td><s:property value="blackPhoneNum"/></td>
		  		<td>
		  			<s:if test="blackCirculation==0">
		  				天
		  			</s:if>
		  			<s:elseif test="blackCirculation==1">
						周
					</s:elseif>
					<s:elseif test="blackCirculation==2">
						月
					</s:elseif>
					<s:elseif test="blackCirculation==3">
						年
					</s:elseif>
		  		</td>
		  		<td><s:date name="blackStartTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		  		<td><s:date name="blackEndTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		  		<td><s:property value="blackLimit"/></td>
		  		<td><s:property value="blackReason"/></td>
		  		<td><a href="#" onclick="removeBlack(<s:property value="blackId"/>,<s:property value="productId"/>);">删除</a></td>
		  	</tr>
            </s:iterator>
        </table>
    </div>
    <table width="700" border="0" align="center">
        <s:include value="/WEB-INF/pages/back/base/pag.jsp"/>
    </table>
</div>
<script type="text/javascript">
	function removeBlack(blackId,product){
		if(confirm('您确定要删除些规则吗?')){
			window.location.href = "${basePath}/prodblack/delete.do?prodBlackList.blackId="+blackId+"&productId="+product+"";
		}
	}
</script>
</body>
</html>
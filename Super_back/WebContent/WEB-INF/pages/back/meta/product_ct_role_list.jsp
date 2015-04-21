<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——产品预控列表</title>
<%@ include file="/WEB-INF/pages/back/base/jquery.jsp"%>
<%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/base/dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>js/meta/control_role.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/lvmama_common.js"></script>
<style type="text/css">
	.container_span_progress {
		border:solid #999 1px;
		background-color: #fff;
		height: 7px;
		overflow: hidden;
		display: inline-block;
		width:40px;
	}
	.content_span_progress {
		background-color: #19ED12;
		height: 7px;
		display: inline-block;
		margin: 0 !important;
	}
	.date_searcher_fill {
		width: 132px;
		height: 20px;
		border: 1px solid #A1C5E6;
		cursor:pointer;
	}
</style>
</head>
  
<body>
<div class="main main01">
	<div class="row1">
    	<h3 class="newTit">预控权限列表</h3>
   	<form action="<%=basePath%>/meta/toControlRoleList.do" method="post" id="query_form"><s:hidden name="productType"/>
      	<table width="980" border="0" cellspacing="0" cellpadding="0" class="newInput">
      		<tr>
		       	<td><em id="testTip">用户姓名：</em></td>
		       	<td><input type="text" class="newtext1" value="${condition.userName }" name="condition.userName" /></td>
		       	<td width="20"></td>
		        <td><input type="button" class="button" value="新增" id="newControlRoleBtn"/></td>
		        <td><input type="submit" class="button" value="查询"/></td>
      		</tr>
    	</table>
    </form>
    </div>
    <div class="row2">
    	<table width="96%" border="0" cellspacing="0" cellpadding="0" class="newTable">
              <tr class="newTableTit">
                <td align="center" style="width:50px;">用户姓名</td>
                <td align="center" style="width:40px;">查看范围</td>
                <td align="center" style="width:50px;">创建时间</td>
                <td align="center" style="width:35px;">修改时间</td>
                <td align="center" style="width:25px;">操作</td>
              </tr>
              <s:iterator value="roleList" id="role">
              <tr>
                <td align="center"><s:property value="userName"/></td>
                <td align="center"><s:property value="@com.lvmama.comm.vo.Constant$PRODUCT_CONTROL_ROLE@getCnName(roleArea)"/></td>
                <td align="center"><s:date name="createTime" format="yyyy-MM-dd HH:mm"/></td>
                <td align="center"><s:date name="updateTime" format="yyyy-MM-dd HH:mm"/></td>
                <td align="center">
                	<a href="javascript:showControlRoleDialog(<s:property value="productControlRoleId"/>)">修改</a> 
                	<a href="javascript:deleteRole(<s:property value="productControlRoleId"/>)">删除</a> 
                </td>
              </tr>
               </s:iterator>
      </table>
      <table width="96%">
      	<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
      </table>
    </div>
</div>
<div id="role_div" url="<%=basePath%>meta/editControlRole.do"></div>
</body>
</html>


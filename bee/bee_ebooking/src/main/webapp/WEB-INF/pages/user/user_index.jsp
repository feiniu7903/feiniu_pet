<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<style type="text/css">
label.error {
  color: Red;
  font-size: 13px;
  margin-left: 0;
  padding-left: 0;
}
</style>
<script type="text/javascript">
function initPasswordHandler(userId){
	if(confirm("确定初始化密码？")) {
		$.post("init_password.do",{userId:userId},function(data){
			if(data.success){
				alert("操作成功");
			}else{
				alert(data.msg);
			}
		});
	}
}
</script>
</head>
<body id="body_yhgl">
	<jsp:include page="../common/head.jsp"></jsp:include>
    <div class="breadcrumb">
		<ul class="breadcrumb_nav">
			<li class="home">首页</li>
	    	<li>用户管理</li>
	    </ul>
    	<a href="http://www.lvmama.com/zt/ppt/ebk/yonghu-guide.ppt" class="ppt_xz">用户管理操作PPT下载</a>
	</div>
	<dl class="order_nav order_nav_js">
		<dt>用户权限管理</dt>
	    <dd <s:if test="#request.valid==\"true\" || #request.valid==null">class="order_nav_dd"</s:if>>
	    	<a href="index.do?valid=true">有效用户</a>
	    </dd>
	    <dd <s:if test="#request.valid==\"false\"">class="order_nav_dd"</s:if>>
	    	<a href="index.do?valid=false">已关闭用户</a>
	    </dd>
	    <dd <s:if test="#request.valid==\"all\"">class="order_nav_dd"</s:if>>
	    	<a href="index.do?valid=all">全部用户</a>
	    </dd>
	    <dt class="new_yh">
	    	<img alt="" src="http://pic.lvmama.com/img/ebooking/new_yh.gif">
	    	<a href="to_add_user.do">新建用户</a>
	    </dt>
	</dl>
	
	<div class="order_all">
	  <div class="tableWrap">
		<p class="table01Header">
			全部用户
		</p>
		<table width="960" border="0" class="table01">
		    <tbody>
		    <tr class="even">
		      <th width="180">用户名</th>
              <th width="180">姓名</th>
		      <th width="200">所属部门</th>
		      <th width="180">是否可用</th>
		      <th>操作</th>
		    </tr>
		    <s:iterator value="ebkUserPage.items" var="item">
				<tr>
					<td><s:property value="userName" /></td>
					<td>
						${item.name }
					</td>
					<td>
						${item.department }
					</td>
					<td>
						<s:if test="#item.valid == \"true\"">是</s:if>
						<s:if test="#item.valid == \"false\"">否</s:if>
					</td>
					<td class="gl_cz">
						<a href="to_update_user.do?userId=${item.userId}">修改</a>
						<s:if test="#item.valid == \"true\"">
						<a href="javascript:void(0);" onclick="initPasswordHandler(${item.userId})">初始化密码</a>
						</s:if>
					</td>
				</tr>
	  		</s:iterator>
        	</tbody>
        </table>   
        <div>
        	<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pageSupplier(ebkUserPage,'')"/>
        </div> 
       </div>
	</div>
	<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>
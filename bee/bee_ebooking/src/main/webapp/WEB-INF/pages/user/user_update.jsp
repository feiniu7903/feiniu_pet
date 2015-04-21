<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<style type="text/css">
label.error {
	color: Red;
	font-size: 13px;
	margin-left: 0;
	padding-left: 0;
}
</style>
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script type="text/javascript" src="../js/base/jquery.validate.js"></script>
<link rel="stylesheet" href="../css/base/ztree/zTreeStyle.css">
<script type="text/javascript" src="../js/base/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="../js/base/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<SCRIPT type="text/javascript">
	$(document).ready(function() {
		$.post("get_user_permission_tree.do",
				{
					userId:$("#userIdHd").val()
				},
				function(data){
					var setting = {check : {enable : true},
							data : {
								simpleData : {
									enable : true
								}
							}
						};
				$.fn.zTree.init($("#permTree"), setting, data);
		},"json");
		$("#updateUserForm").validate({
			rules: {
				"user.name":{                         
					required: true
				}
			}, 
			messages: {
				"user.name": {                         
					required: "请输入姓名"
				}
			}, 
			errorPlacement: function (error, element) { 
		        error.appendTo(element.parent());                 
		    } 
		});
	});
	
	function saveHandler(){
		var treeObj = $.fn.zTree.getZTreeObj("permTree");
		var nodes = treeObj.getCheckedNodes(true);
		var ids = new Array();
		for(var i in nodes){
			ids.push(nodes[i].id);
		}
		if(ids.length == 0){
			alert("请选择权限");
			return false;
		}
		$("#permIdsHd").val(ids.toString());
		$("#updateUserForm").submit();
	}
</SCRIPT>
</head>
<body id="body_yhgl">
	<jsp:include page="../common/head.jsp"></jsp:include>
	<div class="breadcrumb">
		<ul class="breadcrumb_nav">
			<li class="home">首页</li>
			<li>用户管理</li>
	    	<li>修改用户</li>
		</ul>
	</div>
	<div class="snSpt_mainBox">
		<h4 class="snSpt_mainTit"></h4>
		<div class="snSpt_userinfoBox">
			<p class="tjyh_top"><a href="index.do" class="snSpt_userinfo_tit">返回用户管理 »</a></p>
			<div class="snSpt_userinfoBox_s snSpt_fl">
				<div class="snSpt_userinfoBox_t">用户资料</div>
				<form id="updateUserForm" action="update_user.do" method="post">
					<input type="hidden" id="userIdHd" name="user.userId" value="${user.userId }">
					<input type="hidden" id="permIdsHd" name="permIds">
					<ul class="snSpt_userinfolist">
						<li><label class="snSpt_lb" style="width: 80px;">用户名：</label>
							${user.userName}
						</li>
						<li><label class="snSpt_lb" style="width: 80px;">姓名：</label><input
							name="user.name" type="text" value="${user.name }"/></li>
						<li><label class="snSpt_lb" style="width: 80px;">部门：</label><input
							name="user.department" type="text" value="${user.department }"/></li>
						<li><label class="snSpt_lb" style="width: 80px;">固定电话：</label><input
							name="user.phone" type="text" value="${user.phone }"/></li>
						<li><label class="snSpt_lb" style="width: 80px;">手机：</label><input
							name="user.mobile" type="text" value="${user.mobile }"/></li>
						<li><label class="snSpt_lb" style="width: 80px;">邮箱：</label><input
							name="user.email" type="text" value="${user.email }"/></li>
						<li><label class="snSpt_lb" style="width: 80px;">状态：</label> 
						<label><input name="user.valid" type="radio" value="true" class="snSpt_radio" 
								<s:if test="user.valid == \"true\"">
								checked="checked"
								</s:if>
								>开启</label>
								&nbsp;&nbsp;&nbsp;&nbsp; 
						<label><input name="user.valid" type="radio" value="false" class="snSpt_radio"
							<s:if test="user.valid == \"false\"">
							checked="checked"
							</s:if>
						>关闭</label>
						</li>
					</ul>
				</form>
			</div>
			<div class="snSpt_userinfoBox_s snSpt_fr" style="height: 100%">
				<div class="snSpt_userinfoBox_t">
					权限范围 <i class="snSpt_titicon"></i>
				</div>
				<div class="snSpt_userpowers_box">
					<ul id="permTree" class="ztree"></ul>
				</div>
			</div>
			<div class="snSpt_btnR">
				<a class="snspt_Btn snspt_srBtn psw_sure" href="javascript:void(0)" onclick="saveHandler()">保存</a>
			</div>
		</div>
	</div>
	<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>
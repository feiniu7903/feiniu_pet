<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
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
<script type="text/javascript" src="../js/base/jquery.form.js"></script>
<link rel="stylesheet" href="../css/base/ztree/zTreeStyle.css">
<script type="text/javascript" src="../js/base/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="../js/base/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<SCRIPT type="text/javascript">
	$(document).ready(function() {
		$.post("get_permission_tree.do",function(data){
			var setting = {check : {enable : true},
							data : {
								simpleData : {
									enable : true
								}
							}
						};
			$.fn.zTree.init($("#permTree"), setting, data);
		},"json");
		$("#addUserForm").validate({
			rules: {    
				"user.userName":{
					required:true,
					remote:{
			               type:"POST",
			               url:"is_exist_username.do",             
			               data:{
			                  userName:function(){return $("#userNameInput").val();}
			               } 
		              }
				},
				"user.password":{
					required:true,
					number:true,
					minlength:6
				},
				"user.name":{                         
					required: true
				},
				"user.department":{                         
					required: true
				}
			}, 
			messages: {    
				"user.userName":{
					required:"请输入用户名",
					remote:"用户名已存在"
				},    
				"user.password":{
					required:"请输入密码",
					number:"请输入数字",
					minlength:"至少6位数字"
				},
				"user.name": {                         
					required: "请输入姓名"
				},
				"user.department": {                         
					required: "请输入 部门"
				}
			}, 
			errorPlacement: function (error, element) { 
		        error.appendTo(element.parent());                 
		    } 
		});
		$(".close").click(function(){
			
		})
	});
	
	function saveHandler(){
		var treeObj = $.fn.zTree.getZTreeObj("permTree");
		var nodes = treeObj.getCheckedNodes(true);
		var ids = new Array();
		for(var i in nodes){
			ids.push(nodes[i].id);
		}
		if(!$("#addUserForm").valid()){
			return false;
		}
		if(ids.length == 0){
			alert("请选择权限");
			return false;
		}
		$("#permIdsHd").val(ids.toString());
		$('#addUserForm').ajaxSubmit({
			success : function(responseText) { 
				if("SUCCESS" == responseText){
					tan_show();
					$("#addUserForm").reset();
				}else{
					alert('操作失败');
				}
			}
		});
	}
	 function tan_show(){
		 var index = $('.rizhi_show').index(this);
		 var _hight_w =$(window).height();
		 var _hight_t =$('.eject_rz').eq(index).height();
		 var _hight =_hight_w - _hight_t;
		 var _top = $(window).scrollTop()+_hight/2;
		 var height_w =$(document).height();
		 $('.eject_rz').eq(index).css({'top':_top}).show();
		 $('.bg_opacity2').eq(index).css({'height':_hight_t+31,'top':_top-5}).show();
		 $('.bg_opacity1').eq(index).css({'height':height_w,'width':$(document.body).width()}).show();
	} 
</SCRIPT>
</head>
<body id="body_yhgl">
	<jsp:include page="../common/head.jsp"></jsp:include>
	<div class="breadcrumb">
		<ul class="breadcrumb_nav">
			<li class="home">首页</li>
			<li>用户管理</li>
	    	<li>添加新用户</li>
		</ul>
	</div>

	<div class="snSpt_mainBox">
		<h4 class="snSpt_mainTit"></h4>
		<div class="snSpt_userinfoBox">
			<p class="tjyh_top"><a href="index.do?valid=true" class="snSpt_userinfo_tit">返回用户管理 »</a></p>
			<div class="snSpt_userinfoBox_s snSpt_fl">
				<div class="snSpt_userinfoBox_t">用户资料</div>
				<form id="addUserForm" action="save_user.do" method="post">
					<input type="hidden" id="permIdsHd" name="permIds">
					<ul class="snSpt_userinfolist">
						<li><label class="snSpt_lb" style="width: 80px;"><b style="color: red;">*</b>用户名：</label><input id="userNameInput"
							name="user.userName" type="text" /></li>
						<li><label class="snSpt_lb" style="width: 80px;"><b style="color: red;">*</b>密码：</label><input id="userPasswordInput"
							name="user.password" type="password" /></li>
						<li><label class="snSpt_lb" style="width: 80px;"><b style="color: red;">*</b>姓名：</label><input
							name="user.name" type="text" /></li>
						<li><label class="snSpt_lb" style="width: 80px;"><b style="color: red;">*</b>部门：</label><input
							name="user.department" type="text" /></li>
						<li><label class="snSpt_lb" style="width: 80px;">固定电话：</label><input
							name="user.phone" type="text" /></li>
						<li><label class="snSpt_lb" style="width: 80px;">手机：</label><input
							name="user.mobile" type="text" /></li>
						<li><label class="snSpt_lb" style="width: 80px;">邮箱：</label><input
							name="user.email" type="text" /></li>
						<li><label class="snSpt_lb" style="width: 80px;">状态：</label> 
						<label> <input name="user.valid" type="radio" value="true" class="snSpt_radio" checked="checked">开启</label>
								&nbsp;&nbsp;&nbsp;&nbsp; 
						<label><input name="user.valid" type="radio" value="false" class="snSpt_radio">关闭</label>
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
    
    <div class="bg_opacity1 show_hide"></div>
    <iframe class="bg_opacity2 show_hide"></iframe>
    <div class="eject_rz show_hide" style="text-align:left">
        <h4>添加成功</h4>
        <p class="tab_bot_p_3_t">新用户添加成功!</p>
        <p class="tab_bot_p_1 tab_bot_p_3">
            <a class="snspt_Btn snspt_srBtn psw_sure" href="to_add_user.do">继续添加</a>　　
            <a class="snspt_Btn snspt_srBtn psw_sure" href="index.do?valid=true">返回用户管理</a>
        </p>
        <span class="close" onclick="window.location.href='to_add_user.do';return true;"></span>
    </div>
</body>
</html>
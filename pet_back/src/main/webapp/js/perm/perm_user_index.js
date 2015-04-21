$(document).ready(function(){
	
});

//新增用户
function newUserHandler(){
	$("<iframe frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "新增用户",
        position: 'center',
        width: 800, 
        height: 220,  
        close: function(event,ui){
        	window.location.href=window.location.href; 
        }
	}).width(820).height(220).attr("src","to_add.do");
}

//显示用户信息
function showUserHandler(userId){
	$("<iframe frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "用户信息",
        position: 'center',
        width: 750, 
        height: 500
	}).width(720).height(480).attr("src","to_show.do?userId="+userId);
	
}
function editUserHandler(userId){
	$("<iframe frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "修改用户",
        position: 'center',
        width: 720, 
        height: 230
	}).width(700).height(230).attr("src","to_edit.do?userId="+userId);
}

//初始化密码
function initPasswordHandler(userId){
	$("<div/>").dialog({
		autoOpen: true, 
        modal: true,
        position: 'center',
        title : "提示",
        width: 180, 
        height: 100,
        buttons: { 
        	"确定": function() { 
        		$(this).dialog("close"); 
        		$.post("reset_password.do",
    					{
    						userId:userId
    					},
    					function(result){
    						if(result == "true"){
    							Utils.alert("操作成功");
    						}else{
    							Utils.alert("操作失败");
    						}
    					});
        		}
        }
	}).html("确定初始化此用户的密码？");
}
function showLogHandler(userId){
	$("<iframe frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "日志信息",
        position: 'center',
        width: 725, 
        height: 350
	}).width(700).height(350).attr("src","log.do?userId="+userId);
}
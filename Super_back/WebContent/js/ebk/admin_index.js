function closePopupWin(winId){
	$("#"+winId).dialog("close");
	window.location.reload();
}
function initPasswordHandler(userId){
	$("<div>确定初始化密码？</div>").dialog({
		modal:true,
		title:"提示",
		position:'center',
		width:200,
		height:100,
		buttons:{
			"确定":function(){
				$(this).dialog("close");
				$.post("init_password.do",{adminId:userId},function(data){
					if("success" == data){
						alert("操作成功");
					}else{
						alert("操作失败");
					}
				});
			}
		}
	});
}
function editAdminHandler(userId){
	$("<iframe id='editWin' frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "修改用户",
        position: 'center',
        width: 520, 
        height: 300
	}).width(540).height(320).attr("src","to_edit_admin.do?userId="+userId);
}

function userProductHandler(userId,supplierId,bizType){
	$("<iframe id='userProductWin' frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "产品权限",
        position: 'center',
        width: 900, 
        height: 550
	}).width(880).height(530).attr("src","to_admin_product.do?userId="+userId+"&supplierId="+supplierId+"&bizType="+bizType);
}

function userTargetHandler(userId,supplierId,bizType){
	$("<iframe id='userProductWin' frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "用户履行对象权限",
        position: 'center',
        width: '80%', 
        height: 550
	}).width('98%').height(530).attr("src","performTargetList.do?userId="+userId+"&supplierId="+supplierId+"&bizType="+bizType);
}
function searchMetaProduct(targetId){
	$("<iframe id='userProductWin' frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "采购产品信息",
        position: 'center',
        width: '98%', 
        height: 450
	}).width('98%').height(448).attr("src","searchMetaProduct.do?targetId="+targetId);
}
function showUserMenuHandler(userId,bizType){
	$("<iframe frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "用户菜单权限",
        position: 'center',
        width: 420, 
        height: 400
	}).width(400).height(400).attr("src","to_show_user_menu.do?userId="+userId+"&bizType="+bizType);
}

function canPrint(userId , trueOrfalse){
	if(window.confirm("确认"+(trueOrfalse=='true'?"开通打印权限吗？":"关闭打印权限吗？"))){
		$.post(
				"/super_back/ebk/admin/canPrint.do",
				{"userId":userId,
				 "print" : trueOrfalse
				},
			function(data){
				if(data=='success'){
					window.location.reload();
					alert("操作成功");
				}else{
					alert('操作失败');
				}
			});
	}
}
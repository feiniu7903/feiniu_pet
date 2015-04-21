function addAdminHandler(supId,supName){
	$("<iframe id='addAdminWin' frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "新增管理员用户",
        position: 'center',
        width: 520, 
        height: 300
	}).width(540).height(320).attr("src","to_add_admin.do?supId="+supId+"&supName="+encodeURIComponent(encodeURIComponent(supName)));
}
function adminManageHandler(supId,supName){
	$("<iframe id='adminManageWin' frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "账号管理",
        position: 'center',
        width: 960, 
        height: 540
	}).width(950).height(530).attr("src","admin_search.do?supId="+supId);
}

function closePopWin(winId){
	$("#"+winId).dialog("close");
	$("#"+winId).remove();
}
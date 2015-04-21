function addComplaint() {
	$("<iframe frameborder='0' ></iframe>").dialog({
		autoOpen : true,
		modal : true,
		title : "新增投诉",
		position : 'center',
		width : 780,
		height : 400
	}).width(750).height(380).attr("src", "order/complaint/addComplaint.do?flag=true");
}
function checkComplaint(){
	if (!document.getElementById("genderM").checked && !document.getElementById("genderW").checked) {
		alert("请选择性别！");
		return false;
	}
	if ($("#complaintName").val() == "") {
		alert("投诉会员名不能为空！");
		return false;
	}
	var mobile=$("#contactMobile").val();
	if (mobile == "") {
		alert("联系电话不能为空！");
		return false;
	}
	if ($("#contact").val() == "" ) {
		alert("投诉联系人不能为空！");
		return false;
	}
	if ($("#source").val() == "") {
		alert("请选择投诉来源！");
		return false;
	}
	if ($("#identity").val() =="") {
		alert("请选择投诉人身份！");
		return false;
	}
	if ($("#urgent").val() == "") {
		alert("是否紧急不能为空！");
		return false;
	}
	if ($("#replyAging").val() == "") {
		alert("请选择回复时效！");
		return false;
	}
	
	if ($("#sysCode").val() == "") {
		alert("请选择业务系统！");
		return false;
	}
	
	var relatedOrder=$("#relatedOrder").val();
	var relatedOrder1=relatedOrder.split(",");
	//判断输入的文本内容中是否存在中文输入法的逗号
	if(/，/.test(relatedOrder)){
	    alert("多个关联订单时，请使用英文逗号分隔");
	    return false;
	}
	if(relatedOrder!=""){
		for (var i = 0; i < relatedOrder1.length; i++) {
			if(relatedOrder1[i]==""){
	    		alert("输入的关联订单格式不对");
				return false;
			}
		}
	}
	var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	var email=$("#email").val();
	if(email!="" && !myreg.test(email)){
		alert('邮箱格式错误！');
        return false;
	}
/*
	if ($("#belongsCenter").val() == "") {
		alert("请选择所属中心！");
		return false;
	}
*/
	if ($("#detailsComplaint").val() == "") {
		alert("请输入投诉详情！");
		return false;
	}
	return true;
}
function checkTextLength(){
	$("#typeNameCount").text(20-$("#typeName").val().length);
	$("#typeNameCount1").text(20-$("#typeName").val().length);

	$("#typeDescriptionCount").text(100-$("#typeDescription").val().length);
	$("#typeDescriptionCount1").text(100-$("#typeDescription").val().length);
}

function checkTypeValue(){
	var typeName = $("#typeName").val();
	var sort = $("#sort").val();
	if(typeName==""){
		alert("请输入类型名称");
		return;
	}
	var reg=/^-?\d+$/;
	if(sort==""){
		alert("排序不能为空");
		return;
	}else if(!reg.test(sort)){
		alert("排序只能是整数");
		return;
	}
	return true;
}

//添加投诉类型
function addType() {
	if(!checkTypeValue()){
		return false;
	}
	
	$.post("complaintType/addType.do", 
			$("#addType_form").serialize(),
			function(data) {
				if ("SUCCESS" == data) {
					alert("操作成功");
					parent.location.reload(parent.location.href);
				}else if("ISEXIST"==data){
					alert("该投诉类型已经存在");
				} else {
					alert("操作失败");
				}
		}
	);
}
//修改投诉类型
function editType() {
	if(!checkTypeValue()){
		return false;
	}
	$.post("complaintType/update_type.do",
			$("#updateType_form").serialize(),
			function(data) {
				if ("SUCCESS" == data) {
					alert("操作成功");
					parent.location.reload(parent.location.href);
				} else {
					alert("操作失败");
				}
		}
	);
}
//验证输入的字符串的格式
function checkContent(obj){
	var obj1=obj.split(",");
	for (var i = 0; i < obj1.length; i++) {
		if(obj1[i]==""){
    		alert("输入人员的格式不对");
			return false;
		}
	}
	return true;
}
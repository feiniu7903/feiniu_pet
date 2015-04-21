$(document).ready(function(){
	$("#searchForm").validate({
			rules: {                     
				departmentName:{                         
					required: true                     
				},
				parentOrgId:{
					required:function(element){
						if($("#parentOrgLevelSlct").val() != "0" && $("#parentOrgSlct").val() == ""){
							return true;
						}
						return false;
					}
				}
			}, 
			messages: {                     
				departmentName: {                         
					required: "请选择组织名称"                    
				},
				parentOrgId:{
					required:"请选择父级组织"
				}
			}, 
			errorPlacement: function (error, element) { 
		        error.appendTo(element.parent());            
		    } 
		});
	$("#searchForm1").validate({
		rules: {                     
			departmentName:{                         
				required: true                     
			},
			parentOrgId:{
				required:function(element){
					if($("#parentOrgLevelSlct1").val() != "0" && $("#parentOrgSlct1").val() == ""){
						return true;
					}
					return false;
				}
			}
		}, 
		messages: {                     
			departmentName: {                         
				required: "请选择组织名称"                    
			},
			parentOrgId:{
				required:"请选择父级组织"
			}
		}, 
		errorPlacement: function (error, element) { 
	        error.appendTo(element.parent());            
	    } 
	});
	//加载一级部门
	Utils.setComboxDataSource("/pet_back/perm_organization/get_org_list_by_level.do?level="+$("#parentOrgLevelSlct").val(),
			"parentOrgSlct", true, undefined);
});
function levelChangeHandler(){
	Utils.setComboxDataSource("/pet_back/perm_organization/get_org_list_by_level.do?level="+$("#parentOrgLevelSlct").val(),
			"parentOrgSlct", true, undefined);
}
function deleteOrgHandler(orgId){
	$("<div />").dialog({
		autoOpen:true,
		modal:true,
		title:"提示",
		width:200,
		height: 100,
		buttons:{
			"确定":function(){
				$(this).dialog("close");
				$.post("delete_organization.do?orgId=" + orgId,
						function(data){
							if(data == 1){
								Utils.alert("存在下级组织，无法删除。");
							}else if(data == 2){
								Utils.alert("存在用户，无法删除。");
							}else if(data == "success"){
								window.location.reload();
							}else{
								Utils.alert("操作失败");
							}
						}
				);
			}
		}
	}).html("确定删除？");
}
function editOrgHandler(orgId,orgName,permLevel,parentOrgId){
	$("#orgIdHd").val(orgId);
	$("#departmentNameInput1").val(orgName);
	$("#parentOrgLevelSlct1").val(permLevel - 1);
	Utils.setComboxDataSource("/pet_back/perm_organization/get_org_list_by_level.do?level="+(permLevel - 1),
			"parentOrgSlct1", true, parentOrgId);
	$("#editWin").dialog({
		autoOpen:true,
		modal:true,
		title:"修改组织信息",
		width: 500,
		hight: 300
	});
}

function levelChangeHandler1(){
	Utils.setComboxDataSource("/pet_back/perm_organization/get_org_list_by_level.do?level="+$("#parentOrgLevelSlct1").val(),
			"parentOrgSlct1", true, undefined);
}

function orgHandler1(orgId){
	$("#ul1").empty();
	$("#ul2").empty();
	$("#ul3").empty();
	$.post("get_child_org_list.do",{orgId:orgId},
			function(data){
				$(data).each(function(i,n){
					$("#ul1").append("<li><span><a href=\"#\" onclick=\"deleteOrgHandler(" + n.orgId + ")\">删除</a>|" +
										"<a href=\"#\" onclick=\"editOrgHandler(" + n.orgId + ",'" + n.departmentName+"',"+n.permLevel+","+n.parentOrgId + ")\">修改</a></span>" +
										"<input id=\""+n.orgId+"\" type=\"radio\" value=\"" +
											n.orgId+"\" name=\"org2\" onclick=\"orgHandler2(" + n.orgId + ")\"/>" +
										"<label for=\""+ n.orgId + "\">" + n.departmentName + "</label>" 
									 + "</li>");
				});
			}
			,"json");
}
function orgHandler2(orgId){
	$("#ul2").empty();
	$("#ul3").empty();
	$.post("get_child_org_list.do",{orgId:orgId},
			function(data){
				$(data).each(function(i,n){
					$("#ul2").append(
							"<li><span><a href=\"#\" onclick=\"deleteOrgHandler(" + n.orgId + ")\">删除</a>|" +
							"<a href=\"#\" onclick=\"editOrgHandler(" + n.orgId + ",'" + n.departmentName+"',"+n.permLevel+","+n.parentOrgId + ")\">修改</a></span>" +
							"<input id=\""+n.orgId+"\" type=\"radio\" value=\"" +
								n.orgId+"\" name=\"org3\" onchange=\"orgHandler3(" + n.orgId + ")\"/>" +
							"<label for=\""+ n.orgId + "\">" + n.departmentName + "</label>" 
						 + "</li>"
						 );
				});
			}
			,"json");
}
function orgHandler3(orgId){
	$("#ul3").empty();
	$.post("get_child_org_list.do",{orgId:orgId},
			function(data){
				$(data).each(function(i,n){
					$("#ul3").append(
							"<li><span><a href=\"#\" onclick=\"deleteOrgHandler(" + n.orgId + ")\">删除</a>|" +
							"<a href=\"#\" onclick=\"editOrgHandler(" + n.orgId + ",'" + n.departmentName+"',"+n.permLevel+","+n.parentOrgId + ")\">修改</a></span>" +
							"<label>" + n.departmentName + "</label>" 
							+ "</li>");
				});
			}
			,"json");
}
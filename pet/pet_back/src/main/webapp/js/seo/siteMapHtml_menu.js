{
		function hide(id){
			var _id="."+id;
			$(_id).hide();
		}
		
		function editMainMenu(menuId,menuName,menuUrl,menuSeq){
			$("#ipt_edit_main_menuId").val(menuId);
			$("#ipt_edit_main_name").val(menuName);
			$("#ipt_edit_main_url").val(menuUrl);
			$("#ipt_edit_main_seq").val(menuSeq);
		}
		$('.closed').click(function() {
			$('.shanchu').hide();
		})
		
		function delMainMenu(menuId){
			if(!window.confirm("请确认你是否需要删除该菜单？")){
				return false;
			}
			var param = {'htmlMenuId':menuId};
    		$.ajax({type:"POST", url:basePath+"/seo/deleteMainMapMenu.do", data:param, dataType:"json", success:function (json) {
	    			if("true"==json.flag){
	    				alert("删除成功！");
						window.location.reload(true);
					}else{	
						alert("存在下级菜单，不允许删除");
					}
				}
    		});
		}
		function editMainMenu(menuId,menuName,menuUrl,menuSeq){
			$("#ipt_edit_main_menuId").val(menuId);
			$("#ipt_edit_main_name").val(menuName);
			$("#ipt_edit_main_url").val(menuUrl);
			$("#ipt_edit_main_seq").val(menuSeq);
			
			openWin("xiugai");
		}
		$('.closed').click(function() {
			$('.shanchu').hide();
		})
		function clean_ipt(id){
			var _id="#"+id;
			$(_id).html("");
			$(_id).removeClass("tips");
		}
		function chk_add_value(){
			var isValid=true;
			isValid=add();
			return isValid;
		}
		function add(){
			var isValid=true;
			if($("#ipt_add_menuName").val()==""){
				$("#div_add_menuName").html("请输入菜单名称");
				$("#div_add_menuName").addClass("tips");
				isValid=false;
			}else if($("#ipt_add_menuUrl").val()==""){
				$("#div_add_menuUrl").html("请输入菜单链接");
				$("#div_add_menuUrl").addClass("tips");
				isValid=false;
			}else if($("#ipt_add_seq").val()==""){
				$("#div_add_seq").html("请输入菜单seq排序值");
				$("#div_add_seq").addClass("tips");
				isValid=false;
			}else if(isNaN($("#ipt_add_seq").val())||!/^\d+$/.test($("#ipt_add_seq").val())){
				$("#div_add_seq").html("请输入标准的seq排序值");
				$("#div_add_seq").addClass("tips");
				isValid=false;
			}
			return isValid;
		}
		function chk_edit_value(){
			var isValid=true;
			isValid=edit();
			return isValid;
		}
		
		function edit(){
			var isValid=true;
			if($("#ipt_edit_main_name").val()==""){
				$("#div_edit_main_name").html("请输入菜单名称");
				$("#div_edit_main_name").addClass("tips");
				isValid=false;
			}else if($("#ipt_edit_main_url").val()==""){
				$("#div_edit_main_url").html("请输入菜单链接");
				$("#div_edit_main_url").addClass("tips");
				isValid=false;
			}else if($("#ipt_edit_main_seq").val()==""){
				$("#div_edit_main_seq").html("请输入菜单seq排序值");
				$("#div_edit_main_seq").addClass("tips");
				isValid=false;
			}else if(isNaN($("#ipt_edit_main_seq").val())||!/^\d+$/.test($("#ipt_edit_main_seq").val())){
				$("#div_edit_main_seq").html("请输入标准的seq排序值");
				$("#div_edit_main_seq").addClass("tips");
				isValid=false;
			}
			return isValid;
		}
		function chkChildAddValue(){
			var isValid=true;
			if(($('#ipt_add_menuId option:selected').val()=="")){
				$("#div_add_menuId").html("请选择上级菜单");
				$("#div_add_menuId").addClass("tips");
				isValid=false;
			}
			if(isValid){
				isValid=add();
			}
			return isValid;
		}
		function chkChildEditValue(){
			var isValid=true;
			if(($('#ipt_edit_menuId option:selected').val()=="")){
				$("#div_edit_menuId").html("请选择上级菜单");
				$("#div_edit_menuId").addClass("tips");
				isValid=false;
			}
			if(isValid){
				isValid=edit();	
			}
			return isValid;
		}
		function chk_seq(){
			var isDigital=true;
			$("[name='mapMenuSeq']").each(function(){
				if(isNaN($(this).val())||!/^\d+$/.test($(this).val())){
					alert('排序值【'+$(this).val()+'】必须为非负整数!');
					$(this).focus();
					isDigital=false;
				}
			})
			return isDigital;
			
		}
	}

$(function(){
	$("table.p_table input.p_num").change(function(){
		$(this).parent("td").siblings("td").find("input[type=checkbox]").attr("checked","checked");
	});
});
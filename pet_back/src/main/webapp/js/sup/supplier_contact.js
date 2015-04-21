$(function(){
	$("a.editContact").live("click",function(){
		var supplierId=$(this).attr("data");
		$("#editContact").showWindow({
			data:{"supplierId":supplierId}
		});
	});
	var addContactDiv;
	$("a.addContactBtn").live("click",function(){
		var supplierId=$(this).attr("data");
		addContactDiv=$("#addContactDiv").showWindow({
			data:{"supplierId":supplierId}
		});
	});
	$("a.editContactBtn").live("click",function(){
		var contactId = $(this).attr("data");
		var supplierId = $(this).attr("supplierId");
		addContactDiv = $("#addContactDiv").showWindow({url:basePath+"/sup/contact/toEditContact.do",data:{"contact.contactId":contactId,"supplierId":supplierId}});
	});
	$("input.contactSubmit").live("click",function(){
		var $form=$(this).parents("form");
		$form.validateAndSubmit(function($form,dt){
			var supplierId=$form.find("input[name=contact.supplierId]").val();
			var data=eval("("+dt+")");
			if(data.success){
				alert("操作成功");
				addContactDiv.dialog("close");
				$("#editContact").resetWindow({
					data:{"supplierId":supplierId}
				});
			}else{
				alert(data.msg);
			}
		},{
			onSubmit:function($form){
				// 校验手机格式
				var mobilephone = $form.find("input[name='contact.mobilephone']").val();
				if($.trim(mobilephone) != "") {
					if (mobilephone.length != 11) {
						alert("手机号必须为11位！");
						return false;
					}
					var MOBILE_REGX = /^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/;
					if (!MOBILE_REGX.test(mobilephone)) {
						alert("手机号格式不正确");
						return false;
					}
					return true;
				}
			}
		});
	});
	// 绑定联系人到指定的对象
	$("input.bindContactBtn").live("click",function(){
		var $this=$(this);
		var $form=$this.parents("form");
		var supplierId=$form.find("input[name$=supplierId]").val();
		var selected=$form.find("input[name=contactListId]").val();
		var $bindDiv=$("#bindContactDiv").showWindow({
			width:800,
			data:{"supplierId":supplierId,
				"selectedSupplierId":selected
			},
			buttons:{
				"添加":function(){
					var selected="";
					var showContent="";
					$.each($("#bindContactDiv input[name=contactId]:checked"),function(i,item){
						if(i>0){
							selected+=",";
						}
						var cId=$(item).val();
						selected+=cId;
						showContent+="<div contactId='"+cId+"'><span>"+$("#bindContactDiv #td_contactId_"+$(item).val()).html()+"</span><a href='javascript:void(0)' class='deleteRelation'>删除</a></div>";
					});
					
					if($.trim(selected)==""){
						alert("你没有选中联系人");
						return false;
					}
					
					$form.find("input[name=contactListId]").val(selected);
					$form.find("#contact_show_pos").html(showContent);
					$bindDiv.dialog("close");
				}
			}
		});
	});
	
	$("a.deleteRelation").live("click",function(){
		var $div=$(this).parent("div");
		var $form=$(this).parents("form");
		$div.remove();
		var contactList=$form.find("#contact_show_pos div[contactId]");
		var content="";
		$.each(contactList,function(i,item){
			if(i>=0){
				content+=",";
			}
			content+=$(item).attr("contactId");
		});		
		$form.find("input[name=contactListId]").val(content);
	});
});
/**
 * @author shangzhengyuan
 */
function isDate(s) 
{ 
	var patrn=/^[1-9]{1}[0-9]{3}-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$/; 
	if (!patrn.exec(s)) {
		return false;
	}
	return true;
} 
function show_message(){
	var index = $('.queryebkcomlog').index(this);
	var _hight_w =$(window).height();
	var _hight_t =$('.eject_rz').eq(index).height();
	var _hight =_hight_w - _hight_t;
	var _top = $(window).scrollTop()+_hight/2;
	var height_w =$(document).height();
	$('.eject_rz').eq(index).css({'top':_top}).show();
	$('.bg_opacity2').eq(index).css({'height':_hight_t+31,'top':_top-5}).show();
	$('.bg_opacity1').eq(index).css({'height':height_w,'width':$(document.body).width()}).show();
	} 
function validate(){
	var bTime = $(":text[name=auditTimeStart]").val();
	var eTime = $(":text[name=auditTimeEnd]").val();
	var prodProductId = $(":text[name=prodProductId]").val();
	if((bTime=="" && eTime=="") ||(bTime==undefined && eTime==undefined)){
		bTime = $(":text[name=commitTimeStart]").val();
		eTime = $(":text[name=commitTimeEnd]").val();
	}
	if (bTime != "" && bTime!=undefined) {
		if (!isDate(bTime)) {
			$("#errormessageid").html("<font color='red'>开始日期格式错误</font>");
			return false;
		}
	}
	if (eTime != "" && eTime!=undefined) {
		if (!isDate(eTime)) {
			$("#errormessageid").html("<font color='red'>结束日期格式错误</font>");
			return false;
		}
	}
	if (bTime!='' && eTime!='' && bTime!=undefined && eTime!=undefined){
		if (bTime>eTime){
			$("#errormessageid").html("<font color='red'>开始日期不能小于结束日期</font>");
			return false;
		}
	}
	if(prodProductId !=undefined && prodProductId!="" && !(/^\d+$/.test(prodProductId))){
		$("#errormessageid").html("<font color='red'>驴妈妈产品ID请输入数字</font>");
		return false;
	}
	return true;
}
$(function(){
	$(document.body).ready(function(){
		var optionsTable = {
				url : $("#basepathhiddenid").val()+"product/queryAllCount.do?tokenTime="+(new Date()).getTime()+"&ebkProductViewType="+$("#ebkproductviewtypehiddenid").val(),
				type : 'GET',
				dataType : "text",
				success : function(data) {
					data = eval("("+data+")");
					var id = "productcountid";
					if (data.success) {
						if(null!=data.message.ALL){$("#ALL"+id).html(data.message.ALL)};
						if(null!=data.message.UNCOMMIT_AUDIT){$("#UNCOMMIT_AUDIT"+id).html(data.message.UNCOMMIT_AUDIT);};
						if(null!=data.message.PENDING_AUDIT){$("#PENDING_AUDIT"+id).html(data.message.PENDING_AUDIT);};
						if(null!=data.message.REJECTED_AUDIT){$("#REJECTED_AUDIT"+id).html(data.message.REJECTED_AUDIT);};
						if(null!=data.message.THROUGH_AUDIT){$("#THROUGH_AUDIT"+id).html(data.message.THROUGH_AUDIT);};
					}
				},
				error : function() {
				}
			};
			$.ajax(optionsTable);
			$("#queryProductForm").find(":text,select").each(function(){$(this).val($("#"+$(this).attr("name")+"_parameterhidden").val());});
	});
	 $('.text_ts').ui('lvtip',{
		 hovershow: 200
		 }); 
	$("#searchAllProductBtn").click(function(){
		if(validate())
		$("#queryProductForm").submit();
	});
	$(":text,select").focus(function(){
		$("#errormessageid").html("");
	});
	$(".auditcommitebkproduct").click(function(){
		var ebkprodproductid = $(this).parent("td").attr("ebkprodproductid");
		var ebkprodproductname = $(this).parent("td").attr("ebkprodproductname");
		var optionsTable = {
				url : $("#basepathhiddenid").val()+"product/audit/auditCommit.do?ebkProdProductId="+ebkprodproductid+"&r="+Math.random(),
				type : 'GET',
				dataType : "json",
				success : function(data) {
					if(data.success){
						alert("提交审核成功");
						document.location.reload();
					}else if(data.message == -555){
						alert("该产品的共享库存未设置共享类别，不允许提交审核");
					}else if(data.message == -404){
						alert("审核状态不是未提交，不能提交审核");
					}else{
						alert("提交审核失败");
					}
				},
				error : function() {
					alert("提交审核出错");
				}
			};
		if(confirm("确认要提交审核【"+ebkprodproductname+"】 产品？"))
		   {
			 $.ajax(optionsTable);
		   }
	});
	$(".auditrevokeebkproduct").click(function(){
		var ebkprodproductid = $(this).parent("td").attr("ebkprodproductid");
		var ebkprodproductname = $(this).parent("td").attr("ebkprodproductname");
		var optionsTable = {
				url : $("#basepathhiddenid").val()+"product/audit/auditRevoke.do?ebkProdProductId="+ebkprodproductid+"&r="+Math.random(),
				type : 'GET',
				dataType : "json",
				success : function(data) {
					if(data.success){
						alert("撤消审核成功");
						document.location.reload();
					}else if(data.message == -404){
						alert("审核状态不是待审核，不能撤消审核");
					}else{
						alert("撤消审核失败");
					}
				},
				error : function() {
					alert("撤消审核出错");
				}
			};
		 if(confirm("确认要撤消审核【"+ebkprodproductname+"】 产品？"))
		   {
			 $.ajax(optionsTable);
		   }
	});
	//重新编辑产品
	$(".auditrecover").click(function(){
		var ebkprodproductname = $(this).parent("td").attr("ebkprodproductname");
		var ebkprodproductid = $(this).parent("td").attr("ebkprodproductid");
		var optionsTable = {
				url : $("#basepathhiddenid").val()+"product/audit/auditRecover.do?ebkProdProductId="+ebkprodproductid+"&r="+Math.random(),
				type : 'GET',
				dataType : "json",
				success : function(data) {
					if(data.success){
						window.location.href=$("#basepathhiddenid").val()+"ebooking/product/editEbkProductInit.do?ebkProdProductId="+ebkprodproductid+"&r="+Math.random();
					}else if(data.message == -500){
						alert("重新编辑失败");
					}else{
						alert("重新编辑失败，有可能未登录或产品编号为空");
					}
				},
				error : function() {
					alert("重新编辑出错");
				}
			};
		 if(confirm("确认要重新编辑【"+ebkprodproductname+"】 产品？"))
		   {
			 $.ajax(optionsTable);
		   }
	});
	//查看驳回信息
	$(".queryrejectedmessage").click(function(){
		var ebkprodproductid = $(this).parent("td").attr("ebkprodproductid");
		var prodName = $(this).parent("td").attr("ebkprodproductname");
		var optionsTable = {
				url : $("#basepathhiddenid").val()+"product/query/rejectMessage.do?ebkProdProductId="+ebkprodproductid,
				type : 'GET',
				dataType : "html",
				success : function(data) {
					$.dialog({
						title: '产品 【'+prodName+'】审核驳回信息',
						content: data,
						width:720
					});
					log_view();
				},
				error : function() {
				}
		};
		$.ajax(optionsTable);
	});
	function log_view(title){
		var pagelink =$(".dialog").find(".PageLink,.PrevPage");
		if(undefined!=pagelink){
			pagelink.each(function(){
				var href_url = $(this).attr("href");
				$(this).attr("href","javascript:void(0);");
				if(href_url.length>4){
					$(this).live("click",function(){
						var optionsTable = {
								url :href_url,
								type : 'GET',
								dataType : "html",
								success : function(data) {
									$(".dialog-close").trigger("click");
									$.dialog({
										title: title,
										content: data,
										width:800
									});
									log_view();
								},
								error : function() {
								}
						};
						$.ajax(optionsTable);
					});
				}
			});
		}
	}
	$(".queryebkcomlog").live("click",function(){
		var parentid = $(this).parent("td").attr("parentid");
		var parenttype = $(this).parent("td").attr("parenttype");
		var prodName = $(this).parent("td").attr("ebkprodproductname");
		var optionsTable = {
				url : $("#basepathhiddenid").val()+"log/queryLogMessage.do?parentType="+parenttype+"&parentId="+parentid,
				type : 'GET',
				dataType : "html",
				success : function(data) {
					$.dialog({
						title: '操作日志',
						content: data,
						width:800
					});
					log_view();
				},
				error : function() {
				}
		};
		$.ajax(optionsTable);
	});
	$(".close").live("click",function(){$('.show_hide').hide();});
	
	
	
	
	//一键导入
	$("#import-prod-from-super-btn").live("click",function(){
		var _this=$(this);
		var status=$(_this).attr("status");
		var type=$(_this).attr("data-type");
		if(status=="open"||status==undefined){
			$(_this).html("导入中...");
			$(_this).attr("status","close");
			$.ajax({
				url : $("#basepathhiddenid").val()+"product/importProdByType.do?ebkProductViewType="+type,
				type : 'GET',
				dataType : "json",
				success : function(data) {
					if(data.success==true){
						alert("导入成功");
					}else{
						alert(data.message);
					}
					$(_this).html("一键导入产品");
					$(_this).attr("status","open");
					document.location.reload();
				},
				error : function() {
					alert("网络异常，请稍后");
					$(_this).html("一键导入产品");
					$(_this).attr("status","open");
				}
			});
		}
	});
	
	
	
	
	
	
});
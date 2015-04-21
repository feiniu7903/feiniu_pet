function showInvoiceDetailDiv(invoiceId){
	document.getElementById("invoiceDetailDiv").style.display = "block";
	document.getElementById("bg").style.display = "block";
	$("#invoiceDetailDiv").openDialog();
	$("#invoiceDetailDiv").reload({"invoiceId":invoiceId});
	
}

$(function(){
	$("input.date").datepicker({dateFormat:'yy-mm-dd'});	
	
	$("#inputUserId").jsonSuggest({
		url:"/super_back/user/searchUser.do",
		maxResults: 10,
		minCharacters:1,
		onSelect:function(item){
			$("#orderUserId").val(item.id);
		}
	});

	/*取消订单*/
	$("a.cancel").live("click",function(){
		if(!confirm("确定需要取消当前发票")){
			return false;
		}
		var $this=$(this);
		var result=$this.attr("result");
		var t=$this.attr("t");//打开的位置
		$.post("/super_back/ord/invoiceChangeStatus.do",{"invoiceId":result,"status":"CANCEL"},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				if(t=='list'){//为列表页需要更新对应的信息
					var $td=$("#status_"+result);
					$td.attr("status",data.status);
					$td.html(data.zhStatus);
					if(typeof(current_flag)!=undefined&&current_flag=='red_manager'){
						$td = $this.parent("td");
						$td.find("a.closeRed").remove();
					}
					$this.remove();
				}else{
					showInvoiceDetailDiv(result);
				}
			}else if(data.code==-2){//自动废票
				alert(data.msg);
				if(t=='list'){
					var $td=$("#status_"+result);
					$td.attr("status",'CANCEL');
					$td.html('废票');
					$this.remove();
				}else{
					showInvoiceDetailDiv(result);
				}
			}
		});
	});
	
	/**确认红冲订单**/	
	$("a.confirmRed").live("click",function(){
		if(!confirm("确定需要将当前发票进行确认红冲操作？")){
			return false;
		}
		var $this=$(this);
		var result=$this.attr("result");
		var t=$this.attr("t");//打开的位置
		$.post("/super_back/ord/invoiceChangeStatus.do",{"invoiceId":result,"status":"RED"},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				if(t=='list'){//为列表页需要更新对应的信息
					var $td=$("#status_"+result);
					$td.attr("status",data.status);
					$td.html(data.zhStatus);					
					$td=$this.parent("td");
					$td.find("a.closeRed").remove();
					$this.remove();
				}else{
					showInvoiceDetailDiv(result);
				}
			}
		});
	});

	$("a.closeRed").live("click",function(){
		if(!confirm("确定需要将当前发票进行关闭操作？")){
			return false;
		}
		
		var $this=$(this);
		var result=$this.attr("result");
		$.post("/super_back/ord/doCloseRedInvoice.do",{"invoiceId":result},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				$("#tr_"+result).remove();
			}else{
				alert(data.msg);
			}
		});
	});
		
	$("button.change_curr_status").live("click",function(){
		if(!confirm("确定要变更当前状态")){
			return false;
		}
		var status=$(this).attr("ok_status");
		var invoiceId=$(this).attr("invoiceId");
		$.post("/super_back/ord/invoiceChangeStatus.do",{"invoiceId":invoiceId,"status":status},function(dt){
			try{
				var data=eval("("+dt+")");
			}catch(ex){
				alert("数据加载错误");
				return false;
			}
			if(data.success){
				showInvoiceDetailDiv(invoiceId);
			}else if(data.code==-2){//状态自动变成取消
				alert(data.msg);
				showInvoiceDetailDiv(invoiceId);
			}else{
				alert(data.msg);
			}
		});
	});
	
	/*全选*/
	$("input.allsel").click(function(){
		$("input[name=invoiceId].checks").attr("checked",$(this).attr("checked"))
	});
	
	
	function getSelectedInvoiceList(){
		var $list=$("input[name=invoiceId].checks:checked");
		if($list.size()==0){
			throw "请先选中要操作的发票";
		}
		
		var invoice_ids="";
		var skip_flag=false;
		$.each($list,function(i,n){
			if(i>0){
				invoice_ids+=",";
			}
			var id=$(n).val();
			var status=$("#status_"+id).attr("status");
			if(status=='CANCEL'||status=='BILLED'||status=='POST'||status=='COMPLETE'){
				throw "您选中的序号:"+id+"不可以变更到审核通过或已开票";				
			}
			
			invoice_ids+=id;
		});
		
		if($.trim(invoice_ids)===''){
			throw "请先选中要操作的发票";
		}
		return invoice_ids;
	}
	
	$("input.approve").click(function(){
		if(!confirm("确定要改成审核通过")){
			return false;
		}
		
		var invoice_ids="";
		try{
			invoice_ids=getSelectedInvoiceList();			
		}catch(er){
			alert(er);
			return false;
		}
		
		$.post("/super_back/ord/invoiceListApprove.do",{"invoices":invoice_ids},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				var str="更新成功条数:"+data.results.length;
				if(data.hasCancel){
					str+="\n";
					str+=data.cancel;
				}
				alert(str);
				window.location.reload();
			}else{
				alert(data.msg);
			}
		})
	});
	
	$("input.bill_btn").click(function(){
		if(!confirm("确定要改成已开票")){
			return false;
		}
		var invoice_ids="";
		try{
			invoice_ids=getSelectedInvoiceList();			
		}catch(er){
			alert(er);
			return false;
		}
		
		$.post("/super_back/ord/invoiceListBill.do",{"invoices":invoice_ids},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				alert("更新成功条数:"+data.results.length);
				window.location.reload();
			}else{
				alert(data.msg);
			}
		});
	});
	
	$("input.express_input").live("click",function(){
		var $form=$("#expressForm");
		var invoiceId=$form.find("input[name=invoiceId]").val();
		if($.trim(invoiceId)==''){
			alert("信息不完整,重新刷新页面再操作");
			return false;
		}
		
		var expressNo=$form.find("input[name=expressNo]").val();
		if($.trim(expressNo)==''){
			alert("快递单号不可以为空");
			return false;
		}
		
		$.post("/super_back/ord/updateInvoiceExpress.do",{"invoiceId":invoiceId,"expressNo":expressNo},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				alert("操作成功");
				showInvoiceDetailDiv(invoiceId);
			}else{
				alert(data.msg);
			}
		});
	});
	
	/**修改发票单号**/
	$("input.invoice_input").live("click",function(){
		var $form=$("#invoiceForm");
		var invoiceId=$form.find("input[name=invoiceId]").val();
		if($.trim(invoiceId)==''){
			alert("信息不完整，重新刷新页面再操作");
			return false;
		}
		
		var invoiceNo=$form.find("input[name=invoiceNo]").val();
		if($.trim(invoiceNo)==''){
			alert("发票单号不可以为空");
			return false;
		}
		
		$.post("/super_back/ord/updateInvoiceNo.do",{"invoiceId":invoiceId,"invoiceNo":invoiceNo},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				alert("操作成功");
				showInvoiceDetailDiv(invoiceId);
			}else{
				alert(data.msg);
			}
		});
	});
	
	$("input.export").click(function(){
		var res=$(this).attr("result");
		var $form=$("#exportDiv");
		$form.attr("action","/super_back/ord/export/"+res+".do");
		$form.html($("#searchForm").html());
		document.getElementById("exportDiv").submit();
	});
	
	$("button.printInvoiceBtn").live("click",function(){
		var invoiceId=$("#td_invoice").text();
		var title=$("#td_title").text();
		var price=$("#td_price").text();
		var detail=$("#td_detail").text();
		//var memo=$("#td_memo").text();
		var invoiceActiveX=document.getElementById("invoiceActiveX");
		if(!invoiceActiveX){
			alert("打印控件没有安装");
			return false;
		}
		var res=invoiceActiveX.printInvoice(invoiceId,title,detail,price,"1","");		
		alert("操作完成，请查看发票号并自己添加");
	});
	
})
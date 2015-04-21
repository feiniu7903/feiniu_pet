/**
 * 
 * 该js在订单资源审核当中使用
 * 
 */
function bindSuggest($div){
	$div.find("input[name=searchMeta]").jsonSuggest({
		url:"/super_back/meta/searchMetaList.do",
		maxResults: 10,
		minCharacters:1,
		emptyKeyup:false,
		param:["#change_productType","#change_subProductType"],
		onSelect:function(item){				
			$("input[name=metaProductId]").val(item.id);
			var $select=$div.find("select[name=metaBranchId]");
			$select.empty();
			var branchType=$div.find("input[name=branchType]").val();
			var adult=$div.find("input[name=adultQuantity]").val();
			var child=$div.find("input[name=childQuantity]").val();
			$.post("/super_back/meta/getMetaBranchJSON.do",{"metaProductId":item.id,"branchType":branchType,"adultQuantity":adult,"childQuantity":child},function(dt){
				try{
					var data=eval("("+dt+")");
					if(data.success){
						if(!data.find){
							showError("没有取到采购产品的类别信息");							
						}else{			
							$select.append("<option value=''>请选择</option>");
							for(var i=0;i<data.list.length;i++){					
								$select.append("<option value='"+data.list[i].branchId+"'>"+data.list[i].branchName+"</option>");
							}
							$select.find("option:first").attr("selected","selected");
						}
					}else{
						showError(data.msg);
					}
				}catch(ex){}
			});
		}
	});
}

function selectMetaBranch(index){
	showError("");
	if(index==-1){
		return;
	}
	
	var $div=$("#changeOrderItemMateDiv");
	$("#selectMetaBranchDiv").html(""); 
	if(index>0){	
		var metaBranchId=$div.find("select[name=metaBranchId] option:selected").val();
		var visitTime=$div.find("input[name=visitTime]").val();
		var orderMetaId=$div.find("input[name=orderMetaId]").val();
		var orderId=$div.find("input[name=orderId]").val();
		$.post("/super_back/ordItem/getMetaBranch.do",{"orderItemMeta.orderId":orderId,"orderItemMeta.metaBranchId":metaBranchId,"orderItemMeta.visitTime":visitTime,"orderItemMeta.orderItemMetaId":orderMetaId},function(data){
			if(data.success){
				var html=getOrderItemTable(data);
				$("#selectMetaBranchDiv").html(html);
			}else{				
				showError(data.msg);
			}
		},"json")
	}
}
function getOrderItemTable(data){
	var body = "<table class='box'>";
		body+= "<tr>";
		body+= "<td>子单号</td><td>采购产品ID</td><td>采购产品名称</td><td>类别</td><td>结算单价</td><td>数量</td><td>产品类型</td><td>供应商</td></tr>";
		body+= "<tr>";
		body+= "<td>"+data.orderItemMetaId+"</td>";
		body+= "<td>"+data.metaProductId+"</td>";
		body+= "<td>"+data.metaProductName+"</td>";
		body+= "<td>"+data.metaBranchName+"</td>";
		body+= "<td>"+data.settlementPrice+"</td>";
		body+= "<td>"+data.quantity+"</td>";
		body+= "<td>"+data.zhProductType+"</td>";
		body+= "<td>"+data.supplierName+"</td>";
		body+= "</tr></table><div style='color:red;font-weight:bold'>请仔细检查，切勿添加错误！结算价以保存时刻的结算价为准！</div>";
	return body;	
}
function showError(str){
	$("#changeOrderItemMateMsg").html(str);
}

function doShowChangeDialog(){
	var $dlg=$("#changeOrderItemMateDiv");	
	showError("");
	$dlg.find("input[name=searchMeta]").val('');
	$dlg.find("select[name=metaBranchId]").empty();
	$("#selectMetaBranchDiv").html("");
	$dlg.dialog({
		title:"修改订单采购产品",
		modal:true,
		height:480,		
		width:800,		
		buttons:{
			"保存":function(){
				if(!confirm("确认要更改为选中的采购产品")){
					return false;
				}
				var metaBranchId=$dlg.find("select[name=metaBranchId] option:selected").val();
				if(typeof(metaBranchId)==undefined||$.trim(metaBranchId)==''){
					showError("没有选中要替换的类别");
					return false;
				}
				var orderMetaId=$dlg.find("input[name=orderMetaId]").val();
				var orderId=$dlg.find("input[name=orderId]").val();
				$.post("/super_back/ordItem/doUpdateOrderMeta.do",{"orderId":orderId,"metaId":orderMetaId,"metaBranchId":metaBranchId},function(data){
					if(data.success){
						alert("更新成功")
						$dlg.dialog("destroy").remove();
						if(data.resourceAmple){//如果是资源已经审核通过
							window.location.reload();
						}else{
							$("#approveDiv").reload({"orderId": orderId, "metaId": metaId});							
						}
					}else{
						showError(data.msg);
					}
				},"json");
			}
		},
		open:function(e){
			bindSuggest($dlg);
		}
		
	});
}
function showLogFn(id){
	var params ={'ordTmallDistributorMapId' : id};
	$.post(
			"showLog.do",
			params,
			function(dt) {
				var data = eval("("+dt+")");
				var content = '<div style="float:left;margin-left:-50px;width:300px;height:35px;"><table><tr style="height:20px;"><td align="right" width="25%">操作员:</td>';
					content += '<td align="left" width="35%">'+data.operator+'</td></tr>';
					content += '<tr style="height:40px;"><td align="right" width="25%">操作时间:</td>';
					content += '<td align="left" width="35%">'+data.time+'</td></tr>';
				if (data != 'SUCCESS') {
					art.dialog({
						title : '操作日志',
						content : content,
						ok : true,
						okValue : '确定',
						lock : true
					});
					}
			}
		);
}

function manualProcessFn(id){
	var content =  '<div style="float:left;margin-left:-50px;width:290px;height:70px;">';
	    content += '<table>';
	    content += '<tr style="height:20px;">';
	    content += '<td align="right" width="55%">人工搬单：</td>';
		content += '<td>';
		content += '<select  name="result" disabled="true">';
		content += '<option value="success">搬单成功</option>';
		content += '</select>';
		content += '</td>';
		content += '</tr>';
		content += '<tr style="height:30px;">';
		content += '<td align="right" width="55%" >驴妈妈订单号<font color="red">*</font>：</td>';
		content += '<td rowspan="1"><input type="text" name="lvOrder" id="lvOrder" /></td>';
		content += '</tr>';
		content += '<tr style="height:30px;"><td align="right" width="55%">客服工号<font color="red">*</font>：</td>';
		content += '<td rowspan="1"><input type="text" name="seller" id="seller"/></td>';
		content += '</tr>';
		content += '</table></div>';
		art.dialog({
			id : "confirm-dialog",
			title : "淘宝人工搬单",
			content : content,
			okValue : '确定',
			ok : function() {
				var lvorder = $("#lvOrder").val();
				var seller=$("#seller").val();
				var reg = new RegExp("^[0-9]*$");
				if ($.trim(lvorder).length == 0) {
					alert('订单号必填！');
					return false;
				}
               if(!reg.test(lvorder)){
					alert('订单号不合法');
					return false;
				}
				if($.trim(seller).length==0){
					alert('工号必填');
					return false;
				}
				var params = {
						'ordTmallDistributorMapId' : id,
						'lvOrderId' : lvorder,
						'seller' :seller
					};
				$.post(
						"manualProcess.do",
						params,
						function(dt) {
							var data = eval("("+dt+")");
							if (data.flag == 'SUCCESS') {
								art.dialog({
									title : '消息',
									content : '操作成功',
									ok : true,
									okValue : '确定',
									lock : true
								});
								$("#processStatus_"+id).html("<a  class=\"budget_see\"  href=\"javascript:showLogFn('"+id+"');\">已处理</a>");
								$("#processStatus_"+id).prev().prev().html("否");
								$("#processStatus_"+id).prev().prev().prev().html(data.lvOrderId);
							}else {
								art.dialog({
									title : '消息',
									content : '操作失败',
									ok : true,
									okValue : '确定',
									lock : true
								});
							}
						}
						);
			},
			cancelValue : '取消',
			cancel : true,
			lock : true
		});
}
function updateProcessStatus(id,status,flag){
	var params = {
			'ordTmallDistributorMapId' : id,
			'status' : status
		};
	$.post(
			"updateProcessStatus.do",
			params,
			function(dt) {
				var data = eval("("+dt+")");
				if(data.flag == 'SUCCESS'){
					art.dialog({
						title : '消息',
						content : '处理成功',
						ok : true,
						okValue : '确定',
						lock : true
					});
					if(flag==0){
						$("#processStatus_"+id).html("<a  class=\"budget_see\"  href=\"javascript:showLogFn('"+id+"');\">已处理</a>");	
					}else if(flag==1){
						$("#processStatusX_"+id).html("<a  class=\"budget_see\"  href=\"javascript:void(0);\">已处理</a>");
					}
				}else{
					art.dialog({
						title : '消息',
						content : '处理失败',
						ok : true,
						okValue : '确定',
						lock : true
					});
				}
			}
	);
}
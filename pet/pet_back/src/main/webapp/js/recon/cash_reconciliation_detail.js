function changeStatusFn(id,status) { //单击删除链接的操作
		//alert("查看对账明细"+id+":"+status);
		var content='<div style="float:left;margin-left:-50px;width:300px;height:90px;"><table><tr style="height:40px;"><td align="right" width="35%">对账状态：</td>';
		content+='<td><select id="newReconStatus" name="newReconStatus" disabled="true">';
		content+='<option value="MATCH_SUC">对账成功</option></select></td>';
		content+='</tr><tr><td align="right">备注<font color="red">*</font>：</td><td rowspan="3"><textarea id="reconRemark" rows="3" cols="30"></textarea>';
		content+='</td></tr></table></div>';
		art.dialog({
			id:"confirm-dialog",
			title: "对账状态修改",
		    content: content,
		    okValue: '确定',
		    ok: function () {
		    	var status=$("#newReconStatus").val();
		    	var remark=$("#reconRemark").val();
		    	if(remark.length>200){
		    		$.msg('备注内容长度必需小于200个字符！');
		    		return false;
		    	}
		    	if($.trim(remark).length==0){
		    		$.msg('备注必填！');
		    		return false;
		    	}
		    	var params={
		    			'finReconStatementModel.reconStatementId':id,
		    			'finReconStatementModel.reconStatus':status,
		    			'finReconStatementModel.memo':remark
		    	};
		    	$.post(
						"fin_recon_cash_statement!changeStatus.do",
						params,
						function(data){
							if(data != 'SUCCESS'){
								art.dialog({
									title:'消息',
								    content: '操作成功',
								    ok:true,
								    okValue: '确定',
								    lock:true
								});
								jQuery("#finReconStatement_item_table").trigger("reloadGrid");
								//刷新汇总信息
								$.post(
									"fin_recon_cash_statement!refreshSummary.do",	
									{'finReconStatementModel.reconStatementId':id}
								);
							}else{
								$.msg('失败');
							}
						}
				);
		    },
		    cancelValue: '取消',
		    cancel: true, 
		    lock:true
		});
	} 
	var gridcomplete = function(){  
		$("#finReconStatement_item_table").destroyGroupHeader();
		$("#finReconStatement_item_table").jqGrid('setGroupHeaders', {
			useColSpanStyle : false,
			groupHeaders : [ {
				startColumnName : 'paymentAmount',
				numberOfColumns : 2,
				titleText : '<center><font size=3>驴妈妈</font></center>'
			}, {
				startColumnName : 'gwName',
				numberOfColumns : 8,
				titleText : '<center><font size=3> 收款平台</font></center>'
			} ]
		});
		
	    var ids=$("#finReconStatement_item_table").jqGrid('getDataIDs');
	    for(var i=0; i<ids.length; i++){
	        var id=ids[i];   
	        var rowData = $("#finReconStatement_item_table").jqGrid('getRowData',ids[i]);
	        var operate="";
	        if(rowData.reconStatus!=undefined && rowData.reconStatus!='MATCH_SUC'){
	        	operate +="<a href='javascript:changeStatusFn(&quot;"+ id +"&quot;,&quot;"+ rowData.reconStatus +"&quot);' class = 'budget_see'>修改对账状态</a>";
	        }
	        if(rowData.reconStatus!=undefined && rowData.reconType=='MANUAL'){
	        	operate +="<a href='javascript:detailChangeLogFn(&quot;"+ id +"&quot;);' class = 'budget_see'>查看日志</a>";
	        }
	        $("#finReconStatement_item_table").jqGrid('setRowData', ids[i],
    	    	{ operate: operate,gwName: reconGwObj[rowData.gwName],reconStatus:reconStatusObj[rowData.reconStatus],
    	    		reconType:reconTypeObj[rowData.reconType] });
	    }
	 };
	//查询结算队列项
	function searchFinReconStatementItemHandler(){
		if(!$("#finReconStatement_item_form").validate().checkForm()){
			return false;
		}
		$("#gridDiv").show();
		$("#finReconStatement_item_table").grid({
			former:'#finReconStatement_item_form',
			pager:'#pagebar_div',
			colNames : [ 
			             '',
			             '充值金额', 
			             '外部交易号', 
			             '收款平台',
			             '流水号',
			             '商户订单号',
			             '收款金额',
			             '对账日期',
			             '对账方式',
			             '对账状态',
			             '操作'
			             ],
			colModel : [ 
			             {name:'reconStatementId', index:'id',hidden:true},
			             {name:'paymentAmount',sortable:false, width:100,formatter:currencyFmatter,align:'right'},
			             {name:'lvGwTradeNo', sortable:false, width:100},
			             {name:'gwName', sortable:false, width:120},
			             {name:'gwSn', sortable:false, width:100},
			             {name:'merchantOrderNo', sortable:false, width:100},
			             {name:'gwAmount', sortable:false, width:100,formatter:currencyFmatter,align:'right'},
			             {name:'reconDateStr', sortable:false, width:100,formatter:"date",formatoptions: {newformat: 'Y-m-d'}},
			             {name:'reconType', sortable:false, width:100},
			             {name:'reconStatus', sortable:false, width:100},
			             {name:'operate',width:150,align:'center',sortable:false}
			            ],
			autowidth: true,
			autoheight: true,
			rowNum:10,
			rownumbers: true,
			rowList:[10,20,30,50,100],
			gridComplete:gridcomplete
		});
	}
	
	function exportXlsFileFn(){
		var reconStatus=$("#reconStatus option:selected").val();
		var reconDateFrom=$("#reconDateFrom").val();
		var reconDateTo=$("#reconDateTo").val();
		var gwName=$("#gwName option:selected").val();
		var url="reconStatus="+reconStatus;
		url+="&reconDateFrom="+reconDateFrom;
		url+="&reconDateTo="+reconDateTo;
		url+="&gwName="+gwName;
		url+="&reconSource=2";
		url="fin_recon_cash_statement!xlsFileExport.do?"+url;
		window.open(url,"_blank");
	}
	
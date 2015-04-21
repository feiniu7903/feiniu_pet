$.fn.datebox.defaults.formatter = function(v){
 
   if (v instanceof Date) {
   
    var y = v.getFullYear();
    var m = v.getMonth() + 1;if(m<10){m="0"+m;}
    var d = v.getDate();if(d<10){d="0"+d;}
    var h = v.getHours();if(h<10){h="0"+h;}
    var i = v.getMinutes();if(i<10){i="0"+i;}
    var s = v.getSeconds();
    var ms = v.getMilliseconds();
    if (ms > 0)
     return y + '-' + m + '-' + d ;
    if (h > 0 || i > 0 || s > 0)
     return y + '-' + m + '-' + d ;
    return y + '-' + m + '-' + d;
    
    
   }
   return '';
  } 

var common={};
common.action={
		loading:function(){
			 $("<div class=\"datagrid-mask\" style='z-index:9999;'></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body");  
			    $("<div class=\"datagrid-mask-msg\" style='z-index:10000;'></div>").html("正在运行，请稍候。。。").appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 });  
		},
		loading:function(msg){
			$("<div class=\"datagrid-mask\" style='z-index:9999;'></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body");  
		    $("<div class=\"datagrid-mask-msg\" style='z-index:10000;'></div>").html(msg).appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 });  
		},
		dispalyLoad:function(){
			
			 $(".datagrid-mask").remove();  
			    $(".datagrid-mask-msg").remove();
		},
		alert:function(msg,type){
			$.messager.alert("提示",msg,type);
		},
		confirm:function(msg,control){
			   $.messager.confirm("确认", msg, function (r) {  
			        if (r) {  
			            return true;  
			        }  
			    });  
			    return false;  
		},
		showUploadLogDialog:function(){
			$("#uploadLogiDialog").dialog("open");
		},
		showSyncAddCodeDialog:function(){
			$("#syncCodeForm").find("input[name='addCode']").val("");
			$("#syncAddCodeDialog").dialog("open");
		},
		showViewMSGDialog:function(){
			var row = $("#device_list").datagrid("getSelected");
			if(row==null){
				common.action.alert("请选择一个设备");
				return;
			}
			var option = {
					url:			"manager/viewMSG.do",
					queryParams:	{udid: row.udid,date:$("form#viewMSGForm input[type='text']").val()},
					columns:[[  
								{field:'deviceId',title:"设备编号",width:140}, 
								{field:'command',title:"命令类型",width:80}, 
								{field:'isSuccess',title:"是否成功",width:80}, 
								{field:'createdTime2String',title:"创建时间",width:140}, //创建时间的字符串格式
								{field:'callBackTime2String',title:"回调时间",width:140}, //结束时间的字符串格式
								{field:'addInfo',title:"备注",width:160}
	                       ]]  
			};
			//$("form#viewMSGForm input[type='text']").val($.fn.datebox.defaults.formatter(new Date()));
			$("#msg_list").datagrid(option);
			$("#viewMSGDialog").dialog("open");
		},
		subSyncAddCodForm:function(url){
			var row = $("#device_list").datagrid("getSelected");
			if(row==null){
				common.action.alert("请选择一个设备");
				return;
			}
			url=url+"?udid="+row.udid;
			if($("#syncCodeForm").form("validate")) {
				$.post(url,$("#syncCodeForm").serializeArray(),function(data){
					var d = eval("("+data+")");
					common.action.alert(d.message);
					$("#syncCodeForm").find("input[name='addCode']").val("");
				});
			}
			
		},
		executeCommand:function(url,form){
			var row = $("#device_list").datagrid("getSelected");
			if(row==null){
				common.action.alert("请选择一个设备");
				return;
			}
			url=url+"&udid="+row.udid;
			//判断上传日期是否大于当前日期
			var currentTime = new Date();
			var chosenTimeStr = $("input[name='date']").val();
			var chosenTime = new Date(chosenTimeStr.replace(/-/g,   "/"));
			if(chosenTime>currentTime){
				common.action.alert("上传日期不能大于当前日期");
				return;
			}
			if($("#"+form).form("validate")) {
			$.post(url,$("#"+form).serializeArray(),function(data){
					var d = eval("("+data+")");
					common.action.alert(d.message);
					$("#"+form).find("input[name='date']").val("");
				});
			}
			
		},
		syncNewDevice:function(){
			//syncNewDeviceForm
			if($("#syncNewDeviceForm").form("validate")) {
				$.ajax({url:"manager/asyncNewDevice.do?udid="+$("#syncNewDeviceForm").find("input[name='udid']").val(),
					type:"GET",
					beforeSend:function(R){
				},success:function(data){
					var d = eval("("+data+")");
					common.action.alert(d.message);
				}
				});
//				$.post("manager/asyncNewDevice.do",$("#syncNewDeviceForm").serializeArray(),,function(data){
//					alert(data);
//				});
				}
		},
		deleteHistoryDate:function(){
			var row = $("#device_list").datagrid("getSelected");
			if(row==null){
				common.action.alert("请选择一个设备");
				return;
			}
			$.ajax({
			   type: "POST",
			   url: "deleteHistoryDate.do",
			   data: "udid="+row.udid,
			   success: function(msg){
				   console.info(msg);
			     common.action.alert("删除历史数据成功");
			   }
			});
		},
		process:function(date){
			$("#processDiv").html(date);
		}
		
}
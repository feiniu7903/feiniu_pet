function ajaxPageSize(url, memcachedparam){
	var pageSize = $("#pageSize").val();
	
	if ("" == pageSize || isNaN(pageSize)) {
        alert("请输入合法的数据");
        return;
    }
	
	if(0 > pageSize || 0 == pageSize){
		alert("请输入大于零的数");
		return;
	}
	
	if(500<pageSize){
		alert("每页最多显示500条");
        return;
	}
	
	$.ajax({
        type:"POST",
         url:url,
        data:{
        	"pageSize":pageSize,"memcachedparam":memcachedparam
        },
        dataType:"text",
        success:function (data) {
           if(data=="true"){
        	   alert("操作成功！");
        	   window.location.reload(true);
           }else{
        	   alert("操作失败");
           }
        },
        error:function (data) {
            return false;
        }
    });
	return false;
}

function ajaxSumitPage(url,reviewstatus){
	var len=$("#itemsLength").val();
	  var  arrayObj = new Array();
	  for(var v=1; v<=len;v++ ){
		  var m1;
		  m1=$("#id"+v).val()+":";
		  m1=m1+$("input[name=reviewstatus"+v+"]:checked").val();
		  arrayObj[v]=m1;
		  $("input[name=reviewstatus"+v+"]").val()==reviewstatus;	  
	  }
	  if(confirm("你确定提交此次操作吗?")){  
	  $.ajax({
	        type:"POST",
	         url:url,
	        data: "arrayStr="+arrayObj ,
	        dataType:"text",
	        success:function (data) {
	           if(data=="true"){
	               alert("操作成功！");
	               window.location.reload(true);
	           }else{
	               alert("操作失败");
	           }
	        },
	        error:function (data) {
	            return false;
	        }
	    });
	  		return false;
	  }
}
function ajaxSumitPage2(url,subTable,reviewstatus){
	 if("0"==$("#subTableId").val()){
	        alert("请选择表名，表名不能为空");
	        return false;
	    }
	var len=$("#itemsLength").val();
	  var  arrayObj = new Array();
	  for(var v=1; v<=len;v++ ){
		  var m1;
		  m1=$("#id"+v).val()+":";
		  m1=m1+$("input[name=reviewstatus"+v+"]:checked").val();
		  arrayObj[v]=m1;
		  $("input[name=reviewstatus"+v+"]").val()==reviewstatus;
	  }
	  if(confirm("你确定提交此次操作吗?")){
	  $.ajax({
	        type:"POST",
	         url:url,
	        data: "arrayStr="+arrayObj+"&subTable="+subTable ,
	        dataType:"text",
	        success:function (data) {
	           if(data=="true"){
	               alert("操作成功！");
	               window.location.reload(true);
	           }else{
	               alert("操作失败");
	           }
	        },
	        error:function (data) {
	            return false;
	        }
	    });
	  		return false;	
	  }
}
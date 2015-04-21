$(function(){
    $(".J_select-all").change(function(){
        $(":checkbox").attr("checked", $(".J_select-all").attr("checked"));
    }); 
  });

function changeFirstTypeOnloadSecondType(){
	$("#typeFirstType").click( function() {
	    var firstType=$(this).val();
	    $.post( basePath +"/tagsType/changeFirstTypeOnloadSecondType.do",{
	    	"userTagsType.typeFirstType":firstType
	    },function(data){
	      $("#typeSecondType").empty();
	      $.each(data, function(i,item){
	        var opt="<option value='"+item.second+"'>"+item.second+"</option>";
	        $("#typeSecondType").append(opt);
	      });
	      $("#typeSecondType").show();
	    },"json");
	});
}
//function checkSecond(){
//	var second = $("#typeSecondType").val();
//	if(""==second){
//		alert("请选择类别");
//		return false;
//	}
//	return true;
//}

function getSearchLogsInfo(){
   var arr = "";
   $(":checkbox:checked").each(function(i,n){
         if (n.value != "on") {
        	 //从页面上获取日志表的基本信息
        	 //value:日志ID name:日志关键字 size:日志搜索词频 id:st.count
             arr = arr + n.value + ":" + n.name +":" + n.size + ":" + n.id + ",";
         }
   });
   if (arr.length == 0) {
         alert("选中要操作的数据");
         return;
   };
   return arr;
}


function addSearchLogsToUserTags(url,len){
  var arrayObj = new Array();
  for(var v=1; v<=len;v++ ){
	  var m1;
	  m1=$("#id"+v).val()+":";
	  m1=m1+$("input[name=reviewstatus"+v+"]:checked").val();
	  
	  arrayObj[v]=m1;
  }
  $.ajax({
        type:"POST",
         url:url,
        data: "arrayStr="+arrayObj ,
        dataType:"text",
        success:function (data) {
           if(data=="true"){
               alert("操作成功！");window.location.reload();
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
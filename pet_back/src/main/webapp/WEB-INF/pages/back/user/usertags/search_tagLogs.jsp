<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查询所有用户标签</title>
</head>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script charset="utf-8" src="${basePath}/js/recon/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
    <link rel="stylesheet" type="text/css" href="${basePath}css/ui-components.css"></link>
    <link rel="stylesheet" type="text/css" href="${basePath}css/panel-content.css"></link>

<body>
	<div id="popDiv" style="display: none"></div>
	<div class="iframe-content">
		<div class="p_box">
			<form action="${basePath}tagLogs/search.do?isHide=${isHide}" method="post" id="form1">
			<input type="hidden" id = "isHide" value="${isHide}">
				<table class="p_table form-inline" width="80%">
					<tr class="p_label">
						<td class="p_label" colspan="3" height="20px"></td>
					</tr>
					<tr class="p_label">
						<td>时间范围： <input name="startDate" id="startDate"
							value='<s:property value="startDate"/>' type="text" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" /> 至&nbsp;<input
							name="endDate" id="endDate" value="<s:property value="endDate"/>"
							type="text" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})" />
						</td>
						<td>标签源名称：<input name="tagsLogs.searchLogsName" id="tagsName"></td>
						<td><input type="submit" class="btn btn-small w5"
							id="searchUserTags" value="查&nbsp;&nbsp;询" /></td>
					</tr>
				</table>
			</form>
		</div>

		<div id="tagListTable" class="p_box">
			<table class="p_table table_center">
				<tr>
					<th width="18"></th>
					<th>标签源词频</th>
					<th>标签源名称</th>
					<th>标签源拼音</th>
					<th>一级类别</th>
					<th>二级类别</th>
					<th>状态</th>
					<th>操作</th>
				</tr>


            <s:if test="null != userTagsSearchLogsList && userTagsSearchLogsList.size()>0">
	            <s:iterator value="userTagsSearchLogsList" var="userTagsSearchLogs" status="st">
					<!-- 内容部分 -->
					<tr>
					
						<td><input type="checkbox" id="id${userTagsSearchLogs.searchLogsId }"  value="${userTagsSearchLogs.searchLogsId }" nam="chk"/>
						</td>
						<td>${userTagsSearchLogs.searchLogsFrequence }</td>
						<td><input type="hidden" id="searchLogsName${userTagsSearchLogs.searchLogsId }" value="${userTagsSearchLogs.searchLogsName }"/>${userTagsSearchLogs.searchLogsName }</td>
						<td><input type="text" name="userTags.tagsPinYin${userTagsSearchLogs.searchLogsId }" value="${userTagsSearchLogs.searchLogsPinYin }" /></td>

						<td><select id="typeFirstType${userTagsSearchLogs.searchLogsId }" name="userTagsType.typeFirstType" onclick="changeFirstTypeOnloadSecondType('${userTagsSearchLogs.searchLogsId }')">
						  <option value="时间">时间</option>
						  <option value="地点">地点</option>
						  <option value="个人">个人</option>
						  <option value="价格">价格</option>
						  <option value="交通">交通</option>
						  <option value="特色">特色</option>
						</select></td>
						<td>
						<select id="typeSecondType${userTagsSearchLogs.searchLogsId }" name="userTagsType.typeSecondType" ></select>
						</td>
						<td><input type="radio" name="userTag.Stage${userTagsSearchLogs.searchLogsId }" value="1" checked="checked"/>可用 &nbsp;&nbsp;
						    <input type="radio" name="userTag.Stage${userTagsSearchLogs.searchLogsId }" value="2"/>不可用
						</td>
						<td class="gl_cz">
						    <a href="javascript:doAdd('${userTagsSearchLogs.searchLogsId }');">添加</a>
							<a href="javascript:doIgnore('${userTagsSearchLogs.searchLogsId}');">忽略</a>
						</td>
					</tr>
					<!-- 内容部分 -->
				</s:iterator>
			</s:if>	
			<tr>
			     <td><input class="J_select-all" type="checkbox"></td>
			     <td colspan="2">
			         <input type="button" class="btn btn-small w5" onclick="doIgnoreBySelect()" value="忽&nbsp;&nbsp;略"/>
			         <input type="button" class="btn btn-small w5" onclick="doAddBySelect()" value="添&nbsp;&nbsp;加"/>
			     </td>
			     <td colspan="6">
				    <input id="pageSize" class="btn btn-small w1"
                        type="text" value="${pagination.pageSize}" /> <input
                        class="btn btn-small w5" type="button" value=" GO "
                        onclick="javascript:return ajaxPageSize('${basePath}tagLogs/pagesize.do');">
				 </td>
			</tr>
				<tr>
					<td colspan="3" align="right">总条数：<s:property
							value="pagination.totalResultSize" /></td>
					<td colspan="5"><div style="text-align: right;">
							<s:property escape="false"
								value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)" />
						</div></td>
				</tr>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">
function changeFirstTypeOnloadSecondType(id){
    $("#typeFirstType"+id).click( function() {
        var firstType=$(this).val();
        $.post( basePath +"/tagsType/changeFirstTypeOnloadSecondType.do",{
            "userTagsType.typeFirstType":firstType
        },function(data){
          $("#typeSecondType"+id).empty();
          $.each(data, function(i,item){
            var opt="<option value='"+item.second+"'>"+item.second+"</option>";
            $("#typeSecondType"+id).append(opt);
          });
          $("#typeSecondType"+id).show();
        },"json");
    });
}

$(function(){
    $(".J_select-all").change(function(){
        $(":checkbox").attr("checked", $(".J_select-all").attr("checked"));
    }); 
  });

function doAdd(temp){
	 var results = true;
	 var arrayObj = new Array();
	 if(temp.indexOf(",")>0){
		 temp = temp.substring(0,temp.length-1);	 
		 temp = temp.split(",");
	 for(var i=0;i<temp.length;i++){
         var m1=$("#id"+temp[i]).val()+"/";
         var m2=$("input[name=userTags.tagsPinYin"+temp[i]+"]").val()+"/";
         if("/"==m2.trim()){
        	 alert("请填写拼音");
        	 results = false;
        	 break;
         }
         if(m2.length>51){
        	 alert("标签拼音不可以大于50个字符，请重新输入");
        	 results = false;
             break;
         }
         var m3=$("#typeFirstType"+temp[i]).val()+"/";
         var m4=$("#typeSecondType"+temp[i]).val()+"/";
         if("null/"==m4){
        	 alert("第二类别不能为空");
        	 results = false;
             break;
         }
         var m5=$("input[name=userTag.Stage"+temp[i]+"]:checked").val()+"/";
         var m6=$("#searchLogsName"+temp[i]).val();
         m1=m1+m2+m3+m4+m5+m6;
         arrayObj[i]=m1;
     }
	 }else{
    	 var m1=$("#id"+temp).val()+"/";
         var m2=$("input[name=userTags.tagsPinYin"+temp+"]").val()+"/";
         if("/"==m2.trim()){
             alert("请填写拼音");
             results = false;
         }
         if(m2.length>51){
             alert("标签拼音不可以大于50个字符，请重新输入");
             results = false;
         }
         var m3=$("#typeFirstType"+temp).val()+"/";
         var m4=$("#typeSecondType"+temp).val()+"/";
         if("null/"==m4){
             alert("第二类别不能为空");
             results = false;
         }
         var m5=$("input[name=userTag.Stage"+temp+"]:checked").val()+"/";
         var m6=$("#searchLogsName"+temp).val();
         m1=m1+m2+m3+m4+m5+m6;
         arrayObj=m1;
     }
	 if(results){
		 if(confirm("确定添加吗?")){
		 $.ajax({
	          type:"POST",
	           url:"${basePath}tags/saveTagsByLogs.do",
	          data: "saveUserTagsBySearchLogsInfo="+arrayObj ,
	          dataType:"text",
	          success:function (data) {
	             if(data=="true"){
	                 alert("操作成功!");
	                 window.location.reload(true);
	             }else{
	                 alert("操作失败");
	             }
	          },
	          error:function (data) {
	              return false;
	          }
	      });
		 }
	 } 
}

function doAddBySelect(){
	   var arr = "";
	   $(":checkbox:checked").each(function(i,n){
	         if (n.value != "on") {
	             //从页面上获取日志表的基本信息
	             //value:日志ID name:日志关键字 size:日志搜索词频 id:st.count
	             arr = arr + n.value +",";
	         }
	   });
	   if (arr.length == 0) {
	         alert("选中要操作的数据");
	         return;
	   };
	  doAdd(arr);
	}


function doIgnore(searchId){
    var isHide = $("#isHide").val();
    if(confirm("确定忽略选定的标签源吗?")){
        $.ajax({
            type : "POST",
            url : "${basePath}/tagLogs/isHideUpdate.do",
            data : {
                "TagLogsList" : searchId,
                "isHide" : isHide
            },
            success : function(json) {
                if(json=="true"){
                    alert("操作成功!");
                    window.location.reload(true);
                }else{
                    alert("操作失败!");
                }
            }
        });
    }
}
function doIgnoreBySelect(){
    var isHide = $("#isHide").val();
    var arr = "";
   $(":checkbox:checked").each(function(i,n){
         if (n.value != "on") {
             arr = arr + n.value + ",";  
         }
   });
   if (arr.length == 0) {
       alert("请选择要忽略的标签");
       return;
   };
   if(confirm("确定忽略选定的标签源吗?")){
        $.ajax({
            type : "POST",
            url : "${basePath}/tagLogs/isHideUpdate.do",
            data : {
                "TagLogsList" : arr,
                "isHide" : isHide
            },
            success : function(json) {
                if(json=="true"){
                    alert("操作成功!");
                    window.location.reload(true);
                }else{
                    alert("操作失败!");
                }
            }
        });
    }
}

function ajaxPageSize(url){
	var result = true;
    var pageSize = $("#pageSize").val();
    var memcachedparam="USERTAGS_SEARCHLOGS_PAGESIZE";
    if ("" == pageSize || isNaN(pageSize)) {
        alert("请输入合法的数据");
        result = false;
        return result;
    }
    
    if(0 > pageSize || 0 == pageSize){
        alert("请输入大于零的数");
        result = false;
        return result;
    }
    
    if(500<pageSize){
        alert("每页最多显示500条");
        result = false;
        return result;
    }
    
    if(result)
    $.ajax({
        type:"POST",
         url:url,
        data:{
            "pageSize":pageSize,
            "memcachedparam":memcachedparam
        },
        dataType:"text",
        success:function (data) {
           if(data=="true"){
               alert("操作成功!");
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
</script>
</html>
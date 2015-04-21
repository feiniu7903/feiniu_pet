<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
<script type="text/javascript" src="${basePath}/js/base/typeFirstType.js"></script>
    <link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
    <link rel="stylesheet" type="text/css" href="${basePath}css/ui-components.css"></link>
    <link rel="stylesheet" type="text/css" href="${basePath}css/panel-content.css"></link>
<body>
	<div id="popDiv" style="display: none"></div>
	<div class="iframe-content">
		<div id="tagListTable" class="p_box">
			<table class="p_table table_center">
				<tr>
				    <th width="18"></th>
					<th>编号</th>
					<th>标签名称</th>
					<th>标签拼音</th>
					<th>一级类别</th>
					<th>二级类别</th>
					<th>状态</th>
				</tr>

     <s:if test="userTagsList.size()>0">
            <s:iterator value="userTagsList" var="userTags">
				<!-- 内容部分 -->
				<tr>
				    <td><input type="checkbox" id="id${userTags.tagsId}"  value="${userTags.tagsId}" nam="chk"/></td>
					<td>${userTags.tagsId}</td>
					<td>${userTags.tagsName}</td>
					<td><input type="text" name="userTags.tagsPinYin${userTags.tagsId }" value="${userTags.tagsPinYin }" /></td>
					<td>${userTags.typeFirstType}</td>
					<td>${userTags.typeSecondType}</td>
					<td><s:if test="#userTags.tagsStatus==1">可用</s:if><s:elseif test="#userTags.tagsStatus==2">不可用</s:elseif><s:else>无</s:else></td>
				</tr>
				<!-- 内容部分 -->
			</s:iterator>
	</s:if>	
	           <tr>
	            <td><input class="J_select-all" type="checkbox"></td>
	           <td colspan="7">
	           <input id="updateTagsPinYin" class="btn btn-small w1" type="button" value="提&nbsp;交" onclick="javascript:doUpdateTagsPinYin();">
	           </td>
	          </tr>		
			  <tr>
				<td colspan="2" align="right">总<s:property value="pagination.totalResultSize" />条</td>
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
$(function(){
    $(".J_select-all").change(function(){
        $(":checkbox").attr("checked", $(".J_select-all").attr("checked"));
    }); 
  });
  
function doUpdateTagsPinYin(){
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
    var arrayObj = new Array();
    var temp = arr.substring(0,arr.length-1);    
    temp = temp.split(",");
    for(var i=0;i<temp.length;i++){
        var m1=$("#id"+temp[i]).val()+"/";
        var m2=$("input[name=userTags.tagsPinYin"+temp[i]+"]").val();
        if(""==m2.trim()){
            alert("请填写拼音");
            results = false;
            break;
        }
        if(m2.length>50){
            alert("标签拼音不可以大于50个字符，请重新输入");
            results = false;
            break;
        }
        m1=m1+m2;
        arrayObj[i]=m1;
    }
    if(confirm("确定修改这些拼音么吗?")){
       $.ajax({
            type:"POST",
             url:"${basePath}tags/doUpdateTagsPinYin.do",
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
  
</script>
</html>
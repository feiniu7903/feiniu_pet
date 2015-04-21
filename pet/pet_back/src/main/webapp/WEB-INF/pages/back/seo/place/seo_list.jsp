<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()+"/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>seo-友情链接列表</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script charset="utf-8" src="${basePath}/js/recon/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${basePath}/js/base/date.js"></script>
    <link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
    <link rel="stylesheet" type="text/css" href="${basePath}css/ui-components.css"></link>
    <link rel="stylesheet" type="text/css" href="${basePath}css/panel-content.css"></link>
</head>
<body>
     
    <div id="popDiv" style="display: none"></div>
    <div class="iframe-content">
            <div class="p_box">
    <form action="${basePath}seoLinks/seoPlaceList.do" method="post" id="form1">
        <input type="hidden" name="place.stage" id="stage" value="<s:property value='place.stage'/>"/>
       
	    <table class="p_table form-inline" width="80%">
	    <tr class="p_label"><td  class="p_label" colspan="3" height="20px"></td></tr>
	       <tr class="p_label">
	           <td>
	           <s:if test="place.stage==1">目的地：</s:if>
	           <s:elseif test="place.stage==2">景点：</s:elseif>
	           <s:elseif test="place.stage==3">酒店：</s:elseif>
	               <s:textfield name="seoLinks.placeName" /> 
	           </td>
	            <td>目标位置：<s:select name="seoLinks.location" list="seoLinksIndexList"   listKey="code" listValue="name"></s:select></td>
	            <td>链接地址： <s:textfield name="seoLinks.linkUrl" id="linkUrl"/> </td>
	       </tr>
	     </table>
	       <p class="tc mt20">
               <input type="submit" class="btn btn-small w5" id="allSelect" value="查&nbsp;&nbsp;询"/>
               <input type="button" class="btn btn-small w5" id="seoInsert" value="添&nbsp;&nbsp;加" />
               <input type="button" class="btn btn-small w5" id="seldelete" onclick="seleceDelete('${place.stage }')" value="选中删除"/>
               <a class="btn btn-small w2"   href="javascript:importExcel();">导入</a>
               <a  class="btn btn-small w2"   href="javascript:exportExcel();">导出</a>
               <input type="button" style="display:none;" class="btn btn-small w5" id="allDelete" onclick="javascript:batchDelete();" value="清空数据"/>
          </p>
	    
    </form>
    </div>
    
        <div id="seoListTable" class="p_box">
        <table class="p_table table_center">
                <tr>
                    <th width="18"><input class="J_select-all" type="checkbox"></th>
                    <th width="50">ID</th>
                    <th><s:if test="place.stage==1">目的地</s:if><s:elseif test="place.stage==2">景区</s:elseif><s:else >酒店</s:else></th>
                    <th>关键词</th>
                    <th>链接地址</th>
                    <th>目标位置</th>
                    <th>备注</th>
                    <th>操作</th>
               </tr>
        
          <s:if test="list!=null "> 
           <!-- 内容部分 -->
           <s:iterator value="list" var="var" status="st">
                <tr>
                    <td><input type="checkbox" value="${seoLinksId}" nam="chk" id="79" /></td>
                    <td>${placeId}</td>
                    <td><a target="blank" href="<s:if test="place.stage==3">http://www.lvmama.com/hotel/v${placeId}</s:if><s:else>http://www.lvmama.com/dest/${pinYinUrl}</s:else> " > ${placeName }</a></td>
                    <td>${linkName }</td>
                    <td>${linkUrl }</td>
                    <td>${location }</td>
                    <td>${remark }</td>
                    <td class="gl_cz">
                    <a href="javascript:doEdit('${place.stage }','${seoLinksId }','${placeId }');">编辑</a>
                    <a href="javascript:doDelSeo('${place.stage}','${seoLinksId}');" >删除</a>
                    </td>
                </tr>
           </s:iterator>
           <!-- 内容部分 -->
          </s:if>      
          <tr>
                <td colspan="3" align="right">总条数：<s:property value="pagination.totalResultSize"/></td>
                <td colspan="5"><div style="text-align: right;"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></div></td>
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

/**
 * 重新覆盖table
 */
function reQuerySeoListTable(param){
	var stage= $("#stage").val();
    var location=$("select[name='seoLinks.location']").val();
     param="place.stage="+stage+"&seoLinks.location="+location+param;
	$.ajax({type:"POST", url:"${basePath}/seoLinks/querySeoListForPageTable.do", data:param, dataType:"html", success:function (data) {
         $("#seoListTable").html(data);
    }});
}

function importExcel() {
	$("#popDiv").html();
   var stage= $("#stage").val();
    $("#popDiv").load("${basePath}/seoLinks/importExcel.do?stage="+stage, function() {
        $(this).dialog({
            modal:true,
            title:"上传seo-友情链接",
            width:350,
            height:200
        });
     });                            
} 
	
function doEdit(stage,seoLinksId,placeId) {
	$("#popDiv").html();
    $("#popDiv").load("${basePath}seoLinks/toSeoPlaceEdits.do?place.stage="+stage+"&seoLinks.seoLinksId="+seoLinksId+"&seoLinks.placeId="+placeId, function() {
        $(this).dialog({
            modal:true,
            title:"编辑seo-友情链接",
            width:600,
            height:600
        });
     });                            
} 

$(function(){
    $("#seoInsert").click(function(){
    	$("#popDiv").html();
         $("#popDiv").load("${basePath}seoLinks/toAddSeoLinksPage.do?place.stage=${place.stage}",function() {
                $(this).dialog({
                    modal:true,
                    title:"编辑seo-友情链接",
                    width:600,
                    height:600
                });
            });     
    });         
});

function doDelSeo(stage,seoId) {
    if(confirm("你确定要删除吗?")){
    	var placeName=$("input[name='seoLinks.placeName']").val();
    	var linkUrl=$("input[name='seoLinks.linkUrl']").val();
    	var param ="place.stage="+stage+"&seoLinks.seoLinksId="+seoId;//+"&seoLinks.placeName="+decodeURI(placeName)+"&seoLinks.location="+location2+"&seoLinks.linkUrl="+linkUrl;
    	
        $.ajax({type:"POST", url:"${basePath}seoLinks/doDeleteSeo.do", data:param, dataType:"json", success:function (data) {
            if(data==true){
                alert("删除成功!");
                reQuerySeoListTable("&seoLinks.placeName="+placeName+"&seoLinks.linkUrl="+linkUrl);
            }else{
                alert("删除失败!");     
            }
        } ,error:function(){ 
            alert("出现错误"); 
        } 
       });
    	
    };
}

function seleceDelete(stage) {
    var arr = "";
    $(":checkbox:checked").each(function(i,n){
      if (n.value != "on") {
          arr = arr + n.value + ",";  
      }
    });
    if (arr.length == 0) {
        alert("请选择要删除的");
        return;
    };
   //调整数组
   arr = arr.substring(0,arr.length-1);
   //删除选中的seo-友情链接
   if(confirm("你确定要删除吗?")){
	   location=${basePath}+"seoLinks/doSelectDelete.do?seoList="+arr+"&place.stage="+stage;
   };
};

/**
 * 清空数据
 * nixianjun 2013.8.12
 */
function batchDelete(){
	if(confirm("一次将清空目标位置下的5万条数据，是否继续？")==true)
    {
	    var stage= $("#stage").val();
	    var location=$("select[name='seoLinks.location']").val();
	    var param="stage="+stage+"&location="+location;
        $.ajax({type:"POST", url:"${basePath}/seoLinks/batchDeleteSeo.do", data:param, dataType:"json", success:function (data) {
            if(data.flag==true){
                alert("删除成功!");
                window.location.reload(true);
            }else{
                alert("删除出错!");     
            }
        }});
    }
}
/**
 * 导出数据
 */
function exportExcel(){
	if(confirm("确定导出？")==true)
    {
 	    var url="${basePath}/seoLinks/writeExportExcelData.do?stage="+$("#stage").val()+"&location="+$("select[name='seoLinks.location']").val();
 	    location=url;
    }
}

function popClose(){
    $("span.ui-icon-closethick").click();
}   
</script>

</html>
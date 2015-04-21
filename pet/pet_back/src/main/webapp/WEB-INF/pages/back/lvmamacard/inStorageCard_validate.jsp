<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath() + "/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>校验储值卡</title>
<s:include value="/WEB-INF/pages/pub/lvmamacard.jsp" />
</head>
<body>
<script type="text/javascript">

$(function(){
    
    $("#validateCardNo").val('${validateCardNo}');
    $("#validatestatus").val('${validatestatus}');
    $("#searchCardNo").val('${searchCardNo}');
    $("#searchstatus").attr('value','${searchstatus}');
    $("#inCode").val('${inCode}');
});
    function statusChange(){
         if($("#searchstatus").attr("checked")){
    		  $("#searchstatus").attr("value","true");
     	}else{
    		$("#searchstatus").attr("value","false");
     	}  
         $("#searchCardFrom").submit();
    }

    
    function batchCanel(url){
    	var arr = new Array();
    	var i=0;
     	 $("input[name='resultcardNo']").each(function(){
    		 if($(this).attr("checked")){
    			 arr[i]=($(this).attr('value'));
    			 i=i+1;
    		 }
    	 });
     	 $.ajax({
             type:"POST",
             url:url,
             data: "arrayStr="+arr,
             dataType:"json",
             success:function (data) {
                  if(data.success=="true"){
                     alert("成功！");
                 }else{
                	 alert("失败！");
                 }
             },
             error:function (data) {
                 alert("没有连通");
                 return false;
             }
         });
    }
    
    
</script>
     <div class="iframe-content">
        <div class="p_box">
            <span>入库批次：${inCode}</span>&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-small w5" href="${basePath}/inStorageCard/query.do">返回</a>
            <form  method="post"  id="validateCardFrom">
                <table class="p_table form-inline" width="80%">
                       <tr>
                            <td class="p_label">卡号:</td>
                            <td><input id="validateCardNo" type="text   " />
                            </td>
                            <td class="p_label">密码:</td>
                            <td><input id="validateCardpassword" type="text" />
                            </td>
                            <td>
                               <input type="button" class="btn btn-small w5" value="校验" onclick="javascript:return validatePasswordSumit('${basePath}/validateStorageCard/validatePassword.do')"/>
                            </td>
                      </tr>
                      <tr><td colspan="5">校验结果:<span id="validateResultId"></span> </td></tr>
                </table>
            </form>
        </div>
         <div class="p_box">
             <form  method="post"  id="searchCardFrom" action="${basePath}/validateStorageCard/query.do">
                <table class="p_table form-inline" width="80%">
                       <tr><input id="inCode" type="hidden" name="inCode" value="${inCode }">
                            <td class="p_label">卡号:</td>
                            <td><input id="searchCardNo"  name="searchCardNo" type="text" />
                            </td>
                            <td>
                               <input type="submit" class="btn btn-small w5" value="查询" />
                             </td>
                            <td><input type="checkbox" name="searchstatus" id="searchstatus" value="${searchstatus}"  onchange="statusChange();"  <s:if test="searchstatus=='true'">checked</s:if> />已作废卡号</td>
                      </tr>
                </table>
            </form>
        </div>
          <div class="p_box">
             <tr> 
             <td >
                <input type="button" class="btn btn-small w5" value="批量作废" onclick="javascript:return batchCanel('${basePath}/validateStorageCard/batchCanel.do')"/>
             </td> 
             </tr>
            <table class="p_table ">
                 <s:iterator value="pagination.allItems" var="var" status="st">
                       <s:if test="#st.index==0"><tr></s:if> 
                       <s:if test="(!#st.first)&&((#st.index)%6==0)"></tr><tr></s:if> 
                            <td><input  type="checkbox" name="resultcardNo" <s:if test="status=='CANCEL'">checked</s:if>  value="${cardNo}"  />${cardNo}</td>
                        <s:if test="#st.last"></tr></s:if> 
                 </s:iterator>
                <tr>
                    <td colspan="2" align="right">总条数：<s:property  value="pagination.totalResultSize" /></td>
                    <td colspan="4">  
                        <div style="text-align: right;">
                          <s:property escape="false"  value="pagination.pagination" />
                        </div>
                    </td>
                </tr>
                <tr><td><a class="btn btn-small w5" onclick="javascript:return doUrlAjax('${basePath}/inStorageCard/validatePass.do?inCode=${inCode}')"  value="校验通过" >校验通过</a></td></tr>
            </table>
        </div>
    </div>
</body>
</html>
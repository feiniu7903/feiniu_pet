<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath() + "/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>校验礼品卡</title>
<s:include value="/WEB-INF/pages/pub/lvmamacard.jsp" />
</head>
<body>
<script type="text/javascript">

$(function(){
    $("#searchCardNo").val('${searchCardNo}');
    $("#inCode").val('${inCode}');
});
</script>
     <div class="iframe-content">
        <div class="p_box">
            <span>入库批次：${inCode}</span>
        </div>
         <div class="p_box">
             <form  method="post"  id="searchCardFrom" action="${basePath}/validateStorageCard/notOutOrCancelView.do">
                <table class="p_table form-inline" width="80%">
                       <tr><input id="inCode" type="hidden" name="inCode" value="${inCode }">
                            <input name="paramStatus" value="${paramStatus}" type="hidden"/>
                            <td class="p_label">卡号:</td>
                            <td><input id="searchCardNo"  name="searchCardNo" type="text" />
                            </td>
                            <td>
                               <input type="submit" class="btn btn-small w5" value="查询" />
                             </td>
                       </tr>
                </table>
            </form>
        </div>
          <div class="p_box">
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
                <tr><td>
               <%--  <s:if test="paramStatus=='CANCEL'">
                <a href="${basePath}/inStorageCard/query.do">返回</a>
                </s:if>
                <s:else> --%>
                  <a href="javascript:history.go(-1)">返回上一步</a>
<%--                 </s:else>
 --%>                
                </td></tr>
            </table>
        </div>
    </div>
</body>
</html>
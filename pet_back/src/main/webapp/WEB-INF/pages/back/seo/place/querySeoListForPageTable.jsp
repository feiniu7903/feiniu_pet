<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()+"/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
</head>
<body>
  <div class="p_box">
        <table class="p_table table_center">
                <tr>
                    <th width="18"><input class="J_select-all" type="checkbox"></th>
                    <th>ID</th>
                    <th><s:if test="place.stage==1">目的地</s:if><s:elseif test="place.stage==2">景区</s:elseif><s:else >酒店</s:else></th>
                    <th>关键词</th>
                    <th>链接地址</th>
                    <th>目标位置</th>
                    <th>备注</th>
                    <th>操作</th>
               </tr>
        
          <s:if test="list!=null "> 
           <!-- 内容部分 -->
           <s:iterator value="list">
                <tr>
                     <td><input type="checkbox" value="${seoLinksId}" nam="chk" id="79" /></td>
                    <td>${placeId}</td>
                    <td><a target="blank" href="http://www.lvmama.com/dest/${pinYinUrl}"> ${placeName }</a></td>
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
              <td colspan="6" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td>
           </tr>
        </table>
        </div>

</body>
<script type="text/javascript">
$(function(){
   
  $(".J_select-all").change(function(){
      $(":checkbox").attr("checked", $(".J_select-all").attr("checked"));
  }); 

});
</script>
</html>
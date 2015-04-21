<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看通过卡功能</title>
<s:include value="/WEB-INF/pages/pub/lvmamacard.jsp" />
</head>
<body>
<script type="text/javascript">
$(function(){
  
});
</script>
     <div class="iframe-content">
        <div class="p_box">
            <span>入库批次：${inCode}</span><a class="btn btn-small w2" href="${basePath}/inStorageCard/query.do">返回</a>
            <form  method="post"  id="passCardFrom">
                <table class="p_table form-inline" width="80%">
                       <tr><%--  <input type="hidden" name="amount" value="${amount}" />
                          <input type="hidden" name="inCode" value="${inCode}" />
 --%>                            <td class="p_label">卡号:</td>
                            <td><input name="cardNo" type="text" />
                            </td>
                            <td class="p_label">出库号:</td>
                            <td><input name="outNo" type="text" />
                            </td>
                             <td class="p_label">购买公司:</td>
                            <td><input name="outSaleComp" type="text" />
                            </td>
                             <td class="p_label">销售人员:</td>
                            <td><input name="outSalePerson" type="text" />
                            </td>
                            <td>
                               <input type="submit" class="btn btn-small w5" value="查询" />
                            </td>
                      </tr>
                </table>
            </form>
        </div>
         <div class="p_box">
             <table class="p_table form-inline" width="80%">
                 <tr>
                    <th width="100">总数量</th>
                    <th>面值</th>
                    <th>出库数量</th>
                    <th>未出库数量</th>
                    <th>作废卡</th>
                    <th>卡号范围</th>
                    <th>入库时间</th>
                 </tr>
                   <tr> 
                   <td>${lvmamaCardStatistics.inCount}</td><td>${lvmamaCardStatistics.amount}</td>
                   <td>${ lvmamaCardStatistics.outCount}</td>
                   <td><a href="${basePath}//validateStorageCard/notOutOrCancelView.do?inCode=${inCode}&paramStatus=NOTOUT">${lvmamaCardStatistics.notOutCount }</a></td>
                   <td><a href="${basePath}//validateStorageCard/notOutOrCancelView.do?inCode=${inCode}&paramStatus=CANCEL">${lvmamaCardStatistics.cancelCount}</a></td>
                   <td>${lvmamaCardStatistics.cardNoBegin }~${ lvmamaCardStatistics.cardNoEnd}</td>
                   <td><s:date name="lvmamaCardStatistics.inDate" format="yyyy年MM月dd日"/> </td>
                    </tr>
            </table>
         </div>
          <div class="p_box">
            <table class="p_table ">
                 <tr> 
                    <th width="100">出库号</th><th>出库数量</th><th>卡号范围</th><th>购买公司</th><th>销售人员</th>
                 </tr>
                 <s:iterator value="pagination.allItems" var="var" status="st">
                  <tr><td>${outCode}</td><td>${outDetailsCount}</td><td>${cardNoBegin}~${cardNoEnd}</td><td>${saleToCompany}</td><td>${outDetailsCount }</td></tr>     
                 </s:iterator>
                <tr>
                    <td colspan="2" align="right">总条数：<s:property  value="pagination.totalResultSize" /></td>
                    <td colspan="4">  
                        <div style="text-align: right;">
                          <s:property escape="false"  value="pagination.pagination" />
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</body>
</html>
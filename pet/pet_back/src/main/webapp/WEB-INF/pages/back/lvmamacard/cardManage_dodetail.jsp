<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath() + "/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>入库功能</title>
<s:include value="/WEB-INF/pages/pub/lvmamacard.jsp" />
</head>
<body>
 <div class="iframe-content">
        <div class="p_box">
            <table class="p_table form-inline" width="80%">
                <tr>
                    <th>卡号</th>
                    <th>已使用金额</th>
                    <th>未使用金额</th>
                    <th>有效期</th>
                    <th>面值</th>
                    <th>卡状态</th>
                </tr>
                <tr>
                    <td>${lvmamaStoredCard.cardNo}</td> <td>${lvmamaStoredCard.amountFloat-lvmamaStoredCard.balanceFloat}</td> <td>${lvmamaStoredCard.balanceFloat}</td>
                     <td><s:date name="lvmamaStoredCard.overTime" format="yyyy年MM月dd日" ></s:date></td> <td>${lvmamaStoredCard.amountFloat}</td> <td>${lvmamaStoredCard.cnStatus }</td> 
                </tr>
            </table>
        </div>  
         <div class="p_box">
            <table class="p_table form-inline" width="80%">
                <tr>
                    <th>订单号</th>
                    <th>订单金额</th>
                    <th>礼品卡支付金额</th>
                    <th>产品名称</th>
                    <th>订单日期</th>
                    <th>状态</th>
                </tr>
                 <s:iterator value="resutlList"  >
                <tr>
                    <td> ${orderId }</td> <td>${orderPayFloat}</td> <td>${amountFloat}</td>
                     <td>${productName}</td> <td><s:date name="createTime" format="yyyy年MM月dd日" ></s:date></td> <td>${zhUsageType}</td>
                </tr>
              </s:iterator>  
                 <tr><td><a href="javascript:popClose()">返回上一步</a></td></tr>
            </table>
        </div>
    </div>
</body>
</html>
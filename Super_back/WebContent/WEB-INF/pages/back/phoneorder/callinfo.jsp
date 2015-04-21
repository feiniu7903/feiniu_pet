<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <script type="text/javascript">
        function refreshPayInfo() {
            $("#historyPayDiv").load($('#historyPayDiv').attr('href'), {
                orderId: '${order.orderId}'
            });
        }
        /*
         function toChinaPnr(param) {
         window.open("<%=basePath%>/ord/payment/chinapnr.do?orderId= ${order.orderId}","支付信息", "height=480, width=850,top=200, left=200")
         }
         */
        /*
         function toAccountPay(orderId) {
         window
         .open(
         "<%=basePath%>/ord/placeorder/accountpay.zul?orderId=${order.orderId}&payTotal=${order.oughtPayFloat}",
         "支付信息", "height=480, width=850,top=200, left=200")
         }
         */
        function toByPay() {
            window.open("<%=basePath%>ord/payment/byPay.do?orderId=${order.orderId}", "支付信息", "height=480, width=850,top=200, left=200");
        }

        function openMoneyAccountPayByPhoneWindow() {
            window.open("<%=basePath%>ord/payment/orderMoneyAccountPay.do?orderId=${order.orderId}", "支付信息", "height=480, width=850,top=200, left=200");
        }

        function transferPayment() {
            var a = confirm("订单要将原订单的资金转移到此订单?");
            if (a == true) {
                $.get("<%=basePath%>ord/transfer_payment.do",
                        {orderId: "${order.orderId}"},
                        function (data, textStatus) {
                            var t = eval("(" + data + ")");
                            if (t.result) {
                                alert("资金转移成功!");
                                $("#BtnTransferPayment").hide();
                                $("#refreshPayInfo").click();
                            } else {
                                alert("资金转移失败!");
                            }
                        });
            }
        }
        function sendRefundSexplanation(){

            var mobile = $.trim($("#mobileNumber").val());
            if (mobile == '') {
                alert("请输入手机号！");
                $("#mobileNumber").focus();
                return;
            }
            if (mobile.length != 11) {
                alert("手机号必须为11位！");
                $("#mobileNumber").focus();
                return;
            }
            var MOBILE_REGX = /^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/;
            if (!MOBILE_REGX.test(mobile)) {
                alert("手机号格式不正确");
                $("#mobileNumber").focus();
                return;
            }

            $.post(
                    "/super_back/phoneOrder/sendRefundSexplanation.do",
                    $("#sendRefundSexplanationForm").serialize(),
                    function (data) {

                        if (data.jsonMap.status == "success") {
                            alert("发送成功");
                            $('#refund_sexplanation_div').dialog("close");
                        } else {
                            alert(data.jsonMap.status);
                        }
                    }
            );
        }
        function sendRefundSexplanationDiv(){
            $('#refund_sexplanation_div').dialog({
                title : "发送退改规则",
                width : 500,
                modal : true
            })
        }
    </script>
</head>

<body>
<s:if test="order != null">
    订单已经提交详细信息如下：
    <table width="100%"
           style="margin-top: 10px; margin-left: 5px; margin-bottom: 10px;">
        <tr>
            <td>订单号：</td>
            <td>${order.orderId}<input type="button" class="button_new"
                                       value="复制订单号"
                                       onclick="window.clipboardData.setData('Text','${order.orderId}');">

                <input type='button' value="转到单笔订单查询" class="button_new"
                       onclick="window.location.href='<%=basePath%>ord/order_monitor_list.do?pageType=single&orderId=${order.orderId}';"/>
                <input type="button" class="button02" value="发送退改规则" onclick="sendRefundSexplanationDiv(${order.orderId})"/>
            </td>
        </tr>

        <tr>
            <td><input type='button' value="刷新支付信息"
                       onclick="refreshPayInfo();" class="button_new" id="refreshPayInfo"/></td>
            <td><s:if test="order.paymentTarget=='TOLVMAMA'">
                <s:if test="order.isPaymentChannelLimit()">
                    <font color="red" style="font-weight: bold;">该订单有支付渠道限制（${order.zhPaymentChannel}），请到前台支付</font>
                </s:if>
                <s:else>
                    <s:if test="order.hasNeedPrePay()">
                        <s:if test="order.isApprovePass()">
                            <input type='button' value="电话支付" onclick="toByPay();" class="button_new"/>
                        </s:if>
                    </s:if>
                    <s:else>
                        <s:if test="order.isApprovePass()">
                            <input type='button' value="电话支付" onclick="toByPay();" class="button_new"/>
                        </s:if>
                        <s:if test="order.isCanToPay()">
                            <!-- <input type='button' value="信用卡电话支付" class="button_new" onclick="toChinaPnr('${chinapnrPram}');"/> -->
                            <br/> 存款账户余额为${moneyAccount.maxPayMoneyYuan }元 <br/>
                            <s:if test="!haveMoblie">
                                <font color="red" style="font-weight: bold;">该账户未设置存款账户提醒手机号，请先设置号码后再进行存款账户电话支付</font>
                            </s:if>
                            <s:elseif test="!havePaymentPassword">
                                <font color="red" style="font-weight: bold;">该账户未设置存款账户支付密码，请先设置支付密码再进行存款账户电话支付</font>
                            </s:elseif>
                            <s:elseif test="tempCloseCashAccountPay">
                                <font color="red" style="font-weight: bold;">对于广州长隆供应商的产品暂时关闭存款账户电话支付功能</font>
                            </s:elseif>
                            <s:elseif test="canAccountPay">
                                <input type='button' value="使用存款账户电话支付" class="button_new" onclick="openMoneyAccountPayByPhoneWindow('${order.orderId}');"/>
                            </s:elseif>
                        </s:if>
                    </s:else>
                    <s:if test="canTransfer">
                        <br/>
                        <input type="button" class="button_new" id="BtnTransferPayment" onClick="javascript:transferPayment();" value="资金转移"/>
                    </s:if>
                </s:else>
            </s:if></td>
        </tr>
        <tr>
            <td colspan="2">支付信息: <br/>

                <div id="historyPayDiv"
                     href="<%=basePath%>ajax/loadOrderPaymentInfo.do"></div>
            </td>
        </tr>
    </table>

    <div id="refund_sexplanation_div" style="display: none;">
        <form id="sendRefundSexplanationForm">
        <table>
            <tr>
                <td>手机号码：</td>
                <td>
                    <input type="text" name="mobileNumber" id="mobileNumber" style="border:1px solid #A1C5E6" value="${mobileNumber}"/>
                </td>
            </tr>
            <tr>
                <td style="vertical-align: middle;">短信内容：</td>
                <td>
                    <textarea rows="10" cols="50" name="smsInfo" readonly>订单号：${order.orderId}${refundSexplanation}</textarea>
                </td>
            </tr>
        </table>
        </form>
        <input type="button" class="button" value="提交" onclick="sendRefundSexplanation()"/>
    </div>
</s:if>
</body>
</html>

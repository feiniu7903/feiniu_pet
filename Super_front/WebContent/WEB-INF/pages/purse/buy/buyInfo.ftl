
<input type="hidden" id="productId" name="buyInfo.productId"  value="<@s.property value="buyInfo.productId"/>"/>
<input type="hidden" name="buyInfo.visitTime" value="${buyInfo.visitTime?if_exists}" />
<input type="hidden" name="buyInfo.paymentTarget" value="${buyInfo.paymentTarget?if_exists}" />
<@s.iterator value="buyInfo.buyNum" var="buy">
 <input name="buyInfo.buyNum.<@s.property value="#buy.key"/>" type="hidden" value="<@s.property value="#buy.value"/>" />
</@s.iterator>

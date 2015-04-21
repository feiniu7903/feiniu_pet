<@s.if test="title=='unpaid'">
未支付订单
</@s.if>
<@s.elseif test="title=='approving'">
审核中订单
</@s.elseif>
<@s.elseif test="title=='paid'">
已付款订单
</@s.elseif>
<@s.elseif test="title=='finished'">
已完成订单
</@s.elseif>
<@s.else>
已取消的订单
</@s.else>

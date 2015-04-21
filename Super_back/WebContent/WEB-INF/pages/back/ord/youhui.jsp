<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script type="text/javascript">
    
    var orderId=${orderId};
    
    function couponChecked(obj){
    	var checkbox = $(obj);
    	var p = checkbox.parent();
    	
    	if (checkbox.attr("checked")) {
    	p.find("#inputCodeSpan").fadeIn();
    	} else {
    	p.find("#inputCodeSpan").fadeOut();	
    	}
    }
    
     function saveCoupon(){
     //var code = $("input[name=code]").val();
     	$("#saveCouponBtn").attr("disabled","true");
        var couponId = $("#submitCouponId").val();
        var code = $("#submitCouponCode").val();
    
        $.post("<%=basePath%>/orderCoupon/saveCoupon.do",{"orderId":orderId,"code":code,"couponId":couponId},function(data) {
	   	    var dt=eval("("+data+")");
	   	    if(dt.success){
	   	    	alert("\u64cd\u4f5c\u6210\u529f!");
	   	      $("#historyDiv").reload({"orderId":orderId});
	   	    }else{
	   	    	alert(dt.msg);
	   	    	$("#saveCouponBtn").removeAttr("disabled");
	   	    }	
	});
    }
    
    
    function useCoupon(obj, index){
    var button = $(obj);
    var p = button.parent();
    var couponId;
    if(index != null){
    	couponId = p.find("#couponId"+index).val();
    }
    var code = p.find("#code").val();
    	$.ajax( {
		type : "POST",
		dataType : "json",
		url : "<%=basePath%>/orderCoupon/choseCoupon.do",
		async : false,
		data : {couponId:couponId,code:code,orderId:orderId},
		timeout : 3000,
		error : function(a, b, c) {
			if (b == "timeout") {
				alert("请求超时");
			} else if (b == "error") {
				alert("请求出错");
			}
		},
		success : function(data) {
		if(data.info.key=="OK"){
			alert(data.info.value+",优惠金额"+(data.info.youhuiAmount/100));
			$("#submitCouponId").val(couponId);
			$("#submitCouponCode").val(code);
		} else {
			alert(data.info.value);
		}
		}
	});

   }

    </script>
  </head>
  
  <body>
  		
        <p class="ordersuml">

      <s:if test="userCoupon==true">
        <div>
        	  <em>可享优惠:</em>
			     <s:if test="showYouHui==true && (product.couponAble=='true' || product.couponAble == null || product.couponAble == '')">
        			<div id="useCode">
				 		<input type="input" id="code"/> <input type="button" onclick="useCoupon(this, null)" value="验证优惠券"/>
				 		<span id="info" style="color:red;font-weight:bold;"></span>
        			    
					</div> 
				</s:if>
				<s:else>
				<p>
					<span>此产品目前不适用优惠券</span>
				</p>
				</s:else>
				<em>可参与活动:</em>
				<s:if test="showYouHui==true && (product.couponActivity=='true' || product.couponActivity == null || product.couponActivity == '')">
					<div>
						<s:iterator value="partyCouponList" status="point">
							
							<div>
								<input name="choseParty" type="radio" onClick="useCoupon(this, <s:property value="#point.index+1"/>)"/> <s:property value="couponName"/> <br/>
								<input id="couponId<s:property value="#point.index+1"/>" type="hidden" value="<s:property value="couponId"/>"/>
							</div>
						</s:iterator>
					</div>
				</s:if>
				<s:else>
				<p>
					<span>此产品目前不适用优惠活动</span>
				</p>
				</s:else>
				<input id="submitCouponId" type="hidden" />
				<input id="submitCouponCode" type="hidden" />
				<s:if test="showYouHui==true && (product.couponActivity=='true' || product.couponActivity == null || product.couponActivity == '' || product.couponAble=='true' || product.couponAble == null || product.couponAble == '')">
				      <input type="button" id="saveCouponBtn"  name="submit" onclick="saveCoupon()" value="确定"/>
				</s:if>
		   </div><!--ordersum end-->
		   </s:if>
		   
  </body>
</html>


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

<script type="text/javascript">
$(function(){
$(".remark").click(function(){
	$("input:[type=button]").attr("disabled",true);
	var _cardNo = $(this).attr("cardNo");
	var _beizhu = $("#remarks").val();
	var a = _beizhu.split("");
	var l = 0;
	for (var i=0;i<a.length;i++) {  
		 if (a[i].charCodeAt(0)<299) {
			 l++;  
		} else {
			l+=2;
		} 
	}
	if(l>200){
		alert("备注不能超过"+200+"个字符！");
		$("input:[type=button]").attr("disabled",false);
		return false;
	}
	$.ajax({type : "POST",
          url : "${basePath}cardManage/updateRemark.do",data:{cardNo:_cardNo,beizhu:_beizhu+""},
          success : function(json) {
              if(json=="true"){
            	  alert("成功！");popClose();
                  window.location.reload(true); 
              }else{
            	  alert("失败！");popClose();
              }
              $("input:[type=button]").attr("disabled",false);
          }
	  });

});
	
});
</script>
</head>
<body>
 <div class="iframe-content">
        <div class="p_box">
            <table class="p_table form-inline" width="80%">
                <tr>
                    <td>${lvmamaStoredCard.cardNo}备注<s:textarea   value="%{lvmamaStoredCard.beizhu}" name="beizhu" id="remarks" cssStyle="width:90%" rows="4" ></s:textarea>
	        				<div id="limitTips"></div></td>
                </tr>
            </table>
        </div>  
         <div class="p_box" style=" text-align: center;">
         	<input type="button" class="btn btn-small w5 remark" value="修改" cardNo="${lvmamaStoredCard.cardNo}" /> &nbsp;&nbsp;
         	<input type="button" class="btn btn-small w5" value="返回" onclick="javascript:popClose();" />
        </div>
    </div>
</body>
</html>
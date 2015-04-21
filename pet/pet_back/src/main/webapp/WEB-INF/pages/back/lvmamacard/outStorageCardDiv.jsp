<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath() + "/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<s:include value="/WEB-INF/pages/pub/lvmamacard.jsp" />
</head>
<script type="text/javascript">

$(function(){
$(".paycon").click(function(){
	$("input:[type=button]").attr("disabled",true);
	var _outCode = $(this).attr("outCode");
	var _outStatus = $(this).attr("outStatus");
	$.ajax({type : "POST",
          url : "${basePath}outStorage/outStorageConfirm.do",data:{outCode:_outCode,outStatus:_outStatus},
          success : function(json) {
              if(json=="true"){
            	  alert("成功！");popClose();
                  window.location.reload(true); 
              }else{
            	  alert("失败，"+ json);popClose();
              }
              $("input:[type=button]").attr("disabled",false);
          }
	  });

});
	
});
</script>
<body>
<script type="text/javascript">
</script>
     <div class="iframe-content">
        <div class="p_box">
             <form  method="post"  name="form2" id="passCardFrom">
                <table class="p_table form-inline" width="100%">
                       <tr>
                            <td><h1>出库号${outCode}出库，是否已支付款项？</h1></td>
                      </tr>
                    <tr>
                      <td>
                       <input type="button" class="btn btn-small w5 paycon" value="已支付" outCode="${outCode}" outStatus="2" />
                       <input type="button" class="btn btn-small w5 paycon" value="未支付" outCode="${outCode}" outStatus="1" />
                       <input type="button" class="btn btn-small w5 paycon" value="无需支付" outCode="${outCode}" outStatus="3" />
                       <input type="button" class="btn btn-small w5" value="返回" onclick="javascript:popClose();" />
                       </td>
                    </tr>
                 </table>
            </form>
        </div>
         
       
    </div>
</body>
</html>
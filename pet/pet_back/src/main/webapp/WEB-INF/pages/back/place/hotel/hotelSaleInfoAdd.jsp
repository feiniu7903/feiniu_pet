<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>销售信息添加</title>
<script type="text/javascript">
	function checkForm(){
		var url="${basePath}/place/hotelSaleInfoUpdate.do";
		checkAndSubmit(url,'SaleInfoFrom');
		return true;
	}
	//提交之后，重刷新table
	function checkAndSubmit(url,form) {
	var options = {
			url:url,
			type : 'POST',
			dataType:'json',
				success:function(data){
	      		   if(data.success==true) {
					 alert("操作成功!");
					 $("#popDiv").dialog("close");
	  				} else {
					  alert("操作失败："+data.message);
				    }
				},
			error:function(){
	                 alert("出现错误");
	             }
		};

	$('#'+form).ajaxSubmit(options);
	}
</script>
</head>
<body>


 <div class="cg_tab_list" style="display:block;">
	<form id="SaleInfoFrom" action="${basePath}/place/hotelSaleInfoUpdate.do" method="post" >
	<s:hidden name="place.placeId"></s:hidden>
	<input name="place.stage" type="hidden" value="${place.stage}"/>
	<input name="place.name"  type="hidden" value="${place.name }"/>
	<input name="oldPlaceName" type="hidden" value="${place.name }"/>
	<s:hidden name="stage" value="place.stage"></s:hidden>
	<s:hidden name="placeId"></s:hidden>
	<table class="p_table">
     <tr>
        <td class="p_label"  width="15%" ><strong  >酒店特色：</strong></td>
        <td  width="85%">
        	<s:textarea name="place.feature" theme="simple" style="width:800px;" onKeyUp="textCounter('features', 2000,'features_limitTips')" onkeypress="textCounter('features', 2000,'features_limitTips')"  id="features" cols="250" rows="4"></s:textarea>
        	<div id="features_limitTips"></div>
        </td>
      </tr>
      <tr>
        <td  class="p_label" ><strong  >预订须知：</strong></td>
        <td>
        	<s:textarea name="place.orderNotice" style="width:800px;" theme="simple" onKeyUp="textCounter('orderNotice', 2000,'orderNotice_limitTips')" onkeypress="textCounter('orderNotice', 2000,'orderNotice_limitTips')"  id="orderNotice"  cols="250" rows="4"></s:textarea>
        	<div id="orderNotice_limitTips"></div>
        </td>
      </tr>
       <tr>
        <td  class="p_label" ><strong  >重要提示：</strong></td>
        <td>
        	<s:textarea name="place.importantTips" style="width:800px;" theme="simple" onKeyUp="textCounter('importantTips', 2000,'importantTips_limitTips')" onkeypress="textCounter('importantTips', 2000,'importantTips_limitTips')"  id="importantTips"  cols="250" rows="4"></s:textarea>
        	<div id="importantTips_limitTips"></div>
        </td>
      </tr>
    </table>
	<p class="tc mt10 mb10">
	    <input class="btn btn-small w5" value="确定" onclick="return checkForm();"></input>
	</p>
  
     </form>
</div>

</body>
</html>
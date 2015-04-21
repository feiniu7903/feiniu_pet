<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——编辑套餐</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/prod/toSavePack.do" name="packForm" id="packForm">
<div class="main main06">
	<div class="row1">
	 <input type="hidden" name="packId" value="${prodProductJourneyPack.prodJourneyPackId}"/>
	 <input type="hidden" name="productId" value="${prodProductJourneyPack.productId}"/>
    	<h3 class="newTit">套餐名称 <input type="text" name="prodProductJourneyPack.packName" id="packName" value="${prodProductJourneyPack.packName}"/></h3>
  </div><!--row1 end-->
  <div class="row2">    
        <table width="90%" border="0" cellspacing="0" cellpadding="0" id="journey_tb" class="newTable">
         <tr class="newTableTit">
           <td>时间范围</td>
           <td>行程标题</td>
           <td>出发地</td>
           <td>目的地</td>
           <td colspan="4">关联产品</td>
         </tr>
         <tr>
           <td>&nbsp;</td>
           <td>&nbsp;</td>
           <td>&nbsp;</td>
           <td>&nbsp;</td>
           <td>交通组</td>
           <td>酒店组</td>
           <td>门票组</td>
           <td>当地观光游组</td>
         </tr>
         <s:iterator value="prodProductJourneyPack.prodProductJourneys" var="journey">     
         <tr id="tr_pj_${journey.prodJourenyId}" >
           <td class="time">${journey.journeyTimeStr}</td>
           <td>${journey.fromPlace.name}到${journey.toPlace.name}</td>
           <td>${journey.fromPlace.name}</td>
           <td>${journey.toPlace.name}</td>
           <td class="traffic" req="${journey.trafficPolicy}">
           		<div class="content">
           			<s:if test="#journey.trafficList!=null && #journey.trafficList.size()>0" >
           			<s:iterator value="#journey.trafficList" id="pp" status="index">
	           			<span><input type="checkbox" name="prodProductJourneyPack.prodBranchIds" value="${pp.journeyProductId}-${pp.prodBranchId}" <s:if test='#pp.YesOrNoPackJournetProduct=="true"'> checked="true" </s:if> <s:if test='#pp.require=="true"'> disabled="disabled" </s:if>></input>${pp.prodBranch.prodProduct.productName}[${pp.prodBranch.branchName}]</span>
	           		</s:iterator>
	                </s:if>
           		</div>
           		<%-- <div>
	                <span><a href="#" class="edit" tt="TRAFFIC">修改产品</a></span>
           		</div> --%>                
           </td>
           <td class="hotel" req="${journey.hotelPolicy}">
           		<div class="content">
           			<s:if test="#journey.hotelList!=null && #journey.hotelList.size()>0">
           			<s:iterator value="#journey.hotelList" id="pp">
	           			<span><input type="checkbox" name="prodProductJourneyPack.prodBranchIds" value="${pp.journeyProductId}-${pp.prodBranchId}" <s:if test='#pp.YesOrNoPackJournetProduct=="true"'> checked="true" </s:if> <s:if test='#pp.require=="true"'> disabled="disabled" </s:if>></input>${pp.prodBranch.prodProduct.productName}[${pp.prodBranch.branchName}]</span>
	           		</s:iterator>
	                </s:if>
           		</div>
           		<%-- <div>
	                <span><a href="#" class="edit" tt="HOTEL">修改产品</a></span>
           		</div>  --%>
           </td>
           <td class="ticket" req="${journey.ticketPolicy}">
           		<div class="content">
           			<s:if test="#journey.ticketList && #journey.ticketList.size()>0">
           			<s:iterator value="#journey.ticketList" id="pp">
	           			<span><input type="checkbox" name="prodProductJourneyPack.prodBranchIds" value="${pp.journeyProductId}-${pp.prodBranchId}" <s:if test='#pp.YesOrNoPackJournetProduct=="true"'> checked="true" </s:if> <s:if test='#pp.require=="true"'> disabled="disabled" </s:if>></input>${pp.prodBranch.prodProduct.productName}[${pp.prodBranch.branchName}]</span>
	           		</s:iterator>
	                </s:if>
           		</div>
           		<%-- <div>
	                <span><a href="#" class="edit" tt="TICKET">修改产品</a></span>
           		</div>  --%>
           </td>
           <td class="route" req="${journey.routePolicy}">
           		<div class="content">
           			<s:if test="#journey.routeList && #journey.routeList.size()>0">
           			<s:iterator value="#journey.routeList" id="pp">
	           			<span><input type="checkbox" name="prodProductJourneyPack.prodBranchIds" value="${pp.journeyProductId}-${pp.prodBranchId}" <s:if test='#pp.YesOrNoPackJournetProduct=="true"'> checked="true" </s:if> <s:if test='#pp.require=="true"'> disabled="disabled" </s:if>></input>${pp.prodBranch.prodProduct.productName}[${pp.prodBranch.branchName}]</span>
	           		</s:iterator>
	                </s:if>
           		</div>
           		<%-- <div>
	                <span><a href="#" class="edit" tt="ROUTE">修改产品</a></span>
           		</div>  --%>
           </td>
         </tr>
         </s:iterator>
       </table>   
        <p class="main4Bottom"><em class="button" id="packButton">保存套餐</em></p> 
  </div>
</div>
</form>
</body>
<script type="text/javascript">
$(function(){
	
	function valid(){
		var name=$.trim($("#packName").val());
		if(getStrLength(name)>40 || getStrLength(name)<1){
			alert("套餐名不能为空,字符不超过40,汉字不超过20。");
			return false;
		}
		var trafficReq = validJournet(".traffic");
		var ticketReq = validJournet(".ticket");
		var hotelReq = validJournet(".hotel");	
		var routeReq = validJournet(".route");
		if(!(trafficReq&&ticketReq&&hotelReq&&routeReq)){
			alert("红色板块为必选组");
			return false;
		}
		return true;
	}
	function getStrLength(str){
		if(str=="") return 0;
		var char_length = 0;
        for (var i = 0; i < str.length; i++){
            var son_char = str.charAt(i);
            encodeURI(son_char).length > 2 ? char_length += 2 : char_length += 1;
        }
	    return char_length;
	}
	function validJournet(classStr){
		var haveCheck = true;
		$(classStr).each(function(){
			$this = $(this);
			if("true" == $this.attr("req")){
				var ischeck = false;
				$this.find("input").each(function(){
					if(true==$(this).attr("checked")){
						ischeck = true;
					}
				});
				if(!ischeck){
					$this.attr("style","background-color:red;");
					haveCheck = false;
				}else{
					$this.attr("style","");
				}
			}
		});
		return haveCheck;
	}
	$("#packButton").click(function(){
		if(valid()){
			var d="";
			$("input[disabled='disabled'][checked='true']").each(function(){
				d=d+"&prodProductJourneyPack.prodBranchIds="+$(this).val();
				
			});
			var data= $('#packForm').serialize()+d;
	    	$.ajax({
				url:$("form[name=packForm]").attr("action"),
				data:data,
				type:"POST",
				success:function(dt){
					showPackInfo();
					$("#create_journey_pack_div").dialog("close");
				},
				error:function(dt){
					$("#create_journey_pack_div").dialog("close");
				}
			});
		}
	});
});
</script>
</html>
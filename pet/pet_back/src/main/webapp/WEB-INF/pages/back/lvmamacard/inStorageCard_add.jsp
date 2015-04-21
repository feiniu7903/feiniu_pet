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
<script type="text/javascript">
        function check(id){
        	var a = /^(\d{4})-(\d{2})-(\d{2})$/;
        	if (!a.test(document.getElementById(id).value)) { 
        	    return false ;
        	  }else{
        	  return true ;
        	} 
         } 
        
    function addSumit(url){
    	   //校验
            if($("#amount").val()==""){
        		alert("请选择面值");$("#amount").focus();return false;
        	}
        	if($("#count").val()==""||($("#count").val()!=""&&$("#count").val()<=0)){
                alert("请填写数量并且大于0");$("#count").focus();return false;
            }
        	/* if(!check("validDate")){
                alert("日期未填写或者日期不正确!");$("#validDate").focus();return false;
        	} */
        	
    	   // var buttonV=  $("#addButton").val();
        	if(!confirm("确定要增加卡吗？")){
        		return false;
        	 }else{
                  $("#addButton").attr("disabled",true);
        	 }
        	 var amount=$("#amount").val();var count=$("#count").val();var validDate=$("#validDate").val();
              $.ajax({
                type:"POST",
                async: false,
                url:url,
                data:{
                     amount :amount, count:count,validDate:validDate
                },
                dataType:"json",
                success:function (data) {
                     if(data.success=="true"){
                    	alert("操作成功！");  
                    }else{
                    	alert("操作失败！"); 
                    }
                },
                error:function (data) {
                	alert("没有连通");
                    return false;
                }
            });  
            $("#addButton").attr("disabled",false);
            $("#addButton").val(buttonV);    
    	return true;
    }

</script>
     <div class="iframe-content">
        <div class="p_box">
            <form  method="post"  id="addBatchCardForm" action="${basePath}/inStorageCard/add.do">
                <table class="p_table form-inline" width="80%">
                       <tr>
                            <td class="p_label">面值:</td>
                            <td><select   id="amount" name="amount" >
                                      <option   value="">请选择面值</option>
                                     <s:iterator value="yuanList" var="var" status="st">
                                        <option   value="${key}">${key}</option>
                                    </s:iterator>
                            </select>
                            </td>
                           <td class="p_label">数量:</td>
                            <td><input id="count" type="text" name="count"/>
                            </td>
                            <td class="p_label">有效日期:</td>
                            <td><input id="validDate" name="validDate"  class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" /><span class="red">格式yyyy-MM-dd,如果不填写，就是卡无期</span>
                            </td>
                            
                    </tr>
                </table>
                <p class="tc mt20">
                    <input id="addButton" type="button" class="btn btn-small w5" value="新&nbsp;&nbsp;增" onclick="javascript:return  addSumit('${basePath}/inStorageCard/add.do')"/>
                    <a  class="btn btn-small w2" value="返&nbsp;&nbsp;回" href="${basePath}/inStorageCard/query.do">返回</a>
                </p>
            </form>
        </div>
    </div>
</body>
</html>
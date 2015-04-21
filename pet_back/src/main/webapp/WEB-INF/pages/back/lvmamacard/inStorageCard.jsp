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

/**
 * 作废批次卡
 */
 function cancelAjax(batchCode,url){
	 if(confirm("确认作废"+batchCode+"批次礼品卡？")){
		 $.ajax({
             type:"POST",
             url:url,
             data:{
            	 inCode :batchCode
             },
             dataType:"json",
             success:function (data) {
                  if(data.success=="true"){
                      alert("成功！"); popClose();window.location.reload(true);
                 }else{
                     alert("失败！");
                 }
                 
             },
             error:function (data) {
                 alert("没有连通");
                 return false;
             }
         });
		 return true;
	 }else{
		 return false;
	 }
 }
 
 $(function(){
	 $("input[name='searchIncode']").val('${searchIncode}'); 
	$("input[name='searchCardNo']").val('${searchCardNo}'); 
	$("select[name='searchAmount']").val('${searchAmount}'); 
	$("select[name='searchInStatus']").val('${searchInStatus}'); 
 });
 
 
 function sendEmail(url){
	 var toEmail=prompt("请输入要发送的邮箱","");
	  
	 if (toEmail!=null && toEmail!="")   
	   {     
	     $.ajax({
             type:"POST",
             url:url+"toEmail"+toEmail,
             dataType:"text",
             success:function (data) {
             },
             error:function (data) {
                 alert("没有连通");
                 return false;
             }
         });
	 }    
 }
</script>
    <div id="popDiv" style="display: none"></div>
    <div class="iframe-content">
        <div class="p_box">
            <form    method="post" id="form1"  action="${basePath}/inStorageCard/query.do">
                <table class="p_table form-inline" width="80%">
                       <tr>
                           <td class="p_label">入库批次:</td>
                            <td><input name="searchIncode" type="text" value="${searchIncode}"/>
                            </td>
                            <td class="p_label">卡号:</td>
                            <td><input name="searchCardNo" type="text" value="${searchCardNo}"/>
                            </td>
                            <td class="p_label">面值:</td>
                            <td><select  name="searchAmount" value="${searchAmount}">
                                 <option   value="">请选择</option>
                                     <s:iterator value="yuanList" var="var" status="st">
                                        <option   value="${key}">${key}</option>
                                    </s:iterator>
                            </select>
                            </td>
                            <td class="p_label">状态:</td>
                            <td><select   name="searchInStatus" value="${searchInStatus}">
                             <option   value="">请选择</option>
                                     <s:iterator value="statusList" var="var" status="st">
                                        <option   value="${key}">${value}</option>
                                    </s:iterator>
                            </select>
                            </td>
                    </tr>
                </table>
                <p class="tc mt20">
                    <input type="submit" class="btn btn-small w5" value="查&nbsp;&nbsp;询" />
                    <mis:checkPerm permCode="3417">
                    <a class="btn btn-small w2" value="新&nbsp;&nbsp;增" href="${basePath}/inStorageCard/initadd.do">新增</a>
                    </mis:checkPerm>
                </p>
            </form>
        </div>
        <div class="p_box">
            <table class="p_table ">
                <tr>
                    <th width="100">入库批次</th>
                    <th>面值</th>
                    <th>数量</th>
                    <th>卡号范围</th>
                    <th>入库时间</th>
                    <th>批次状态</th>
                    <th>操作</th>
                </tr>
                 <s:iterator value="pagination.allItems" var="var" status="st">
                        <tr>
                            <td>${inCode}</td>
                            <td>${amount}</td>
                            <td>${inCount}</td>
                            <td>${cardNoBegin}~${cardNoEnd}</td>
                            <td><s:date  name="inDate"  format="yyyy年MM月dd日"></s:date></td>
                            <td>${cnInStatus}</td>
                            <td>
                            <s:if test="inStatus==1"> 
                            <mis:checkPerm permCode="3418">
                                <a  class="btn btn-small w2" href="${basePath}/validateStorageCard/query.do?inCode=${inCode}">校验</a>&nbsp&nbsp
                             </mis:checkPerm>
                            <mis:checkPerm permCode="3419">
                                <a class="btn btn-small w2" onclick="javascript:cancelAjax('${inCode}','${basePath}//inStorageCard/cancel.do')">作废</a>&nbsp&nbsp
                             </mis:checkPerm>
                            <mis:checkPerm permCode="3420">
                                <a class="btn btn-small w5"   href="${basePath}/inStorageCard/outexcel.do?inCode=${inCode}">导出并发邮件</a>&nbsp&nbsp
                            </mis:checkPerm>
                             </s:if>
                             <s:if test="inStatus==2">
                             <mis:checkPerm permCode="3436"> 
                                 <a  class="btn btn-small w2" onclick="javascript:loadPopDiv('${basePath}/inStorageCard/initpass.do?inCode=${inCode}','通过礼品卡',1000,350);">通过</a>
                             </mis:checkPerm>
                             <mis:checkPerm permCode="3418">
                                 <a  class="btn btn-small w2" href="${basePath}/validateStorageCard/query.do?inCode=${inCode}">校验</a>&nbsp&nbsp
                             </mis:checkPerm>
                             <mis:checkPerm permCode="3419">
                                 <a class="btn btn-small w2" onclick="javascript:cancelAjax('${inCode}','${basePath}//inStorageCard/cancel.do')">作废</a>&nbsp&nbsp
                             </mis:checkPerm>
                             </s:if>
                             <s:if test="inStatus==3">
                             <mis:checkPerm permCode="3421">
                             <a class="btn btn-small w4" href="${basePath}//validateStorageCard/notOutOrCancelView.do?inCode=${inCode}&paramStatus=CANCEL">查看校验</a>&nbsp&nbsp
                             </mis:checkPerm>
                             </s:if> 
                             <s:if test="inStatus==4">
                             <mis:checkPerm permCode="3422">
                               <a class="btn btn-small w4" href="${basePath}/inStorageCard/initviewpass.do?inCode=${inCode}&&amount=${amount}">查看通过卡</a>&nbsp&nbsp
                             </mis:checkPerm>
                             </s:if> 
                             </td>
                        </tr>
                 </s:iterator>
                                <tr>
                    <td colspan="4" align="right">查询结果： <s:property  value="pagination.totalResultSize" />个批次,卡总量:${cardCount}<s:if test="searchInStatus==null||searchInStatus==''||searchInStatus==4">,未出库数量:${cardCountForNoStock}</s:if></td>
                    <td colspan="3">  
                        <div style="text-align: right;">
                            <s:property escape="false"
                                value="pagination.pagination" />
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</body>
</html>
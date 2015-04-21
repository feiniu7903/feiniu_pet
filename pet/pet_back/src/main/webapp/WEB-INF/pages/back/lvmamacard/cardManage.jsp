<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath() + "/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>卡管理</title>
<s:include value="/WEB-INF/pages/pub/lvmamacard.jsp" />

</head>
<body>
<script type="text/javascript">

/**
 * ajax
 */
 function doAjax(url,message){
	 if(confirm(message)){
		 $.ajax({
             type:"POST",
             url:url,
             async:false,
             dataType:"json",
             success:function (data) {
                  if(data.success=="true"){
                	  if(data.message!=undefined){
                		  alert("成功:"+data.message);
                	  }else{
                        alert("成功！");
                	  }                	  
                 }else{ 
                	 if(data.message!=undefined){
                       alert("失败:"+data.message);
                     }else{
                        alert("失败");
                     }  
                 }
                  window.location.reload(true);
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
 
	function doReset(url, cardNo) {
		var mobile = prompt("请输入要发送（卡号和新密码）的手机号", "");
		if (mobile != null && mobile != "") {
			 var r = /^[0-9]{11}$/;
             if(!r.test(mobile)){
                 alert("手机号必须为数字,必须为11位  请填写！"); 
                 return false;
              }
			 $.ajax({
	             type:"POST",
	             url:url+"&mobile="+mobile,
	             async:false,
	             dataType:"json",
	             success:function (data) {
	                  if(data.success=="true"){
	                      if(data.message!=undefined){
	                          alert("成功:"+data.message);
	                      }else{
	                        alert("成功！");
	                      }                   
	                 }else{ 
	                     if(data.message!=undefined){
	                       alert("失败:"+data.message);
	                     }else{
	                        alert("失败");
	                     }  
	                 }
	                  window.location.reload(true);
	             },
	             error:function (data) {
	                 alert("没有连通");
	                 return false;
	             }
	         });
	         return true;
		}
	}

	$(function() {
		$("input[name='searchValidDate']").val('${searchValidDate}');
		$("input[name='cardNoBegin']").val('${cardNoBegin}');
		$("input[name='cardNoEnd']").val('${cardNoEnd}');
		$("select[name='searchAmount']").val('${searchAmount}');
		$("select[name='searchstatus']").val('${searchstatus}');
		$("#chk_all").click(
				function() {
					$("input[name='chk_list']").attr("checked",
							$(this).attr("checked"));
				});
	});

	/**
	 *批量延期或者冻结
	 */
	function batchUp(url, message, st) {
		if (confirm(message)) {
			var arr = new Array();
			var i = 0;
			var flag = true;
			$("input[name='chk_list']").each(
					function() {
						if ($(this).attr("checked")) {
							if (st == 'DEPLAY'
									&& ($(this).attr('status') == 'FREEZE')) {
								flag = false;
							}
							arr[i] = ($(this).attr('value'));
							i = i + 1;
						}
					});
			if (i == 0) {
				alert("请选择卡号!");
				return false;
			}
			if (flag == false) {
				alert("您选择的卡号有冻结的，请清除冻结的卡号！");
				return false;
			}
			$.ajax({
				type : "POST",
				url : url + "&arrayStr=" + arr,
				dataType : "json",
				success : function(data) {
					if (data.success == "true") {
						alert("成功！");
						window.location.reload(true);
					} else {
						alert("失败   1！");
					}
				},
				error : function(data) {
					alert("没有连通");
					return false;
				}
			});

		} else {
			return false;
		}
		return true;
	}
	
	
	/**
     *批量解冻
     */
    function batchdoUnFrozen(url, message) {
        if (confirm(message)) {
            var arr = new Array();
            var i = 0;
            var flag = true;
            $("input[name='chk_list']").each(
                    function() {
                        if ($(this).attr("checked")) {
                            if (($(this).attr('status') != 'FREEZE')) {
                                flag = false;
                            }
                            arr[i] = ($(this).attr('value'));
                            i = i + 1;
                        }
                    });
            if (i == 0) {
                alert("请选择卡号!");
                return false;
            }
            if (flag == false) {
                alert("您选择的卡号有未冻结的，请清除未冻结的卡号！");
                return false;
            }
            $.ajax({
                type : "POST",
                url : url + "?arrayStr=" + arr,
                dataType : "json",
                success : function(data) {
                    if (data.success == "true") {
                        alert("成功！");
                        window.location.reload(true);
                    } else {
                        alert("失败   1！");
                    }
                },
                error : function(data) {
                    alert("没有连通");
                    return false;
                }
            });

        } else {
            return false;
        }
        return true;
    }
</script>
    <div id="popDiv" style="display: none"></div>
    <div class="iframe-content">
        <div class="p_box">
            <form    method="post" id="form1"  action="${basePath}//cardManage/query.do">
                <table class="p_table form-inline" width="80%">
                       <tr>
                           <td class="p_label">有效日期:</td>
                            <td><input  name="searchValidDate"  class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
                            </td>
                            <td class="p_label">面值:</td>
                            <td><select  name="searchAmount">
                                 <option   value="">请选择</option>
                                     <s:iterator value="yuanList" var="var" status="st">
                                        <option   value="${key}">${key}</option>
                                    </s:iterator>
                            </select>
                            </td>
                            <td class="p_label">状态:</td>
                            <td><select   name="searchstatus">
                             <option   value="">请选择</option>
                                     <s:iterator value="cardStatusList" var="var" status="st">
                                        <option   value="${key}">${value}</option>
                                    </s:iterator>
                            </select>
                            </td>
                    </tr>
                    <tr>
                        <td class="p_label">卡号范围:</td>
                         <td colspan="5"><input  type="text" name="cardNoBegin"  value="${cardNoBegin}" />
                         ~
                          <input name="cardNoEnd"  type="text" value="${cardNoEnd}"/>
                        </td>  
                        
                    </tr>
                </table>
                <p class="tc mt20">
                    <input type="submit" class="btn btn-small w5" value="查&nbsp;&nbsp;询" />
                 </p>
            </form>
        </div>
        <div class="p_box">
            <table class="p_table " width="80%">
                 <tr>
                   <input class="btn btn-small w5" type="checkbox" name="chk_all" id="chk_all" >全选 </input>
                   <mis:checkPerm permCode="3433">
                  <input class="btn btn-small w5" type="button" value="批量延期" onclick="return batchUp('${basePath}/cardManage/batchDeplayOrDoFrozen.do?paramStatus=DEPLAY','确定批量延期吗','DEPLAY')" />
                  </mis:checkPerm>
                  <mis:checkPerm permCode="3434">  
                  <input class="btn btn-small w5" type="button" value="批量冻结" onclick="return batchUp('${basePath}/cardManage/batchDeplayOrDoFrozen.do?paramStatus=FREEZE','确定批量冻结吗','FREEZE')" />
                  </mis:checkPerm> 
                   <input class="btn btn-small w5" type="button" value="批量解冻" onclick="return batchdoUnFrozen('${basePath}/cardManage/batchdoUnFrozen.do','确定批量解冻吗')" />
                   
                  </tr>
                <tr>
                    <th width="140" >卡号</th>
                    <th>面值</th>
                    <th>已使用金额</th>
                    <th>未使用金额</th>
                    <th>有效期</th>
                    <th>卡状态</th>
                    <th>操作</th>
                </tr>
                 <s:iterator value="pagination.allItems" var="var" status="st">
                        <tr>
                            <td><input type="checkbox" name="chk_list" value="${cardNo}" status="${status}">${cardNo}</td>
                            <td>${amountFloat}</td>
                            <td>${amountFloat-balanceFloat}</td>
                            <td>${balanceFloat}</td>
                            <td><s:date name="overTime" format="yyyy年MM月dd日" /></td>
                            <td>${cnStatus}</td>
                             <td>
                             <mis:checkPerm permCode="3430">	
                             	<a  class="btn btn-small w2" onclick="javascript:controlOneSumit(this,doAjax,'${basePath}/cardManage/doDelay.do?cardNo=${cardNo}','确定要将卡号：${cardNo}延期')">延期</a>&nbsp&nbsp
                             </mis:checkPerm>
                             <mis:checkPerm permCode="3431">
                                 <s:if test="status=='FREEZE'" >
                                    <a  class="btn btn-small w2" onclick="javascript:controlOneSumit(this,doAjax,'${basePath}/cardManage/doUnFrozen.do?cardNo=${cardNo}','确定要将卡号：${cardNo}解冻？')">解冻</a>&nbsp&nbsp
                                 </s:if>
                                 <s:else>
                                    <a  class="btn btn-small w2" onclick="javascript:controlOneSumit(this,doAjax,'${basePath}/cardManage/doFrozen.do?cardNo=${cardNo}','确定要将卡号：${cardNo}冻结')">冻结</a>&nbsp&nbsp
                                 </s:else>
                             </mis:checkPerm>
                             <mis:checkPerm permCode="3432">
                                 <a  class="btn btn-small w2" onclick="javascript:loadPopDiv('${basePath}/cardManage/detail.do?cardNo=${cardNo}','明细',1000,350);">明细</a>&nbsp&nbsp 
                             </mis:checkPerm>
                             	<a  class="btn btn-small w2" onclick="javascript:loadPopDiv('${basePath}/cardManage/remark.do?cardNo=${cardNo}','备注',800,300);">备注</a>&nbsp&nbsp    
                                <a  class="btn btn-small w2" onclick="javascript:doReset('${basePath}/cardManage/doReset.do?cardNo=${cardNo}','${cardNo}')">重置</a>&nbsp&nbsp
                             </td>
                             
                        </tr>
                 </s:iterator>
                  <tr>
                    <td colspan="4" align="right">总条数：<s:property  value="pagination.totalResultSize" /></td>
                    <td colspan="3">  
                        <div style="text-align: right;">
                            <s:property escape="false"   value="pagination.pagination" />
                        </div>
                    </td>
                </tr>
            </table>
        </div>
       <div class="p_box">
             <form  method="post"  id="validateCardFrom">
                <table class="p_table form-inline" width="80%">
                       <tr>
                            <td class="p_label">卡号:</td>
                            <td><input id="validateCardNo" type="text   " />
                            </td>
                            <td class="p_label">密码:</td>
                            <td><input id="validateCardpassword" type="text" />
                            </td>
                            <td>
                               <input type="button" class="btn btn-small w5" value="校验" onclick="javascript:return validatePasswordSumit('${basePath}/validateStorageCard/validatePassword.do')"/>
                            </td>
                      </tr>
                      <tr><td colspan="5">校验结果:<span id="validateResultId"></span> </td></tr>
                </table>
            </form>
        </div>
    </div>
</body>
</html>
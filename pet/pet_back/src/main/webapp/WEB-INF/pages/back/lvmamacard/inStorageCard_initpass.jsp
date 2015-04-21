<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath() + "/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>通过礼品卡</title>
<s:include value="/WEB-INF/pages/pub/lvmamacard.jsp" />
</head>
<script type="text/javascript">
function passAjax(url,inCode){
        $.ajax({
        type:"POST",
        url:url,
        data:{
            inCode :inCode
        },
        dataType:"json",
        success:function (data) {
             if(data.success=="true"){
                alert("成功！");  popClose();window.location.reload(true);
            }else{
            	 alert("失败 ！"); 
            }
        },
        error:function (data) {
            alert("没有连通");
            return false;
        }
    });
return true;
}
</script>
<body>
<script type="text/javascript">
</script>
     <div class="iframe-content">
        <div class="p_box">
             <form  method="post"  id="passCardFrom">
                <table class="p_table form-inline" width="80%">
                       <tr>
                            <td><h1>${inCode}批次礼品卡有${cancelCount}张校验作废，确认通过或重新校验？</h1></td>
                      </tr>
                    <tr>
                      <td>
                       <input type="button" class="btn btn-small w5" value="确认" onclick="javascript:return controlOneSumit(this,passAjax,'${basePath}/validateStorageCard/pass.do','${inCode}')"/>
                       &nbsp;&nbsp;<a class="btn btn-small w3"  href="${basePath}/validateStorageCard/query.do?inCode=${inCode}">重新校验 </a> 
                       &nbsp;&nbsp;<input type="button" class="btn btn-small w5" value="返回" onclick="javascript:popClose();" />
                       </td>
                    </tr>
                 </table>
            </form>
        </div>
         
       
    </div>
</body>
</html>
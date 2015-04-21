<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>查看回传件</title>
<%-- <script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script> --%>
<script type="text/javascript" src="${basePath}/js/base/jquery.mousewheel.js"></script>
<script type="text/javascript" src="${basePath}/js/base/jquery.rotate.1-1.js"></script>
<script type="text/javascript">
$(function(){
	 var upcheck=1;
	$('#selectImageDiv').mousewheel(function(event, delta, deltaX, deltaY) {
	    if (delta > 0)
			upcheck=1;
	    else 
	    	upcheck=0;
		showimg();
	    event.stopPropagation();
	    event.preventDefault();
	});
	function getStyleValue(objname,stylename){
	    if(objname.currentStyle){
	        return objname.currentStyle[stylename]
	    }else if(window.getComputedStyle){
	        stylename = stylename.replace(/([A-Z])/g, "-$1").toLowerCase();  
	        return window.getComputedStyle(objname, null).getPropertyValue(stylename);  
	    }
	}
	function showimg(){
	    var tmpobj = document.getElementById("showimg");
	    var width = parseInt(getStyleValue(tmpobj,'width'));
	    var height = parseInt(getStyleValue(tmpobj,'height'));
	    var i = width/height;
	    if(upcheck){
	        if(width<=1024){
	            tmpobj.style.height = (height + 30) + 'px';
	            tmpobj.style.width = (width + 30*i) + 'px';
	        }
	    }else{
	        if(width>=35){
	            tmpobj.style.height = (height - 30) + 'px';
	            tmpobj.style.width = (width - 30*i) + 'px';
	        }
	    }
	}	
});
</script>
</head>
<body>
	<div class="iframe-content">
		<table cellpadding="1" cellspacing="1">
		    <tr>
		      <td colspan="3">
		         <s:if test="ordFaxRecvList!=null && ordFaxRecvList.size>0">
					<table class="p_table form-inline">
					  <tbody>
						<tr>
							<th>传真编号</th>
							<th>发送号码</th>
							<th>接收时间</th>
							<th>传真文件</th>
							<th>操作</th>
						</tr>
						<s:iterator value="ordFaxRecvList" var="recv">
						<tr>
						  <td>${recv.ordFaxRecvId }</td>
						  <td>${recv.callerId }</td>
						  <td><s:date name="#recv.recvTime" format="yyyy-MM-dd HH:mm"/></td>
						  <td>${recv.fileUrl }</td>
						  <td><a href="javascript:void(0);" class="changeFaxReceiveFile" ordFaxRecvId="${recv.ordFaxRecvId }" ebkCertificateId="${ebkCertificateId }">查看回传件</a></td>
						</tr>
						</s:iterator>
					 </tbody>
					</table>
					<script type="text/javascript">
					$(function(){
						$(".changeFaxReceiveFile").click(function() {
								var ordFaxRecvId = $(this).attr("ordFaxRecvId");
								var ebkCertificateId = $(this).attr("ebkCertificateId");
								$("#show_receive_file_div").empty();
								$("#show_receive_file_div").showWindow({
									width : 1000,
									title : '回传件',
									data : {
										'ebkCertificateId' : ebkCertificateId,
										'ordFaxRecvId':ordFaxRecvId
									}
								});
							});
					});
					</script>
					</s:if>
		      </td>
		    </tr>
			<tr>
				<td><a href="${ordFaxRecv.fullUrl}" target="_blank">下载传真回传件</a></td>
				<td>&nbsp;&nbsp;</td>
				<td>
					<h3>关联凭证</h3>
				</td>
			</tr>
			<tr>
				<td width="50%" height="530"><s:if test="fileType != null">
						<s:if test='fileType == "jpg" || fileType == "JPG"'>
							<a href="${ordFaxRecv.fullUrl }"></a>
						</s:if>
						<s:elseif test='fileType == "pdf" || fileType == "PDF"'>
							<object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000"
								width="100%" height="100%" border="0">
								<param name="_Version" value="65539" />
								<param name="_ExtentX" value="20108" />
								<param name="_ExtentY" value="10866" />
								<param name="_StockProps" value="0" />
								<param name="SRC" value="${ordFaxRecv.fullUrl }" />
								<embed src="${ordFaxRecv.fullUrl }" width="100%" height="100%"></embed>
							</object>
						</s:elseif>
						<s:elseif test='fileType.equalsIgnoreCase("tif") || fileType.equalsIgnoreCase("TIFF")'>
							<div id="selectImageDiv" style="TEXT-ALIGN:center;max-width:524px;max-height:624px;overflow:scroll;" ></div>
							<div><select id="selectImage" onchange="selectImage()">
							<s:iterator value="ordFaxRecv.imageList" id="image" status="s" >
							<option value="${image}">第<s:property value="#s.index+1"/>页</option>
							</s:iterator>
							</select><a href="javascript:void(0)" onclick="rotate(-90)">向左</a><a href="javascript:void(0)" onclick="rotate(90)">向右</a></div>
							<script type="text/javascript">
								function selectImage(){
									var $select =$("#selectImage");
									var val=$select.find("option:selected").val();
									$("#selectImageDiv").html("<img width='500' height='600' id='showimg' src='<s:property  value="faxRoot"/>"+val+"'/>");
								}
								selectImage();
								var init_angle=0;
								function rotate(num){
									init_angle=(init_angle+num)%360;
									$("#selectImageDiv>*").rotate(init_angle,1);
								}
							</script>
						</s:elseif>
					</s:if> <s:else>
						未上传回传件
					</s:else></td>
				<td>&nbsp;&nbsp;</td>
				<td style="vertical-align: top;" width="50%">
					<div id="relate_certificate_div">
						<s:include value="/WEB-INF/pages/back/fax/relate_certificate.jsp" />
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
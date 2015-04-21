<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="l" uri="/tld/lvmama-tags.tld"%>
<%
 String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>version</title>
<link rel="stylesheet" href="<%=basePath %>/css/place/backstage_table.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/place/panel.css"/>
<link rel="stylesheet" href="<%=basePath %>/js/base/autocomplete/jquery.ui.autocomplete.css"/>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script type="text/javascript">
	var basePath="<%=basePath %>";
</script>
<script type="text/javascript" src="<%=basePath %>/js/place/houtai.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/seo/panel_custom.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/mobile/mobile_client_version.js?v=20140219"></script>
<script type="text/javascript">
    // 新增 . 
	function addMobileVersion(){
		openWin('addVersion');
	}
	
	function endFunction(id){
		window.location.reload(true);
	}
	
	var search_second_channel =  '${mobileVersion.seconedChannel}';
	var search_first_channel = '${mobileVersion.firstChannel}'; //'<s:property value="#mobileVersion.firstChannel"/>';

</script>
<style type="">
.ul_detail li {
    display: inline;
    float: left;
    margin-bottom: 5px;
    text-align: left;
    width: 100%;
}
</style>
</head>
<body style="background:#fff; padding-bottom:100px"> 
    <div id="panel_content">
    	<div class="p_crumbs"><p> &nbsp;&nbsp;版本(Version) </p></div>
        <!-- 查询  -->
        <div class="p_oper">
			<form action="<%=basePath%>/mobile/mobileClient!getMoibleVersionList.do" method="post" id="queryListForm">
				<table border="0" cellspacing="0" cellpadding="0" class="search_table" width="100%">
					<tr>
						<td>审核状态：</td>
						<td>  
						    <select id="s_isAuditing" name="mobileVersion.isAuditing">			
						        <option value = "">全部</option>		
								<option value = "false" <s:if test='mobileVersion.isAuditing == "false"'>selected</s:if> >审核中</option>
								<option value = "true" <s:if test='mobileVersion.isAuditing == "true"'>selected</s:if> >已审核</option>
							</select>
						</td>
						<td>所属平台：</td>
						<td>  
						    <select id="s_platform" name="mobileVersion.platform">				
						        <option value = "">全部</option>		
								<option value = "IPHONE" <s:if test='mobileVersion.platform == "IPHONE"'>selected</s:if> >iPhone平台</option>
								<option value = "IPAD" <s:if test='mobileVersion.platform == "IPAD"'>selected</s:if> >ipad平台</option>
								<option value = "ANDROID" <s:if test='mobileVersion.platform == "ANDROID"'>selected</s:if> >安卓手机平台</option>
								<option value = "ANDROID_HD" <s:if test='mobileVersion.platform == "ANDROID_HD"'>selected</s:if> >安卓平板平台</option>
								<option value = "WP7" <s:if test='mobileVersion.platform == "WP7"'>selected</s:if> >WP7</option>
								<option value = "WP8" <s:if test='mobileVersion.platform == "WP8"'>selected</s:if> >WP8</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>一级渠道：</td>
						<td>  
						    <select id="s_firstChannel" name="mobileVersion.firstChannel" onchange="firstChannelChange('queryListForm','s_firstChannel','s_seconedChannel')">					
						        <option value = "">全部</option>	
								<option value = "IPHONE" <s:if test='mobileVersion.firstChannel == "IPHONE"'>selected</s:if> >iPhone平台</option>
								<option value = "IPAD" <s:if test='mobileVersion.firstChannel == "IPAD"'>selected</s:if> >ipad平台</option>
								<option value = "ANDROID" <s:if test='mobileVersion.firstChannel == "ANDROID"'>selected</s:if> >安卓手机平台</option>
								<option value = "ANDROID_HD" <s:if test='mobileVersion.firstChannel == "ANDROID_HD"'>selected</s:if> >安卓平板平台</option>
								<option value = "WP7" <s:if test='mobileVersion.firstChannel == "WP7"'>selected</s:if> >WP7</option>
								<option value = "WP8" <s:if test='mobileVersion.firstChannel == "WP8"'>selected</s:if> >WP8</option>
							</select>
						</td>
						
						<td>二级渠道：</td>
						<td>  
						    <select id="s_seconedChannel" name="mobileVersion.seconedChannel">
						       <option value = "">全部</option>	
						       <s:if test='#mobileVersion.platform == "IPHONE"'>
									<option value = "APPSTORE" <s:if test='mobileVersion.seconedChannel == "APPSTORE"'>selected</s:if> >appstore</option>
									<option value = "IOS_91" <s:if test='mobileVersion.seconedChannel == "IOS_91"'>selected</s:if> >91手机助手</option>
									<option value = "TONGBUTUI" <s:if test='mobileVersion.seconedChannel == "TONGBUTUI"'>selected</s:if> >同步推</option>
							        <option value = "WEIFENGYUAN" <s:if test='mobileVersion.seconedChannel == "WEIFENGYUAN"'>selected</s:if> >威锋源</option>
							        <option value = "PP" <s:if test='mobileVersion.seconedChannel == "PP"'>selected</s:if> >PP助手</option>
						        </s:if>		
								<s:if test='#mobileVersion.platform == "ANDROID"'>
									<option value = "LVMM" <s:if test='mobileVersion.seconedChannel == "LVMM"'>selected</s:if> >官网</option>
									<option value = "ANDROID_91" <s:if test='mobileVersion.seconedChannel == "ANDROID_91"'>selected</s:if> >91手机助手</option>
									<option value = "ANDROID_360" <s:if test='mobileVersion.seconedChannel == "ANDROID_360"'>selected</s:if> >360手机助手</option>
									<option value = "GOAPK" <s:if test='mobileVersion.seconedChannel == "GOAPK"'>selected</s:if> >安智市场</option>
									<option value = "QQ" <s:if test='mobileVersion.seconedChannel == "QQ"'>selected</s:if> >腾讯应用中心</option>
									<option value = "XIAOMI" <s:if test='mobileVersion.seconedChannel == "XIAOMI"'>selected</s:if> >小米应用商店</option>
									<option value = "GFAN" <s:if test='mobileVersion.seconedChannel == "GFAN"'>selected</s:if> >机锋市场</option>
									<option value = "HIAPK" <s:if test='mobileVersion.seconedChannel == "HIAPK"'>selected</s:if> >安卓市场</option>
									<option value = "WANDOUJIA" <s:if test='mobileVersion.seconedChannel == "WANDOUJIA"'>selected</s:if> >豌豆荚</option>
									<option value = "APPCHINA" <s:if test='mobileVersion.seconedChannel == "APPCHINA"'>selected</s:if> >应用汇</option>
									<option value = "BAIDU" <s:if test='mobileVersion.seconedChannel == "BAIDU"'>selected</s:if> >百度应用中心</option>
									<option value = "SAMSUNG" <s:if test='mobileVersion.seconedChannel == "SAMSUNG"'>selected</s:if> >三星应用商店</option>
									<option value = "NDUO" <s:if test='mobileVersion.seconedChannel == "NDUO"'>selected</s:if> >N多市场</option>
									<option value = "LENOVO" <s:if test='mobileVersion.seconedChannel == "LENOVO"'>selected</s:if> >联想乐商店</option>
									<option value = "ANDROID_3G" <s:if test='mobileVersion.seconedChannel == "ANDROID_3G"'>selected</s:if> >3G安卓市场</option>
									<option value = "HUAWEI" <s:if test='mobileVersion.seconedChannel == "HUAWEI"'>selected</s:if> >智慧云</option>
									<option value = "WOSTORE" <s:if test='mobileVersion.seconedChannel == "WOSTORE"'>selected</s:if> >联通沃商店</option>
									<option value = "MUMAYI" <s:if test='mobileVersion.seconedChannel == "MUMAYI"'>selected</s:if> >木蚂蚁</option>
									<option value = "EOEMARKET" <s:if test='mobileVersion.seconedChannel == "EOEMARKET"'>selected</s:if> >优亿市场</option>
									<option value = "YIDONGMM" <s:if test='mobileVersion.seconedChannel == "YIDONGMM"'>selected</s:if> >移动MM</option>
									<option value = "ZTE" <s:if test='mobileVersion.seconedChannel == "ZTE"'>selected</s:if> >中兴商店</option>
									<option value = "MEIZU" <s:if test='mobileVersion.seconedChannel == "MEIZU"'>selected</s:if> >魅族商店</option>
									<option value = "COOLMART" <s:if test='mobileVersion.seconedChannel == "COOLMART"'>selected</s:if> >酷派商店</option>
									<option value = "CROSSMAO" <s:if test='mobileVersion.seconedChannel == "CROSSMAO"'>selected</s:if> >十字猫</option>
									<option value = "GOOGLEPLAY" <s:if test='mobileVersion.seconedChannel == "GOOGLEPLAY"'>selected</s:if> >googlePlay</option>
									<option value = "ALIPAY" <s:if test='mobileVersion.seconedChannel == "ALIPAY"'>selected</s:if> >支付宝</option>
									<option value = "UC" <s:if test='mobileVersion.seconedChannel == "UC"'>selected</s:if> >UC</option>
									<option value = "MADHOUSE" <s:if test='mobileVersion.seconedChannel == "MADHOUSE"'>selected</s:if> >亿动广告</option>
									<option value = "sanxing_wallet" <s:if test='mobileVersion.seconedChannel == "sanxing_wallet"'>selected</s:if> >三星钱包</option>
									<option value = "PANKU1" <s:if test='mobileVersion.seconedChannel == "PANKU1"'>selected</s:if> >盘库1</option>
									<option value = "PANKU2" <s:if test='mobileVersion.seconedChannel == "PANKU2"'>selected</s:if> >盘库2</option>
									<option value = "PANKU3" <s:if test='mobileVersion.seconedChannel == "PANKU3"'>selected</s:if> >盘库3</option>
									<option value = "PANKU4" <s:if test='mobileVersion.seconedChannel == "PANKU4"'>selected</s:if> >盘库4</option>
									<option value = "PANKU5" <s:if test='mobileVersion.seconedChannel == "PANKU1"'>selected</s:if> >盘库5</option>
									<option value = "OPPO" <s:if test='mobileVersion.seconedChannel == "OPPO"'>selected</s:if> >OPPO商店</option>
									<option value = "GUODONG" <s:if test='mobileVersion.seconedChannel == "GUODONG"'>selected</s:if> >GUODONG</option>
								    <option value = "ALL" <s:if test='mobileVersion.seconedChannel == "ALL"'>selected</s:if> >其他渠道 </option>
								</s:if>
								<s:if test='#mobileVersion.platform == "IPAD"'>
									<option value = "APPSTORE" <s:if test='mobileVersion.seconedChannel == "APPSTORE"'>selected</s:if> >appstore</option>
									<option value = "IOS_91" <s:if test='mobileVersion.seconedChannel == "IOS_91"'>selected</s:if> >91手机助手</option>
									<option value = "WEIFENGYUAN" <s:if test='mobileVersion.seconedChannel == "WEIFENGYUAN"'>selected</s:if> >威锋源</option>
									<option value = "TONGBUTUI" <s:if test='mobileVersion.seconedChannel == "TONGBUTUI"'>selected</s:if> >同步推</option>
						        </s:if>		
						        <s:if test='#mobileVersion.platform == "WP8"'>
									<option value = "WPMARKET" <s:if test='mobileVersion.seconedChannel == "WPMARKET"'>selected</s:if> >WP商店</option>
									<option value = "ALL" <s:if test='mobileVersion.seconedChannel == "ALL"'>selected</s:if> >其他</option>
						        </s:if>	
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<!-- 注意：此处调用弹窗的class必须排在第一位，如btn_padd 排在前面，且btn_padd与对应弹窗#padd_box中padd是相同的 -->
				        	<input type="submit" value="查&#12288;询" class="btn_bulkadd p_btn" />&#12288;&#12288;
				        	<input type="button" value="新&#12288;增" class="btn_padd p_btn" onclick="addMobileVersion()"/>&#12288;&#12288;
						</td>
					</tr>
				</table>
			</form>
        </div>
        
        <!-- 列表  -->
        <div class="oper_box">
        	<table class="p_table">
                <thead>
                    <tr>
                    <th><input type="checkbox" onclick="checkedAll(this);" value=""/></th>
                        <th>id</th>
                        <th>标题</th>
                        <th>内容</th>
                        <th>审核状态</th>
                        <th>强制更新</th>
                        <th>版本号</th>
                        <th>所属平台</th>
                        <th>一级投放渠道</th>
                        <th>二级投放渠道</th>
                        <th>更新地址</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                   <s:iterator value="pagination.items" var="version">
                    <tr>
                        <td><input type="checkbox" name="versionId_seq" value="<s:property value="#version.id"/>"/></td>
                        <td><s:property value="#version.id"/></td>
                        <td><s:property value="#version.title"/></td>
                        <td style="width:20%;"><s:property value="#version.content"/></td>
                        <td>
	                         <s:if test="#version.isAuditing == 'true'">
	                         	未审核
	                         </s:if>
	                         <s:if test="#version.isAuditing == 'false'">
	                         	 已审核
	                         </s:if>
                        </td>
                        <td>
	                        <s:if test="#version.forceUpdate == 'true'">
	                         	是
	                         </s:if>
	                         <s:if test="#version.forceUpdate == 'false'">
	                         	 否
	                         </s:if>
                        </td>
                        <td><s:property value="#version.version"/></td>
                        <td>
                   		    <select id="platform" disabled>					
								<option value = "IPHONE" <s:if test='#version.platform == "IPHONE"'>selected</s:if> >iPhone平台</option>
								<option value = "IPAD" <s:if test='#version.platform == "IPAD"'>selected</s:if> >ipad平台</option>
								<option value = "ANDROID" <s:if test='#version.platform == "ANDROID"'>selected</s:if> >安卓手机平台</option>
								<option value = "ANDROID_HD" <s:if test='#version.platform == "ANDROID_HD"'>selected</s:if> >安卓平板平台</option>
								<option value = "WP7" <s:if test='#version.platform == "WP7"'>selected</s:if> >WP7</option>
								<option value = "WP8" <s:if test='#version.platform == "WP8"'>selected</s:if> >WP8</option>
							 </select>
                        </td>
                        <td>
	                        <select id="platform" disabled>					
									<option value = "IPHONE" <s:if test='#version.platform == "IPHONE"'>selected</s:if> >iPhone平台</option>
									<option value = "IPAD" <s:if test='#version.platform == "IPAD"'>selected</s:if> >ipad平台</option>
									<option value = "ANDROID" <s:if test='#version.platform == "ANDROID"'>selected</s:if> >安卓手机平台</option>
									<option value = "ANDROID_HD" <s:if test='#version.platform == "ANDROID_HD"'>selected</s:if> >安卓平板平台</option>
									<option value = "WP7" <s:if test='#version.platform == "WP7"'>selected</s:if> >WP7</option>
									<option value = "WP8" <s:if test='#version.platform == "WP8"'>selected</s:if> >WP8</option>
							 </select>
                        </td>
                        <td>
                        	<select id="seconedChannel" disabled>
							   <s:if test='#version.platform == "IPHONE"'>
									<option value = "APPSTORE" <s:if test='#version.seconedChannel == "APPSTORE"'>selected</s:if> >appstore</option>
									<option value = "IOS_91" <s:if test='#version.seconedChannel == "IOS_91"'>selected</s:if> >91手机助手</option>
									<option value = "TONGBUTUI" <s:if test='#version.seconedChannel == "TONGBUTUI"'>selected</s:if> >同步推</option>
							        <option value = "WEIFENGYUAN" <s:if test='#version.seconedChannel == "WEIFENGYUAN"'>selected</s:if> >威锋源</option>
							        <option value = "PP" <s:if test='#version.seconedChannel == "PP"'>selected</s:if> >PP助手</option>
						        </s:if>		
								<s:if test='#version.platform == "ANDROID"'>
									<option value = "LVMM" <s:if test='#version.seconedChannel == "LVMM"'>selected</s:if> >官网</option>
									<option value = "ANDROID_91" <s:if test='#version.seconedChannel == "ANDROID_91"'>selected</s:if> >91手机助手</option>
									<option value = "ANDROID_360" <s:if test='#version.seconedChannel == "ANDROID_360"'>selected</s:if> >360手机助手</option>
									<option value = "GOAPK" <s:if test='#version.seconedChannel == "GOAPK"'>selected</s:if> >安智市场</option>
									<option value = "QQ" <s:if test='#version.seconedChannel == "QQ"'>selected</s:if> >腾讯应用中心</option>
									<option value = "XIAOMI" <s:if test='#version.seconedChannel == "XIAOMI"'>selected</s:if> >小米应用商店</option>
									<option value = "GFAN" <s:if test='#version.seconedChannel == "GFAN"'>selected</s:if> >机锋市场</option>
									<option value = "HIAPK" <s:if test='#version.seconedChannel == "HIAPK"'>selected</s:if> >安卓市场</option>
									<option value = "WANDOUJIA" <s:if test='#version.seconedChannel == "WANDOUJIA"'>selected</s:if> >豌豆荚</option>
									<option value = "APPCHINA" <s:if test='#version.seconedChannel == "APPCHINA"'>selected</s:if> >应用汇</option>
									<option value = "BAIDU" <s:if test='#version.seconedChannel == "BAIDU"'>selected</s:if> >百度应用中心</option>
									<option value = "SAMSUNG" <s:if test='#version.seconedChannel == "SAMSUNG"'>selected</s:if> >三星应用商店</option>
									<option value = "NDUO" <s:if test='#version.seconedChannel == "NDUO"'>selected</s:if> >N多市场</option>
									<option value = "LENOVO" <s:if test='#version.seconedChannel == "LENOVO"'>selected</s:if> >联想乐商店</option>
									<option value = "ANDROID_3G" <s:if test='#version.seconedChannel == "ANDROID_3G"'>selected</s:if> >3G安卓市场</option>
									<option value = "HUAWEI" <s:if test='#version.seconedChannel == "HUAWEI"'>selected</s:if> >智慧云</option>
									<option value = "WOSTORE" <s:if test='#version.seconedChannel == "WOSTORE"'>selected</s:if> >联通沃商店</option>
									<option value = "MUMAYI" <s:if test='#version.seconedChannel == "MUMAYI"'>selected</s:if> >木蚂蚁</option>
									<option value = "EOEMARKET" <s:if test='#version.seconedChannel == "EOEMARKET"'>selected</s:if> >优亿市场</option>
									<option value = "YIDONGMM" <s:if test='#version.seconedChannel == "YIDONGMM"'>selected</s:if> >移动MM</option>
									<option value = "ZTE" <s:if test='#version.seconedChannel == "ZTE"'>selected</s:if> >中兴商店</option>
									<option value = "MEIZU" <s:if test='#version.seconedChannel == "MEIZU"'>selected</s:if> >魅族商店</option>
									<option value = "COOLMART" <s:if test='#version.seconedChannel == "COOLMART"'>selected</s:if> >酷派商店</option>
									<option value = "CROSSMAO" <s:if test='#version.seconedChannel == "CROSSMAO"'>selected</s:if> >十字猫</option>
									<option value = "GOOGLEPLAY" <s:if test='#version.seconedChannel == "GOOGLEPLAY"'>selected</s:if> >googlePlay</option>
									<option value = "ALIPAY" <s:if test='#version.seconedChannel == "ALIPAY"'>selected</s:if> >支付宝</option>
									<option value = "UC" <s:if test='#version.seconedChannel == "UC"'>selected</s:if> >UC</option>
									<option value = "MADHOUSE" <s:if test='#version.seconedChannel == "MADHOUSE"'>selected</s:if> >亿动广告</option>
									<option value = "sanxing_wallet" <s:if test='#version.seconedChannel == "sanxing_wallet"'>selected</s:if> >三星钱包</option>
									<option value = "PANKU1" <s:if test='#version.seconedChannel == "PANKU1"'>selected</s:if> >盘库1</option>
									<option value = "PANKU2" <s:if test='#version.seconedChannel == "PANKU2"'>selected</s:if> >盘库2</option>
									<option value = "PANKU3" <s:if test='#version.seconedChannel == "PANKU3"'>selected</s:if> >盘库3</option>
									<option value = "PANKU4" <s:if test='#version.seconedChannel == "PANKU4"'>selected</s:if> >盘库4</option>
									<option value = "PANKU5" <s:if test='#version.seconedChannel == "PANKU1"'>selected</s:if> >盘库5</option>
									<option value = "OPPO" <s:if test='#version.seconedChannel == "OPPO"'>selected</s:if> >OPPO商店</option>
									<option value = "GUODONG" <s:if test='#version.seconedChannel == "GUODONG"'>selected</s:if> >GUODONG</option>
									<option value = "ALL" <s:if test='#version.seconedChannel == "ALL"'>selected</s:if> >其它</option>
								</s:if>
								<s:if test='#version.platform == "IPAD"'>
									<option value = "APPSTORE" <s:if test='#version.seconedChannel == "APPSTORE"'>selected</s:if> >appstore</option>
									<option value = "IOS_91" <s:if test='#version.seconedChannel == "IOS_91"'>selected</s:if> >91手机助手</option>
									<option value = "WEIFENGYUAN" <s:if test='#version.seconedChannel == "WEIFENGYUAN"'>selected</s:if> >威锋源</option>
									<option value = "TONGBUTUI" <s:if test='#version.seconedChannel == "TONGBUTUI"'>selected</s:if> >同步推</option>
						        </s:if>	
						        <s:if test='#version.platform == "WP8"'>
									<option value = "WPMARKET" <s:if test='#version.seconedChannel == "WPMARKET"'>selected</s:if> >WP商店</option>
									<option value = "ALL" <s:if test='#version.seconedChannel == "ALL"'>selected</s:if> >其他</option>
						        </s:if>		
							 </select>
                        </td>
                         <td><s:property value="#version.updateUrl"/></td>
                         <td><s:date name="#version.createdTime" format="yyyy-MM-dd" /></td>
                        <td>
                            <a href="javascript:void(0)" class="p_btn_edit">编辑<i class="p_arrow"></i></a> 
                            <s:if test="#version.isAuditing == 'true'">
	                            <a href="javascript:void(0)" class="p_btn_del" onclick="updateAuditeStatus('<s:property value="#version.id"/>','false')">审核通过&nbsp;</a> 
                            </s:if>
                            <s:if test="#version.isAuditing == 'false'">
	                            <a href="javascript:void(0)" class="p_btn_del" onclick="updateAuditeStatus('<s:property value="#version.id"/>','true')">取消审核&nbsp;</a> 
                            </s:if>
                        	<a href="javascript:void(0)" class="p_btn_del" onclick="delVersion('<s:property value="#version.id"/>')">删除&nbsp;</a>
                        </td>
                    </tr>
                    <tr class="p_detail">
                    	<td colspan="13">
                    	<h5>详细信息</h5>
                        <div class="detail_box">
                        	<form class="form_hor" onsubmit="saveMobileVersion('ri<s:property value="#version.id"/>');return false;" id="ri<s:property value="#version.id"/>">
                            <input type="hidden" id="id" value="<s:property value="#version.id"/>"/>
                            <input type="hidden" id="isAuditing" value="<s:property value="#version.isAuditing"/>"/>
                            <input type="hidden" id="forceUpdate" value="<s:property value="#version.forceUpdate"/>"/>
                            <ul class="ul_detail vip_info clearfix">
                                  <li>
				                      <label>
					                      <span class="label_text">标题：</span>
					                      <input type="text" id="title" size="60" value="<s:property value="#version.title"/>"/>(最多200个字符)
				                      </label>
				                  </li>
				                   <li>
				                      <label>
					                      <span class="label_text">更新内容：</span>
					                      <textarea class="p_long" id="content" rows="5" cols="80"  ><s:property value="#version.content"/></textarea>(最多200个字符)
				                      </label>
				                  </li>
				                   <li>
				                      <label>
					                      <span class="label_text">最新版本号：</span>
					                      <input type="text" id="version"  size="60"  value="<s:property value="#version.version"/>"/>
				                      </label>
				                  </li>
				                  <li>
				                      <label>
					                      <span class="label_text">更新地址：</span>
					                      <input type="text" id="updateUrl"  size="60"  value="<s:property value="#version.updateUrl"/>"/>(最多200个字符)
				                      </label>
				                   </li>
				                   <li>
				                      <label>
					                       <span class="label_text">所属平台：</span>
					                       <select id="platform" >					
												<option value = "IPHONE" <s:if test='#version.platform == "IPHONE"'>selected</s:if> >iPhone平台</option>
												<option value = "IPAD" <s:if test='#version.platform == "IPAD"'>selected</s:if> >ipad平台</option>
												<option value = "ANDROID" <s:if test='#version.platform == "ANDROID"'>selected</s:if> >安卓手机平台</option>
												<option value = "ANDROID_HD" <s:if test='#version.platform == "ANDROID_HD"'>selected</s:if> >安卓平板平台</option>
												<option value = "WP7" <s:if test='#version.platform == "WP7"'>selected</s:if> >WP7</option>
												<option value = "WP8" <s:if test='#version.platform == "WP8"'>selected</s:if> >WP8</option>
											</select>
				                      </label>
				                   </li>
				                   <li>
				                      <label>
					                       <span class="label_text">一级投放渠道：</span>
					                       <select id="firstChannel" onchange="firstChannelChange('ri<s:property value="#version.id"/>','firstChannel','seconedChannel')">					
												<option value = "IPHONE" <s:if test='#version.firstChannel == "IPHONE"'>selected</s:if> >iPhone平台</option>
												<option value = "IPAD" <s:if test='#version.firstChannel == "IPAD"'>selected</s:if> >ipad平台</option>
												<option value = "ANDROID" <s:if test='#version.firstChannel == "ANDROID"'>selected</s:if> >安卓手机平台</option>
												<option value = "ANDROID_HD" <s:if test='#version.firstChannel == "ANDROID_HD"'>selected</s:if> >安卓平板平台</option>
												<option value = "WP7" <s:if test='#version.firstChannel == "WP7"'>selected</s:if> >WP7</option>
												<option value = "WP8" <s:if test='#version.firstChannel == "WP8"'>selected</s:if> >WP8</option>
											</select>
				                      </label>
				                  </li>
				                   <li>
				                      <label>
					                      <span class="label_text">二级投放渠道：</span>
					                      <select id="seconedChannel">
										       <s:if test='#version.platform == "IPHONE"'>
													<option value = "APPSTORE" <s:if test='#version.seconedChannel == "APPSTORE"'>selected</s:if> >appstore</option>
													<option value = "IOS_91" <s:if test='#version.seconedChannel == "IOS_91"'>selected</s:if> >91手机助手</option>
													<option value = "TONGBUTUI" <s:if test='#version.seconedChannel == "TONGBUTUI"'>selected</s:if> >同步推</option>
											        <option value = "WEIFENGYUAN" <s:if test='#version.seconedChannel == "WEIFENGYUAN"'>selected</s:if> >威锋源</option>
											        <option value = "PP" <s:if test='#version.seconedChannel == "PP"'>selected</s:if> >PP助手</option>
										        </s:if>		
												<s:if test='#version.platform == "ANDROID"'>
													<option value = "LVMM" <s:if test='#version.seconedChannel == "LVMM"'>selected</s:if> >官网</option>
													<option value = "ANDROID_91" <s:if test='#version.seconedChannel == "ANDROID_91"'>selected</s:if> >91手机助手</option>
													<option value = "ANDROID_360" <s:if test='#version.seconedChannel == "ANDROID_360"'>selected</s:if> >360手机助手</option>
													<option value = "GOAPK" <s:if test='#version.seconedChannel == "GOAPK"'>selected</s:if> >安智市场</option>
													<option value = "QQ" <s:if test='#version.seconedChannel == "QQ"'>selected</s:if> >腾讯应用中心</option>
													<option value = "XIAOMI" <s:if test='#version.seconedChannel == "XIAOMI"'>selected</s:if> >小米应用商店</option>
													<option value = "GFAN" <s:if test='#version.seconedChannel == "GFAN"'>selected</s:if> >机锋市场</option>
													<option value = "HIAPK" <s:if test='#version.seconedChannel == "HIAPK"'>selected</s:if> >安卓市场</option>
													<option value = "WANDOUJIA" <s:if test='#version.seconedChannel == "WANDOUJIA"'>selected</s:if> >豌豆荚</option>
													<option value = "APPCHINA" <s:if test='#version.seconedChannel == "APPCHINA"'>selected</s:if> >应用汇</option>
													<option value = "BAIDU" <s:if test='#version.seconedChannel == "BAIDU"'>selected</s:if> >百度应用中心</option>
													<option value = "SAMSUNG" <s:if test='#version.seconedChannel == "SAMSUNG"'>selected</s:if> >三星应用商店</option>
													<option value = "NDUO" <s:if test='#version.seconedChannel == "NDUO"'>selected</s:if> >N多市场</option>
													<option value = "LENOVO" <s:if test='#version.seconedChannel == "LENOVO"'>selected</s:if> >联想乐商店</option>
													<option value = "ANDROID_3G" <s:if test='#version.seconedChannel == "ANDROID_3G"'>selected</s:if> >3G安卓市场</option>
													<option value = "HUAWEI" <s:if test='#version.seconedChannel == "HUAWEI"'>selected</s:if> >智慧云</option>
													<option value = "WOSTORE" <s:if test='#version.seconedChannel == "WOSTORE"'>selected</s:if> >联通沃商店</option>
													<option value = "MUMAYI" <s:if test='#version.seconedChannel == "MUMAYI"'>selected</s:if> >木蚂蚁</option>
													<option value = "EOEMARKET" <s:if test='#version.seconedChannel == "EOEMARKET"'>selected</s:if> >优亿市场</option>
													<option value = "YIDONGMM" <s:if test='#version.seconedChannel == "YIDONGMM"'>selected</s:if> >移动MM</option>
													<option value = "ZTE" <s:if test='#version.seconedChannel == "ZTE"'>selected</s:if> >中兴商店</option>
													<option value = "MEIZU" <s:if test='#version.seconedChannel == "MEIZU"'>selected</s:if> >魅族商店</option>
													<option value = "COOLMART" <s:if test='#version.seconedChannel == "COOLMART"'>selected</s:if> >酷派商店</option>
													<option value = "CROSSMAO" <s:if test='#version.seconedChannel == "CROSSMAO"'>selected</s:if> >十字猫</option>
													<option value = "GOOGLEPLAY" <s:if test='#version.seconedChannel == "GOOGLEPLAY"'>selected</s:if> >googlePlay</option>
												    <option value = "ALIPAY" <s:if test='#version.seconedChannel == "ALIPAY"'>selected</s:if> >支付宝</option>
												 <option value = "UC" <s:if test='#version.seconedChannel == "UC"'>selected</s:if> >UC</option>
												 <option value = "MADHOUSE" <s:if test='#version.seconedChannel == "MADHOUSE"'>selected</s:if> >亿动广告</option>
												 <option value = "sanxing_wallet" <s:if test='#version.seconedChannel == "sanxing_wallet"'>selected</s:if> >三星钱包</option>
												 <option value = "PANKU1" <s:if test='#version.seconedChannel == "PANKU1"'>selected</s:if> >盘库1</option>
												<option value = "PANKU2" <s:if test='#version.seconedChannel == "PANKU2"'>selected</s:if> >盘库2</option>
												<option value = "PANKU3" <s:if test='#version.seconedChannel == "PANKU3"'>selected</s:if> >盘库3</option>
												<option value = "PANKU4" <s:if test='#version.seconedChannel == "PANKU4"'>selected</s:if> >盘库4</option>
												<option value = "PANKU5" <s:if test='#version.seconedChannel == "PANKU1"'>selected</s:if> >盘库5</option>
												<option value = "OPPO" <s:if test='#version.seconedChannel == "OPPO"'>selected</s:if> >OPPO商店</option>
												<option value = "GUODONG" <s:if test='#version.seconedChannel == "GUODONG"'>selected</s:if> >GUODONG</option>
												  <option value = "ALL" <s:if test='#version.seconedChannel == "ALL"'>selected</s:if> >其它</option>
												</s:if>
						
												<s:if test='#version.platform == "IPAD"'>
													<option value = "APPSTORE" <s:if test='#version.seconedChannel == "APPSTORE"'>selected</s:if> >appstore</option>
													<option value = "IOS_91" <s:if test='#version.seconedChannel == "IOS_91"'>selected</s:if> >91手机助手</option>
													<option value = "WEIFENGYUAN" <s:if test='#version.seconedChannel == "WEIFENGYUAN"'>selected</s:if> >威锋源</option>
									                <option value = "TONGBUTUI" <s:if test='#version.seconedChannel == "TONGBUTUI"'>selected</s:if> >同步推</option>
										        </s:if>	
										        <s:if test='#version.platform == "WP8"'>
													<option value = "WPMARKET" <s:if test='#version.seconedChannel == "WPMARKET"'>selected</s:if> >WP商店</option>
									                <option value = "ALL" <s:if test='#version.seconedChannel == "ALL"'>selected</s:if> >其他</option>
										        </s:if>			
										 </select>
				                      </label>
				                  </li>
                            </ul>
                            <p class="p_sure">
	                        <button class="p_btn" type="submit">保&#12288;存</button></p>
                            </form>
                        </div>
                        </td>
                     </tr>
                     
                    </s:iterator>
                </tbody>
            </table>
            <div class="p_page">
                <div class="pages"><span>共<s:property value="pagination.totalResultSize"/>条</span><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></div>
            </div>
        </div>
    </div>
    
    <!-- 新增页面 -->
	<input type="hidden" value="新增页面" id="addVersionClick"/>
	<div class="js_zs js_cl_all" id="addVersion" style="width:650px;">
		<h3><a class="close" href="javascript:void(0);" onclick="closeWin('addVersion')">X</a>新增页面</h3>
			<div class="tab_ztxx_all">
		      <form id="addMobileVersionForm" method="post" onsubmit="saveMobileVersion('addMobileVersionForm');return false;" >
		      <input type="hidden" name="mobileVersion.id" id = "id" />
			  <table width="100%" border="0"  class="tab_ztxx">
			     <tr>
				      <td class="bgNavBlue" align="left" colspan="4">&nbsp;&nbsp;标&nbsp;&nbsp;题&nbsp;&nbsp;:&nbsp;&nbsp;<input type="text" size="60" name="mobileVersion.title" id="title"/>(最多200个字符)
				      </td>
			     </tr>
			     <tr>
				      <td class="bgNavBlue" align="left"  colspan="4">&nbsp;&nbsp;内&nbsp;&nbsp;容&nbsp;&nbsp;:&nbsp;
				      	  <textarea name="mobileVersion.content" class="p_long" id="content" rows="5" cols="160"></textarea>(最多200个字符)
				      </td>
			     </tr>
			     <tr>
				      <td class="bgNavBlue" align="left"  colspan="4">更新地址:<input type="text" size="60" name="mobileVersion.updateUrl" id="updateUrl"/>(最多200个字符)</td>
			     </tr>
			     <tr id="son_modeType">
				      <td class="bgNavBlue" align="left">&nbsp;版&nbsp;本&nbsp;号:&nbsp;<input type="text" size="20" name="mobileVersion.version" id="version"/>
				      </td>
				      <td class="bgNavBlue" align="left">所属平台:
				          <select id="platform" name="mobileVersion.platform">					
							    <option value = "IPHONE">iPhone平台</option>
								<option value = "IPAD" >ipad平台</option>
								<option value = "ANDROID" >安卓手机平台</option>
								<option value = "ANDROID_HD" >安卓平板平台</option>
								<option value = "WP7">WP7</option>
								<option value = "WP8">WP8</option>
						  </select>
				      </td>
			     </tr>
			     <tr>
				      <td class="bgNavBlue" align="left">一级渠道:
				          <select id="firstChannel" name="mobileVersion.firstChannel" onchange="firstChannelChange('addMobileVersionForm','firstChannel','seconedChannel');">					
							    <option value = "IPHONE">iPhone平台</option>
							 	<option value = "IPAD" >ipad平台</option>
								<option value = "ANDROID" >安卓手机平台</option>
								<option value = "ANDROID_HD" >安卓平板平台</option>
								<option value = "WP7">WP7</option>
								<option value = "WP8">WP8</option>
						  </select>
				      </td>
				      <td class="bgNavBlue" align="left">二级渠道:
				          <select id="seconedChannel" name="mobileVersion.seconedChannel">					
						  </select>
				      </td>
			     </tr>
			  </table>
			  <input value="提交" type="submit" />
			</form>
		</div>
	    <!--  <iframe id="addVersionIframeObc" src="" width="100%" height="100" frameborder="0" scrolling="no" ></iframe> -->
	</div>
	
</body>
</html>

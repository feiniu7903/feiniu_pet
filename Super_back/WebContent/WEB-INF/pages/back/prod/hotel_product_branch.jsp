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
<title>super后台——销售产品列表</title>
<link href="<%=basePath%>style/houtai.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
</head>

<body>
<div class="main main09">
	<div class="row1">
    	<h3 class="newTit">产品列表<a href="#">添加新产品</a></h3>
        <div class="nav">
        	<ul>
            	<li><a href="#">基本信息</a></li>
            	<li class="current"><a href="#">票种</a></li>
            	<li><a href="#">附加产品</a></li>
            	<li><a href="#">产品图片</a></li>
            	<li><a href="#">描述信息</a></li>
            	<li><a href="#">行程展示</a></li>
            	<li><a href="#">产品标签</a></li>
            </ul>    
		</div><!--nav end-->
   </div><!--row1 end-->    
   <div class="row2">
   		<table class="newTable" width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr class="tableTit">
            <td>类别名称</td>
            <td>人数</td>
            <td>类别描述</td>
            <td>计价单位</td>
            <td>最小起订量</td>
            <td>最大订购数</td>
            <td>操作</td>
          </tr>
          <tr>
            <td>成人票</td>
            <td><span>成人：1</span><span>儿童：0</span></td>
            <td>成人票</td>
            <td>人/张</td>
            <td>1</td>
            <td>10</td>
            <td><a href="#">查看价格</a><a href="#">修改价格</a><a href="#">打包采购</a></td>
          </tr>
          <tr>
            <td>成人票</td>
            <td><span>成人：1</span><span>儿童：0</span></td>
            <td>成人票</td>
            <td>人/张</td>
            <td>1</td>
            <td>10</td>
            <td><a href="#">查看价格</a><a href="#">修改价格</a><a href="#">打包采购</a></td>
          </tr>
        </table>
        <h3 class="titBotton">新建</h3>
   </div><!--row2 end-->
   <div class="row3">
   		<ul class="new">
        	<li>
         	   <label>请选择：</label>
                <select>
                  <option selected="selected">上海景域集团有限公司</option>
                  <option>上海驴妈妈国际旅游社</option>
                </select>
            </li>
        	<li><label>类别名称：</label><input type="text" class="text1" name="text1"><label>计价单位：</label><input type="text" class="text1" name="text1"></li>
        	<li class="newNumber"><label>人数：</label><span>成人：</span><input type="text" class="text1" name="text1"><span>儿童：</span><input type="text" class="text1" name="text1"></li>
        	<li><label>票种描述：</label><textarea name="text2" cols="text2" rows="text2" class="text2"></textarea></li>
        </ul>
        <p><em class="button button2">完成操作</em></p>
   </div><!--row3 end-->
</div><!--main01 main05 end-->
</body>
</html>




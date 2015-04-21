<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.lvmama.pet.corputils.*" %>
<%@page import="java.io.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String fileWebroot = pageContext.getServletContext().getRealPath("/");
MyNum myNum = new MyNum();

String imageSource = request.getParameter("imageSource");//图片源
String imageW = String.valueOf(myNum.roundToInt(request.getParameter("imageW")));//图片宽
String imageH = String.valueOf(myNum.roundToInt(request.getParameter("imageH")));//图片高
String imageX = String.valueOf(myNum.roundToInt(request.getParameter("imageX")));//图片X位置
String imageY = String.valueOf(myNum.roundToInt(request.getParameter("imageY")));//图片Y位置

String imageRotate = request.getParameter("imageRotate");
String viewPortW = request.getParameter("viewPortW");
String viewPortH = request.getParameter("viewPortH");

String selectorX = String.valueOf(myNum.roundToInt(request.getParameter("selectorX")));//选中区位置X
String selectorY = String.valueOf(myNum.roundToInt(request.getParameter("selectorY")));//选中区位置Y
String selectorW = String.valueOf(myNum.roundToInt(request.getParameter("selectorW")));//选中区位置宽
String selectorH = String.valueOf(myNum.roundToInt(request.getParameter("selectorH")));//选中区位置高


String suffix = imageSource.substring(imageSource.lastIndexOf(".")+1);
String cutImg = "placeUploadPics/"+System.currentTimeMillis()+"."+suffix;

session.setAttribute("imageSource",imageSource);
session.setAttribute("cutImgName",cutImg);


int cutX = 0,cutY=0;
cutX = Math.abs(Integer.parseInt(imageX)-Integer.parseInt(selectorX));
cutY =  Math.abs(Integer.parseInt(imageY)-Integer.parseInt(selectorY));
ImageUtils img = null;
if(imageSource.indexOf("http")!=-1){
	img = ImageUtils.load(imageSource);//更新时加载源图
}else{
	img = ImageUtils.load(fileWebroot+imageSource);//加载源图
}
img.zoomTo(Integer.parseInt(imageW),Integer.parseInt(imageH));//缩放源图
img.rotateTo(Integer.parseInt(imageW),Integer.parseInt(imageH),Integer.parseInt(imageRotate));//旋转源图
img.cutTo(Integer.parseInt(imageX),Integer.parseInt(imageY),Integer.parseInt(selectorX),Integer.parseInt(selectorY),cutX,cutY,Integer.parseInt(selectorW),Integer.parseInt(selectorH));//裁剪图片
img.save(fileWebroot + cutImg, suffix);//保存裁剪后的图片
File cutImgfile = new File(fileWebroot + cutImg);
if(cutImgfile.exists()){
	out.print(cutImg);
}

%>
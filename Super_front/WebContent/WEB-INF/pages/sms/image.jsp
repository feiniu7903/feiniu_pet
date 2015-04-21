<%@ page import="java.io.OutputStream" %><%
try{
	Object obj = request.getAttribute("codeImage");
	if(obj != null) {
		byte[] datas = (byte[])obj;
		response.setContentType("IMAGE/JPG");   
		OutputStream output = response.getOutputStream();   
		response.setContentLength(datas.length);   
		output.write(datas);   
		output.flush();
		output.close();   
		response.flushBuffer();
		out.clear();
		out = pageContext.pushBody();
	}
}catch(Exception ex) {
	ex.printStackTrace();
}%>


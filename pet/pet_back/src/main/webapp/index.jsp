<%
if (session.getAttribute("SESSION_USER")==null) {
	response.sendRedirect(request.getContextPath()+"/login.do");
}else{
	response.sendRedirect(request.getContextPath()+"/index.do");
}

%>
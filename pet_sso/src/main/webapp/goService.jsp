<%@page import="com.lvmama.comm.pet.po.user.UserUser"%>
<%@page import="com.lvmama.comm.utils.ServletUtil"%>
<%@page import="com.lvmama.comm.vo.Constant"%>
<jsp:include page="/common/coremetricsHead.jsp"></jsp:include>
<%@ page session="false" %>
<%
	try{
		 UserUser user = (UserUser)ServletUtil.getSession(request, response, Constant.SESSION_FRONT_USER);
		 String userNo = user.getUserNo();
		 %>
		 <script>
		 	cmCreateRegistrationTag('<%=userNo%>',"null","null","null","null","null","null-_-null-_-null-_-null-_-null");
		 </script>
		 <% 
	}catch(Exception e){
		
	}finally{
			String serviceId = (String) request.getAttribute("serviceId");
			String token = (String) request.getAttribute("token");
			String service = null;
			boolean safari = true; // will set this below
		
			if (serviceId.indexOf('?') == -1) {
				service = serviceId + "?ticket=" + token;
			} else {
				String[] serviceIdP = serviceId.split("\\?");
				service = serviceIdP[0] + "?";
				if (serviceIdP[1].length() > 0) {
					String[] serviceIdParam = serviceIdP[1].split("&");
					for (int i = 0; i < serviceIdParam.length; i++) {
						if (serviceIdParam[i].indexOf("ticket=") == -1) {
							service += serviceIdParam[i] + "&";
						}
					}
				}
				service = service + "ticket=" + token;
			}
		
			service = edu.yale.its.tp.cas.util.StringUtil.substituteAll(
					service, "\n", "");
			service = edu.yale.its.tp.cas.util.StringUtil.substituteAll(
					service, "\r", "");
			service = edu.yale.its.tp.cas.util.StringUtil.substituteAll(
					service, "\"", "");
		
			if (((String) request.getAttribute("first")).equals("false")
					|| request.getHeader("User-Agent") == null
					|| request.getHeader("User-Agent").indexOf("Safari") == -1) {
				safari = false;
			}
		%>
		<%
			if (!safari) {
		%>
		<script>
		  window.location.href="<%=service%>";
		</script>
		<%
			} else {
		%>
		<script>
		  window.location.href="<%=service%>";
		</script>
		<%
			}
	}
%>



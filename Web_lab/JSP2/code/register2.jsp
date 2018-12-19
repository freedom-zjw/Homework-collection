<%@ page language="java" import="java.util.*" 
         contentType="text/html; charset=utf-8"%>
<%request.setCharacterEncoding("utf-8");%>
<%
    String submit2 = (String) request.getParameter("submit2");
	if (submit2 != null)
		response.sendRedirect("http://172.18.187.11/main.htm");
	String club1 = request.getParameter("club1");
	String club2 = request.getParameter("club2");
	String club3 = request.getParameter("club3");
	int cnt = 0;
	String club = "[";
	if (club1 != null){
		cnt += 1;
		if (cnt > 1) club += ",";
		club += club1;
	}
	if (club2 != null){
		cnt += 1;
		if (cnt > 1) club += ",";
		club += club2;
	}
	if (club3 != null){
		cnt += 1;
		if (cnt > 1) club += ",";
		club += club3;
	}
	club += "]";
%>
<!DOCTYPE HTML>
<html>
  <head><title>用户注册输入显示</title></head>  
  <body>
		id: <%=request.getParameter("id") %><br/>
		同学名: <%=request.getParameter("Name") %><br/>
		密码: <%=request.getParameter("password") %><br/>
		校区: <%=request.getParameter("campus") %><br/>
		社团: <%=club%><br/>
		年级: <%=request.getParameter("grade") %><br/>
		说明: <%=request.getParameter("content") %><br/>
		submit1: <%=request.getParameter("submit1") %><br/>
		submit2: <%=submit2%><br/>
  </body>
</html>


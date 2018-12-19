<%@ page language="java" import="java.util.*,java.io.*" pageEncoding="utf-8" 
contentType="text/html;charset=utf-8"%>
<!DOCTYPE  html>
<html  lang="utf-8">
<head>
<title>Session Y</title>
</head>
<body>
  <h1>Session Y</h1>
  <% String id = session.getId();
  	 String course = (String)session.getAttribute("course");
  %>
  <P>session ID:<%=id %></P>
  <P>course:<%=course %></P>
  <p><a href="X.jsp">X.jsp</a>
  <p><a href="Z.jsp">Z.jsp</a>
</body>
</html>
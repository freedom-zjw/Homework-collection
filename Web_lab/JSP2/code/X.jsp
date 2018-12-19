<%@ page language="java" import="java.util.*,java.io.*" pageEncoding="utf-8" 
contentType="text/html;charset=utf-8"%>
<!DOCTYPE  html>
<html  lang="utf-8">
<head>
<title>Session X</title>
</head>
<body>
  <h1>Session X</h1>  
  <% String id = session.getId();
  	 Date date = new Date();
  	 String course = "移动应用设计 --- " + date.toString();
  	 session.setAttribute("course", course);
  %>
  <P>session ID:<%=id %></P>
  <p><a href="Y.jsp">Y.jsp</a>
  <p><a href="Z.jsp">Z.jsp</a>
</body>
</html>
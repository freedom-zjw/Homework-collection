<%@ page language="java" import="java.util.*,java.sql.*"  
         contentType="text/html; charset=utf-8"%>  
<%   
	request.setCharacterEncoding("utf-8");
	String msg ="";
	String connectString = "jdbc:mysql://localhost:3306/teaching"
					+ "?autoReconnect=true&useUnicode=true"
					+ "&characterEncoding=UTF-8";  	   
    String param = request.getParameter("pid");  
    String pid = "";  
    if(param != null && !param.isEmpty())pid = param;  
    try{  
        Class.forName("com.mysql.jdbc.Driver");  
        Connection con = DriverManager.getConnection(connectString,"user", "123");  
        Statement stmt = con.createStatement();  
		String sql = "";  
        String pre="delete  from stu where id=%s";  
        sql = String.format(pre, pid);  
        int cnt = stmt.executeUpdate(sql);  
        if (cnt>0) msg = "Delete Success!";
        stmt.close();  
        con.close();  
    }catch (Exception e){  
        msg = e.getMessage();  
    }      
%>  
<!DOCTYPE HTML>  
<html>  
    <head>  
        <title>删除学生记录</title>  
        <style>  
            .container{margin:0 auto;  width:500px;text-align:center;}    
        </style>  
    </head>  
    <body>  
        <div class="container">  
            <h1>删除学生记录</h1>  
            <%=msg%><br>  
            <a href='browseStu.jsp'>返回</a>  
        </div>  
    </body>  
</html>  
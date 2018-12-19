<%@ page language="java" import="java.util.*,java.sql.*"  
         contentType="text/html; charset=utf-8"%>  
<%   
	request.setCharacterEncoding("utf-8");
	String msg ="";
	String connectString = "jdbc:mysql://localhost:3306/teaching"
					+ "?autoReconnect=true&useUnicode=true"
					+ "&characterEncoding=UTF-8";  	
	String updateButton = request.getParameter("update");   
    String clearButton = request.getParameter("clear");    
    String param = request.getParameter("pid");  
    String pid = "";  
    if(param != null && !param.isEmpty())pid = param;  
    String num = request.getParameter("num");  
    if(num==null) num="";  
    String name = request.getParameter("name");  
    if(name==null) name="";  
    try{  
        Class.forName("com.mysql.jdbc.Driver");  
        Connection con = DriverManager.getConnection(connectString,"user", "123");  
        Statement stmt = con.createStatement();  
		String sql = "";  
        if(updateButton != null){        
            String pre="update stu set num='%s',name='%s' where id=%d";       
            sql = String.format(pre, num, name, Integer.parseInt(pid));  
            int cnt = stmt.executeUpdate(sql);  
            if(cnt>0) msg = "修改成功!";  
        }  
        else{  
            String pre="select * from stu where id=%d";  
            sql = String.format(pre, Integer.parseInt(pid));  
            ResultSet rs = stmt.executeQuery(sql);  
            if(rs.next()){  
                num = rs.getString("num");  
                name = rs.getString("name");  
            }  
        }    
        stmt.close();  
        con.close();  
    }catch (Exception e){  
        msg = e.getMessage();  
    }      
%>  
<!DOCTYPE HTML>  
<html>  
    <head>  
        <title>新增学生记录</title>  
        <style>  
            .container{margin:0 auto;  width:500px;text-align:center;  }  
            form {line-height:50px; }  
            a{ margin-top : 300px;}  
        </style>  
    </head>  
    <body>  
        <div class="container">  
            <h1>修改学生记录</h1>  
            <form action="updateStu.jsp?pid=<%=pid%>" method="post">  
                学号: <input id="num" name="num" type="text" value=<%=num %> ><br>  
                姓名: <input id="name" type="text" name="name" value=<%=name %>><br>  
                <input type="submit" name="update" value="修改">  
                <input type="submit" name="clear" value="清空">  
            </form>  
            <%=msg%><br>  
            <a href='browseStu.jsp'>返回</a>  
        </div>  
    </body>  
</html>  
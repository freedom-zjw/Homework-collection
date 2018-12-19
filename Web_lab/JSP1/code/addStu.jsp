<%@ page language="java" import="java.util.*,java.sql.*"  
 contentType="text/html; charset=utf-8"%>  
<%   
	request.setCharacterEncoding("utf-8");  
	String msg = "";  
	String connectString = "jdbc:mysql://localhost:3306/teaching"
					+ "?autoReconnect=true&useUnicode=true"
					+ "&characterEncoding=UTF-8"; 
	if(request.getMethod().equalsIgnoreCase("post")){  
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(connectString, "user", "123");
			Statement stmt = con.createStatement();  
			String pre="insert into stu(num,name) values('%s','%s')"; 
			String num = request.getParameter("num");  
			String name = request.getParameter("name");  
			String sql = String.format(pre,num,name);  
			int cnt = stmt.executeUpdate(sql);  
			if(cnt>0) msg = "保存成功!";   
			stmt.close();  
			con.close();  
		}catch (Exception e){  
			msg = e.getMessage();  
		}  
	}  
%>  
<!DOCTYPE HTML>
<html>
	<head>  
		<title>新增学生记录</title>  
		<style type="text/css">
			.container{text-align:center;
			}
			input{margin:10px auto;}
		</style>  
	</head>  
	<body>  
		<div class="container">  
			<h1>新增学生记录</h1>  
			<form action="addStu.jsp" method="post" name="newAdd">  
				学号:<input id="num" name="num" type="text" ><br>  
				姓名:<input id="name" type="text" name="name" ><br>  
				<input type="submit" name="sub" value="保存">  
			</form>  
			<%=msg%><br> 
			<a href='browseStu.jsp'>返回</a>  
		</div>  
	</body>  
</html>  
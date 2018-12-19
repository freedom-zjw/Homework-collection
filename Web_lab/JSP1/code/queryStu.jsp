<%@ page language="java" import="java.util.*,java.sql.*" 
         contentType="text/html; charset=utf-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String msg ="";

	String connectString = "jdbc:mysql://localhost:3306/teaching"
					+ "?autoReconnect=true&useUnicode=true"
					+ "&characterEncoding=UTF-8"; 
	String user="user";
	String pwd="123";
	String sql="";
	StringBuilder table=new StringBuilder("");
	table.append("<table>");
	table.append("<tr> <th class=\"id\">id</th>" + 
	                  "<th class=\"xh\">学号</th>" + 
										"<th class=\"xm\">姓名</th>" +
										"<th class=\"cz\">-</th> </tr>");
	try{
	  Class.forName("com.mysql.jdbc.Driver");
	  Connection con=DriverManager.getConnection(connectString, user, pwd);
	  Statement stmt=con.createStatement();
		String name = request.getParameter("query");
		String queryButton = request.getParameter("sub");
		if (queryButton != null){
			if (name==null)sql = String.format("select * from stu");
			else {
				name = name.trim();
				if (name=="")sql = String.format("select * from stu");
				else sql = "select * from stu where name like '%" +
				           	name + "%' or num like '%" + name + "%'";
			}
		}
		else sql = String.format("select * from stu");
	  ResultSet rs=stmt.executeQuery(sql);
	  while(rs.next()) {
    	 	table.append("<tr>");  
        table.append("<td  class=\"id\">"+rs.getString("id")+"</td>");  
        table.append("<td  class=\"xh\">"+rs.getString("num")+"</td>");  
        table.append("<td  class=\"xm\">"+rs.getString("name")+"</td>");  
        table.append("<td class=\"cz\">"+  
                         "<a href='updateStu.jsp?pid="+rs.getString("id")+"'>修改</a>"+"      "+  
                         "<a href='deleteStu.jsp?pid="+rs.getString("id")+"'>删除</a>"+  
                         "</td>");  
        table.append("</tr>"); 
	  }
	  rs.close();
	  stmt.close();
	  con.close();
	}catch (Exception e){
	  msg = e.getMessage();
	}
	table.append("</table>"); 
%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>查询浏览学生名单</title>
		<style style="text/css">
				.container{margin: 20px auto;width:530px;text-align: center;}
				table,th,td{border:1px solid black; border-collapse:collapse;}
				.id{width:100px;}
				.xh{width:200px;}
				.xm{width:80px;}
				.cz{width:150px;}
				a{color:blue;}
		</style>
	</head>
	<body>
  	<div class="container">
	  	<h1>查询学生名单</h1>
			<form action="queryStu.jsp" method="post" name="f">
				输入查询:<input id="query" name="query" type="text" value="">		                     
				<input type="submit" name="sub" value="查询">
		    <br/><br/>
			</form> 
	  	<%=table%><br><br>
			<div style="float:left">  
        <a href="addStu.jsp" >新增</a> 
				<a href="browseStu.jsp" >返回</a> 
    	</div> 
			<br><br>
			<%=msg%><br><br>
  	</div>
	</body>
</html>

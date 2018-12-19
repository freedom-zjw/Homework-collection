<%@ page language="java" import="java.util.*,java.sql.*" 
         contentType="text/html; charset=utf-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String msg ="";
	Integer pgno = 0;  //当前页号
	Integer pgcnt = 4; //每页行数
	String param = request.getParameter("pgno");
	if(param != null && !param.isEmpty())pgno = Integer.parseInt(param);
	param = request.getParameter("pgcnt");
	if(param != null && !param.isEmpty())pgcnt = Integer.parseInt(param);
	int pgprev = (pgno>0)?pgno-1:0;
	int pgnext = pgno + 1;

	String connectString = "jdbc:mysql://localhost:3306/teaching"
					+ "?autoReconnect=true&useUnicode=true"
					+ "&characterEncoding=UTF-8"; 
	String user="user";
	String pwd="123";
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
		String sql = String.format("select * from stu limit %d, %d;", pgno*pgcnt, pgcnt);
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
		table.append("</table>"); 
	  rs.close();
	  stmt.close();
	  con.close();
	}catch (Exception e){
	  msg = e.getMessage();
	}
%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>翻页浏览学生名单</title>
		<style style="text/css">
				.container{margin: 20px auto;width:530px;}
				.container h1{text-align: center;}
				table{margin:auto;text-align:center}
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
	  	<h1>浏览学生名单</h1>  
	  		<%=table%><br><br>
				<div style="float:left">  
        	<a href="addStu.jsp" >新增</a> 
					<a href="queryStu.jsp" >查询</a> 
    		</div> 
				<div style="float:right">  
        	<a href="browseStu.jsp?pgno=<%=pgprev%>&pgcnt=<%=pgcnt%>">上一页</a>
					<a href="browseStu.jsp?pgno=<%=pgnext%>&pgcnt=<%=pgcnt%>">下一页</a>  
    		</div> 
				<br><br>
				<%=msg%><br><br>
  	</div>
	</body>
</html>

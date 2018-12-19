<%@ page language="java" import="java.util.*,java.io.*,java.sql.*,java.awt.List" 
         contentType="text/html; charset=utf-8"%>
<%request.setCharacterEncoding("utf-8");%>  
<%!
	Connection conn;
	String msg=new String();
    //连接数据库
    boolean connect() {  
        String connectString = "jdbc:mysql://172.18.187.233:53306/teaching"  
            + "?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&&useSSL=false";  
        try {  
            Class.forName("com.mysql.jdbc.Driver");  
            conn = DriverManager.getConnection(connectString, "user", "123");  
            return true;  
        } catch (Exception e) {  
            System.out.println(e.getMessage()); 
			msg = "连接不到数据库";
        }  
        return false;  
    }  
	ResultSet executeUpdate(String sqlSentence) {
    	Statement stat;
    	ResultSet rs = null;      
    	try {
			stat =(Statement) conn.createStatement();       //获取执行sql语句的对象
			int cnt = stat.executeUpdate(sqlSentence);
			if(cnt!=0)msg="修改成功!";
			else 	msg="修改失败!";
    	} catch (Exception e) {
			System.out.println(e.getMessage());
			msg="修改失败!";
    	}
    	return rs;
	}
%> 
<%
	String id=request.getParameter("t1");
	String name=request.getParameter("t2");
    if(connect()){
    	msg="修改失败!";
        String sql=new String();
        sql="update stu set name='"+name+"' where id='"+id+"';";
        ResultSet rs = executeUpdate(sql);
    }
 %>
 <%=msg%>
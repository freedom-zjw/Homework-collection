<%@ page    
import="java.sql.*,java.awt.List"  
contentType="text/html; charset=utf-8"  
 %>
<% request.setCharacterEncoding("utf-8");%>
<%!  
    Connection conn;  
    String id=null;
    String num=null;
    String name=null; 
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
        }  
        return false;  
    }  
  
    //得到查询结果
    ResultSet executeQuery(String sqlSentence) {  
        Statement stat;  
        ResultSet rs = null;   
        try {  
            stat = conn.createStatement();       //获取执行sql语句的对象  
            rs = stat.executeQuery(sqlSentence); //执行sql查询，返回结果集  
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }  
        return rs;  
    }  
    int cnt;  
    //储存查询记录的条数，将其放置进对应的list里  
    void showStudents(ResultSet rs){  
        try {  
            while(rs.next()){  
                cnt=cnt+1;
                if (cnt > 1)return;
                id = rs.getString("id");  
                num = rs.getString("num");  
                name = rs.getString("name");  
            }  
        }  
        catch (Exception e) {  
            System.out.println(e.getMessage());  
        }  
    }  
%>  
<%  if(connect()){%>  
<%  
        cnt=0;  
        String sql="select * from stu where id="+request.getParameter("id")+";";  
        ResultSet rs = executeQuery(sql);  
        showStudents(rs);  
%>  
<%  if(cnt==1){ %>
<%      out.println("id:"+ id +"</br>");
        out.println("num:"+ num +"</br>");
        out.println("name:"+ name +"</br>");%>
    <%}  
    else{%>  
<%      out.print("null");%>       
    <%}%>  
<%} %>  
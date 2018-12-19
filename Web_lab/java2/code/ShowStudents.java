import java.sql.*;

public class ShowStudents {
	static private Connection conn;
	static int cnt = 0;

	public static void main(String args[]) {
		if (connect()) {
			ResultSet rs = executeQuery("select * from stu;");
			showStudents(rs);
		} else {
			System.out.println("Connect Error!");
		}
	}

	// 建立连接
	private static boolean connect() {
		String connectString = "jdbc:mysql://172.18.187.230:53306/teaching"
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
	
	//执行SQL查询语句, 返回结果集
	static private ResultSet executeQuery(String sqlSentence) {
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

	//显示查询结果
	private static void showStudents(ResultSet rs){
	    try {
	       while(rs.next()){
	          System.out.println(rs.getString("name"));		   
	       }
	    }
	    catch (Exception e) {
		System.out.println(e.getMessage());
	    }
	}


}

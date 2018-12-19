 import java.util.*;
 import java.sql.*;

 public class DeleteStu{
     static Scanner reader = new Scanner(System.in);
     InputData inputData = new InputData();
     static private Connection conn;
     static int sno = 1;
     static int cnt = 0;
     public static void main(String args[]) {
        System.out.println("输入学号或姓名（部分匹配）以删除记录，输入以-开头数字则按id删除，exit或空行退出\n");
         String sqlSentence = getNextLine();
         while (!sqlSentence.equals("")){
            if (connect()){
                try{
                    executeQuery(sqlSentence);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                System.out.println(String.valueOf(cnt) + "个记录被删除.\n");
            }
            else System.out.println("Connect Error!");
            sno++;
            sqlSentence = getNextLine();
        }
     }	
    private static String getNextLine(){
        System.out.print(sno+">");
        if (reader.hasNextLine()){
            String line = reader.nextLine();
            if (line.equals("exit") || line.trim().length()==0){
                return "";
            }
            String val[] = line.trim().split(" ");
            if (val[0].charAt(0) == '-'){
                return "DELETE FROM stu WHERE  id="+val[0].substring(1,val[0].length());
            }
            else{
                return "DELETE FROM stu WHERE num LIKE '%"+val[0]+"%' or name LIKE'%"+val[0]+"%'";
            }
        }
        return "";
    }
	private static boolean connect() {
		String connectString = "jdbc:mysql://172.18.187.230:53306/teaching9"
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
	static private boolean executeQuery(String sqlSentence) {
        Statement stat;
        try {
            stat = conn.createStatement(); 
            cnt = stat.executeUpdate(sqlSentence); 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (cnt > 0);
   }
 }

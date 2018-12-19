import java.util.*;

public class InputData {
	static Scanner in = new Scanner(System.in);
        static int sno = 1;
	public static void main(String args[]) {
                String lineWords[];
 		while ((lineWords=getNextLineWords())!=null) { // 是否还有输入
                   System.out.println(String.format("输入了%d个词：%s",lineWords.length,Arrays.toString(lineWords))+"\n");
		} 
	}

        public static String[] getNextLineWords(){
           if (sno==1){
     	      System.out.println("每行输入若干词(只能用一个空格间隔)，exit或空行退出.\r\n");
           }
           System.out.print("第"+sno+"行> ");			
	   if (!in.hasNextLine()) { // 是否还有输入
              return null;
           }
	   String line = in.nextLine(); // 读取下一行
           line = line.trim();
           if (line.equals("exit") || line.length() == 0) {
	       return null;
	   }
           sno++;
	   return line.split(" ");	
       } 
}
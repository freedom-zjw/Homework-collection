import java.io.*;
import java.util.*;

class ShowTags{
  public static void main(String[] args)throws IOException{
  	String content = readFile("grassland.htm");
		int len = content.length();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int st, ed;
		for (int i=0; i<len; i++){
			if (content.charAt(i) == '<' && content.charAt(i+1) != ' '){
					st = i;
					for (int j=st; j<len; j++){
						if (content.charAt(j)==' ' || content.charAt(j)=='>'){
							ed = j;
							String sub = new String(content.substring(i, j));
							sub = sub + '>';
							sub = sub.toUpperCase();
							if (map.containsKey(sub) == false)
								map.put(sub, 0);
							int nowValue = map.get(sub);
							map.put(sub, nowValue + 1);
							i = j;
							break;
						}
					}
			}
		}
		int cnt = 0;
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			System.out.print(key.toString() + ": " + val.toString() + "  ");
			if (++cnt == 3){
				cnt = 0;
				entry = (Map.Entry) iter.next();
				System.out.println();
			}
		}
  }
  static String readFile(String fileName) throws IOException{
    	StringBuilder sb = new StringBuilder("");
			int c1;
			FileInputStream f1= new FileInputStream(fileName);		
			InputStreamReader in = new InputStreamReader(f1, "UTF-8");
			while ((c1 = in.read()) != -1) {
	  		sb.append((char) c1);
			}        
      return sb.toString();
  }
}



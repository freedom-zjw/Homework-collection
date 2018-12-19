public class StringFunc{
    final static String s="扁担长，板凳宽，板凳没有扁担长，扁担没有板凳宽。扁担要绑在板凳上,板凳偏不让扁担绑在板凳上。";
    public static void main(String args[]){
        System.out.println(s.substring(0,3));
        int w = s.indexOf("扁担");
        while(w != -1){
            System.out.printf("%d ", w);
            w = s.indexOf("扁担", w+1);
        }
        System.out.println();

        String ss="";
        long st = System.currentTimeMillis();
        for(int i=0; i<10000; i++)ss = ss + s;
        long ed = System.currentTimeMillis();
        System.out.printf("字符串相加的时间: %dms  字符串总长度: %d\n", ed-st, ss.length());

        st = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder("");
        for(int i=0; i<10000; i++)sb.append(s);
        ed = System.currentTimeMillis();
        System.out.printf("StringBuilder的时间: %dms  字符串总长度: %d\n", ed-st, sb.length());
    }
}




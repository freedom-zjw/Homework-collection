import java.util.*;

public class Fib{
    public static void main(String args[]){
        long fib0=0,fib1=1,fib2=1;
        ArrayList<Long> fibs = new ArrayList<Long>();
        fibs.add(fib0);
        fibs.add(fib1);
        fibs.add(fib2);
        while(true){
            long x = fibs.get(fibs.size()-1);
            long y = fibs.get(fibs.size()-2);
            if (Long.MAX_VALUE - x < y)
                break;
            fibs.add(x+y);
        }
        System.out.printf("max fib(Long): %d count: %d\n", fibs.get(fibs.size()-1), fibs.size());
        Iterator iter = fibs.iterator();
        Double oldFib = 0.0;
        Double nextFib;
        ArrayList<Double> ratio = new ArrayList<Double>();
        int cnt = 0;
        while(iter.hasNext()){
            Long tmp = (Long) iter.next();
            nextFib = tmp.doubleValue();
            if (++cnt > 1)
                ratio.add(oldFib/nextFib);
            oldFib = nextFib;
        }
        cnt = 0;
        for (Double  x : ratio) {
            System.out.print(x);
            System.out.print("   ");
            if (++cnt == 4){
                System.out.println();
                cnt = 0;
            }
        }
    }
}




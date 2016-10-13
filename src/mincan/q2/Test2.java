package mincan.q2;

import java.util.ArrayList;
import java.util.List;



public class Test2 {
    public final static int DEFAULT_NUM = 4;
	public final static String TTAS_METHOD = "TTAS";
    public final static String CLH_METHOD = "CLH";
    public final static String MCS_METHOD = "MCS";
    
	public static void main(String[] args) throws InterruptedException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub
		int num = 0;
		int type = 0;
		String method = "";
        if(args.length == 2 && args[1].matches("^[0-9]+") && args[0].matches("^[A-Z]+")){
        	num = Integer.valueOf(args[1]);
        	switch(args[0]){
        		case TTAS_METHOD: type =1; method="TTAS"; break;
        		case CLH_METHOD: type =2; method= "CLH"; break;
        		case MCS_METHOD: type = 3; method = "MCS"; break;
        		default: System.out.println("Method: " + args[0]+" Not Found"); break;
        	}
        }else{
        	System.out.println("Usage: java Test <method>  <num>");
        	System.out.println("Method :  TTAS | CLH");
        	System.out.println("num must be integer");
        	System.out.println("Defualt running  TTAS with 4 threads");
        	System.out.println("----------------------");
        }
        num = num==0? DEFAULT_NUM:num;
        type = type == 0? 1 : type;
        method = method.compareTo("") == 0 ? "TTAS" : method;
        System.out.println("You have create "+num+" threads");
        System.out.println("With method: "+ method);
        List<Thread> threads = new ArrayList<Thread>();
        Lock lock = (Lock)Class.forName("mincan.q2." + method).newInstance();
        // create threads
        for(int i=0;i<num;i++) threads.add(new TestThread3(lock));
        // start threads
        for(int i=0;i<num;i++){
        	threads.get(i).start();
        }
        // calculate average time
        long sum_time = 0;
        for(int i=0;i<num;i++){
			threads.get(i).join();
			sum_time += ((TestThread3)threads.get(i)).getCount();
        }
        double average_time = (double)sum_time/num;
        System.out.println(num+" Threads With method: "+ method +" average throughput is "+average_time);
	}

}

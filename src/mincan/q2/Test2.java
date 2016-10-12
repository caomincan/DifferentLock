package mincan.q2;

import java.util.ArrayList;
import java.util.List;


public class Test2 {
    public final static int DEFAULT_NUM = 4;
	public final static String TTAS_METHOD = "TTAS";
    public final static String CLH_METHOD = "CLH";
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int num = 0;
		int type = 0;
		String method = "";
        if(args.length == 2 && args[1].matches("^[0-9]+") && args[0].matches("^[A-Z]+")){
        	num = Integer.valueOf(args[1]);
        	switch(args[0]){
        		case TTAS_METHOD: type =1; method="TTAS"; break;
        		case CLH_METHOD: type =2; method= "CLH"; break;
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
        Lock l = null;
        switch(type){
        case 1: l= new TTAS(); break;
        case 2: l= new CLH(); break;
        }
        // create threads
        for(int i=0;i<num;i++) threads.add(new TestThread3(l));
        // start threads
        for(int i=0;i<num;i++) threads.get(i).start();
        // calculate average time
        long sum_time = 0;
        for(int i=0;i<num;i++){
        	try {
				threads.get(i).join();
				sum_time += ((TestThread3)threads.get(i)).getTime();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        long average_time = sum_time/num;
        System.out.println(num+" Threads With method: "+ method +" average barrier time is "+average_time+" ms");
	}

}

package mincan.q2;

import java.util.concurrent.atomic.AtomicInteger;

public class TestThread3 extends Thread{
	public static AtomicInteger MAX_NUM_THREAD = new AtomicInteger(0);
	
	private Lock mylock;
	private int id;
	private long time;
	
	public TestThread3(Lock lock){
		mylock = lock;
		time = 0;
		id = MAX_NUM_THREAD.getAndIncrement();
	}
	
	@Override
	public void run(){
		int warm_up = 20;
		for(int i=0;i<warm_up;i++){
			mylock.lock();
			mylock.unlock();	
		}
		long start = System.nanoTime();
		long duration = 0;
		while( duration < 200000000){
			mylock.lock();
			time++;
			mylock.unlock();
			duration = System.nanoTime()-start;
			//System.out.println("Thread id "+id+" duration "+ duration);
		}
	}
	
	public void foo(){}
	
	public long getTime(){
		return time;
	}
}

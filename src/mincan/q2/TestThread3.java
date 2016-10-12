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
		long start = System.currentTimeMillis();
		mylock.lock();
        foo();
		mylock.unlock();
		long end = System.currentTimeMillis();
		time = end-start;
	}
	
	public void foo(){}
	
	public long getTime(){
		return time;
	}
}

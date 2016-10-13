package mincan.q2;

import java.util.concurrent.atomic.*;

public class MCS implements Lock {
	AtomicReference<QNode> tail;
	ThreadLocal<QNode> myNode;
	
	class QNode{
		volatile boolean locked =false;
		volatile QNode next = null;
	}
		
	public MCS(){
		tail = new AtomicReference<QNode>(null);
		myNode = new ThreadLocal<QNode>(){
			protected QNode initialValue(){
				return new QNode();
			}
		};
	}
	@Override
	public void lock() {
		// TODO Auto-generated method stub
		QNode qnode = myNode.get();
		QNode pred = tail.getAndSet(qnode);
		if(pred != null){
			qnode.locked = true;
			pred.next = qnode;
			// wait until predecessor gives up the lock
			while(qnode.locked) {}
		}
	}

	@Override
	public void unlock() {
		// TODO Auto-generated method stub
		QNode qnode = myNode.get();
		if(qnode.next == null){
			if(tail.compareAndSet(qnode, null))
				return;
			// wait until predecessor fills in its next field
			while(qnode.next == null) {}
		}
		qnode.next.locked = false;
		qnode.next = null;
	}
	


}

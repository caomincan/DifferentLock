package mincan.q2;

import java.util.concurrent.atomic.*;

public class CLH implements Lock{
	AtomicReference<QNode> tail = new AtomicReference<QNode>(new QNode());
	ThreadLocal<QNode> myPred;
	ThreadLocal<QNode> myNode;
	
	public CLH(){
		tail = new AtomicReference<QNode>(new QNode());
		myNode = new ThreadLocal<QNode>(){
			protected QNode initialValue(){
				return new QNode();
			}
		};
		myPred = new ThreadLocal<QNode>(){
			protected QNode initialValue(){
				return null;
			}
		};
	}
	@Override
	public void lock() {
		// TODO Auto-generated method stub
		QNode qnode = myNode.get();
		qnode.locked = true;
		QNode pred = tail.getAndSet(qnode);
		myPred.set(pred);
		while(pred.locked){}
	}

	@Override
	public void unlock() {
		// TODO Auto-generated method stub
		QNode qnode = myNode.get();
		qnode.locked = false;
		myNode.set(myPred.get());
	}

}

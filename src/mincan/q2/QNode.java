package mincan.q2;

public class QNode {
     public boolean locked = false;
     public QNode next = null;
     
     public QNode(boolean value){
    	 locked = value;
     }
     
     public QNode() {};
}

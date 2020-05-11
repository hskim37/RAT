//********************************************************************
//  LinkedQueue.java       Java Foundations
//
//  Represents a linked implementation of a queue.
//********************************************************************
/**
 * Represents a linked implementation of a queue
 */
package javafoundations;

import javafoundations.exceptions.*;

public class LinkedQueue<T> implements Queue<T>
{
    private int count;
    private LinearNode<T> front, rear;
    
    /**
     * Constructor for LinkedQueue
     * Initialize instance variables and creates an empty queue
     */
    public LinkedQueue()
    {
        count = 0;
        front = rear = null;
    }

   
    /**
     * Adds the specified element to the rear of this queue
     * @param element of type T
     */
    public void enqueue (T element)
    {
        LinearNode<T> node = new LinearNode<T>(element);

        if (count == 0)
            front = node;
        else
            rear.setNext(node);

        rear = node;
        count++;
    }

    
    /**
     * Removes the element at the front of this queue
     * @return the element being removed
     */
    public T dequeue () throws EmptyCollectionException{
        //may thrown an exception when the queue is empty
        if (count == 0) throw new EmptyCollectionException
            ("Dequeue failed. The queue is empty.");
        T result = front.getElement();
        count--;
        front= front.getNext();
        //dealing with the situation when there was only one LinearNode in the linkedQueue
        if (this.isEmpty()){
        rear = null;
        }
        return result;
    }

    /**
     * Return the first element in the LinkedQueue
     * 
     * @return the first element
     */
    //may thrown an exception when the queue is empty
    public T first () throws EmptyCollectionException{ 
        if (count == 0) throw new EmptyCollectionException
            ("The queue is empty.");
        return front.getElement();
    }

    /**
     * Return the size of the LinkedQueue
     * 
     * @return the size of the linked queue
     */
    public int size(){ 
        return count;
    }

    /**
     * Return if the boolean is empty or not
     * 
     * @return a boolean 
     */
    public boolean isEmpty(){ 
        return (count == 0);
    }

    /**
     * String representation of the LinkedQueue
     * 
     * @return String representation of the object
     * @override toString
     */
    public String toString(){
        String s = "<front of queue> ";
        LinearNode<T> cur = front;
        while (cur != null){
            s += cur.getElement() + " ";
            cur = cur.getNext();
        }
       
        return s + "<rear of queue>" ;
        
    }
    
    /**
     * Main driver of the test
     */
    public static void main(String[] args){
        LinkedQueue<String> l1 = new LinkedQueue<String>();
        //testings with the empty queue
        System.out.println("Testings on an empty LinkedQueue:");
        System.out.println("[testings of dequeue() method on an empty queue is not shown here due to the exception it throws]");
        System.out.println("[Expect isEmpty() method to return: true]");
        System.out.println("isEmpty: " + l1.isEmpty());
        System.out.println("Expect size() method to return: 0]");
        System.out.println("size: " + l1.size());
        //System.out.println("[Expect first() method to throw an EmptyCollectionException]");
        //l1.first();
        //System.out.println("\n[Expecting dequeue() method to throw an EmptyCollectionException]");
        //l1.dequeue();
        l1.enqueue("element1");
        System.out.println("[After enqueuing the queue with one element: 'element1']");
        System.out.println(l1);
        
        //testings with singleton queue
        System.out.println("\nTestings on a singleton LinkedQueue:");
        System.out.println("[Expect isEmpty() method to return: false]");
        System.out.println("isEmpty: " + l1.isEmpty());
        System.out.println("[Expect size() method to return: 1]");
        System.out.println("size: " + l1.size());
        System.out.println("[Expect first() method to return \"element1\"]");
        System.out.println("first: " + l1.first());
        l1.dequeue();
        System.out.println("[After dequeuing the queue <expect to empty the queue>]");
        System.out.println(l1);
        l1.enqueue("element2");
        System.out.println("[After enqueuing the queue with element: 'element2']");
        System.out.println(l1);
        
        //testings with queue of multiple elements
        System.out.println("\nTestings on a LinkedQueue with multiple elements:");
        l1.enqueue("element3");
        l1.enqueue("element4");
        l1.enqueue("element5");
        l1.enqueue("element6");
        System.out.println("[After enqueuing the queue with element3,4,5,6 as strings");
        System.out.println(l1);
        System.out.println("[Expect isEmpty() method to return: false]");
        System.out.println("isEmpty: " + l1.isEmpty());
        System.out.println("Expect size() method to return: 5]");
        System.out.println("size: " + l1.size());
        System.out.println("[Expect first() method to return \"element2\"]");
        System.out.println("first: " + l1.first());
        l1.dequeue();
        l1.dequeue();
        System.out.println("[After dequeuing the queue twice]");
        System.out.println(l1);
        System.out.println("[Expect isEmpty() method to return: false]");
        System.out.println("isEmpty: " + l1.isEmpty());
        System.out.println("Expect size() method to return: 3]");
        System.out.println("size: " + l1.size());
        System.out.println("[Expect first() method to return \"element4\"]");
        System.out.println("first: " + l1.first());
        
        l1.enqueue("element7");
        System.out.println("[After enqueuing the queue with 'element7']");
        System.out.println(l1);
 
    }
    
}

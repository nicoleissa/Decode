package project3;

import java.util.*;

/**
 * The MDeque class is that of a double ended queue using a doubly linked list implementation; implements the iterable interface.
 * The class contains push, pop, and peek methods for the front, middle, and rear elements of the list of O(1) performance as well as methods to return the 
 * size of the list, an iterator of the list's elements, a reverse iterator of the list's elements, and a string representation of the list.
 * 
 * A node class is written in the class to keep track of the next and previous nodes as well as the data for each element.
 * 
 * Two internal iterator classes are also included - ForwardIterator and ReverseIterator.
 * 
 * @author Nicole Issagholian 
 *
 */

public class MDeque<E> implements Iterable<E> {
	
	//front, rear, middle nodes
	//size variable of list
	int size = 0;
	Node front = null;
	Node rear = null;
	Node middle = null;

	//node class - keep track of data of elements, next and previous nodes
	private class Node { 
		E data;
		Node next;
		Node prev;
		
		public Node (E data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		} 
	}
	
	/**
	 * pushFront method that inserts the specified item at the front of this mdeque. 
	 * @param item is element to add.
	 * @throws IllegalArgumentException if item is null. 
	 */
	public void pushFront(E item) throws IllegalArgumentException {
		Node newNode = new Node(item);
		
		//if item is null, throw exception
		if (item == null) {
			throw new IllegalArgumentException("The item is null");
		}
		
		//if size of list is 0, add new node to list 
		//new node at front and rear then since only element now in list
		if (front == null) {
			front = rear = middle = newNode;
			size++;
		}
		
		//for all other sizes, insert new node before original front
		else {	
			front.prev = newNode;
			newNode.next = front;
			front = newNode;

			//if size is odd, advance middle node to previous one 
			if (size % 2 != 0) {	
				middle = middle.prev;
			}
			
			size++;
		}
		
	}
	
	/**
	 * pushBack method that inserts the specified item at the back of this mdeque. 
	 * @param item is element to add.
	 * @throws IllegalArgumentException if item is null. 
	 */
	public void pushBack(E item) throws IllegalArgumentException {
		Node newNode = new Node(item);
		
		//if item is null, throw exception
		if (item == null) {
			throw new IllegalArgumentException("The item is null");
		}
		
		//if size of list is 0, add new node to list 
		//new node at front and rear then since only element now in list
		if (front == null) {
			front = rear = middle = newNode;
			size++;
		}

		//for all other sizes, insert new node after original rear
		else {	
			rear.next = newNode;
			newNode.prev = rear;
			rear = newNode;
			
			//if size is even, advance middle node to next one 
			if (size % 2 == 0) {
				middle = middle.next;
			}
			size++;
		} 
		
	}
	
	/**
	 * pushMiddle method that inserts the specified item in the middle of this mdeque. 
	 * @param item is element to add.
	 * @throws IllegalArgumentException if item is null. 
	 */
	public void pushMiddle(E item) throws IllegalArgumentException {
		Node newNode = new Node(item);
		
		//if item is null, throw exception
		if (item == null) {
			throw new IllegalArgumentException("The item is null");
		}
		
		//if size of list is 0, add new node to list 
		//new node at front and rear then since only element now in list
		else if (front == null) {
			front = rear = middle = newNode;
			size++;
		}
		
		//if size of list is 1, add new node after front
		//new node is now the rear
		else if (front == rear) {
			front.next = newNode; 
			newNode.prev = front;
			rear = middle = newNode;
			size++;
		}
		
		//if size of list is 2, add new node after front
		//new node is now the rear
		else if (front.next == rear) {
			rear.next = newNode;
			newNode.prev = rear;
			rear = middle = newNode;
			size++;
		}
		
		//for all other sizes, insert new node before original middle of list
		else {
			if (size % 2 != 0) {
				newNode.prev = middle.prev;
				newNode.next = middle;
				middle.prev.next = newNode;
				middle.prev = newNode;
				middle = newNode;
			}
		}	
		size++;
	}
	
	
	/**
	 * popFront method that retrieves and removes the first element of this mdeque. 
	 * @return the front of this mdeque, or null if the mdeque is empty.
	 */
	public E popFront() {
		Node newFront = null;
		Node temp = front;
		
		//if size of list is 0, return null (can't remove anything if empty)
		if (front == null) {
			return null;
		}
		
		//if size of list is 1, remove that single element that exists
		if (front == rear) {
			front = rear = middle = null;
		}
		
		//for all other sizes, remove original front node and make new front node after original front
		else {
			newFront = front.next;
			front = newFront;
			newFront.prev = null;
		
			//if size is odd, advance middle node to next one 
			if (size % 2 != 0) {
				middle = middle.next;
			}
		}
		size--;
		
		return temp.data;
	}
	
	/**
	 * popBack method that retrieves and removes the back element of this mdeque. 
	 * @return the back of this mdeque, or null if the mdeque is empty.
	 */
	public E popBack() {		
		Node newRear = null;
		Node temp = rear;
 		
		//if size of list is 0, return null (can't remove anything if empty)
		if (front == null) {
			return null;
		}
		
		//if size of list is 1, remove that single element that exists
		if (front == rear) {
			front = rear = middle = null;
		}
		
		//if size of list is 1, remove original rear node and make new rear node before original rear
		else {
			newRear = rear.prev;
			rear = newRear;
			newRear.next = null;
			
			//if size is even, advance middle node to prev one 
			if (size % 2 == 0) {
				middle = middle.prev;
			}
		}
		size--;
		
		return temp.data;
	}
	
	/**
	 * popMiddle method that retrieves and removes the middle element of this mdeque. 
	 * @return the middle of this mdeque, or null if the mdeque is empty.
	 */
	public E popMiddle() {		
		Node current = front;
		Node temp;
		
		//if size of list is 0, return null (can't remove anything if empty)
		if (front == null) {
			return null;
		}
		
		//if size of list is 1, remove that single element that exists
		if (front == rear) {
			temp = front;
			front = rear = middle = null;
			size--;
			return temp.data;
		}
		
		//if size of list is 2, remove rear element and make rear point to front
		if (front.next == rear) {
			temp = rear;
			rear = front;
			front.next = null;
			size--;
			return temp.data;
		}

		//if size of list is 3, remove rear element (which is middle) and make new rear the element before original middle
		if (current.next.next == rear) {
			temp = rear = middle; 
			current.next.next = null;
			rear = middle = current.next;
			size--;
			return temp.data;
		}
		
		//for all other sizes, remove and return original middle and make prev node new middle
		else {
			temp = middle;
			middle.next.prev = middle.prev;
			middle.prev.next = middle.next;
			
			//advance middle node to next one 
			middle = middle.next;
			size--;
			return temp.data;		
		}
	}
	
	/**
	 * peekFront method that retrieves the first element of this mdeque. 
	 * @return the front of this mdeque, or null if this mdeque is empty.
	 */
	public E peekFront() {
		//if size of list is 0, return null 
		if (front == null) {
			return null;
		}
		
		//else, return data of front element
		return front.data;
	}
	
	/**
	 * peekBack method that retrieves the back element of this mdeque. 
	 * @return the back of this mdeque, or null if this mdeque is empty.
	 */
	public E peekBack() {
		//if size of list is 0, return null 
		if (front == null) {
			return null;
		}
		
		//else, return data of rear element
		return rear.data;
	}
	
	/**
	 * peekMiddle method that retrieves the middle element of this mdeque. 
	 * @return the middle of this mdeque, or null if this mdeque is empty.
	 */
	public E peekMiddle() {
		//if size of list is 0, return null
		if (front == null) {
			return null;
		}
		
		//else, return data of middle element		
		return middle.data;
	}
	
	/**
	 * size method that returns the number of elements in this mdeque. 
	 * @return the number of elements in this mdeque.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * reverse method that reverses the order of the elements in this mdeque. 
	 */
	public void reverse() {
		Node current = front;
		Node temp = null;
		
		//swap previous and next pointers of each node to reverse direction of mdeque
		while (current != null) {
			temp = current.prev;
			current.prev = current.next;
			current.next = temp;
			current = current.prev;
		}
		
		if (temp != null) {
			front = temp.prev;
		}
	}
	
	/**
	 * internal ForwardIterator class to iterate through list from front to back (forward direction/sequential order).
	 * contains hasNext(), next(), and remove() methods.
	 */
	private class ForwardIterator implements Iterator<E> {
		private Node current;
		
		//ForwardIterator constructor 
		//sets current to front
		ForwardIterator(Node front) {
			current = front;
		}
		
		/**
		 * hasNext() method that returns true if the iteration has more elements. 
		 * @return true if iteration has more elements.
		 */
		@Override
		public boolean hasNext() {		
			return (current != null);
		}
		
		/**
		 * next() method that returns the next element in the iteration. 
		 * @return the next element in the iteration.
		 * @throws NoSuchElementException if the iteration has no more elements. 
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException("A next element does not exist.");			
			}
			E temp = current.data;
			current = current.next;
			return temp;
		}
	}
	
	/**
	 * internal ReverseIterator class to iterate through list from back to front (reverse order).
	 * contains hasNext(), next(), and remove() methods.
	 */
	private class ReverseIterator implements Iterator<E> {
		private Node current;
		
		//ReverseIterator constructor 
		//sets current to rear
		ReverseIterator(Node rear) {
			current = rear;
		}
		
		/**
		 * hasNext() method that returns true if the iteration has more elements. 
		 * @return true if iteration has more elements.
		 */
		@Override
		public boolean hasNext() {		
			return current != null;
		}
		
		/**
		 * next() method that returns the next element in the iteration. 
		 * @return the next element in the iteration.
		 * @throws NoSuchElementException if the iteration has no more elements. 
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException("A next element does not exist.");			
			}
			E temp = current.data;
			current = current.prev; 
			return temp;
		}
	}
	
	/**
	 * iterator method that returns an iterator over the elements in this mdeque in proper sequence. 
	 * The elements returned will be returned in order from front to back.
	 * @return an iterator over the elements in this mdeque in proper sequence.
	 */
	public Iterator<E> iterator() {		
		return new ForwardIterator(this.front); 
	}
	
	/**
	 * reverseIterator method that returns an iterator over the elements in this mdeque in reverse sequential order. 
	 * The elements returned will be returned in order from back to front.
	 * @return an iterator over the elements in this mdeque in reverse sequence.
	 */
	public Iterator<E> reverseIterator() {
		return new ReverseIterator(this.rear); 
	}
	
	/**
	 * toString method that returns the reverseToString method which recursively returns a string representation of this mdeque. 
	 * The string representation consists of a list of the collection's elements in the order 
	 * they are returned by its iterator, enclosed in square brackets("[]").
	 * Adjacent elements are separated by the characters ", " (comma and space).
	 * @return a string representation of this mdeque.
	 */
	@Override
	public String toString() {						
		Node temp = front;
		
		return "[" + recurseToString(temp) + "]";

	}
	
	/**
	 * reverseToString method is a wrapper method that recursively returns a string representation of this mdeque. 
	 * @return a string representation of this mdeque.
	 */
	public String recurseToString(Node current) {				
		
		if (current.next == null) {
			return "" + current.data;
		}		
		
		else {
			return current.data.toString() + ", " + recurseToString(current.next);
		}

	}
	
	
}

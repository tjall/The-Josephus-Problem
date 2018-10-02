import java.util.Iterator;
//creator  Tyler Allen(タイラー)


public class CircularLinkedList<E> implements Iterable<E> {

	
	
	
	Node<E> head;
	Node<E> tail;
	int size;  

	
	
	
	public CircularLinkedList() {
		
	}


	// I highly recommend using this helper method
	// Return Node<E> found at the specified index
	// be sure to handle out of bounds cases
	private Node<E> getNode(int index ) {
		if(index < 0 || index > size) { 
			throw new IndexOutOfBoundsException("Not a valid index :(");
		}
		Node<E> current = head;
		for(int i = 0; i <  index;i++) {
			current = current.next;
		}
		return current;
	}


	// attach a node to the end of the list
	public boolean add(E item) {
		this.add(size,item);
		return false;

	}

	
	// Cases to handle
	// out of bounds
	// adding to empty list
	// adding to front
	// adding to "end"
	// adding anywhere else
	// REMEMBER TO INCREMENT THE SIZE
	public void add(int index, E item){
		
		// scenario 1: out of bounds 
		if(index < 0 || index < size) { 
			throw new IndexOutOfBoundsException("Not a valid index :(");
		}
		Node<E> temp = new Node<E>(/*item */item);
		
		if (size == 0) {
			head =  temp;
			tail = temp;
			tail.next = head;
			size++;
		}
		
		// scenario 2: adding head
		else if(index == 0) {
			temp.next = head;
			head = temp;
			tail.next = head;
			size++;
		}
		// scenario 3: adding to the tail
		else if(index == size) {
			temp.next = head;
			tail.next = temp;
			tail = temp;
			size ++;
		}
		else {
			Node<E> before = getNode(index-1);
			temp.next = before.next;
			before.next = temp;
			size++;
		}
		
		
	}

	

	
	
	// remove must handle the following cases
	// out of bounds
	// removing the only thing in the list
	// removing the first thing in the list (need to adjust the last thing in the list to point to the beginning)
	// removing the last thing 
	// removing any other node
	// REMEMBER TO DECREMENT THE SIZE
	public E remove(int index) {
		E toReturn = null;
		// scenario 1 : out of bonds
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException("cant remove that!");
		}
		if(index == 0) {
			   toReturn = head.item;
			   head = head.next;
			   tail.next = head;
			}
		else if(index == size) {
			toReturn = tail.item;
			Node<E> before = getNode(index - 1);
			before.next = head;
            tail = before;
            //size --;
		}
		else {
			Node<E> before = getNode(index - 1);
			toReturn = before.next.item;
			before.next = before.next.next;
		}
		size--;
		return toReturn;
	}
	
	
	
	
	// Turns your list into a string
	// Useful for debugging
	public String toString(){
		Node<E> current =  head;
		StringBuilder result = new StringBuilder();
		if(size == 0){
			return "";
		}
		if(size == 1) {
			return head.item.toString();
			
		}
		else{
			do{
				result.append(current.item);
				result.append(" ==> ");
				current = current.next;
			} while(current != head);
		}
		return result.toString();
	}
	
	
	public Iterator<E> iterator() {
		return new ListIterator<E>();
	}
	
	
	
	// this class is not static because it needs the class it's inside of to survive!
	private class ListIterator<E> implements Iterator<E>{
		
		Node<E> nextItem;
		Node<E> prev;
		int index;
		
		@SuppressWarnings("unchecked")
		//Creates a new iterator that starts at the head of the list
		public ListIterator(){
			nextItem = (Node<E>) head;
			index = 0;
		}

		// returns true if there is a next node
		// this is always should return true if the list has something in it
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return size != 0;
		}
		
		// advances the iterator to the next item
		// handles wrapping around back to the head automatically for you
		public E next() {
			// TODO Auto-generated method stub
			prev =  nextItem;
			nextItem = nextItem.next;
			index =  (index + 1) % size;
			return prev.item;
	
		}
		
		// removed the last node was visted by the .next() call 
		// for example if we had just created a iterator
		// the following calls would remove the item at index 1 (the second person in the ring)
		// next() next() remove()
		public void remove() {
			int target;
			if(nextItem == head) {
				target = size - 1;
			} else{ 
				target = index - 1;
				index--;
			}
			CircularLinkedList.this.remove(target); //calls the above class
		}
		
	}
	
	
	private static class Node<E>{
		E item;
		Node<E> next;
		
		public Node(E item) {
			this.item = item;
		}
		
	}
	
	
	// The answer of n = 13,  k = 2 is 
	// the 11th person in the ring (index 10)
	public static void main(String[] args){
		CircularLinkedList<Integer> l =  new CircularLinkedList<Integer>();
		int n = 13;
		int k = 2;
		

		// add your nodes here!
		for(int i = 1; i <= n; i++) {
			l.add(i);
			//System.out.println(l);
			
		}
		
		System.out.println(l);
		
		// uses the iterator to iterate around the list
		Iterator<Integer> iter = l.iterator();
		while(l.size > 0) {
			for(int i = 0; i < k; i++) {
				if(iter.hasNext() == true) {
				   iter.next();
				}
			}
			iter.remove();
			System.out.println(l);
		}
		
		
		
		
	}



	
	
}

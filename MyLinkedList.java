package List;

public class MyLinkedList<AnyType> implements Iterable<AnyType> {
	private static class Node<AnyType> {
		public Node(AnyType x, Node<AnyType> p, Node<AnyType> n)
		{ date = x; prev = p; next = n; }
		public AnyType date;
		public Node<AnyType> prev;
		public Node<AnyType> next;
	}
	
	public MyLinkedList()
	{ doClear(); }
	public void clear()
	{ doClear(); }
	private void doClear() {
		beginMarker = new Node<AnyType>(null, null, null);
		endMarker = new Node<AnyType>(null, beginMarker, null);
		beginMarker.next = endMarker;
		
		theSize = 0;
		modCount++;
	}
	public int size() 
	{ return theSize; }
	public boolean isEmpty() 
	{ return size() == 0; }
	
	public boolean add( AnyType x ) 
	{ add( size(), x ); return true; }
	public void add( int idx, AnyType x ) 
	{ addBefore( getNode( idx, 0, size() ), x ); }
	public AnyType get( int idx )
	{ return getNode( idx ).date; }
	public AnyType set( int idx, AnyType newVal ) {
		Node<AnyType> p = getNode( idx );
		AnyType oldVal = p.date;
		p.date = newVal;
		
		return oldVal;
	}
	public AnyType remove( int idx )
	{ return remove( getNode(idx) ); }
	
	/**
	 *Adds an item to this Collection, at specified position p.
	 *Items at or after that position are slid one position higher.
	 *@param p Node to add before.
	 *@param x any object.
	 *@throws IndexOutOfBoundsException if idx not between 0 and size().  
	 */
	private void addBefore( Node<AnyType> p, AnyType x ) {
		Node<AnyType> newNode = new Node<>( x, p.prev, p);
		newNode.prev.next = newNode;
		p.prev = newNode;
		theSize++;
		modCount++;
	}
	
	/**
	 *Removes the object contained in Node p.
	 *@param p the node containing the object.
	 *@return the item was removed from the collection.  
	 */
	private AnyType remove( Node<AnyType> p) {
		p.prev.next = p.next;
		p.next.prev = p.prev;
		theSize--;
		modCount--;
		
		return p.date;
	}
	
	/**
	 *Gets the Node at position idx, which must range from 0 to size() - 1
	 *@param idx index to search at.
	 *@return internal node corresponding to idx.
	 *@throws IndexOutBoundsException if idx is not
	 *		  between 0 and size() - 1 ,inclusive. 
	 */
	private Node<AnyType> getNode( int idx ){
		return getNode( idx, 0, size() - 1 );
	}
	/**
	 * Gets the Node at position idx, which must range from 0 to size() - 1. 
	 * @param idx index to search at.
	 * @param low lowest valid index.
	 * @param upper highest valid index.
	 * @return internal node corresponding to idx
	 * @throws IndexOutOfBoundsException if idx not 
	 * 		   between lower and upper, inclusive. 
	 */
	private Node<AnyType> getNode(int idx, int low, int upper){
		Node<AnyType> p;
		if( idx < low || idx > upper ) {
			throw new IndexOutOfBoundsException();
		}
		
		if( idx < size()/2 ) {
			p = beginMarker;
			for( int i = 0; i < idx; i++ ) {
				p = p.next;
			}
		}else {
			p = endMarker;
			for( int i = size(); i > idx; i-- ) {
				p = p.prev;
			}
		}
		
		return p;
	}
	
	@Override
	public java.util.Iterator<AnyType> iterator() {
		// TODO Auto-generated method stub
		return new LinkedListIterator();
	}
	private class LinkedListIterator implements java.util.Iterator<AnyType> {
		private Node<AnyType> current = beginMarker;
		private int expectedModCount = modCount;
		private boolean okToRemove = false;
		
		@Override
		public boolean hasNext() {
			return current != endMarker;
		}

		@Override
		public AnyType next() {
			if( expectedModCount != modCount ) {
				throw new java.util.ConcurrentModificationException();
			}
			if( !hasNext() ) {
				throw new java.util.NoSuchElementException();
			}
			
			AnyType nextItem = current.date;
			current = current.next;
			okToRemove = true;
			return nextItem;
		}
		
		public void remove() {
			if( expectedModCount != modCount) {
				throw new java.util.ConcurrentModificationException();
			}
			if( !okToRemove ) {
				throw new IllegalStateException();
			}
			
			MyLinkedList.this.remove( current.prev );
			expectedModCount++;
			okToRemove = false;
		}
		
	}
	
	private int theSize;
	private int modCount = 0;
	private Node<AnyType> beginMarker;
	private Node<AnyType> endMarker;
}

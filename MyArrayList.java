package List;

import org.omg.CORBA.Current;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

public class MyArrayList<AnyType> implements Iterable<AnyType> {
	private static final int DEFAULT_CAPACITY = 10;
	
	private  int theSize;
	private AnyType[] theItems;
	
	public MyArrayList() {
		// TODO Auto-generated constructor stub
		doClear();
	}
	
	public void clear() {
		doClear();
	}
	
	private void doClear() {
		theSize = 0;
		ensureCapacity( DEFAULT_CAPACITY );
	}
	
	public int size() {
		return theSize;
	}
	public boolean isEmpty() {
		return size() == 0;
	}
	public void tirmToSize() {
		//删除预留位置
		ensureCapacity( size() );
	}
	
	public AnyType get( int idx ) {
		if( idx >= size() || idx < 0) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return theItems[ idx ];
	}
	
	public AnyType set( int idx, AnyType newVal ) {
		if( idx >= size() || idx < 0) {
			throw new ArrayIndexOutOfBoundsException();
		}
		AnyType old = theItems[ idx ];
		theItems[ idx ] = newVal;
		return old;
	}
	
	public void ensureCapacity( int newCapacity ) {
		// TODO Auto-generated method stub
		if( newCapacity < theSize )
			return;
		
		AnyType[] old = theItems;
		//下面的操作是创建一个泛型类型界限的数组，然后使用一个数组进行类型转换
		//这将产生一个警告，但在泛型集合的实现中这是不可避免的。
		theItems = ( AnyType[] ) new Object[ newCapacity ];
		
		for( int i = 0; i < size(); i++ ) {
			theItems[i] = old[i];
		} 
	}
	public boolean add( AnyType x ) {
		add( size(), x );
		return true;
	}
	public void add( int idx, AnyType x) {
		if( theItems.length == size() )
			ensureCapacity( theSize *2 +1);
		for( int i = theSize; i > idx; i--)
			theItems[ i ] = theItems[ i -1 ];
		theItems[idx] = x;
		
		theSize++;
	}
	
	public AnyType remove( int idx ) {
		if( idx >= size() || idx < 0) {
			throw new ArrayIndexOutOfBoundsException();
		}
		AnyType removedItem = theItems[ idx ];
		for( int i = idx; i < theSize; i++ )
			theItems[ i ] = theItems[ i + 1 ];
		theSize--;
		
		return removedItem;
	}
	
	@Override
	public java.util.Iterator<AnyType> iterator() {
		// TODO Auto-generated method stub
		return new ArrayListIterator();
	}
	
	private class ArrayListIterator implements java.util.Iterator<AnyType>{
		private int current = 0;
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return current < size();
		}

		@Override
		public AnyType next() {
			// TODO Auto-generated method stub
			if( !hasNext() )
				throw new java.util.NoSuchElementException();
			return theItems[ current++ ];
			
		}
		
		public void remove() {
			MyArrayList.this.remove( --current );
		}
		
	}
}

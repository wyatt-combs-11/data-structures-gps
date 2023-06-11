import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * This HeapPriorityQ class is part of the Graph data structure. It is used by the Dijkstra class
 * to help determine the shortest path from one Vertex to another and all possible paths found
 * while using the Dijkstra algorithm.
 * 
 * @author wyattcombs
 *
 * @param <T> The object type that is to be sorted in the Queue
 */

public class HeapPriorityQ<T extends Comparable<? super T>> implements PriorityQueueInterface<T>  {
	//=================================================================== Properties
	private T[] elements;
	private int size;
	private static final int DEFAULT_CAPACITY = 10;
	
	//=================================================================== Constructors
	/** Creates an empty HeapPriorityQ with a default capacity. */
	public HeapPriorityQ() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Creates an empty HeapPriorityQ with an initial capacity.
	 * 
	 * @param initialCapacity The capacity of the HeapPriorityQ
	 */
	public HeapPriorityQ(int initialCapacity) {
		elements = (T[]) new Comparable [initialCapacity + 1];
		size = 0;
	}
	
	/**
	 * Creates a HeapPriorityQ and populates it with entries.
	 * 
	 * @param entries The information to sorted in the Queue
	 */
	public HeapPriorityQ(T[] entries) {
		this(entries.length);
		for(T entry: entries)
			add(entry);
	}

	//=================================================================== Methods
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean isFull() {
		return size + 1 == elements.length;
	}

	@Override
	public void clear() {
		Arrays.fill(elements, 1, size + 1, null);
		size = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void add(T newEntry) {
		verifyCapacity();
		elements[++size] = newEntry;
		reheapUp(size);
	}

	/** Checks that the current capacity is valid. */
	private void verifyCapacity() {
		if(isFull())
			elements = Arrays.copyOf(elements, 2 * size + 1);
	}

	/**
	 * Restructures the Queue if needed.
	 * 
	 * @param index The index of the item to be reheaped
	 */
	private void reheapUp(int index) {
		if(index < 2)	return;
		
		int parentIndex = index/2;
		if(elements[index].compareTo(elements[parentIndex]) > 0)	return;
		swap(index, parentIndex);
		reheapUp(parentIndex);
	}

	/**
	 * Swaps the positions of two elements.
	 * 
	 * @param i The index of the first element
	 * @param j The index of the second element
	 */
	private void swap(int i, int j) {
		T temp = elements[i];
		elements[i] = elements[j];
		elements[j] = temp;
	}

	@Override
	public T peek() {
		return isEmpty() ? null: elements[1];
	}

	@Override
	public T remove() {
		if(isEmpty())	throw new NoSuchElementException();
		
		T ret = elements[1];
		elements[1] = elements[size];
		elements[size--] = null;
		reheapDown(1);
		return ret;
	}

	/**
	 * Restructures the Queue if needed.
	 * 
	 * @param index The index of the item to be reheaped
	 */
	private void reheapDown(int index) {
		if(size == 1)
			return;
		int parentIndex = index;
		int leftIndex = 2 * parentIndex;
		int rightIndex = 2 * parentIndex + 1;
		
		while(leftIndex <= size) {
			int maxChildIndex = findMaxChildIndex(leftIndex, rightIndex);
			if(elements[maxChildIndex].compareTo(elements[parentIndex]) > 0)
				return;
			
			swap(parentIndex, maxChildIndex);
			
			parentIndex = maxChildIndex;
			leftIndex = 2 * parentIndex;
			rightIndex = 2 * parentIndex + 1;
		}
	}

	/**
	 * Finds the largest child of the current element in the heap.
	 * 
	 * @param leftIndex The left child's index
	 * @param rightIndex The right child's index
	 * @return The largest child of the element
	 */
	private int findMaxChildIndex(int leftIndex, int rightIndex) {
		if(elements[rightIndex] == null)
			return leftIndex;
		return elements[leftIndex].compareTo(elements[rightIndex]) > 0 ? rightIndex: leftIndex;
	}

	@Override
	public String toString() {
		return (Arrays.toString(elements));
	}
}

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * This binary min-heap implementation of a priority queue ensures that
 * the element at the root is the smallest among all elements.
 * It provides efficient operations to maintain the heap property when
 * elements are added or removed.
 *
 * @param <E> the type of elements in this heap, which must be Comparable
 */
public class BinaryMinHeap<E extends Comparable<E>> implements Iterable<E>
{
    private E[] _heap;
    private int _size;
    private static final int DEFAULT = 10;

    /**
     * Constructs a new BinaryMinHeap with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the heap
     * @throws IllegalArgumentException if the initial capacity is less than 1
     */
    public BinaryMinHeap(int initialCapacity)
    {
        if (initialCapacity < 1)
        {
            throw new IllegalArgumentException();
        }
        _heap = (E[]) new Comparable[DEFAULT];
        _size = 0;
    }

    /**
     * Constructs a new BinaryMinHeap with the default initial capacity.
     */
    public BinaryMinHeap()
    {
        this(DEFAULT);
    }

    /**
     * Adds the specified element to the heap in priority order.
     * The element must not be null and must be comparable to other elements in the heap.
     * This method ensures that the heap property is maintained after the addition.
     *
     * @param element the element to add to the heap
     * @throws IllegalArgumentException if the element is null
     */
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot add null to the BinaryMinHeap");
        }
        if (_size == _heap.length) {
            _grow();
        }
        _heap[_size] = element; // Insert the element at the end of the heap
        _siftUp(_size);
        _size++;
    }



    /**
     * Retrieves, without removing, the highest-priority element from the heap.
     * This will be the smallest element as per the min-heap property.
     * If the heap is empty, this method throws a NoSuchElementException.
     *
     * @return the highest-priority element in the heap
     * @throws NoSuchElementException if the heap is empty
     */
    public E get() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return _heap[0];
    }



    /**
     * Retrieves and removes the minimum element of the heap.
     *
     * @return the minimum element
     */
    public E remove()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }
        E minElement = _heap[0];
        _heap[0] = _heap[--_size];
        _heap[_size] = null;
        _siftDown(0);
        return minElement;
    }

    /**
     * Clears the heap, removing all elements.
     */
    public void clear()
    {
        // Arrays is extremely useful learning about copyOf and .fill
        // much cleaner than using a loop.
        Arrays.fill(_heap, null);
        _size = 0;
    }

    /**
     * Returns the number of elements in the heap.
     *
     * @return the number of elements
     */
    public int size()
    {
        return _size;
    }

    /**
     * Returns _size == 0
     * @return returns _size == 0
     */
    public boolean isEmpty() {
        return _size == 0;
    }

    /**
     * Creates and returns an iterator for the binary min heap.
     * The iterator will iterate over the heap so that each call to next()
     * returns the smallest element remaining in the heap and removes it from the heap.
     *
     * @return an iterator that consumes elements from the heap in ascending order
     */
    public Iterator<E> iterator()
    {
        return new BinaryMinHeapIterator();
    }

    /**
     * Restores the heap property by moving the element at the specified index up
     * the heap until it is greater than or equal to its parent or is at the root of the heap.
     *
     * This method is used when a new element is added to the heap and may violate
     * the heap property by being smaller than its parent.
     *
     * @param index the index of the element to sift up
     */
    void _siftUp(int index)
    {
        E element = _heap[index];
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            E parent = _heap[parentIndex];
            if (element.compareTo(parent) >= 0)
            {
                _heap[index] = element;
                return;
            }
            _heap[index] = parent;
            index = parentIndex;
        }
        _heap[index] = element;
    }

    /**
     * Restores the heap property by moving the element at the specified index down
     * the heap until it is less than or equal to its children or is at a leaf position.
     *
     * This method is used when the root element is removed and the last element of the heap
     * is moved to the root. The heap property may be violated and needs to be restored.
     *
     * @param index the index of the element to sift down
     */
    void _siftDown(int index)
    {
        E element = _heap[index];
        int childIndex = 2 * index + 1;
        while (childIndex < _size) {
            int smallestChild = childIndex;
            if (childIndex + 1 < _size && _heap[childIndex + 1].compareTo(_heap[childIndex]) < 0)
            {
                smallestChild = childIndex + 1;
            }
            if (element.compareTo(_heap[smallestChild]) <= 0)
            {
                _heap[index] = element;
                return;
            }
            _heap[index] = _heap[smallestChild];
            index = smallestChild;
            childIndex = 2 * index + 1;
        }
        _heap[index] = element;
    }

    /**
     * _grow is a private method that is used to grow the length of the heap by 2x
     * then use Array copy to copy old heap into new heap
     */
    private void _grow() {
        _heap = Arrays.copyOf(_heap, _heap.length * 2);
    }



    /**
     * BinaryMinHeapIterator is a private class within BinaryMinHeap that is used to
     * Iterate through each element while using hasNext and next methods.
     */
    private class BinaryMinHeapIterator implements Iterator<E>
    {
        private final BinaryMinHeap<E> heapCopy = new BinaryMinHeap<>(_heap.length);
        {
            for (int i = 0; i < _size; i++)
            {
                heapCopy.add(_heap[i]);
            }
        }

        /**
         * Checks if there is a next element in the heap.
         *
         * @return returns true if there is a next element.
         */
        public boolean hasNext()
        {
            return !heapCopy.isEmpty();
        }

        /**
         * Retreives the next element in the heap.
         * @return returns the next element in the heap.
         */
        public E next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }
            return heapCopy.remove();
        }
    }
}

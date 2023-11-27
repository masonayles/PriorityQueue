import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 *
 * @param <E>
 */
public class BinaryMinHeap<E extends Comparable<E>> implements Iterable<E>
{
    private E[] _heap;
    private int _size;
    private static final int DEFAULT = 10;

    /**
     * @param initialCapacity
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
     *
     */
    public BinaryMinHeap()
    {
        this(DEFAULT);
    }

    /**
     * @param element
     */
    public void add(E element)
    {
        if (element == null)
        {
            throw new IllegalArgumentException();
        }
        if (_size == _heap.length)
        {
            _grow();
        }
        _heap[_size] = element;
        _siftUp(_size);
        _size++;
    }


    /**
     * Retrieves, but does not remove, the minimum element of the heap.
     *
     * @return the minimum element
     */
    public E get()
    {
        if (isEmpty())
        {
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
     * @return
     */
    public boolean isEmpty() {
        return _size == 0;
    }

    /**
     * @return
     */
    public Iterator<E> iterator()
    {
        return new BinaryMinHeapIterator();
    }

    /**
     * @param index
     */
    private void _siftUp(int index)
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
     *
     * @param index
     */
    private void _siftDown(int index)
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

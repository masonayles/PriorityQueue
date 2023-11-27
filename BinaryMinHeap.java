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
     *
     * @param initialCapacity
     */
    public BinaryMinHeap(int initialCapacity)
    {
        if (initialCapacity < 1)
        {
            throw new IllegalArgumentException();
        }
        _heap = (E[]) new Comparable[DEFAULT];
        _size = 0;    }

    /**
     *
     */
    public BinaryMinHeap()
    {
        this(DEFAULT);
    }

    /**
     *
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
        Arrays.fill(_heap, null);
        _size = 0;
    }

    /**
     * Returns the number of elements in the heap.
     * @return the number of elements
     */
    public int size() {
        return _size;
    }

    /**
     *
     * @return
     */
    public boolean isEmpty()
    {
        return _size == 0;
    }

    /**
     *
     * @return
     */
    public Iterator<E> iterator()
    {
        return new BinaryMinHeapIterator();
    }

    private void _siftUp(int index)
    {
        E element = _heap[index];
        while (index > 0)
        {
            int parentIndex = (index - 1) / 2;
            E parent = _heap[parentIndex];
            if (element.compareTo(parent) >= 0)
            {
                return;
            }
            _heap[index] = parent;
            index = parentIndex;
        }
        _heap[index] = element;
    }

    private void _siftDown(int index)
    {
        E element = _heap[index];
        int childIndex = 2 * index + 1;
        while (childIndex < _size)
        {
            if (childIndex + 1 < _size && _heap[childIndex + 1].compareTo(_heap[childIndex]) < 0) {
                childIndex++;
            }
            if (element.compareTo(_heap[childIndex]) <= 0)
            {
                return;
            }
            _heap[index] = _heap[childIndex];
            index = childIndex;
            childIndex = 2 * index + 1;
        }
        _heap[index] = element;
    }

    /**
     *
     */
    private void grow()
    {
        while ( )

    }

    /**
     *
     */
    private class BinaryMinHeapIterator implements Iterator<E>
    {
        private int _index = 0;

        /**
         *
         * @return
         */
        public boolean hasNext()
        {

        }

        /**
         *
         * @return
         */
        public E next()
        {

        }
    }
}

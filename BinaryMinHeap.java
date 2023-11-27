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

    }

    /**
     *
     * @return
     */
    public E get()
    {

    }

    /**
     *
     * @return
     */
    public E remove()
    {

    }

    /**
     *
     */
    public void clear()
    {
        _size = 0;
    }

    /**
     *
     * @return
     */
    public int size()
    {
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

    /**
     *
     * @param index
     */
    private void siftUp(int index)
    {

    }

    /**
     *
     * @param index
     */
    private void siftDown(int index)
    {

    }

    /**
     *
     */
    private void grow()
    {

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

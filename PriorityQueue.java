/**
 * A priority queue with highest-priority-out semantics.
 *
 * Note that elements must be comparable to each other, and also must not be null.
 * The "highest priority" semantics are determined by the implementing class.
 *
 * @param <E> The type of element contained in the queue.
 */
public interface PriorityQueue<E extends Comparable<E>>
{
    /**
     * Adds the specified element to the queue in priority order.
     * @param element The element to add to the queue.
     * @throws IllegalArgumentException If the specified element to add is null.
     */
    void add(E element);

    /**
     * Gets the next element from the queue, in priority order, without removing it.
     * @return The next element, in priority order.
     * @throws NoSuchElementException If the queue is empty.
     */
    E get();

    /**
     * Removes the next element from the queue, in priority order.
     * @return The next element, in priority order.
     * @throws NoSuchElementException If the queue is empty.
     */
    E remove();

    /**
     * Clears the queue.
     */
    void clear();

    /**
     * Returns the number of elements contained in the queue.
     * @return The number of elements contained in the queue.
     */
    int size();

    /**
     * Determines whether the queue is empty or not.
     * @return True if the queue is empty, else false.
     */
    boolean isEmpty();
}

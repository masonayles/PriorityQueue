/**
 * Interface that can go over a collection of elements using an iterator.
 * @param <E>
 */
public interface Iterable<E>
{
    Iterator<E> iterator();
}

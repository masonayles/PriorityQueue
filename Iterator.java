/**
 * Iterface that iterates a collection of elements.
 * Iterator allows for sequential access to the elements in a collection.
 * @param <E> <E> the type of element in collection.
 */
public interface Iterator<E>
{
    boolean hasNext();
    E next();
}

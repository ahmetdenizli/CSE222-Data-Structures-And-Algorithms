import java.util.ListIterator;

public class MyQueue<E> extends MyLinkedList<E> {
    /**
     * Inserts an item to the queue
     * @param item - Element to insert
     * @return Returns true
     */
    public boolean offer(E item)
    {
        ListIterator it = listIterator();
        it.add(item);
        return true;
    }

    /**
     * Removes the head of this queue
     * @return Returns removed element
     */
    public E poll()
    {
        E itemToReturn;
        ListIterator it = listIterator();

        while (it.hasNext())
            it.next();

        itemToReturn = (E)it.previous();
        it.remove();

        return itemToReturn;
    }

    /**
     * Returns first element to remove with poll operation without removing
     * @return Head of queue
     */
    public E peek()
    {
        return get(0);
    }
}

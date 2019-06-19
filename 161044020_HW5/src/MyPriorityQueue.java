import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;

public class MyPriorityQueue<E> {

    //private final MyArrayList<E> theData;
    private MyLinkedList<E> theData;
    private Comparator<E> comparator = null;
    private static final int DEFAULT_INITIAL_CAPACITY = 11;

    public MyPriorityQueue(Comparator<E> comparator) {
        this(DEFAULT_INITIAL_CAPACITY, comparator);
    }

    public MyPriorityQueue(int initialCapacity, Comparator<E> comparator){
        if (initialCapacity < 1)
            throw new IllegalArgumentException();
        this.theData = new MyLinkedList<E>();
        this.comparator = comparator;
    }

    public boolean offer(E item) {
            // Add the item to the heap.
            ListIterator it = theData.listIterator();

            while (it.hasNext())
            {
                E check =(E) it.next();
                if (comparator.compare(item, check) <= 0)
                {
                    theData.add(it.previousIndex(), item);
                    break;
                }
            }
            if (!it.hasNext())
                theData.addLast(item);

            return true;
    }

    public E poll() {
        synchronized (theData) {
            if (theData.getSize() == 0) {
                return null;
            }
            ListIterator it = theData.listIterator(theData.getSize());
            // Save the top of the heap.
                E result = (E) it.previous();
                it.remove();

                return result;

        }
    }

    private int compare(E left, E right) {
        // Checks Comparator is defined.
        if (comparator != null) {
            return comparator.compare(left, right);
        } else { // Else use left’s compareTo method.
            return ((Comparable<E>) left).compareTo(right);
        }
    }

    public boolean isEmpty(){
        return theData.getSize() == 0;
    }

}

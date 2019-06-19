import java.util.Iterator;
import java.util.NoSuchElementException;

public class ExperimentList implements Iterable {

    /** Starting address of linked list */
    private Experiment head;
    /** Starting head day address of linked list */
    private ExpDay head_day;
    /** Size of linked list */
    private int size;

    /**
     * Constructor: Null parameter constructor
     */
    public ExperimentList() {
        head = null;
        head_day = null;
        size = 0;
    }

    /**
     * Constructor: Creates a new ExperimentList get reference for head
     * @param head Reference for head
     */
    public ExperimentList(Experiment head) {
        this.head = head;
        head_day = new ExpDay(this.head);
    }

    /**
     * Override for Iterator method
     * @return Returns MyIterator() iterator for ExperimentList class
     */
    @Override
    public Iterator iterator() {
        return new MyIterator();
    }

    /**
     * Insert experiment to the end of the day
     * @param e Given experiment for insert
     */
    public void addExp(Experiment e)
    {
        if (e == null)
            return;

        if (size == 0) {
            head = e;
            head_day = new ExpDay(head);
            size++;
        }
        else {
            ExpDay temp = head_day;
            Experiment last = head;

            if (temp.data.getDay() > e.day){
                head_day = new ExpDay(e);
                head_day.next = temp;

                head = e;
                head.next = last;

                size++;
                return;
            }

            while (temp != null){
                if (temp.data.getDay() == e.day){
                    last = getLastOfDay(e.day, temp);
                    if (last != null){
                        e.next = last.next;
                        last.next = e;
                        size++;
                        return;
                    }
                }
                else if(temp.next != null && temp.next.data.getDay() > e.day){
                    last = getLastOfDay(e.day, temp);
                    if (last != null){
                        e.next = last.next;
                        last.next = e;

                        ExpDay newexpDay = new ExpDay(e);
                        newexpDay.next = temp.next;
                        temp.next = newexpDay;
                        size++;
                        return;
                    }
                }
                else if(temp.next == null){
                    last = getLastOfDay(e.day, temp);
                    if (last != null){
                        e.next = last.next;
                        last.next = e;

                        temp.next = new ExpDay(e);
                        size++;
                        return;
                    }
                }
                temp = temp.next;
            }

        }

    }

    /**
     * Method for getting last experiment of given day
     * @param day Given day for getting
     * @param exp_day Given Experiment day for cursor
     * @return Returns last experiment of given day or null if parameters are wrong
     */
    private Experiment getLastOfDay(int day, ExpDay exp_day){
        if (exp_day == null)
            return null;

        if (exp_day.data.getDay() <= day) {
            Experiment current = exp_day.data;
            while (current.next != null && current.getDay() == current.next.getDay())
            {
                current = current.next;
            }

            return current;
        }

        return null;
    }

    /**
     * Get the experiment with the given day and position
     * @param day Given day for getting
     * @param index Given index for getting
     * @return Returns experiment if wrong day and wrong index is given return null
     */
    public Experiment getExp(int day, int index){
        if (index < 0 || index >= size) {
            System.out.println("Out of index! \n");
            return null;
        } else if (day < 0){
            System.out.println("Enter day greater then 0! \n");
            return null;
        }

        ExpDay exp_day = head_day;
        while (exp_day != null){
            if (exp_day.data.getDay() == day){
                Experiment exp = exp_day.data;
                for (int i=0; i<index && exp != null; i++) {
                    exp = exp.next;
                }
                if (exp != null)
                    return exp;
                else {
                    System.out.println("Out of index! \n");
                    return null;
                }
            }
            exp_day = exp_day.next;
        }

        return null;
    }

    /**
     * Set the experiment with the given day and position
     * @param day Given day for setting
     * @param index Given index for setting
     * @param e Given experiment for setting
     */
    public void setExp(int day, int index, Experiment e){
        if (index < 0 || index >= size) {
            System.out.println("Out of index! \n");
        }
        if (head == null || e ==null)
            System.out.println("Null parameter or list \n");

        ExpDay exp_day = head_day;
        while (exp_day != null){
            if (exp_day.data.getDay() == day){
                Experiment exp = exp_day.data;

                for (int i=0; i<index && exp != null; i++) {
                    exp = exp.next;
                }
                if (exp != null) {
                    exp.copy(e);
                }
                else
                    System.out.println("Out of index! \n");

                return;
            }
            exp_day = exp_day.next;
        }
        System.out.println("Out of day! \n");

    }

    /**
     * Remove the experiment specified as index from given day
     * @param day Given day
     * @param index Given specified index
     * @return Returns removed Experiment if wrong day and index is given return null
     */
    public Experiment removeExp(int day, int index){
        if (index < 0 || index >= size) {
            System.out.println("Out of index! \n");
            return null;
        }
        Experiment exp, temp;

        if (index != 0){
            exp = getExp(day, index-1);
            if (exp != null){
                temp = exp.next;
                exp.next = temp.next;
                size--;
                return temp;
            } else
                return  null;
        } else{
            ExpDay exp_day = head_day;
            if (exp_day.data.getDay() == day){
                temp = head;
                head = temp.next;
                if (temp.next.day == day)
                    exp_day.data = temp.next;
                else
                    head_day = exp_day.next;
                size--;
                return temp;
            }

            while (exp_day != null){
                if (exp_day.next != null && exp_day.next.data.getDay() == day) {
                    break;
                }
                exp_day = exp_day.next;
            }

            exp = getLastOfDay(day, exp_day);
            if (exp != null){
                temp = exp.next;
                exp.next = temp.next;
                if (temp.next.day == day)
                    exp_day.next.data = temp.next;
                else
                    exp_day.next = exp_day.next.next;
                size--;
                return temp;
            } else {
                System.out.println("Out of day! \n");
                return null;
            }
        }

    }

    /**
     * Remove all experiments in a given day
     * @param day Given day for remove all experiments
     */
    public void removeDay(int day){
        if (day < 0){
            System.out.println("Enter day greater then 0! \n");
            return;
        }

        ExpDay exp_day = head_day;
        if (exp_day.data.getDay() == day) {
            head_day = exp_day.next;
            head = exp_day.next.data;

            Experiment current =exp_day.data;
            while (current.day == current.next.day){
                size--;
                current = current.next;
            }
            size--;

            return;
        }

        while (exp_day.next != null) {
            if (exp_day.next.data.day == day) {

                Experiment current =exp_day.data, temp = exp_day.next.data;

                while (temp.next != null && temp.getDay() == temp.next.getDay()){
                    size--;
                    temp = temp.next;
                }
                temp = temp.next;
                size--;

                while (current.next != null && current.getDay() == current.next.getDay()){
                    current = current.next;
                }

                if (exp_day.next.next != null) {
                    exp_day.next = exp_day.next.next;
                    current.next = temp;
                }
                else {
                    current.next = null;
                    exp_day.next = null;
                }

                return;
            }
            exp_day = exp_day.next;
        }

        System.out.println("Please Enter valid day for remove! \n");
    }

    /**
     * Method for prints all Experiments in experiment and day view
     */
    public void listAll()
    {
        System.out.println("List experiment view:");
        Experiment last = head;
        while( last != null) {
            System.out.println(last.toString());
            last = last.next;
        }
        System.out.println("List day view:");
        ExpDay last2 = head_day;
        while( last2 != null) {
            System.out.println(last2.toString());
            last2 = last2.next;
        }
    }

    /**
     * Lists all completed experiments in a given day
     * @param day Given day for list Experiments
     */
    public void listExp(int day){
        if (day < 0){
            System.out.println("Enter day greater then 0! \n");
            return;
        }
        ExpDay exp_day = head_day;

        while (exp_day != null) {
            if (exp_day.data.getDay() == day) {
                Experiment current = exp_day.data;
                while (current != null && current.getDay() == day)
                {
                    if (current.completed)
                        System.out.println(current);
                    current = current.next;
                }
                return;
            }
            exp_day = exp_day.next;
        }
        System.out.println("Please Enter valid day! \n");
    }

    /**
     * Sorts the experiments in a given day according to the accuracy,
     * the changes  be done on the list
     * @param day Given day for order
     */
    public void orderDay(int day){
        if (day < 0){
            System.out.println("Enter day greater then 0! \n");
            return;
        }
        ExpDay exp_day = head_day;

        while (exp_day != null) {
            if (exp_day.data.getDay() == day) {

                Experiment current = exp_day.data, temp, cursor = exp_day.data;

                ExperimentList newList = new ExperimentList();
                while (current !=null && current.day == day){
                    temp = new Experiment(current);

                    newList.sortedInsert(temp);
                    newList.size++;
                    current = current.next;
                }

                temp = newList.head;
                while ( temp != null){
                    cursor.copy(temp);
                    cursor = cursor.next;
                    temp = temp.next;
                }
                return;
            }
            exp_day = exp_day.next;
        }
        System.out.println("Please Enter valid day! \n");
    }

    /**
     * Sorts all the experiments in the list according to the accuracy, and
     * do not changes the original list since the day list may be damage
     * @return new ExperimentList sorted to accuracy
     */
    public ExperimentList orderExperiments(){
        ExperimentList newList = new ExperimentList();

        Experiment current = head, temp;

        while (current !=null){
            temp = new Experiment(current);

            newList.sortedInsert(temp);
            newList.size++;
            current = current.next;
        }

        return newList;
    }

    /**
     * function to insert a new_node in a list.
     * @param new_node node for insert
     */
    private void sortedInsert(Experiment new_node)
    {
        Experiment current;

        if (head == null || head.accuracy >= new_node.accuracy)
        {
            new_node.next = head;
            head = new_node;
        }
        else {
            current = head;

            while (current.next != null && current.next.accuracy < new_node.accuracy)
                current = current.next;

            new_node.next = current.next;
            current.next = new_node;
        }
    }

    public class ExpDay {
        /** First Experiment node of day*/
        private Experiment data;
        /** Next node of Experiment day*/
        private ExpDay next;
        /** Creates a new Experiment day node with a null next field
         @param dataItem The data stored
         */
        private ExpDay(Experiment dataItem) {
            data = dataItem;
            next = null;
        }
        /** Creates a new Experiment day node that references another node
         @param dataItem The data stored
         @param nodeRef The node referenced by new node
         */
        private ExpDay(Experiment dataItem, ExpDay nodeRef) {
            data = dataItem;
            next = nodeRef;
        }

        /**
         * Override of toString method
         */
        @Override
        public String toString() {
            return data.toString();
        }

    }

    /** Inner iterator class to iterate list */
    public class MyIterator implements Iterator {

        /** Cursor for iterator */
        private int pointer;
        /** Pointed element of the list */
        private Experiment Node;

        /** No parameter constructor */
        public MyIterator()
        {
            pointer = 0;
            Node = ExperimentList.this.head;
        }

        /** hasNext override to indicate if list has more elements
         * @return True if there are more elements, else returns false
         */
        @Override
        public boolean hasNext()
        {
            return (pointer < ExperimentList.this.size);
        }

        /** next override to return next value of the list */
        @Override
        public Experiment next() throws NoSuchElementException
        {
            if(hasNext())
            {
                Experiment currentData = Node;
                Node = Node.next;
                ++pointer;
                return currentData;
            }

            throw new NoSuchElementException();
        }

        @Override
        /** remove override to throw exception */
        public void remove() throws UnsupportedOperationException
        {
            throw new UnsupportedOperationException();
        }
    }

}

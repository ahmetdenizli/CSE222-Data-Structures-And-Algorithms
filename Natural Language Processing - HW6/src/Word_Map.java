import java.util.*;

public class Word_Map implements Map, Iterable
{

    final static int INITCAP=10;  //initial capacity
    int CURRCAP = INITCAP;   //current capacity
    final static float LOADFACT = 0.75f;
    private Node table[];
    private Node head;
    private Node tail;
    private int size;

    /**
     * No parameter constructor
     */
    public Word_Map() {
        this.table = new Node[INITCAP];
        for (int i = 0; i < CURRCAP; i++)
            table[i] = new Node();
        size = 0;
        head = null;
        tail = null;
    }

    /**
     * Return iterator for this Word_map.
     * @return iterator for this Word_map
     */
    @Override
    public Iterator iterator() {
        return new MyIterator();
    }

    /**
     * Node Class for Word_map table.
     */
    static class Node {
        // complete this class according to the given structure in homework definition
        public String key;
        public File_Map value;
        public Node next;

        private Node() {
            key = null;
            value = new File_Map();
            next = null;
        }

        /** String and File_Map parameter constructor
         * @param word - String value for Node key
         * @param file_map_item - Sets value as given parameter
         */
        private Node(String word, File_Map file_map_item) {
            key = word;
            value = file_map_item;
            next = null;
        }

        /** String, File_Map and Node parameter constructor
         * @param word - String value for Node key
         * @param file_map_item - Sets value as given parameter
         * @param ref  - Sets next node as given parameter
         */
        private Node(String word, File_Map file_map_item, Node ref) {
            key = word;
            value = file_map_item;
            next = ref;
        }
    }

    /**
     * Returns the number of elements in this Word_Map.
     * @return the number of elements in this Word_Map
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns true if this Word_Map contains no elements.
     * @return true if this Word_Map contains no elements.
     */
    @Override
    public boolean isEmpty() {
        return size==0;
    }

    /**
     * Returns true if this Word_Map contains a mapping for the specified word.
     * @param key - The word whose presence in this map is to be tested
     * @return true if this Word_Map contains a mapping for the specified word.
     */
    @Override
    /*Use linked structure instead of table index
    to perform search operation effectively
     * */
    public boolean containsKey(Object key) {
        if (key==null)
            return false;

        int index = key.hashCode()%CURRCAP;
        if (index < 0)
            index += CURRCAP;

        while (table[index%CURRCAP].key != null ) {
            if (table[index % CURRCAP].key.equals(key))
                return true;
            else
                ++index;
        }
        return false;
    }

    /**
     * Returns true if this Word_Map one or more keys to the specified Entry value, which is filename and index.
     * @param value - Entry whose presence in this Word_Map is to be tested
     * @return true if this Word_Map one or more keys to the specified Entry value.
     */
    @Override
    /*Use linked structure instead of table index
    to perform search operation effectively
     * */
    public boolean containsValue(Object value) {
        Entry entry = (Entry) value;

        if (entry == null || entry.getKey() == null || entry.getValue() == null)
            return false;

        int index = entry.getKey().hashCode()%CURRCAP;
        if (index < 0)
            index += CURRCAP;

        while (table[index%CURRCAP].key != null ) {
            if (table[index % CURRCAP].value.containsKey((String) entry.getKey()) && table[index % CURRCAP].value.containsValue(value) )
                return true;
            else
                ++index;
        }
        return false;
    }

    /**
     * Returns the File_map to which the specified word is mapped, or null if this map contains no mapping for the word.
     * @param key - the word whose associated value is to be returned
     * @return the File_map to which the specified word is mapped, or null if this map contains no mapping for the word.
     */
    @Override
    public Object get(Object key) {
        if (key==null)
            return null;

        int index = key.hashCode()%CURRCAP;
        if (index < 0)
            index += CURRCAP;

        while (table[index%CURRCAP].key != null ) {
            if (table[index % CURRCAP].key.equals(key))
                return table[index % CURRCAP].value;
            else
                ++index;
        }

        return null;
    }

    /**
     * Associates the specified value with the specified key in this Word_Map.
     * @param key - word with which the specified Entry is to be associated
     * @param value - Entry to be associated with the specified word
     * @return the Node associated with key, or null if there was no mapping for key
     */
    @Override
    /*
    Use linear probing in case of collision
    * */
    public Object put(Object key, Object value) {
        Entry entry = (Entry) value;
        boolean newWord = true;

        if (key==null || entry == null || entry.getKey() == null || entry.getValue() == null)
            return null;

        int index = key.hashCode()%CURRCAP;
        if (index < 0)
            index += CURRCAP;

        if (table[index].key == null ) {
            table[index].key = (String) key;
            table[index].value.put( entry.getKey(), entry.getValue());
        } else if ( table[index].key.equals(key) ) {
            table[index].value.put( entry.getKey(), entry.getValue());
            size--;
            newWord = false;
        } else {
            while (table[index%CURRCAP].key != null ) {
                if (!table[index % CURRCAP].key.equals(key))
                    ++index;
                else {
                    size--;
                    newWord = false;
                    break;
                }
            }
            index = index % CURRCAP;

            table[index].key = (String) key;
            table[index].value.put(entry.getKey(), entry.getValue());
        }
        ++size;

        if (newWord) {
            if (head == null) {
                head = table[index];
                head.next = null;
                tail = head;
            } else {
                tail.next = table[index];
                tail = tail.next;
                tail.next = null;
            }
        }

        double loadFactor = (double) size / CURRCAP;
        if (loadFactor > LOADFACT)
            rehash();

        return table[index];
    }

    @Override
    /*You do not need to implement remove function
    * */
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map m) {
        for ( Object e: m.entrySet()) {
            Entry entry = (Entry) e;
            this.put((String) entry.getKey(), (Integer) entry.getValue());
        }
    }

    /**
     * Removes all of the mappings from this Word_Map. The map will be empty after this call returns.
     */
    @Override
    public void clear() {
        this.table = new Node[INITCAP];

        for (int i = 0; i < CURRCAP; i++)
            table[i] = new Node();

        size = 0;
        head = null;
        tail = null;
    }

    /**
     * Returns a Set view of the keys contained in this Word_Map.
     * @return a Set view of the keys contained in this Word_Map.
     */
    @Override
    /*Use linked structure instead of table index
    for efficiency
     * */
    public Set keySet() {
        Set<String> keySet = new HashSet<>();
        Node current = head;
        while (current.next != null) {
            keySet.add(current.key);
            current = current.next;
        }
        if (current.key != null)
            keySet.add(current.key);

        return keySet;
    }

    /**
     * Returns a Collection view of the values contained in this Word_Map.
     * @return a Collection view of the values contained in this Word_Map.
     */
    @Override
    /*Use linked structure instead of table index
    for efficiency
     * */
    public Collection values() {
        ArrayList<Map.Entry<ArrayList<String>,ArrayList<List<Integer>>>> values = new ArrayList<>(size);
        Node current = head;
        while (current != null){
            values.add(Map.entry(current.value.fnames, current.value.occurances));
            current = current.next;
        }
        return values;
    }

    @Override
    /*You do not need to implement entrySet function
     * */
    public Set<Entry> entrySet() {
        return null;
    }

    /**
     * Capacity doubled and hashing again Word_Map.
     */
    private void rehash() {
        CURRCAP = CURRCAP * 2;
        Node[] oldTable = table;
        table = new Node[CURRCAP];
        for (int i = 0; i < CURRCAP; i++) {
            table[i] = null;
        }

        for (Node item : oldTable) {
            if (item.key != null) {
                int index = item.key.hashCode() % CURRCAP;
                if (index < 0)
                    index += CURRCAP;

                if (table[index] == null) {
                    table[index] = item;
                } else {
                    while (table[index % CURRCAP] != null)
                        ++index;

                    table[index % CURRCAP] = item;
                }
            }
        }
        for (int i = 0; i < CURRCAP; i++) {
            if (table[i] == null)
                table[i] = new Node();
        }

    }

    /** Inner iterator class to iterate Word_Map */
    public class MyIterator implements Iterator {

        /** Cursor for iterator */
        private int pointer;
        /** Pointed element of the list */
        private Node node;

        /** No parameter constructor */
        public MyIterator()
        {
            pointer = 0;
            node = Word_Map.this.head;
        }

        /** hasNext override to indicate if list has more elements
         * @return True if there are more elements, else returns false
         */
        @Override
        public boolean hasNext()
        {
            return (pointer < Word_Map.this.size);
        }

        /** next override to return next value of the list */
        @Override
        public Node next() throws NoSuchElementException
        {
            if(hasNext())
            {
                Node currentData = node;
                node = node.next;
                ++pointer;
                return currentData;
            }

            throw new NoSuchElementException();
        }

        /** remove override to throw exception */
        @Override
        public void remove() throws UnsupportedOperationException
        {
            throw new UnsupportedOperationException();
        }
    }
}

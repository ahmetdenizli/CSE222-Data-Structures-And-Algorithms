import java.util.*;

public class File_Map implements Map
{
    /*
    For this hashmap, you will use arraylists which will provide easy but costly implementation.
    Your should provide and explain the complexities for each method in your report.
    * */
    ArrayList<String> fnames;
    ArrayList<List<Integer>> occurances;

    /**
     * No parameter constructor
     */
    public File_Map() {
        this.fnames = new ArrayList<String>();
        this.occurances = new ArrayList<List<Integer>>();
    }

    /**
     * Returns the number of elements in this File_Map.
     * @return the number of elements in this File_Map
     */
    @Override
    public int size() {
        return fnames.size();
    }

    /**
     * Returns true if this File_Map contains no elements.
     * @return true if this File_Map contains no elements.
     */
    @Override
    public boolean isEmpty() {
        return fnames.isEmpty();
    }

    /**
     * Returns true if this File_Map contains a mapping for the specified filename.
     * @param key - The filename whose presence in this map is to be tested
     * @return true if this File_Map contains a mapping for the specified filename.
     */
    @Override
    public boolean containsKey(Object key) {
        return fnames.contains((String) key);
    }

    /**
     * Returns true if this File_Map one or more keys to the specified Entry value, which is filename and index.
     * @param value - Entry whose presence in this Word_map is to be tested
     * @return true if this File_Map one or more keys to the specified Entry value.
     */
    @Override
    public boolean containsValue(Object value) {
        Entry entry = (Entry) value;
        int index = fnames.indexOf((String) entry.getKey());
        if (index == -1)
            return false;
        return occurances.get(index).contains(entry.getValue());
    }

    /**
     * Returns the List to which the specified filename is mapped, or null if this map contains no mapping for the filename.
     * @param key - the filename whose associated value is to be returned
     * @return the List to which the specified filename is mapped, or null if this map contains no mapping for the filename.
     */
    @Override
    public Object get(Object key) {
        return occurances.get(fnames.indexOf(key));
    }

    /**
     * Associates the specified value with the specified key in this File_Map.
     * @param key - filename with which the specified index is to be associated
     * @param value - index to be associated with the specified filename
     * @return the previous value associated with key, or null if there was no mapping for key
     */
    @Override
    /*Each put operation will extend the occurance list*/
    public Object put(Object key, Object value) {
        if (this.containsKey(key)) {
            List<Integer> list = occurances.get(fnames.indexOf(key));
            list.add((Integer) value);
            return list.get(list.size()-1);
        } else {
            fnames.add((String) key);
            occurances.add(new ArrayList<Integer>() );
            List<Integer> list = occurances.get(fnames.indexOf(key));
            list.add((Integer) value);
            return null;
        }

    }

    /**
     * Removes the mapping for the specified key from this File_Map if present.
     * @param key - filename whose mapping is to be removed from the map
     * @return the removed item or null.
     */
    @Override
    public Object remove(Object key) {
        if (this.containsKey(key)){
            int index = fnames.indexOf(key);
            fnames.remove(index);
            return occurances.remove(index);
        } else
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
     * Removes all of the mappings from this File_Map. The map will be empty after this call returns.
     */
    @Override
    public void clear() {
        fnames.clear();
        occurances.clear();
    }

    /**
     * Returns a Set view of the keys contained in this File_Map.
     * @return a Set view of the keys contained in this File_Map.
     */
    @Override
    public Set keySet() {
        return new HashSet<>(fnames);
    }

    /**
     * Returns a Collection view of the values contained in this File_Map.
     * @return a Collection view of the values contained in this File_Map.
     */
    @Override
    public Collection values() {
        return occurances;
    }

    /**
     * Returns a Set view of the key and values entries contained in this File_Map.
     * @return a Set view of the key and values entries contained in this File_Map.
     */
    @Override
    public Set<Entry> entrySet() {
        Set<Entry> entrySet = new HashSet<Entry>();
        for (int i = 0; i < fnames.size(); i++) {
            int finalI = i;
            Entry entry = new MyEntry<String, List<Integer>>(fnames.get(finalI), occurances.get(finalI));
            entrySet.add(entry);
        }
        return entrySet;
    }

}

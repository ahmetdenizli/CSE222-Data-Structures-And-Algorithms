public class MyArrayList<E> {

    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;
    private Object elements[];

    public MyArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public MyArrayList(int capacity) {
        elements = new Object[capacity];
    }

    public void add(E e) {
        if (size == elements.length) {
            reallocate();
        }
        elements[size++] = e;
    }

    private void reallocate() {
        int newCapacity = 2 * size;
        Object[] newData = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = elements[i];
        }
        elements = newData;
    }

    public E get(int i) {
        if (i>= size || i <0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size " + size );
        }
        return (E) elements[i];
    }

    public E set(int index, E newVal) {
        if (index < 0 || index >= size) throw new ArrayIndexOutOfBoundsException();
        E old = (E) elements[index];
        elements[index] = newVal;
        return old;
    }

    public E remove(int index){
        if(index < size){
            E obj = (E) elements[index];
            for (int i = index; i < getSize() - 1; i++) {
                elements[i] = elements[i + 1];
            }
            size--;
            return obj;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return (size == 0);
    }

    public void swap(int index1, int index2) {
        if (index1 < size && index2 < size && index1 != index2){
            E temp = this.get(index1);
            this.set(index1, this.get(index2));
            this.set(index2, temp);
        }
    }
}

import java.util.EmptyStackException;

public class MyStack<E> {
    private int maxSize;
    private Object[] stackArray;
    private int top;

    public MyStack() {
        maxSize = 10;
        stackArray = new Object[maxSize];
        top = -1;
    }

    public MyStack(int s) {
        maxSize = s;
        stackArray = new Object[maxSize];
        top = -1;
    }

    public E push(E j) {
        if (isFull()){
            reallocate();
        }
        stackArray[++top] = j;
        return j;
    }

    public E pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return (E) stackArray[top--];
    }

    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return (E) stackArray[top];
    }

    public boolean isEmpty() {
        return (top == -1);
    }

    public boolean isFull() {
        return (top == maxSize - 1);
    }

    private void reallocate() {
        int newCapacity = 2 * maxSize;
        Object[] newData = new Object[newCapacity];
        for (int i = 0; i < maxSize; i++) {
            newData[i] = stackArray[i];
        }
        maxSize = newCapacity;
        stackArray = newData;
    }

    public int getSize() {
        return top+1;
    }

}

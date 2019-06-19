import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyIterator implements Iterator {

    private MyList<Integer> list;
    /** Cursor for iterator */
    private int pointer;

    public MyIterator(int[][] data) {
        list = new MyList<Integer>();
        pointer = 0;

        int m = data.length, n = data[0].length, x = 0, y = 0;

        recursively_traverse(data, m, n, x, y);
    }

    // traverse recursively with given 2D array spirally clockwise starting at the top left element
    private void recursively_traverse(int[][] data, int row, int col, int x, int y){
        if (!(row > 0) || !(col > 0))
            return;
        if (row == 1) {
            for (int i = 0; i < col; i++)
                list.add(data[x][y++]);
            return;
        } else if (col == 1) {
            for (int i = 0; i < row; i++)
                list.add(data[x++][y]);
            return;
        }


        for (int i = 0; i < col-1; i++) // move to right
            list.add(data[x][y++]);
        for (int i = 0; i < row-1; i++) // move to down
            list.add(data[x++][y]);
        for (int i = 0; i < col-1; i++) // move to left
            list.add(data[x][y--]);
        for (int i = 0; i < row-1; i++) // move to top
            list.add(data[x--][y]);

        x++;
        y++;
        row -= 2;
        col -= 2;

        recursively_traverse(data, row, col, x, y);
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return (pointer < list.getSize());
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Object next() throws NoSuchElementException {
        if(hasNext())
        {
            Integer currentData = list.get(pointer);
            ++pointer;
            return currentData;
        }

        throw new NoSuchElementException();
    }
}

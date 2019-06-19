import java.io.BufferedReader;
import java.io.FileReader;

public class part1 {

    /**
     * Reads file line by line and search white components and shows them
     * @param args Given filename
     */
    public static void main(String[] args){

        MyList<String> lines = new MyList<String>();
        char [][] matrix;
        boolean [][] discovered;
        int count = 0, row, col = 0;

        // Reads digital image matrix line by line and pass to lines list
        // col = max of length lines
        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("\\s+","");
                lines.add(line);
                if (line.length() > col)
                    col = line.length();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        row = lines.getSize();                          // Getting row numbers
        matrix = new char[row][col];                    // Create 2d char array with row and col
        discovered = new boolean[row][col];             // Create 2d boolean array for discover, initially all cells is false

        // Fills matrix cells
        for (int i =0; i<row; i++){
            for (int j=0; j<col; j++) {
                try {
                    matrix[i][j] = lines.get(i).charAt(j);
                } catch (Exception e){
                    matrix[i][j] = '-';
                }
            }
        }

        // iterativeSearch for white components
        for (int i =0; i<row; i++){
            for (int j=0; j<col; j++)
                if (!discovered[i][j] && matrix[i][j]=='1' )
                    iterativeSearch(matrix, i, j, discovered, (char) ('A' + count++));
                else if (!discovered[i][j] && matrix[i][j]=='0' ) {
                    matrix[i][j] = '.';
                    discovered[i][j] = true;
                }
        }

        // Prints white components of matrix
        for (int i =0; i<row; i++){
            for (int j=0; j<col-1; j++)
                System.out.print(matrix[i][j]+" ");
            System.out.print(matrix[i][col-1]);
            System.out.println();
        }
        System.out.println("The number of white components : " + count);

    }

    /**
     * This method looks around the white cell iteratively and shows white component with given letter.
     * @param matrix 2d char array for matrix of image
     * @param r Row index for white component
     * @param c Column index for white component
     * @param discovered 2d boolean array for record the discovered cells
     * @param let Letter for components
     */
    private static void iterativeSearch(char[][] matrix, int r, int c, boolean[][] discovered, char let) {
        // creates stacks used to do iteratively search
        MyStack<Integer> row = new MyStack<Integer>();
        MyStack<Integer> col = new MyStack<Integer>();

        // pushes given row and column to the stacks
        row.push(r);
        col.push(c);

        while (!row.isEmpty())
        {
            r = row.pop();
            c = col.pop();

            if (discovered[r][c])
                continue;

            discovered[r][c] = true;
            matrix[r][c] = let;

            if (r<matrix.length-1 &&!discovered[r+1][c] && matrix[r+1][c]=='1') {
                row.push(r+1);
                col.push(c);
            }
            if (c<matrix[r].length-1 && !discovered[r][c+1] && matrix[r][c+1]=='1') {
                row.push(r);
                col.push(c+1);
            }
            if (c>0 && !discovered[r][c-1] && matrix[r][c-1]=='1') {
                row.push(r);
                col.push(c-1);
            }
            if (r>0 && !discovered[r-1][c] && matrix[r-1][c]=='1') {
                row.push(r-1);
                col.push(c);
            }
        }
    }

}

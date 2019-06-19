import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        try {
            ReadGraph("input.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This Method reads given graph file and store it to ListGraph.
     *
     * @param filename given filename for graph.
     * @param directed given bool, if graph is directed it will be true, else false.
     * @throws IOException if an I/O error occurs
     */
    private static void ReadGraph(String filename, boolean directed) throws IOException
    {
        Scanner sc = new Scanner(new File(filename));

        // Create new ListGraph with given graph file.
        ListGraph graph = (ListGraph) AbstractGraph.createGraph(sc, directed);

        // int the number of people who are considered popular by every other person
        int numOfPopularPeople = 0;
        // 2d boolean array for relations.
        boolean[][] array = graph.BFStoFindPopularPeople();

        // for loop to look every person
        for (int i = 0; i < array.length; i++) {
            int count =0;

            // for loop to find person who considered popular between among others
            for (int j = 0; j < array.length; j++) {
                // Checks if the person are considered popular by other person
                // if it is count increase by one
                if ( i!=j && array[j][i])
                    count++;
            }
            // Checks if the person are considered popular by every other person
            // if it is numOfPopularPeople increase by one
            if (count == array.length-1)
                numOfPopularPeople++;
        }

        // Printing the number of people who are considered popular by every other person
        System.out.println(numOfPopularPeople);

        // Write the number of people who are considered popular by every other person
        // to output.txt
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        writer.write(numOfPopularPeople + "");
        writer.close();
    }
}

import java.io.IOException;
import java.util.Scanner;

public abstract class AbstractGraph implements Graph {

    /** The number of vertices */
    private int numV;
    /** Flag to indicate whether this is a directed graph */
    private boolean directed;

    /**
     * Construct a graph with the specified number of vertices
     * and the directed flag. If the directed flag is true,
     * this is a directed graph.
     * @param numV The number of vertices
     * @param directed The directed flag
     */
    public AbstractGraph(int numV, boolean directed) {
        this.numV = numV;
        this.directed = directed;
    }

    /**
     * Return the number of vertices.
     * @return The number of vertices
     */
    @Override
    public int getNumV() {
        return numV;
    }

    /**
     * Return whether this is a directed graph.
     * @return true if this is a directed graph
     */
    @Override
    public boolean isDirected() {
        return directed;
    }

    /**
     * Load the edges of a graph from the data in an input file.
     * The file should contain a series of lines, each line
     * with two or three data values. The first is the source,
     * the second is the destination, and the optional third
     * is the weight.
     * @param scan The buffered reader containing the data
     * @throws IOException if an I/O error occurs
     */
    public void loadEdgesFromFile(Scanner scan) throws IOException {
        String line;
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            String[] tokens = line.split("\\s+");
            try {
                int source = Integer.parseInt(tokens[0]);
                int dest = Integer.parseInt(tokens[1]);
                double weight = 1.0;
                if (tokens.length == 3) {
                    weight = Double.parseDouble(tokens[2]);
                }
                insert(new Edge(source - 1, dest - 1, weight));
            } catch (Exception e){ }
        }
    }

    /**
     * Factory method to create a graph and load the data from an input
     * file. The first line of the input file should contain the number
     * of vertices. The remaining lines should contain the edge data as
     * described under loadEdgesFromFile.
     * @param scan The Scanner connected to the data file
     * @param isDirected true if this is a directed graph,
     *                   false otherwise
     * @throws IOException if an I/O error occurs
     */
    public static Graph createGraph(Scanner scan, boolean isDirected) throws IOException {
        int numV = scan.nextInt();
        scan.nextLine();
        AbstractGraph returnValue = null;

        returnValue = new ListGraph(numV, isDirected);

        returnValue.loadEdgesFromFile(scan);
        return returnValue;
    }
}
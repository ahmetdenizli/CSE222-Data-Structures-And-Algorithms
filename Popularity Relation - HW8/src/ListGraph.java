import java.util.Iterator;

public class ListGraph extends AbstractGraph {

    /** An array of Lists to contain the edges that
     *  originate with each vertex.
     */
    private MyList<Edge>[] edges;

    /** Construct a graph with the specified number of
     *  vertices and directionality.
     *  @param numV The number of vertices
     *  @param directed The directionality flag
     */
    public ListGraph(int numV, boolean directed) {
        super(numV, directed);
        edges = new MyList[numV];
        for (int i = 0; i < numV; i++) {
            edges[i] = new MyList<Edge>();
        }
    }

    /** Determine whether an edge exists.
     *  @param source The source vertex
     *  @param dest The destination vertex
     *  @return true if there is an edge from source to dest
     */
    @Override
    public boolean isEdge(int source, int dest) {
        return edges[source].contains(new Edge(source, dest));
    }

    /** Insert a new edge into the graph.
     *  @param edge The new edge
     */
    @Override
    public void insert(Edge edge) {
        edges[edge.getSource()].add(edge);
        if (!isDirected()) {
            edges[edge.getDest()].add(new Edge(edge.getDest(), edge.getSource(), edge.getWeight()));
        }
    }

    /** Return an iterator to the edges connected
     *  to a given vertex.
     *  @param source The source vertex
     *  @return An Iterator<Edge> to the vertices
     *          connected to source
     */
    @Override
    public Iterator<Edge> edgeIterator(int source) {
        // linked list has already implements iterator so just call it
        return edges[source].iterator();
    }

    /** Get the edge between two vertices. If an
     *  edge does not exist, an Edge with a weight
     *  of Double.POSITIVE_INFINITY is returned.
     *  @param source The source
     *  @param dest The destination
     *  @return the edge between these two vertices
     */
    @Override
    public Edge getEdge(int source, int dest) {
        Edge target = new Edge(source, dest, Double.POSITIVE_INFINITY);
        Iterator<Edge> it = edges[source].iterator();
        for (Iterator<Edge> it1 = it; it1.hasNext(); ) {
            Edge edge = it1.next();
            if (edge.equals(target)) {
                return edge; // Desired edge found, return it.
            }
        }
        // Assert: All edges for source checked.
        return target; // Desired edge not found.
    }

    /**
     * Method to find direct or transitive relation between people
     * in breadth first order. Then return this relations.
     *
     * @return 2d boolean array that contains popularity relation between people
     */
    public boolean[][] BFStoFindPopularPeople()
    {
        // Queue for breadth first search
        MyQueue<Integer> queue = new MyQueue<Integer>();

        // 2d boolean array for relations between people
        boolean[][] relations = new boolean[getNumV()][getNumV()];

        for (int i = 0; i < getNumV(); i++) {
            queue.offer(i);

            while (!queue.isEmpty()) {
                int current = queue.poll();
                // iterator to the edges connected to current vertex.
                Iterator<Edge> it = edgeIterator(current);

                while (it.hasNext()) {
                    Edge edge = it.next();
                    int neighbor = edge.getDest();

                    try {
                        if (!relations[i][neighbor]) {
                            relations[i][neighbor] = true;
                            queue.offer(neighbor);
                        }
                    } catch (Exception e){}
                }
            }
        }

        return relations;
    }
}
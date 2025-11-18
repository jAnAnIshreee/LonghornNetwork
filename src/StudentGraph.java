import java.util.*;

/**
 * Represents an undirected weighted graph where each node is a
 * UniversityStudent and edges represent connection strengths
 * between students. Stored as an adjacency list for efficient
 * traversal using algorithms such as Prim's and Dijkstra's.
 */
public class StudentGraph {

    /** Maps each student to a list of their connected edges. */
    private Map<UniversityStudent, List<Edge>> adjacencyList;

    /**
     * Creates a StudentGraph containing the given students as nodes.
     * Edges can be added afterward using addEdge.
     *
     * @param students the students to include as nodes in the graph
     */
    public StudentGraph(Collection<UniversityStudent> students) {
        // implementation omitted
    }

    /**
     * Adds an undirected weighted edge between two students.
     * The edge is inserted for both students to maintain an
     * undirected graph structure.
     *
     * @param a the first student
     * @param b the second student
     * @param weight the connection strength between them
     */
    public void addEdge(UniversityStudent a, UniversityStudent b, double weight) {
        // implementation omitted
    }

    /**
     * Returns a list of edges connected to the given student.
     *
     * @param student the student whose neighbors are requested
     * @return list of edges representing neighboring connections
     */
    public List<Edge> getNeighbors(UniversityStudent student) {
        return null; // implementation omitted
    }

    /**
     * Returns all student nodes in the graph.
     *
     * @return a collection of all students in the graph
     */
    public Collection<UniversityStudent> getAllNodes() {
        return null; // implementation omitted
    }

    /**
     * Represents a weighted edge connecting a student to another student.
     */
    public static class Edge {
        private UniversityStudent neighbor;
        private double weight;

        /**
         * Constructs an edge connecting to a neighbor with the given weight.
         *
         * @param neighbor the connected student
         * @param weight the connection strength
         */
        public Edge(UniversityStudent neighbor, double weight) {
            // implementation omitted
        }

        /**
         * @return the student this edge connects to
         */
        public UniversityStudent getNeighbor() {
            return null; // implementation omitted
        }

        /**
         * @return the weight of the edge
         */
        public double getWeight() {
            return 0.0; // implementation omitted
        }
    }
}

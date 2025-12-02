import java.util.*;

/**
 * Represents an undirected weighted graph where each node is a
 * UniversityStudent and edges represent connection strengths
 * between students. Stored as an adjacency list for efficient
 * traversal using algorithms such as Prim's and Dijkstra's.
 */
public class StudentGraph {

    /**edge represents a connection from one student to a neighbor with a given weight
     *
     */
    public static class Edge{
        public UniversityStudent neighbor;
        public int weight;

        /**Constructor for edge
         *
         */
        public Edge(UniversityStudent neighbor, int weight){
            this.neighbor = neighbor;
            this.weight = weight;
        }

        /**
         *
         * @return formatted string representing an edge
         */
        public String toString(){
            return "(" + neighbor.name + ", " + weight + ")";
        }
    }
    /** Maps each student to a list of their connected edges. */
    private Map<UniversityStudent, List<Edge>> adjacencyList;

    /**
     * Constructs a StudentGraph containing the given students as nodes.
     * Edges can be added afterward using addEdge.
     *
     * @param students the students to include as nodes in the graph
     */
    public StudentGraph(Collection<UniversityStudent> students) {
        adjacencyList = new HashMap<>();
        //Initialize nodes
        for(UniversityStudent s : students){
            adjacencyList.put(s, new ArrayList<>());
        }

        List<UniversityStudent> studentList = new ArrayList<>(students);
        //Creates edges between every pair of students
        for(int i=0; i<students.size(); i++){
            for(int j= i + 1; j < studentList.size(); j++){
                UniversityStudent s1  = studentList.get(i);
                UniversityStudent s2 = studentList.get(j);
                int weight = s1.calculateConnectionStrength(s2);
                if(weight > 0){
                    addEdge(s1, s2, weight);
                }
            }
        }
    }

    /**
     * Adds an undirected weighted edge between two students.
     * The edge is inserted for both students to maintain an
     * undirected graph structure.
     *
     * @param s1 the first student
     * @param s2 the second student
     * @param weight the connection strength between them
     */
    public void addEdge(UniversityStudent s1, UniversityStudent s2, int weight) {
       // if (!adjacencyList.containsKey(s1) || !adjacencyList.containsKey(s2)) return;
        adjacencyList.get(s1).add(new Edge(s2, weight));
        adjacencyList.get(s2).add(new Edge(s1, weight));
    }

    /**
     * Returns a list of edges connected to the given student.
     *
     * @param s the student whose neighbors are requested
     * @return list of edges representing neighboring connections
     */
    public List<Edge> getNeighbors(UniversityStudent s) {
       // return adjacencyList.getOrDefault(s, Collections.emptyList());
        return adjacencyList.get(s);
    }

    /**
     * Returns all student nodes in the graph.
     *
     * @return a collection of all students in the graph
     */
    public Set<UniversityStudent> getAllNodes() {
        return adjacencyList.keySet();
    }



    /**
     * Prints the adjacency list for debugging purposes.
     */
    public void displayGraph() {
//        for (Map.Entry<UniversityStudent, List<Edge>> entry : adjacencyList.entrySet()) {
//            System.out.print(entry.getKey().name + " -> ");
//            System.out.println(entry.getValue());
//        }
        System.out.println("\nStudent Graph:");
        for (UniversityStudent s : adjacencyList.keySet()){
            System.out.println(s.name + " -> " + adjacencyList.get(s));
        }
    }
}

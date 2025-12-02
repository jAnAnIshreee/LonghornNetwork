import java.util.*;

/**
 * Handles grouping students into pods based on their relationships
 * within a StudentGraph. Pods are formed by exploring
 * connected regions of the graph and grouping students into sets
 * of the specified size.
 *
 */
public class PodFormation {
    /** The graph containing students and their connection strengths. */
    private final StudentGraph graph;
    /**
     * Constructor for forming pods
     *
     * @param graph the student graph used to determine pod groupings
     */
    public PodFormation(StudentGraph graph) {
        this.graph = graph;
    }


    /**
     * Forms pods of the specified size. This is done by
     * identifying connected components in the graph and dividing each
     * component into pods of up to the given size.
     *
     * @param podSize the target number of students in each pod
     */
    public void formPods(int podSize) {
        // Method signature only
    }
}

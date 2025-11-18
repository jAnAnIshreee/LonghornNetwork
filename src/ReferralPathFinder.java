import java.util.*;

/**
 * Allows for finding referral paths in a student graph
 * using Dijkstra's algorithm. Paths are evaluated by connection strength,
 * where stronger connections correspond to shorter distances.
 */
public class ReferralPathFinder {
    private StudentGraph graph;
    /**
     * Constructs a ReferralPathFinder for the given student graph.
     *
     * @param graph the student graph used for connection-based path searches
     */
    public ReferralPathFinder(StudentGraph graph) {
        // Constructor
    }

    /**
     * Uses Dijkstra's algorithm to find the strongest-connection path
     * from the starting student to any student who has interned at the
     * specified company. Stronger connections are treated as shorter by
     * negating edge weights.
     *
     * Returns an empty list if no matching student can be reached.
     *
     * @param start the student from whom the search begins
     * @param targetCompany the company to search for in internship histories
     * @return the referral path as a list of students, or an empty list if none exists
     */
    public List<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
        // Method signature only
        return new ArrayList<>();
    }
}

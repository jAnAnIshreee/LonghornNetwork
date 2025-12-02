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
        this.graph = graph;
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
        //Maps to store the best known distance and previous node for path reconstruction
        Map<UniversityStudent, Double> dist = new HashMap<>();
        Map<UniversityStudent, UniversityStudent> prev = new HashMap<>();
        Set<UniversityStudent> visited = new HashSet<>();

        //Initialize distances to infinity
        for(UniversityStudent s : graph.getAllNodes()){
            dist.put(s, Double.MAX_VALUE);
            prev.put(s, null);
        }
        dist.put(start, 0.0);

        //Priority queue orders nodes by their current distance
        PriorityQueue<UniversityStudent> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));
        pq.add(start);

        while(!pq.isEmpty()){
            UniversityStudent u = pq.poll();
            if(visited.contains(u)){
                continue;
            }
            visited.add(u);

            //Check if this student has interned at the target company
            for(String internship : u.previousInternships){
                if(internship.equalsIgnoreCase(targetCompany)){
                    //Reconstruct the path from start to u
                    List<UniversityStudent> path = new ArrayList<>();
                    UniversityStudent cur = u;
                    while (cur != null){
                        path.add(cur); //adding current student to path
                        cur = prev.get(cur); //getting the previous distance of student
                    }
                    Collections.reverse(path);
                    return path;
                }
            }

           //Relaxation for neighbors
           for(StudentGraph.Edge edge : graph.getNeighbors(u)){
               UniversityStudent v = edge.neighbor;
               if(visited.contains(v)) continue;

               //Calculate new "distance" using the reciprocal of edge weight
               double newDist = dist.get(u) + (1.0 / edge.weight);
               if(newDist < dist.get(v)){
                   dist.put(v, newDist);
                   prev.put(v, u);
                   pq.add(v);
               }
           }

        }

        return new ArrayList<>();

    }
}

import java.util.*;

/**
 * Implements the Gale–Shapley stable matching algorithm to assign
 * roommate pairs based on student preference lists. Students without
 * preferences remain unmatched, and there is appropriate handling for incomplete or
 * cyclic preference structures.
 */
public class GaleShapley {
    /**
     * Performs stable roommate matching using the Gale–Shapley algorithm. Students
     * propose in order of their preferences, and a proposal is accepted
     * if the receiver is unpaired or prefers the proposer over their
     * current match.
     *
     * @param students the students to match using stable pairing
     */
    public static void assignRoommates(List<UniversityStudent> students) {
        // Map to hold final pairings: each student is paired with a roomate
        Map<UniversityStudent, UniversityStudent> roommatePairs = new HashMap<>();

        //Tracks which proposal each student is up to
        Map<UniversityStudent, Integer> nextProposalIndex = new HashMap<>();

        //Map to quickly lookup a student by name
        Map<String, UniversityStudent> nameToStudent = new HashMap<>();

        for (UniversityStudent s: students){
            nameToStudent.put(s.name, s);
            nextProposalIndex.put(s, 0);
        }

        //Queue for students who are free and still have preferences to propose
        Queue<UniversityStudent> freeStudents = new LinkedList<>();
        for(UniversityStudent s : students){
            if(!s.roommatePreferences.isEmpty()){
                freeStudents.offer(s);
            }
        }

        while(!freeStudents.isEmpty()){ //there are still students that exist
            UniversityStudent s = freeStudents.poll();

            //Skip if s is already paired
            if(s.getRoommate() != null){
                continue;
            }

            int index = nextProposalIndex.get(s);
            if(index >= s.roommatePreferences.size()){
                continue; //s has no more preferences
            }

            String preferredName = s.roommatePreferences.get(index);
            nextProposalIndex.put(s, index + 1);
            UniversityStudent t = nameToStudent.get(preferredName);
            if(t == null){
                //preferred student not found, try next option
                if(nextProposalIndex.get(s) < s.roommatePreferences.size()){
                    freeStudents.offer(s);
                }
                continue;
            }

            //if t does not list s as a preference, reject s.
            if(!t.roommatePreferences.contains(s.name)){
                if(nextProposalIndex.get(s) < s.roommatePreferences.size()){
                    freeStudents.offer(s);
                }
                continue;
            }

            //If t s free, pair s and t
            if(t.getRoommate() == null){
                roommatePairs.put(s, t);
                roommatePairs.put(t, s);
                s.setRoommate(t);
                t.setRoommate(s);
            } else{
                //t is already paired: check if t prefers s over current partner
                UniversityStudent currentPartner = t.getRoommate();
                int currentIndex = t.roommatePreferences.indexOf(currentPartner.name);
                int newIndex = t.roommatePreferences.indexOf(s.name);
                if(newIndex < currentIndex){
                    //t prefers s over their current partner
                    roommatePairs.put(t, s);
                    roommatePairs.put(s, t);
                    roommatePairs.remove(currentPartner);
                    freeStudents.offer(currentPartner);
                    currentPartner.setRoommate(null);
                    s.setRoommate(t);
                    t.setRoommate(s);
                } else{
                    //t rejects s
                    if(nextProposalIndex.get(s) < s.roommatePreferences.size()){
                        freeStudents.offer(s);
                    }
                }
            }

        }

        //Print the roommate pairs
        System.out.println("\nRoommate Pairings (Gale-Shapley):");
        Set<UniversityStudent> printed = new HashSet<>();
        for(UniversityStudent s : roommatePairs.keySet()){
            UniversityStudent partner = roommatePairs.get(s);
            if(!printed.contains(s) && !printed.contains(partner)){
                System.out.println(s.name + " paired with " + partner.name);
                printed.add(s);
                printed.add(partner);
            }
        }


    }
}

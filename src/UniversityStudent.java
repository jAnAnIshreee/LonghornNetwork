import java.util.*;
import java.util.concurrent.Semaphore;

public class UniversityStudent extends Student {

    protected String name;
    protected int age;
    protected String gender;
    protected int year;
    protected String major;
    protected double gpa;
    protected List<String> roommatePreferences;
    protected List<String> previousInternships;

    private UniversityStudent roommate;

    // ============================
    // Friend & Chat Fields
    // ============================
    private final List<String> friendRequests = new ArrayList<>();
    private final List<String> chatHistory = new ArrayList<>();
    private final Semaphore friendLock = new Semaphore(1);
    private final Semaphore chatLock = new Semaphore(1);

    /**
     * Full constructor for UniversityStudent.
     */
    public UniversityStudent(String name, int age, String gender, int year,
                             String major, double gpa,
                             List<String> roommatePreferences,
                             List<String> previousInternships) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.year = year;
        this.major = major;
        this.gpa = gpa;
        this.roommatePreferences = roommatePreferences != null ? roommatePreferences : new ArrayList<>();
        this.previousInternships = previousInternships != null ? previousInternships : new ArrayList<>();
        this.roommate = null;
    }

    // ============================
    // Roommate Methods
    // ============================
    public void setRoommate(UniversityStudent roommate) { this.roommate = roommate; }
    public UniversityStudent getRoommate() { return roommate; }

    public String getName() { return name; }

    // ============================
    // Connection Strength
    // ============================
    @Override
    public int calculateConnectionStrength(Student other) {
        if (!(other instanceof UniversityStudent)) return 0;
        UniversityStudent o = (UniversityStudent) other;

        int score = 0;

        if (this.roommate != null && this.roommate.equals(o)) score += 4;

        for (String company : this.previousInternships) {
            if (o.previousInternships.contains(company)) score += 3;
        }

        if (!this.major.isEmpty() && this.major.equalsIgnoreCase(o.major)) score += 2;

        if (this.age != -1 && o.age != -1 && this.age == o.age) score += 1;

        return score;
    }

    // ============================
    // Friend & Chat Methods
    // ============================
    public void addFriendRequest(String senderName) {
        try {
            friendLock.acquire();
            friendRequests.add(senderName);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            friendLock.release();
        }
    }

    public List<String> getFriendRequests() {
        try {
            friendLock.acquire();
            return new ArrayList<>(friendRequests);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        } finally {
            friendLock.release();
        }
    }

    public void addChatMessage(String msg) {
        try {
            chatLock.acquire();
            chatHistory.add(msg);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            chatLock.release();
        }
    }

    public List<String> getChatHistory() {
        try {
            chatLock.acquire();
            return new ArrayList<>(chatHistory);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        } finally {
            chatLock.release();
        }
    }

    // ============================
    // Misc Methods
    // ============================
    public Set<String> getCompanies() {
        Set<String> set = new HashSet<>();
        if (previousInternships != null) {
            for (String c : previousInternships) {
                if (c != null && !c.trim().isEmpty()) set.add(c.trim());
            }
        }
        return set;
    }

    @Override
    public String toString() {
        return name + " (" + major + ")";
    }
}

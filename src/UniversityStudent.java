import java.util.*;

/**
 * Represents a university student and provides an implementation for calculating
 * connection strength based on shared attributes such as roommate status,
 * internships, major, and age.
 */
public class UniversityStudent extends Student {
    // TODO: Constructor and additional methods to be implemented
//    protected String name;
//    protected int age;
//    protected String gender;
//    protected int year;
//    protected String major;
//    protected double gpa;
//    protected List<String> roommatePreferences;
//    protected List<String> previousInternships;

    //new field to store assigned roomate
    private UniversityStudent roommate;
    /**
     * Creates a UniversityStudent with the given attributes.
     *
     * @param name the student's name
     * @param age the student's age
     * @param gender the student's gender
     * @param year the student's academic year
     * @param major the student's major field of study
     * @param gpa the student's GPA
     * @param roommatePreferences list of preferred roommates
     * @param previousInternships list of companies the student interned at
     */
    public UniversityStudent(String name, int age, String gender, int year,
                             String major, double gpa,
                             List<String> roommatePreferences,
                             List<String> previousInternships) {
        // constructor implementation omitted
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.year = year;
        this.major = major;
        this.gpa = gpa;
        this.roommatePreferences = roommatePreferences;
        this.previousInternships = previousInternships;
        this.roommate = null; //initially no roomate is assigned
    }

    /**setter for roomate used by Gale Shapley
     *
     * @param roommate the roomate to assign a student with
     */
    public void setRoommate(UniversityStudent roommate){
        this.roommate = roommate;
    }

    /**Getter for roomate
     *
     * @return the specified roommate
     */
    public UniversityStudent getRoommate(){
        return roommate;
    }


    /**
     * Computes the connection strength between this student and another
     * using the defined scoring rules:
     * - +4 if they are roommates
     * - +3 for each shared internship
     * - +2 if they share the same major
     * - +1 if they are the same age
     *
     * @param other the student to compare with
     * @return the total calculated connection strength
     */
    @Override
    public int calculateConnectionStrength(Student other) {        if (!(other instanceof UniversityStudent)) return 0;
        UniversityStudent o = (UniversityStudent) other;

        int score = 0;

        // +4 if either lists the other as a preferred roommate
        if (this.roommate != null && this.roommate.equals(o)) {
            score += 4;
        }

        // +3 per shared internship
        for (String company : this.previousInternships) {
            if (o.previousInternships.contains(company)) {
                score += 3;
            }
        }

        // +2 if same major (case-insensitive) and non-empty
        if (!this.major.isEmpty() && this.major.equalsIgnoreCase(o.major)) {
            score += 2;
        }

        // +1 if same age and valid
        if (this.age != -1 && o.age != -1 && this.age == o.age) {
            score += 1;
        }

        return score;
    }
    //optional use for debugging
    @Override
    public String toString() {
        return "UniversityStudent{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", year=" + year +
                ", major='" + major + '\'' +
                ", gpa=" + gpa +
                ", roommatePreferences=" + (roommatePreferences.isEmpty() ? "[]" : roommatePreferences) +
                ", previousInternships=" + (previousInternships.isEmpty() ? "[]" : previousInternships) +
                '}';
    }
}


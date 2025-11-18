import java.util.*;

/**
 * Represents a student profile with standard identification information
 * as well as roommate preferences and a list of previous internships.
 * Specific student types extend this class and implement their own
 * connection strength calculation based on shared attributes.
 */
public abstract class Student {
    protected String name;
    protected int age;
    protected String gender;
    protected int year;
    protected String major;
    protected double gpa;
    protected List<String> roommatePreferences;
    protected List<String> previousInternships;

    /**
     * Computes the connection strength between this student and another.
     * Implementations may consider attributes such as shared internships,
     * major, age, or whether the two students are roommates.
     *
     * @param other the student to compare against
     * @return a non-negative integer representing the connection strength
     */
    public abstract int calculateConnectionStrength(Student other);
}

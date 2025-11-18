import java.util.*;

/**
 * Represents a university student and provides an implementation for calculating
 * connection strength based on shared attributes such as roommate status,
 * internships, major, and age.
 */
public class UniversityStudent extends Student {
    // TODO: Constructor and additional methods to be implemented
    protected String name;
    protected int age;
    protected String gender;
    protected int year;
    protected String major;
    protected double gpa;
    protected List<String> roommatePreferences;
    protected List<String> previousInternships;
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
    public int calculateConnectionStrength(Student other) {
        return 0; // implementation omitted
    }
}


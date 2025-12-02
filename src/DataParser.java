import java.io.*;
import java.util.*;

/**
 * DataParser allows for reading and parsing student data files into
 * UniversityStudent objects. The input format includes fields such as demographics, academic details,
 * roommate preferences, and previous internships.
 */
public class DataParser {
    /**
     * Parses the given student data file and creates a list of
     * UniversityStudent objects. Each student's attributes are extracted from
     * key-value pairs, and list based fields (such as roommate preferences and
     * internship history) are processed accordingly. Invalid or incomplete
     * entries are handled by applying defaults or skipping the
     * problematic fields.
     *
     * @param filename the filepath to the student data file to parse
     * @return a list of UniversityStudent objects based on the file contents
     * @throws IOException if the file cannot be opened or read
     */
    public static List<UniversityStudent> parseStudents(String filename) throws IOException {
        List<UniversityStudent> students = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            Map<String, String> fields = new HashMap<>();

            while ((line = br.readLine()) != null) {
                line = line.trim();

                // Start of a new student block (recommended)
                if (line.equalsIgnoreCase("Student:")) {
                    if (!fields.isEmpty()) {
                        UniversityStudent s = buildStudent(fields);
                        if (s != null) students.add(s);
                        fields.clear();
                    }
                    continue;
                }

                // Skip blank lines
                if (line.isEmpty()) continue;

                // Expect "Key: Value"
                int sep = line.indexOf(':');
                if (sep == -1) {
                    // malformed line, ignore
                    System.err.println("Warning: skipping malformed line (no ':'): " + line);
                    continue;
                }

                String key = line.substring(0, sep).trim().toLowerCase();
                String value = line.substring(sep + 1).trim();
                fields.put(key, value);
            }

            // Add last record if file didn't end with "Student:" or blank line
            if (!fields.isEmpty()) {
                UniversityStudent s = buildStudent(fields);
                if (s != null) students.add(s);
            }
        }

        return students;
    }

    private static UniversityStudent buildStudent(Map<String, String> fields) {
        // Strings default to empty string
        String name = fields.getOrDefault("name", "");
        String gender = fields.getOrDefault("gender", "");
        String major = fields.getOrDefault("major", "");

        // Numeric fields use sentinel defaults
        int age = parseIntSafe(fields.get("age"));       // -1 if missing/invalid
        int year = parseIntSafe(fields.get("year"));     // -1 if missing/invalid
        double gpa = parseDoubleSafe(fields.get("gpa")); // -1.0 if missing/invalid

        List<String> roommatePrefs = parseList(fields.getOrDefault("roommatepreferences", ""));
        List<String> previousInternships = parseList(fields.getOrDefault("previousinternships", ""));

        try {
            return new UniversityStudent(
                    name,
                    age,
                    gender,
                    year,
                    major,
                    gpa,
                    roommatePrefs,
                    previousInternships
            );
        } catch (Exception e) {
            System.err.println("Error constructing UniversityStudent for '" + name + "': " + e.getMessage());
            return null;
        }
    }

    private static int parseIntSafe(String s) {
        if (s == null || s.trim().isEmpty()) return -1;
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double parseDoubleSafe(String s) {
        if (s == null || s.trim().isEmpty()) return -1.0;
        try {
            return Double.parseDouble(s.trim());
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }

    /**
     * Split a comma-separated list into trimmed, non-empty items.
     * If input is empty or null -> return empty list.
     */
    private static List<String> parseList(String raw) {
        List<String> out = new ArrayList<>();
        if (raw == null || raw.trim().isEmpty()) return out;
        String[] parts = raw.split(",");
        for (String p : parts) {
            String t = p.trim();
            if (!t.isEmpty()) out.add(t);
        }
        return out;
    }
}

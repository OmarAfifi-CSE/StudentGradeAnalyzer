package main.model;

// Import Jackson annotations
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore; // <-- Import this
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Student {
    private String id;
    private String firstName;
    private String lastName;
    private double grade;

    /**
     * No-argument constructor required by Jackson for deserialization.
     */
    public Student() {
    }

    /**
     * Constructor used by your application code and also usable by Jackson
     * thanks to the annotations.
     * @param id Student ID
     * @param firstName Student's first name
     * @param lastName Student's last name
     * @param grade Student's grade
     */
    @JsonCreator // Indicates this constructor can be used by Jackson to create instances from JSON
    public Student(
            @JsonProperty("id") String id, // Maps the "id" field in JSON to this parameter
            @JsonProperty("firstName") String firstName, // Maps the "firstName" field in JSON
            @JsonProperty("lastName") String lastName,   // Maps the "lastName" field in JSON
            @JsonProperty("grade") double grade) {     // Maps the "grade" field in JSON
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.grade = grade;
    }

    // --- Getters (remain the same) ---
    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getGrade() {
        return grade;
    }

    // --- Setters (remain the same) ---
    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    // --- Utility/Helper Methods ---

    /**
     * Tells Jackson to ignore this method completely during
     * serialization (saving) and deserialization (loading).
     * @return Calculated full name.
     */
    @JsonIgnore // <-- ADD THIS ANNOTATION
    public String getFullName() {
        return firstName + " " + lastName;
    }


    // --- Overridden Methods (remain the same) ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id); // Good practice: equals based on unique ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Consistent with equals
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", grade=" + grade +
                '}';
    }
}
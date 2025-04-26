package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeAnalytics {
    private List<Student> students;

    public GradeAnalytics(List<Student> students) {
        this.students = new ArrayList<>(students);
    }

    public double calculateMean() {
        if (students.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Student student : students) {
            sum += student.getGrade();
        }

        return sum / students.size();
    }

    public double calculateMedian() {
        if (students.isEmpty()) {
            return 0.0;
        }

        List<Double> grades = new ArrayList<>();
        for (Student student : students) {
            grades.add(student.getGrade());
        }

        Collections.sort(grades);

        int size = grades.size();
        if (size % 2 == 0) {
            return (grades.get(size / 2 - 1) + grades.get(size / 2)) / 2.0;
        } else {
            return grades.get(size / 2);
        }
    }

    public double calculateMode() {
        if (students.isEmpty()) {
            return 0.0;
        }

        Map<Double, Integer> frequencyMap = new HashMap<>();
        int maxFrequency = 0;
        double mode = 0.0;

        for (Student student : students) {
            double grade = student.getGrade();
            int frequency = frequencyMap.getOrDefault(grade, 0) + 1;
            frequencyMap.put(grade, frequency);

            if (frequency > maxFrequency) {
                maxFrequency = frequency;
                mode = grade;
            }
        }

        return mode;
    }

    public double getHighestGrade() {
        if (students.isEmpty()) {
            return 0.0;
        }

        double highest = Double.MIN_VALUE;
        for (Student student : students) {
            if (student.getGrade() > highest) {
                highest = student.getGrade();
            }
        }

        return highest;
    }

    public double getLowestGrade() {
        if (students.isEmpty()) {
            return 0.0;
        }

        double lowest = Double.MAX_VALUE;
        for (Student student : students) {
            if (student.getGrade() < lowest) {
                lowest = student.getGrade();
            }
        }

        return lowest;
    }

    public double calculateStandardDeviation() {
        if (students.isEmpty()) {
            return 0.0;
        }

        double mean = calculateMean();
        double sumOfSquaredDifferences = 0.0;

        for (Student student : students) {
            double difference = student.getGrade() - mean;
            sumOfSquaredDifferences += difference * difference;
        }

        return Math.sqrt(sumOfSquaredDifferences / students.size());
    }

    public Map<String, Integer> calculateGradeDistribution() {
        Map<String, Integer> distribution = new HashMap<>();
        distribution.put("A", 0);  // 90-100
        distribution.put("B", 0);  // 80-89
        distribution.put("C", 0);  // 70-79
        distribution.put("D", 0);  // 60-69
        distribution.put("F", 0);  // 0-59

        for (Student student : students) {
            double grade = student.getGrade();

            if (grade >= 90) {
                distribution.put("A", distribution.get("A") + 1);
            } else if (grade >= 80) {
                distribution.put("B", distribution.get("B") + 1);
            } else if (grade >= 70) {
                distribution.put("C", distribution.get("C") + 1);
            } else if (grade >= 60) {
                distribution.put("D", distribution.get("D") + 1);
            } else {
                distribution.put("F", distribution.get("F") + 1);
            }
        }

        return distribution;
    }
}
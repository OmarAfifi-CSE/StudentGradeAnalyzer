package main.algorithm;

import main.model.Student;
import java.util.ArrayList;
import java.util.List;

public class RadixSort {

    public static void sortByGrade(List<Student> students) {
        if (students.isEmpty()) {
            return;
        }

        double maxGrade = findMaxGrade(students);

        for (int exp = 1; maxGrade / exp > 0; exp *= 10) {
            countingSortByGrade(students, exp);
        }
    }

    private static double findMaxGrade(List<Student> students) {
        double max = 0;
        for (Student student : students) {
            if (student.getGrade() > max) {
                max = student.getGrade();
            }
        }
        return max;
    }

    private static void countingSortByGrade(List<Student> students, int exp) {
        int n = students.size();
        List<Student> output = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            output.add(null);
        }

        int[] count = new int[10];

        for (Student student : students) {
            int index = (int)((student.getGrade() / exp) % 10);
            count[index]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int index = (int)((students.get(i).getGrade() / exp) % 10);
            output.set(count[index] - 1, students.get(i));
            count[index]--;
        }

        for (int i = 0; i < n; i++) {
            students.set(i, output.get(i));
        }
    }

    public static void sortById(List<Student> students) {
        if (students.isEmpty()) {
            return;
        }

        int maxLength = findMaxIdLength(students);

        for (int i = maxLength - 1; i >= 0; i--) {
            countingSortById(students, i);
        }
    }

    private static int findMaxIdLength(List<Student> students) {
        int maxLength = 0;
        for (Student student : students) {
            int length = student.getId().length();
            if (length > maxLength) {
                maxLength = length;
            }
        }
        return maxLength;
    }

    private static void countingSortById(List<Student> students, int position) {
        int n = students.size();
        List<Student> output = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            output.add(null);
        }

        int[] count = new int[256];

        for (Student student : students) {
            int index = position < student.getId().length() ?
                    student.getId().charAt(position) : 0;
            count[index]++;
        }

        for (int i = 1; i < 256; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int index = position < students.get(i).getId().length() ?
                    students.get(i).getId().charAt(position) : 0;
            output.set(count[index] - 1, students.get(i));
            count[index]--;
        }

        for (int i = 0; i < n; i++) {
            students.set(i, output.get(i));
        }
    }
}
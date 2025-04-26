package main.algorithm;

import main.model.Student;
import java.util.List;

public class QuickSort {

    public static void sortByName(List<Student> students, int low, int high) {
        if (low < high) {
            int partitionIndex = partitionByName(students, low, high);
            sortByName(students, low, partitionIndex - 1);
            sortByName(students, partitionIndex + 1, high);
        }
    }

    private static int partitionByName(List<Student> students, int low, int high) {
        String pivot = students.get(high).getFullName();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (students.get(j).getFullName().compareTo(pivot) <= 0) {
                i++;
                swap(students, i, j);
            }
        }
        swap(students, i + 1, high);
        return i + 1;
    }

    public static void sortByGrade(List<Student> students, int low, int high) {
        if (low < high) {
            int partitionIndex = partitionByGrade(students, low, high);
            sortByGrade(students, low, partitionIndex - 1);
            sortByGrade(students, partitionIndex + 1, high);
        }
    }

    private static int partitionByGrade(List<Student> students, int low, int high) {
        double pivot = students.get(high).getGrade();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (students.get(j).getGrade() <= pivot) {
                i++;
                swap(students, i, j);
            }
        }
        swap(students, i + 1, high);
        return i + 1;
    }

    public static void sortById(List<Student> students, int low, int high) {
        if (low < high) {
            int partitionIndex = partitionById(students, low, high);
            sortById(students, low, partitionIndex - 1);
            sortById(students, partitionIndex + 1, high);
        }
    }

    private static int partitionById(List<Student> students, int low, int high) {
        String pivot = students.get(high).getId();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (students.get(j).getId().compareTo(pivot) <= 0) {
                i++;
                swap(students, i, j);
            }
        }
        swap(students, i + 1, high);
        return i + 1;
    }

    private static void swap(List<Student> students, int i, int j) {
        Student temp = students.get(i);
        students.set(i, students.get(j));
        students.set(j, temp);
    }
}
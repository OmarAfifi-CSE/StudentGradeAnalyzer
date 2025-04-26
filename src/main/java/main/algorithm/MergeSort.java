package main.algorithm;

import main.model.Student;
import java.util.ArrayList;
import java.util.List;

public class MergeSort {

    public static void sortByName(List<Student> students) {
        if (students.size() <= 1) {
            return;
        }

        int middle = students.size() / 2;
        List<Student> left = new ArrayList<>(students.subList(0, middle));
        List<Student> right = new ArrayList<>(students.subList(middle, students.size()));

        sortByName(left);
        sortByName(right);

        mergeByName(students, left, right);
    }

    private static void mergeByName(List<Student> result, List<Student> left, List<Student> right) {
        int leftIndex = 0;
        int rightIndex = 0;
        int resultIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex).getFullName().compareTo(right.get(rightIndex).getFullName()) <= 0) {
                result.set(resultIndex++, left.get(leftIndex++));
            } else {
                result.set(resultIndex++, right.get(rightIndex++));
            }
        }

        while (leftIndex < left.size()) {
            result.set(resultIndex++, left.get(leftIndex++));
        }

        while (rightIndex < right.size()) {
            result.set(resultIndex++, right.get(rightIndex++));
        }
    }

    public static void sortByGrade(List<Student> students) {
        if (students.size() <= 1) {
            return;
        }

        int middle = students.size() / 2;
        List<Student> left = new ArrayList<>(students.subList(0, middle));
        List<Student> right = new ArrayList<>(students.subList(middle, students.size()));

        sortByGrade(left);
        sortByGrade(right);

        mergeByGrade(students, left, right);
    }

    private static void mergeByGrade(List<Student> result, List<Student> left, List<Student> right) {
        int leftIndex = 0;
        int rightIndex = 0;
        int resultIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex).getGrade() <= right.get(rightIndex).getGrade()) {
                result.set(resultIndex++, left.get(leftIndex++));
            } else {
                result.set(resultIndex++, right.get(rightIndex++));
            }
        }

        while (leftIndex < left.size()) {
            result.set(resultIndex++, left.get(leftIndex++));
        }

        while (rightIndex < right.size()) {
            result.set(resultIndex++, right.get(rightIndex++));
        }
    }

    public static void sortById(List<Student> students) {
        if (students.size() <= 1) {
            return;
        }

        int middle = students.size() / 2;
        List<Student> left = new ArrayList<>(students.subList(0, middle));
        List<Student> right = new ArrayList<>(students.subList(middle, students.size()));

        sortById(left);
        sortById(right);

        mergeById(students, left, right);
    }

    private static void mergeById(List<Student> result, List<Student> left, List<Student> right) {
        int leftIndex = 0;
        int rightIndex = 0;
        int resultIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex).getId().compareTo(right.get(rightIndex).getId()) <= 0) {
                result.set(resultIndex++, left.get(leftIndex++));
            } else {
                result.set(resultIndex++, right.get(rightIndex++));
            }
        }

        while (leftIndex < left.size()) {
            result.set(resultIndex++, left.get(leftIndex++));
        }

        while (rightIndex < right.size()) {
            result.set(resultIndex++, right.get(rightIndex++));
        }
    }
}
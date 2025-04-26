package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.model.GradeAnalytics;
import main.model.Student;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    @FXML private Label meanLabel;
    @FXML private Label medianLabel;
    @FXML private Label modeLabel;
    @FXML private Label highestLabel;
    @FXML private Label lowestLabel;
    @FXML private Label stdDevLabel;
    @FXML private Label countLabel;
    @FXML private Label rangeLabel;

    private List<Student> students;
    private GradeAnalytics analytics;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setStudents(List<Student> students) {
        this.students = students;
        this.analytics = new GradeAnalytics(students);
        updateStatistics();
    }

    private void updateStatistics() {
        double mean = analytics.calculateMean();
        double median = analytics.calculateMedian();
        double mode = analytics.calculateMode();
        double highest = analytics.getHighestGrade();
        double lowest = analytics.getLowestGrade();
        double stdDev = analytics.calculateStandardDeviation();
        int count = students.size();
        double range = highest - lowest;

        meanLabel.setText(String.format("%.2f", mean));
        medianLabel.setText(String.format("%.2f", median));
        modeLabel.setText(String.format("%.2f", mode));
        highestLabel.setText(String.format("%.2f", highest));
        lowestLabel.setText(String.format("%.2f", lowest));
        stdDevLabel.setText(String.format("%.2f", stdDev));
        countLabel.setText(String.valueOf(count));
        rangeLabel.setText(String.format("%.2f", range));
    }
}
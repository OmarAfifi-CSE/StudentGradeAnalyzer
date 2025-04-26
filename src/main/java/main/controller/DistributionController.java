package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import main.model.GradeAnalytics;
import main.model.Student;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.List;

public class DistributionController implements Initializable {

    @FXML private BarChart<String, Number> barChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private Label aPctLabel;
    @FXML private Label bPctLabel;
    @FXML private Label cPctLabel;
    @FXML private Label dPctLabel;
    @FXML private Label fPctLabel;

    private List<Student> students;
    private GradeAnalytics analytics;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        xAxis.setLabel("Grade Category");
        yAxis.setLabel("Number of Students");
        barChart.setTitle("Grade Distribution");
    }

    public void setStudents(List<Student> students) {
        this.students = students;
        this.analytics = new GradeAnalytics(students);
        updateDistributionChart();
    }

    private void updateDistributionChart() {
        barChart.getData().clear();

        Map<String, Integer> distribution = analytics.calculateGradeDistribution();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Grade Distribution");

        series.getData().add(new XYChart.Data<>("A", distribution.get("A")));
        series.getData().add(new XYChart.Data<>("B", distribution.get("B")));
        series.getData().add(new XYChart.Data<>("C", distribution.get("C")));
        series.getData().add(new XYChart.Data<>("D", distribution.get("D")));
        series.getData().add(new XYChart.Data<>("F", distribution.get("F")));

        barChart.getData().add(series);

        double total = students.size();
        if (total > 0) {
            aPctLabel.setText(String.format("%.1f%%", (distribution.get("A") / total) * 100));
            bPctLabel.setText(String.format("%.1f%%", (distribution.get("B") / total) * 100));
            cPctLabel.setText(String.format("%.1f%%", (distribution.get("C") / total) * 100));
            dPctLabel.setText(String.format("%.1f%%", (distribution.get("D") / total) * 100));
            fPctLabel.setText(String.format("%.1f%%", (distribution.get("F") / total) * 100));
        } else {
            aPctLabel.setText("0.0%");
            bPctLabel.setText("0.0%");
            cPctLabel.setText("0.0%");
            dPctLabel.setText("0.0%");
            fPctLabel.setText("0.0%");
        }

        createHistogram();
    }

    private void createHistogram() {
        XYChart.Series<String, Number> histogram = new XYChart.Series<>();
        histogram.setName("Score Histogram");

        TreeMap<Integer, Integer> buckets = new TreeMap<>();
        for (int i = 0; i <= 10; i++) {
            buckets.put(i * 10, 0);
        }

        for (Student student : students) {
            int bucket = (int) (student.getGrade() / 10) * 10;
            buckets.put(bucket, buckets.getOrDefault(bucket, 0) + 1);
        }

        BarChart<String, Number> histogramChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        histogramChart.setTitle("Score Distribution");

        XYChart.Series<String, Number> histSeries = new XYChart.Series<>();
        histSeries.setName("Score Range");

        for (Map.Entry<Integer, Integer> entry : buckets.entrySet()) {
            String label = entry.getKey() + "-" + (entry.getKey() + 9);
            if (entry.getKey() == 100) {
                label = "100";
            }
            histSeries.getData().add(new XYChart.Data<>(label, entry.getValue()));
        }
    }
}
package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import java.util.ArrayList; // Import ArrayList if not already

public class DistributionController implements Initializable {

    // Existing FXML Fields
    @FXML private BarChart<String, Number> barChart; // A-F Distribution
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private Label aPctLabel;
    @FXML private Label bPctLabel;
    @FXML private Label cPctLabel;
    @FXML private Label dPctLabel;
    @FXML private Label fPctLabel;
    @FXML private BarChart<String, Number> histogramChartDisplay; // Histogram Chart
    @FXML private CategoryAxis histogramXAxis;
    @FXML private NumberAxis histogramYAxis;

    private List<Student> students;
    private GradeAnalytics analytics;

    // Define desired colors
    private final String COLOR_A = "#22c55e"; // Green
    private final String COLOR_B = "#3b82f6"; // Blue
    private final String COLOR_C = "#eab308"; // Yellow/Amber
    private final String COLOR_D = "#f97316"; // Orange
    private final String COLOR_F = "#ef4444"; // Red
    private final String COLOR_HISTOGRAM = "steelblue";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configure A-F Chart
        xAxis.setLabel("Grade Category");
        yAxis.setLabel("Number of Students");
        barChart.setLegendVisible(false);

        // --- Configure Histogram Chart ---
        histogramXAxis.setLabel("Score Range");
        histogramYAxis.setLabel("Number of Students");
        histogramChartDisplay.setLegendVisible(false); // Typically hide legend for histogram too
    }

    public void setStudents(List<Student> students) {
        this.students = (students != null) ? new ArrayList<>(students) : new ArrayList<>();
        this.analytics = new GradeAnalytics(this.students);
        updateDistributionChart();
        createHistogram();
    }

    // Updates the A-F Distribution Chart
    private void updateDistributionChart() {
        barChart.getData().clear();

        if (students.isEmpty()) {
            aPctLabel.setText("0.0%"); bPctLabel.setText("0.0%"); cPctLabel.setText("0.0%");
            dPctLabel.setText("0.0%"); fPctLabel.setText("0.0%");
            return;
        }

        Map<String, Integer> distribution = analytics.calculateGradeDistribution();

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        XYChart.Data<String, Number> dataA = new XYChart.Data<>("A", distribution.getOrDefault("A", 0));
        XYChart.Data<String, Number> dataB = new XYChart.Data<>("B", distribution.getOrDefault("B", 0));
        XYChart.Data<String, Number> dataC = new XYChart.Data<>("C", distribution.getOrDefault("C", 0));
        XYChart.Data<String, Number> dataD = new XYChart.Data<>("D", distribution.getOrDefault("D", 0));
        XYChart.Data<String, Number> dataF = new XYChart.Data<>("F", distribution.getOrDefault("F", 0));

        series.getData().addAll(dataA, dataB, dataC, dataD, dataF);
        barChart.getData().add(series);

        // Apply Colors After Adding Data
        applyBarColors(dataA, COLOR_A);
        applyBarColors(dataB, COLOR_B);
        applyBarColors(dataC, COLOR_C);
        applyBarColors(dataD, COLOR_D);
        applyBarColors(dataF, COLOR_F);

        double total = students.size();
        aPctLabel.setText(String.format("%.1f%%", (distribution.getOrDefault("A", 0) / total) * 100));
        bPctLabel.setText(String.format("%.1f%%", (distribution.getOrDefault("B", 0) / total) * 100));
        cPctLabel.setText(String.format("%.1f%%", (distribution.getOrDefault("C", 0) / total) * 100));
        dPctLabel.setText(String.format("%.1f%%", (distribution.getOrDefault("D", 0) / total) * 100));
        fPctLabel.setText(String.format("%.1f%%", (distribution.getOrDefault("F", 0) / total) * 100));
    }

    private void applyBarColors(XYChart.Data<String, Number> data, String color) {
        Node node = data.getNode();
        if (node != null) { setNodeStyle(node, color); }
        else { data.nodeProperty().addListener((obs, oldNode, newNode) -> { if (newNode != null) { setNodeStyle(newNode, color); } }); }
    }
    private void setNodeStyle(Node node, String color) {
        if (color != null && !color.isEmpty()) { node.setStyle("-fx-bar-fill: " + color + ";"); }
        else { node.setStyle("-fx-bar-fill: cornflowerblue;"); }
    }


    // Calculates and displays the Histogram data
    private void createHistogram() {
        histogramChartDisplay.getData().clear();

        if (students.isEmpty()) return;

        XYChart.Series<String, Number> histSeries = new XYChart.Series<>();

        TreeMap<Integer, Integer> buckets = new TreeMap<>();
        // Initialize buckets from 0-90 in steps of 10
        for (int i = 0; i <= 9; i++) {
            buckets.put(i * 10, 0);
        }

        for (Student student : students) {
            if (student == null) continue; // Basic null check
            int grade = (int) student.getGrade();
            int bucketKey = Math.max(0, (grade / 10) * 10); // Calculate bucket start (0, 10, 20...)
            if (bucketKey >= 100) { // Group 100 and above into the 90s bucket
                bucketKey = 90;
            }
            buckets.put(bucketKey, buckets.getOrDefault(bucketKey, 0) + 1);
        }


        for (Map.Entry<Integer, Integer> entry : buckets.entrySet()) {
            int start = entry.getKey();
            // Adjust label for the last bucket to include 100
            String label = (start == 90) ? "90-100" : start + "-" + (start + 9);
            histSeries.getData().add(new XYChart.Data<>(label, entry.getValue()));
        }

        // Add the calculated series to the VISIBLE histogram chart
        histogramChartDisplay.getData().add(histSeries);

        // Apply a single color to all bars in the histogram
        for(XYChart.Data<String, Number> data : histSeries.getData()) {
            applyBarColors(data, COLOR_HISTOGRAM);
        }
    }
}
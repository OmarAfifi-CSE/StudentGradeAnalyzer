module studentanalyzer {
    // --- Existing JavaFX requirements ---
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // --- Existing Jackson requirements ---
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    // --- Add these Apache POI requirements ---
    requires org.apache.poi.poi;          // Core POI classes
    requires org.apache.poi.ooxml;        // For XLSX format
    requires org.apache.xmlbeans;       // Dependency for ooxml
    requires org.apache.commons.io;
    requires org.apache.commons.compress;// Often needed by POI
    requires org.apache.commons.collections4;
    // --- End Apache POI requirements ---


    // --- Existing opens directives ---
    opens main.model to javafx.base, com.fasterxml.jackson.databind;
    opens main.controller to javafx.fxml;
    opens main to javafx.fxml, javafx.graphics;

    // --- Existing exports ---
    exports main;
}
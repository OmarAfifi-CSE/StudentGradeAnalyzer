package main.controller;

// --- Jackson Imports ---

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
// --- End Jackson Imports ---

// --- Apache POI Imports ---
// Using specific imports where possible, but fully qualified names will be used for Cell/CellType
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; // For XLSX format
// --- End Apache POI Imports ---


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.algorithm.MergeSort;
import main.algorithm.QuickSort;
import main.algorithm.RadixSort;
// import main.model.GradeAnalytics;
import main.model.Student;

// Remove unused IO imports related to CSV
// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.PrintWriter;
// import java.io.FileWriter;
import java.io.File;                     // Still needed for FileChooser result
import java.io.FileInputStream;        // Needed for POI import
import java.io.FileOutputStream;       // Needed for POI export
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MainController implements Initializable {

    // --- FXML Fields (Unchanged) ---
    @FXML
    private TableView<Student> tableView;
    @FXML
    private TableColumn<Student, String> idColumn;
    @FXML
    private TableColumn<Student, String> firstNameColumn;
    @FXML
    private TableColumn<Student, String> lastNameColumn;
    @FXML
    private TableColumn<Student, Double> gradeColumn;
    @FXML
    private ComboBox<String> sortAlgorithmComboBox;
    @FXML
    private ComboBox<String> sortCriteriaComboBox;
    @FXML
    private Button sortButton;
    @FXML
    private Button addStudentButton;
    @FXML
    private Button removeStudentButton;
    @FXML
    private Button removeAllStudentsButton;
    @FXML
    private Button statisticsButton;
    @FXML
    private Button distributionButton;
    @FXML
    private TextField idField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField gradeField;
    // --- End FXML Fields ---


    // --- Data Storage Fields (Unchanged) ---
    private final ObservableList<Student> students = FXCollections.observableArrayList();
    private ObjectMapper objectMapper;
    private Path dataFilePath;
    // --- End Data Storage Fields ---


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // --- Initialize logic remains unchanged ---
        setupPersistence();
        loadStudents();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        tableView.setItems(students);
        sortAlgorithmComboBox.getItems().addAll("QuickSort", "MergeSort", "RadixSort");
        sortCriteriaComboBox.getItems().addAll("Name", "Grade", "ID");
    }

    // --- Persistence Methods (Unchanged) ---
    private void setupPersistence() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            String userHome = System.getProperty("user.home");
            Path appDataDir = Paths.get(userHome, ".StudentGradeAnalyzer");
            Files.createDirectories(appDataDir);
            dataFilePath = appDataDir.resolve("students.json");
        } catch (IOException e) {
            System.err.println("Error creating application data directory: " + e.getMessage());
            showAlert("Initialization Error", "Could not create data directory. Data saving might fail.");
            dataFilePath = Paths.get("students.json");
        } catch (SecurityException se) {
            System.err.println("Security Error accessing user home or creating directory: " + se.getMessage());
            showAlert("Initialization Error", "Permission denied for data directory. Data saving might fail.");
            dataFilePath = Paths.get("students.json");
        }
    }

    private void loadStudents() {
        if (dataFilePath == null) {
            System.err.println("Data file path not initialized. Cannot load.");
            loadSampleData();
            return;
        }
        if (Files.exists(dataFilePath)) {
            try {
                List<Student> loadedList = objectMapper.readValue(dataFilePath.toFile(), new TypeReference<List<Student>>() {
                });
                students.setAll(loadedList);
                System.out.println("Loaded " + students.size() + " students from " + dataFilePath);
            } catch (IOException e) {
                System.err.println("Error loading student data from file: " + dataFilePath);
                e.printStackTrace();
                showAlert("Error Loading Data", "Could not read or parse student data file.\nStarting with sample data.\nError: " + e.getMessage());
                loadSampleData();
            }
        } else {
            System.out.println("Data file not found (" + dataFilePath + "). Loading sample data.");
            loadSampleData();
        }
    }

    public void saveData() {
        if (dataFilePath == null) {
            System.err.println("Data file path not initialized. Cannot save.");
            showAlert("Error Saving Data", "Cannot save data: File path could not be determined.");
            return;
        }
        try {
            Files.createDirectories(dataFilePath.getParent());
            objectMapper.writeValue(dataFilePath.toFile(), new ArrayList<>(students));
            System.out.println("Saved " + students.size() + " students to " + dataFilePath);
        } catch (IOException e) {
            System.err.println("Error saving student data to file: " + dataFilePath);
            e.printStackTrace();
            showAlert("Error Saving Data", "Could not save student data.\nError: " + e.getMessage());
        }
    }


    // --- Sample Data (Unchanged) ---
    private void loadSampleData() {
        students.clear();
        students.add(new Student("S001", "John", "Doe", 85.5));
        students.add(new Student("S002", "Jane", "Smith", 92.3));
        students.add(new Student("S003", "Michael", "Johnson", 78.9));
        students.add(new Student("S004", "Emily", "Williams", 95.7));
        students.add(new Student("S005", "David", "Brown", 68.2));
        students.add(new Student("S006", "Sarah", "Jones", 73.8));
        students.add(new Student("S007", "Daniel", "Miller", 88.4));
        students.add(new Student("S008", "Jessica", "Davis", 91.2));
        students.add(new Student("S009", "Matthew", "Garcia", 65.9));
        students.add(new Student("S010", "Jennifer", "Rodriguez", 82.1));
        students.add(new Student("S011", "Christopher", "Wilson", 79.5));
        students.add(new Student("S012", "Ashley", "Martinez", 87.3));
        System.out.println("Loaded sample student data.");
    }


    // --- Other Event Handlers (Unchanged) ---
    @FXML
    private void handleSortAction(ActionEvent event) { /* ... unchanged ... */
        String algorithm = sortAlgorithmComboBox.getValue();
        String criteria = sortCriteriaComboBox.getValue();
        if (algorithm == null || criteria == null) {
            showAlert("Error", "Please select a sorting algorithm and criteria.");
            return;
        }
        if (students.isEmpty()) {
            showAlert("Information", "There are no students to sort.");
            return;
        }
        List<Student> studentList = new ArrayList<>(students);
        Collections.shuffle(studentList); // Shuffle for performance testing and avoid StackOverflow
        long startTime = System.nanoTime();
        if ("QuickSort".equals(algorithm)) {
            if ("Name".equals(criteria)) QuickSort.sortByName(studentList, 0, studentList.size() - 1);
            else if ("Grade".equals(criteria)) QuickSort.sortByGrade(studentList, 0, studentList.size() - 1);
            else QuickSort.sortById(studentList,0, studentList.size() - 1);
        } else if ("MergeSort".equals(algorithm)) {
            if ("Name".equals(criteria)) MergeSort.sortByName(studentList);
            else if ("Grade".equals(criteria)) MergeSort.sortByGrade(studentList);
            else MergeSort.sortById(studentList);
        } else if ("RadixSort".equals(algorithm)) {
            if ("Grade".equals(criteria)) RadixSort.sortByGrade(studentList);
            else if ("ID".equals(criteria)) RadixSort.sortById(studentList);
            else {
                showAlert("Error", "RadixSort can only be used with Grade or ID criteria.");
                return;
            }
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        students.setAll(studentList);
        showAlert("Sorting Complete", "Sorted using " + algorithm + " by " + criteria + " in " + duration + " ms.");
    }

    @FXML
    private void handleAddStudentAction(ActionEvent event) { /* ... unchanged ... */
        String id = idField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String gradeText = gradeField.getText().trim();
        if (id.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || gradeText.isEmpty()) {
            showAlert("Input Error", "All fields are required to add a student.");
            return;
        }
        if (students.stream().anyMatch(s -> s.getId().equalsIgnoreCase(id))) {
            showAlert("Input Error", "Student ID '" + id + "' already exists.");
            return;
        }
        try {
            double grade = Double.parseDouble(gradeText);
            if (grade < 0 || grade > 100) {
                showAlert("Input Error", "Grade must be a number between 0 and 100.");
                return;
            }
            Student newStudent = new Student(id, firstName, lastName, grade);
            students.add(newStudent);
            idField.clear();
            firstNameField.clear();
            lastNameField.clear();
            gradeField.clear();
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Grade must be a valid number.");
        }
    }

    @FXML
    private void handleRemoveStudentAction(ActionEvent event) { /* ... unchanged ... */
        Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove " + selectedStudent.getFullName() + "?", ButtonType.YES, ButtonType.NO);
            confirmation.setTitle("Confirm Removal");
            confirmation.setHeaderText("Remove Student?");
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    students.remove(selectedStudent);
                }
            });
        } else {
            showAlert("Selection Error", "Please select a student from the table to remove.");
        }
    }

    @FXML
    private void handleRemoveAllStudentsAction(ActionEvent event) {
        if (students.isEmpty()) {
            showAlert("Information", "There are no students to remove.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Removal");
        confirmation.setHeaderText("Remove ALL Students?");
        confirmation.setContentText("Are you sure you want to permanently remove all student records?\nThis action cannot be undone.");
        confirmation.initOwner(tableView.getScene().getWindow()); // Important for modality
        confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO); // Explicitly set buttons

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                students.clear(); // Clear the underlying ObservableList
                showAlert("Success", "All student records have been removed.");
                // Data will be saved as empty on exit, or call saveData() if needed immediately
            }
        });
    }

    @FXML
    private void handleStatisticsAction(ActionEvent event) { /* ... unchanged ... */
        if (students.isEmpty()) {
            showAlert("Information", "No student data available to calculate statistics.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StatisticsView.fxml"));
            Parent root = loader.load();
            StatisticsController controller = loader.getController();
            controller.setStudents(new ArrayList<>(students));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(tableView.getScene().getWindow());
            stage.setTitle("Grade Statistics");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load statistics view: " + e.getMessage());
        } catch (NullPointerException e) {
            e.printStackTrace();
            showAlert("Error", "Error initializing statistics view. Check FXML path and controller setup.");
        }
    }

    @FXML
    private void handleDistributionAction(ActionEvent event) { /* ... unchanged ... */
        if (students.isEmpty()) {
            showAlert("Information", "No student data available to show distribution.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DistributionView.fxml"));
            Parent root = loader.load();
            DistributionController controller = loader.getController();
            controller.setStudents(new ArrayList<>(students));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(tableView.getScene().getWindow());
            stage.setTitle("Grade Distribution");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load distribution view: " + e.getMessage());
        } catch (NullPointerException e) {
            e.printStackTrace();
            showAlert("Error", "Error initializing distribution view. Check FXML path and controller setup.");
        }
    }


    // --- Import/Export Handlers - MODIFIED FOR XLSX ---

    @FXML
    private void handleImportAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Student Data from Excel"); // Use XLSX
        fileChooser.getExtensionFilters().clear(); // Clear old filters
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx")); // Add XLSX filter
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("All Files (*.*)", "*.*"));

        File file = fileChooser.showOpenDialog(tableView.getScene().getWindow());
        if (file != null) {
            Alert importType = new Alert(Alert.AlertType.CONFIRMATION,
                    "Replace existing student data or append to it?",
                    new ButtonType("Replace", ButtonBar.ButtonData.OK_DONE),
                    new ButtonType("Append", ButtonBar.ButtonData.YES),
                    ButtonType.CANCEL);
            importType.setTitle("Import Mode");
            importType.setHeaderText("Choose Import Mode");

            importType.showAndWait().ifPresent(response -> {
                if (response.getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
                    return;
                }

                boolean replaceData = (response.getButtonData() == ButtonBar.ButtonData.OK_DONE);
                List<Student> importedStudents = new ArrayList<>();
                int studentsAdded = 0;
                int headerRowIndex = 0; // Assuming header is the first row (index 0)
                int processedRows = 0;

                try (FileInputStream fis = new FileInputStream(file);
                     Workbook workbook = new XSSFWorkbook(fis)) { // Use XSSFWorkbook for XLSX

                    Sheet sheet = workbook.getSheetAt(0); // Get the first sheet
                    Iterator<Row> rowIterator = sheet.iterator();

                    // Skip header row if it exists
                    if (rowIterator.hasNext()) {
                        rowIterator.next(); // Move past the header
                    }

                    DataFormatter dataFormatter = new DataFormatter(); // Helps get cell values as strings safely

                    while (rowIterator.hasNext()) {
                        Row row = rowIterator.next();
                        processedRows++;
                        // Use fully qualified names for POI Cell and CellType
                        org.apache.poi.ss.usermodel.Cell idCell = row.getCell(0);
                        org.apache.poi.ss.usermodel.Cell firstNameCell = row.getCell(1);
                        org.apache.poi.ss.usermodel.Cell lastNameCell = row.getCell(2);
                        org.apache.poi.ss.usermodel.Cell gradeCell = row.getCell(3);

                        // Check if the ID cell is reasonably valid
                        if (idCell != null && idCell.getCellType() != org.apache.poi.ss.usermodel.CellType.BLANK) {
                            try {
                                String id = dataFormatter.formatCellValue(idCell).trim();
                                String firstName = dataFormatter.formatCellValue(firstNameCell).trim();
                                String lastName = dataFormatter.formatCellValue(lastNameCell).trim();
                                double grade = 0.0;

                                if (gradeCell != null && gradeCell.getCellType() != org.apache.poi.ss.usermodel.CellType.BLANK) {
                                    if (gradeCell.getCellType() == org.apache.poi.ss.usermodel.CellType.NUMERIC) {
                                        grade = gradeCell.getNumericCellValue();
                                    } else {
                                        // Attempt to parse if it's a non-blank string
                                        String gradeStr = dataFormatter.formatCellValue(gradeCell).trim();
                                        if (!gradeStr.isEmpty()) {
                                            grade = Double.parseDouble(gradeStr);
                                        } else {
                                            System.err.println("Skipping row " + (processedRows + headerRowIndex + 1) + " due to empty grade string.");
                                            continue; // Skip this student if grade is empty string
                                        }
                                    }
                                } else {
                                    System.err.println("Skipping row " + (processedRows + headerRowIndex + 1) + " due to blank grade cell.");
                                    continue; // Skip this student if grade cell is blank
                                }


                                if (!id.isEmpty()) { // Add only if ID is not empty
                                    importedStudents.add(new Student(id, firstName, lastName, grade));
                                    studentsAdded++;
                                } else {
                                    System.err.println("Skipping row " + (processedRows + headerRowIndex + 1) + " due to empty ID.");
                                }
                            } catch (NumberFormatException nfe) {
                                System.err.println("Skipping row " + (processedRows + headerRowIndex + 1) + " due to invalid grade format: " + nfe.getMessage());
                            } catch (Exception cellEx) {
                                System.err.println("Skipping row " + (processedRows + headerRowIndex + 1) + " due to cell reading error: " + cellEx.getMessage());
                            }
                        } else {
                            System.err.println("Skipping potentially empty row " + (processedRows + headerRowIndex + 1) + " (based on ID cell).");
                        }
                    }

                    // Update the main list based on import mode
                    if (replaceData) {
                        students.setAll(importedStudents);
                    } else {
                        importedStudents.forEach(imported -> {
                            if (students.stream().noneMatch(existing -> existing.getId().equalsIgnoreCase(imported.getId()))) {
                                students.add(imported);
                            } else {
                                System.out.println("Skipping duplicate ID during append: " + imported.getId());
                            }
                        });
                    }
                    showAlert("Import Successful", "Processed " + processedRows + " data rows.\nImported " + studentsAdded + " students.\nTotal students now: " + students.size());

                } catch (IOException e) {
                    showAlert("Import Error", "Error reading Excel file: " + e.getMessage());
                    e.printStackTrace();
                } catch (Exception e) { // Catch other potential POI errors
                    showAlert("Import Error", "An unexpected error occurred during import: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        }
        // Data will be saved automatically on application exit
    }

    @FXML
    private void handleExportAction(ActionEvent event) {
        if (students.isEmpty()) {
            showAlert("Export Error", "No student data to export.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Student Data to Excel"); // Use XLSX
        fileChooser.getExtensionFilters().clear(); // Clear old filters
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx")); // Add XLSX filter
        fileChooser.setInitialFileName("student_grades_export.xlsx"); // Suggest XLSX filename

        File file = fileChooser.showSaveDialog(tableView.getScene().getWindow());
        if (file != null) {
            // Use try-with-resources for Workbook and FileOutputStream
            try (Workbook workbook = new XSSFWorkbook(); // Create XSSFWorkbook for XLSX
                 FileOutputStream fileOut = new FileOutputStream(file)) {

                Sheet sheet = workbook.createSheet("Student Grades"); // Create a sheet

                // --- Create Header Row ---
                Row headerRow = sheet.createRow(0);
                String[] columns = {"ID", "First Name", "Last Name", "Grade"};

                Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                CellStyle headerCellStyle = workbook.createCellStyle();
                headerCellStyle.setFont(headerFont);

                for (int i = 0; i < columns.length; i++) {
                    // Use fully qualified name for POI Cell
                    org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columns[i]);
                    cell.setCellStyle(headerCellStyle); // Apply bold style
                }

                // --- Populate Data Rows ---
                int rowNum = 1;
                for (Student student : students) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(student.getId());
                    row.createCell(1).setCellValue(student.getFirstName());
                    row.createCell(2).setCellValue(student.getLastName());
                    // Create cell specifically for number formatting if needed
                    org.apache.poi.ss.usermodel.Cell gradeCell = row.createCell(3);
                    gradeCell.setCellValue(student.getGrade());
                    // Optional: Apply a number format (e.g., one decimal place)
                    // CellStyle gradeStyle = workbook.createCellStyle();
                    // gradeStyle.setDataFormat(workbook.createDataFormat().getFormat("0.0"));
                    // gradeCell.setCellStyle(gradeStyle);
                }

                // --- Auto-size columns ---
                for (int i = 0; i < columns.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                // --- Write the workbook to the file ---
                workbook.write(fileOut);

                showAlert("Export Successful", "Exported " + students.size() + " students to:\n" + file.getAbsolutePath());

            } catch (IOException e) {
                showAlert("Export Error", "Error writing Excel file: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) { // Catch other potential POI errors
                showAlert("Export Error", "An unexpected error occurred during export: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // --- Utility Methods (Unchanged) ---
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (title.toLowerCase().contains("error")) {
            alert.setAlertType(Alert.AlertType.ERROR);
        } else if (title.toLowerCase().contains("confirm")) {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
        } else if (title.toLowerCase().contains("warn")) {
            alert.setAlertType(Alert.AlertType.WARNING);
        }
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleExitAction(ActionEvent event) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Confirm Exit");
        confirmation.setHeaderText("Exit Application?");
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                Stage stage = (Stage) tableView.getScene().getWindow();
                stage.close();
            }
        });
    }

    @FXML
    private void handleAboutAction(ActionEvent event) {
        showAlert("About Student Grade Analyzer", "Student Grade Sorting and Analysis System\nVersion 1.0\n");
    }

}
<h1 align="center">Student Grade Sorting and Analysis System 📊</h1>

<p align="center">
  A modern desktop application built with JavaFX 21 for efficiently managing, sorting, analyzing, and visualizing student grade data. Features include data persistence, Excel import/export, multiple sorting algorithms, and statistical/distribution views.
</p>

## Screenshots 📸

<details>
  <summary><strong>Main View</strong> - Manage & Sort Data</summary>
  <br>
  <p align="center">
    <img src="https://raw.githubusercontent.com/OmarAfifi-CSE/StudentGradeAnalyzer/master/src/main/resources/screenshots/MainView.png" alt="Main Application View" width="100%">
  </p>
</details>

<br> <!-- Add extra vertical space -->

<details>
  <summary><strong>Statistics View</strong> - Key Metrics</summary>
  <br>
  <p align="center">
     <img src="https://raw.githubusercontent.com/OmarAfifi-CSE/StudentGradeAnalyzer/master/src/main/resources/screenshots/StatisticsView.png" alt="Statistics Window" width="100%">
  </p>
</details>

<br> <!-- Add extra vertical space -->

<details>
  <summary><strong>Distribution View</strong> - Charts & Percentages</summary>
  <br>
  <p align="center">
    <img src="https://raw.githubusercontent.com/OmarAfifi-CSE/StudentGradeAnalyzer/master/src/main/resources/screenshots/DistributionView.png" alt="Distribution Charts" width="100%">
  </p>
</details>

## Overview 🌟

This application provides a user-friendly interface to:

*   Enter and store student information (ID, First Name, Last Name, Grade).
*   View student data in a clear, sortable table.
*   Sort student records based on various criteria (ID, Name, Grade) using different efficient sorting algorithms (QuickSort, MergeSort, RadixSort).
*   Import student data from Excel (`.xlsx`) files.
*   Export the current student data to Excel (`.xlsx`) files.
*   Calculate and display key statistical metrics for the grades.
*   Visualize the grade distribution using bar charts.
*   Persist data between sessions using a local JSON file.

## Key Features ✨

*   **✍️ Data Entry & Management:**
    *   Easily **add** new students with unique IDs, names, and grades (validated 0-100).
    *   Quickly **remove** selected students via the table.
    *   Built-in **input validation** ensures data integrity.

*   **📊 Data Display:**
    *   Presents student data in a clean, readable **TableView**.
    *   Columns include ID, First Name, Last Name, and Grade.

*   **🚀 Efficient Sorting:**
    *   Sort data instantly by **ID**, **Name**, or **Grade**.
    *   Compare algorithm performance by choosing between:
        *   `QuickSort`
        *   `MergeSort`
        *   `RadixSort` (Optimized for numeric ID & Grade)
    *   See the **sort execution time** displayed after each operation.

*   **🔄 Import/Export (Excel):**
    *   Import student lists directly from `.xlsx` files (ID, FirstName, LastName, Grade expected).
    *   Choose to **replace** existing data or **append** new records (duplicate IDs are skipped on append).
    *   Export the current student table to a new `.xlsx` file with a single click.

*   **📈 Analysis & Visualization:**
    *   **Statistics View:** Get immediate insights with calculated metrics:
        *   Mean, Median, Mode
        *   Highest & Lowest Grades
        *   Standard Deviation & Range
        *   Total Student Count
    *   **Distribution View:** Visualize class performance:
        *   Bar chart showing counts for grade categories (A, B, C, D, F).
        *   Clearly displayed percentages for each grade category.
        *   Detailed score histogram chart (0-9, 10-19, ..., 90-100).

*   **💾 Data Persistence:**
    *   Your data is **automatically saved** to `students.json` when you close the app.
    *   Data is **automatically loaded** when you start the app again – no manual saving needed!

## Technology Stack 💻

*   **Language:** Java (Requires JDK 21 or later to build)
*   **Framework:** JavaFX 21 (via OpenJFX) for the graphical user interface.
*   **Build Tool:** Apache Maven
*   **IDE Used:** [![IntelliJ IDEA](https://img.shields.io/badge/-IntelliJ_IDEA-F52755?logo=IntelliJ_IDEA&logoColor=white)](https://www.jetbrains.com/idea/) (Recommended)
*   **Libraries:**
    *   **Jackson Databind:** For saving/loading student data to/from JSON.
    *   **Apache POI (poi-ooxml):** For reading from and writing to Excel `.xlsx` files.
    *   **Apache Commons (Collections4, Compress, IO):** Dependencies for Apache POI.
    *   **XMLBeans:** Dependency for Apache POI.
*   **Packaging:** [![Launch4j](https://img.shields.io/badge/-Launch4j-307FE1?logo=Launch4j&logoColor=white)](https://launch4j.sourceforge.net/). (for `.exe` wrapper)

## Getting Started 🚀

Follow these instructions to get the application running or to build it from the source code.

### Prerequisites ‼️

*   **To Run the Application (`Student Grade Analyzer.exe`):**
    *   Windows Operating System.
    *   **Java Runtime Environment (JRE) 8** installed. Download it from here [![jre_8](https://img.shields.io/badge/-jre_8_Windows-2E8A46?logo=JRE&logoColor=white)](https://www.java.com/en/download/manual.jsp).
    *   **Java Development Kit (JDK) 21 (64-bit)** installed. Download it from here [![JDK 21](https://img.shields.io/badge/-JDK_21_Windows-EC2025?logo=JDK&logoColor=white)](https://www.oracle.com/eg/java/technologies/downloads/#java21).
*   **To Build from Source:**
    *   **JDK 21 (64-bit)** or later. Make sure JDK `bin` is on your system PATH.
    *   **Apache Maven:** Required to compile and package the project. Make sure `mvn` is executable from your command line (Maven `bin` directory added to PATH).
    *   **Launch4j:** (Optional, if you want to rebuild the `.exe` wrapper) Download from [![Launch4j](https://img.shields.io/badge/-Launch4j-307FE1?logo=Launch4j&logoColor=white)](https://launch4j.sourceforge.net/).

## Running the Application (`.exe`) 🚀

1.  **Install Prerequisites:** Before running the application, ensure you have **both** JRE 8 **AND** JDK 21 (x64) installed on your Windows machine, and that JDK 21 is correctly configured in your system `PATH`.
2.  **Download:** Get the `Student Grade Analyzer.exe` file from the [![Releases](https://img.shields.io/badge/-Releases-0969DA?logo=JDK&logoColor=white)](https://github.com/OmarAfifi-CSE/StudentGradeAnalyzer/tree/master/Releases)
3.  **Run:** Double-click the `Student Grade Analyzer.exe` file. It should use the installed JRE/JDK to launch the JavaFX application.


## IDE Setup (IntelliJ IDEA) 🛠️

Use these steps to run the project directly within IntelliJ IDEA.

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/OmarAfifi-CSE/StudentGradeAnalyzer.git
    cd StudentGradeAnalyzer
    ```
2.  **Open Project in IntelliJ:**
    *   `File` -> `Open...` -> Select the cloned project's `pom.xml` file -> `Open as Project`.
    *   Trust the project if prompted.
3.  **Configure Project SDK:**
    *   `File` -> `Project Structure...` (`Ctrl+Alt+Shift+S`).
    *   `Project Settings` -> `Project`: Set **SDK** to your installed **JDK 21**. Set `Language level` to **SDK default**.
    *   `Apply` -> `OK`.
4.  **Sync Maven Dependencies:**
    *   Open the **Maven** tool window (`View` -> `Tool Windows` -> `Maven`).
    *   Click the **"Reload All Maven Projects"** button (circling arrows). Wait for completion.
5.  **Run/Debug:**
    *   Select the configured `StudentGradeApp` run configuration and click Run (▶️) or Debug (🐞).

## Building from Source ⌨️

This method uses IntelliJ's artifact system, ideal for running within the IDE.

1.  **Prerequisites:** JDK 21 (configured in IntelliJ), JavaFX SDK 21 (know the `lib` path), Maven dependencies synced (`Maven` -> `Reload Project`).
2.  **Configure Artifact (if needed):**
    *   `File` -> `Project Structure` -> `Artifacts` -> `+` -> `JAR` -> `From modules with dependencies...`.
    *   **Module:** `StudentGradeAnalyzer` (or similar).
    *   **Main Class:** `main.StudentGradeAppPublish`.
    *   Select: `Copy to the output directory and link via manifest`.
    *   `OK` -> `Apply` -> `OK`.
3.  **Build Artifact:** `Build` -> `Build Artifacts...` -> Select your artifact -> `Build`. (Output typically in `out/artifacts/...`).

**Note:** This builds an output folder (`out/artifacts/...`) with your JAR and dependencies. It doesn't create a single executable or bundle Java. Running it outside IntelliJ requires manually setting up the `java` command with the correct VM options and classpath. For distribution, consider using `jpackage` or `Launch4j`.

## Usage 📖

1.  **Launch:** Run the `.exe` or execute the JAR (requires manual JavaFX module configuration if run directly).
2.  **View Data:** Existing student data (loaded from `students.json`) is displayed in the table.
3.  **Sort:** Select a sorting algorithm (QuickSort, MergeSort, RadixSort) and a criteria (ID, Name, Grade) from the dropdowns and click "Sort". Note that RadixSort may only be suitable for ID/Grade depending on implementation.
4.  **Add Student:** Enter ID, First Name, Last Name, and Grade in the fields at the bottom and click "Add Student".
5.  **Remove Student:** Select a row in the table and click "Remove Selected".
6.  **Analyze:** Click "View Statistics" or "View Distribution" to open separate windows with calculated metrics and charts.
7.  **Import/Export:** Use the "File" menu to import from or export to `.xlsx` files.
8.  **Exit:** Use the "File" -> "Exit" menu item or the window's close button. Data is saved automatically on exit.

## Configuration 💾

*   Persistent student data is stored in a JSON file located at:
    *   `C:\Users\[YourUsername]\.StudentGradeAnalyzer\students.json` (on Windows)
    *   `~/.StudentGradeAnalyzer/students.json` (on macOS/Linux)
*   Deleting this file will cause the application to load sample data on the next startup.

## Contact 📧

[![Email](https://img.shields.io/badge/-Email-1690DF?logo=gmail&logoColor=white)](mailto:omarafifi.cse@gmail.com)
[![WhatsApp](https://img.shields.io/badge/-WhatsApp-25D366?logo=whatsapp&logoColor=white)](https://wa.me/201154403740)
[![LinkedIn](https://img.shields.io/badge/-LinkedIn-0A66C2?logo=linkedin&logoColor=white)](https://www.linkedin.com/in/omarafifi-cse/)

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;
import java.util.HashMap;

public class AssignmentUnit7 extends Application {

    private ObservableList<Student> students = FXCollections.observableArrayList();
    private ObservableList<Course> courses = FXCollections.observableArrayList();
    

    @Override
    public void start(Stage primaryStage) {
        // create some courses pre defined courses and students for testing
        courses.addAll(new Course("Math"), new Course("Science"), new Course("History"), new Course("English"), new Course("Programming"));
        students.addAll(new Student("John Doe", 20), new Student("Jane Doe", 22), new Student("Alice", 21), new Student("Bob", 23));
        
        try {
            BorderPane root = new BorderPane();
            initializeMainView(root);
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setTitle("Student Management System");
            HomePage(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeMainView(BorderPane root) {
        MenuBar menuBar = new MenuBar();

        Menu HomeMenu = new Menu("Home");
        HomeMenu.setOnAction(e -> HomePage(root));

        Menu studentMenu = new Menu("Students");
        MenuItem addStudent = new MenuItem("Add Student");
        MenuItem updateStudent = new MenuItem("Update Student");
        MenuItem viewStudents = new MenuItem("View Students");

        addStudent.setOnAction(e -> showAddStudentForm());
        updateStudent.setOnAction(e -> showUpdateStudentForm());
        viewStudents.setOnAction(e -> showStudentList(root));


        studentMenu.getItems().addAll(addStudent, updateStudent, viewStudents);

        Menu courseMenu = new Menu("Courses");
        MenuItem enrollStudent = new MenuItem("Enroll Student");

        enrollStudent.setOnAction(e -> showEnrollStudentForm());

        courseMenu.getItems().addAll(enrollStudent);

        Menu gradeMenu = new Menu("Grades");
        MenuItem assignGrade = new MenuItem("Assign Grade");

        assignGrade.setOnAction(e -> showAssignGradeForm());

        gradeMenu.getItems().addAll(assignGrade);

        menuBar.getMenus().addAll(HomeMenu, studentMenu, courseMenu, gradeMenu);

        root.setTop(menuBar);
    }

    void HomePage(BorderPane root) {
        Label label = new Label("Welcome to Student Management System");
        root.setCenter(label);
    }

    private void showAddStudentForm() {
        Stage stage = new Stage();
        GridPane gridPane = new GridPane();
        TextField nameField = new TextField();
        TextField ageField = new TextField();
        Button addButton = new Button("Add Student");

        addButton.setOnAction(e -> {
            String name = nameField.getText();
            try {
                int age = Integer.parseInt(ageField.getText());
                Student student = new Student(name, age);
                students.add(student);
                stage.close();
            } catch (NumberFormatException ex) {
                showError("Please enter a valid age.");
            }
        });

        gridPane.add(new Label("Name:"), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Label("Age:"), 0, 1);
        gridPane.add(ageField, 1, 1);
        gridPane.add(addButton, 1, 2);

        Scene scene = new Scene(gridPane, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Add Student");
        stage.show();
    }

    private void showUpdateStudentForm() {
        Stage stage = new Stage();
        GridPane gridPane = new GridPane();
        TextField nameField = new TextField();
        TextField ageField = new TextField();
        Button updateButton = new Button("Update Student");
        ComboBox<Student> studentDropdown = new ComboBox<>(students);
        studentDropdown.setOnAction(e -> {
            Student selectedStudent = studentDropdown.getValue();
            if (selectedStudent != null) {
                nameField.setText(selectedStudent.getName());
                ageField.setText(String.valueOf(selectedStudent.getAge()));
            }
        });
        gridPane.add(new Label("Select Student: "), 0, 2);
        gridPane.add(studentDropdown, 1, 2);
        gridPane.add(new Label("Name:"), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Label("Age:"), 0, 1);
        gridPane.add(ageField, 1, 1);
        gridPane.add(updateButton, 1, 3);

        updateButton.setOnAction(e -> {
            Student selectedStudent = studentDropdown.getValue();
            if (selectedStudent != null) {
                String name = nameField.getText();
                try {
                    int age = Integer.parseInt(ageField.getText());
                    selectedStudent.updateStudent(name, age);
                    stage.close();
                } catch (NumberFormatException ex) {
                    showError("Please enter a valid age.");
                }
            } else {
                showError("Please select a student to update.");
            }
        });
        Scene scene = new Scene(gridPane, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Update Student");
        stage.show();
    }

    private void showStudentList(BorderPane root) {
        TableView<Student> tableView = new TableView<>(students);
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(ageColumn);

        root.setCenter(tableView);
    }

    private void showEnrollStudentForm() {
        Stage stage = new Stage();
        GridPane gridPane = new GridPane();
        ComboBox<Student> studentDropdown = new ComboBox<>();
        ComboBox<Course> courseDropdown = new ComboBox<>(courses);
        Button enrollButton = new Button("Enroll Student");

        gridPane.add(new Label("Select Course: "), 0, 0);
        gridPane.add(courseDropdown, 1, 0);
        courseDropdown.setOnAction(e -> {
            studentDropdown.getItems().clear();
            Course selectedCourse = courseDropdown.getValue();
            if (selectedCourse != null) {
                for (Student student : students) {
                    if (!student.getCourses().contains(selectedCourse)) {
                        studentDropdown.getItems().add(student);
                    }
                }
            }
        });
        gridPane.add(new Label("Select Student: "), 0, 1);
        gridPane.add(studentDropdown, 1, 1);
        gridPane.add(enrollButton, 1, 2);
        enrollButton.setOnAction(e -> {
            Student student = studentDropdown.getValue();
            Course course = courseDropdown.getValue();
            if (student != null && course != null) {
                student.enrollCourse(course);
                stage.close();
            } else {
                showError("Please select a student and a course.");
            }
        });

        Scene scene = new Scene(gridPane, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Enroll Student");
        stage.show();
    }

    private void showAssignGradeForm() {
        Stage stage = new Stage();
        GridPane gridPane = new GridPane();
        ComboBox<Student> studentDropdown = new ComboBox<>(students);
        ComboBox<Course> courseDropdown = new ComboBox<>();
        TextField gradeField = new TextField();
        Button assignButton = new Button("Assign Grade");
        TableView<Grade> gradeTable = new TableView<>();
        TableColumn<Grade, String> courseColumn = new TableColumn<>("Course");
        TableColumn<Grade, Integer> gradeColumn = new TableColumn<>("Grade");
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        gradeTable.getColumns().addAll(courseColumn, gradeColumn);
        gridPane.add(new Label("Select Student: "), 0, 0);
        gridPane.add(studentDropdown, 1, 0);
        studentDropdown.setOnAction(e -> {
            courseDropdown.getItems().clear();
            Student selectedStudent = studentDropdown.getValue();
            if (selectedStudent != null) {
                for (Course course : selectedStudent.getCourses()) {
                    courseDropdown.getItems().add(course);
                    gradeTable.getItems().add(new Grade(course, selectedStudent.getGrade(course)));
                }
            }
        });
        gridPane.add(gradeTable, 1, 4, 1, 1);
        gridPane.add(new Label("Select Course: "), 0, 1);
        gridPane.add(courseDropdown, 1, 1);
        gridPane.add(new Label("Grade: "), 0, 2);
        gridPane.add(gradeField, 1, 2);
        gridPane.add(assignButton, 1, 3);
        assignButton.setOnAction(e -> {
            Student student = studentDropdown.getValue();
            Course course = courseDropdown.getValue();
            if (student != null && course != null) {
                try {
                    int grade = Integer.parseInt(gradeField.getText());
                    student.assignGrade(course, grade);
                    stage.close();
                } catch (NumberFormatException ex) {
                    showError("Please enter a valid grade.");
                }
            } else {
                showError("Please select a student and a course.");
            }
        });
        stage.setScene(new Scene(gridPane, 300, 400));
        stage.setTitle("Assign Grade");
        stage.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class Student {
        private String name;
        private int age;
        private ArrayList <Course> courses;
        private HashMap<Course, Integer> grades;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
            this.courses = new ArrayList<>();
            this.grades = new HashMap<>();
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public void updateStudent(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void enrollCourse(Course course) {
            courses.add(course);
        }
        public void assignGrade(Course course, int grade) {
            grades.put(course, grade);
        }
        public int getGrade(Course course) {
            return grades.get(course) != null ? grades.get(course) : 0;
        }
        public ArrayList<Course> getCourses() {
            return courses;
        }
        public String getCourseName(Course course) {
            return course.getCourseName();
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class Course {
        private String courseName;

        public Course(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseName() {
            return courseName;
        }
        @Override
        public String toString() {
            return courseName;
        }
    }
    public static class Grade {
        private Course course;
        private int grade;

        public Grade(Course course, int grade) {
            this.course = course;
            this.grade = grade;
        }

        public String getCourse() {
            return course.getCourseName();
        }

        public int getGrade() {
            return grade;
        }
    }
}

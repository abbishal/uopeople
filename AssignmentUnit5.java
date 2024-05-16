import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AssignmentUnit5 {
    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Administrator Interface");
            System.out.println("Total Courses: " + CourseManagement.getCourses().size());
            System.out.println("Total Students: " + CourseManagement.getStudents().size());
            System.out.println("1. Add Course");
            System.out.println("2. Enroll Student");
            System.out.println("3. Assign Grade");
            System.out.println("4. Calculate Overall Grade");
            System.out.println("5. Exit");
            int choice = Inputs.getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    enrollStudent();
                    break;
                case 3:
                    assignGrade();
                    break;
                case 4:
                    calculateOverallGrade();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.print("\033[H\033[2J");
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void addCourse() {
        String courseCode = Inputs.getStringInput("Enter course code: ");
        String courseName = Inputs.getStringInput("Enter course name: ");
        int maxCapacity = Inputs.getIntInput("Enter max capacity: ");
        CourseManagement.addCourse(courseCode, courseName, maxCapacity);
        System.out.print("\033[H\033[2J");
        System.out.println("Course added successfully.");
    }

    public static void enrollStudent() {
        String name = Inputs.getStringInput("Enter student name: ");
        int id = Inputs.getIntInput("Enter student ID: ");
        Student student = findStudent(id);
        if (student == null) {
            student = new Student(name, id);
        }

        // print all courses with code and name
        for (Course course : CourseManagement.getCourses()) {
            System.out.println(course.getCourseCode() + " - " + course.getCourseName());
        }
        String courseCode = Inputs.getStringInput("Enter course code to enroll: ");

        Course course = findCourse(courseCode);
        System.out.print("\033[H\033[2J");
        if (course != null) {
            CourseManagement.enrollStudent(student, course);
            System.out.println("Student enrolled successfully.");
        } else {
            System.out.println("Course not found.");
        }
    }

    public static void assignGrade() {
        // print all students with id
        for (Student student : CourseManagement.getStudents()) {
            System.out.println(student.getId() + " - " + student.getName());
        }
        int id = Inputs.getIntInput("Enter student ID: ");
        Student student = findStudent(id);
        if (student != null) {
            // print all courses with code
            for (Course course : CourseManagement.getCourses()) {
                System.out.println(course.getCourseCode() + " - " + course.getCourseName());
            }
            String courseCode = Inputs.getStringInput("Enter course code: ");

            Course course = findCourse(courseCode);
            if (course != null) {
                int grade = Inputs.getIntInput("Enter grade(0-100): ");
                while (grade < 0 || grade > 100) {
                    System.out.println("Invalid grade. Please enter a grade between 0 and 100.");
                    grade = Inputs.getIntInput("Enter grade(0-100): ");                  
                }
                CourseManagement.assignGrade(student, course, grade);
                System.out.print("\033[H\033[2J");
                System.out.println("Grade assigned successfully.");
            } else {
                System.out.print("\033[H\033[2J");
                System.out.println("Course not found.");
            }
        } else {
            System.out.print("\033[H\033[2J");
            System.out.println("Student not found.");
        }
    }

    public static void calculateOverallGrade() {
        
        // print all students with id
        for (Student student : CourseManagement.getStudents()) {
            System.out.println(student.getId() + " - " + student.getName());
        }
        int id = Inputs.getIntInput("Enter student ID: ");

        Student student = findStudent(id);
        if (student != null) {
            CourseManagement.calculateOverallGrade(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    public static Course findCourse(String courseCode) {
        for (Course course : CourseManagement.getCourses()) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    public static Student findStudent(int id) {
        for (Student student : CourseManagement.getStudents()) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }
}

class Student {
    private String name;
    private int id;
    private ArrayList<String> enrolledCourses;
    private HashMap<Course, Integer> grades;
    private static int totalEnrolledStudents = 0;

    // Constructor
    public Student(String name, int id) {
        this.name = name;
        this.id = id;
        this.enrolledCourses = new ArrayList<>();
        this.grades = new HashMap<>();
        totalEnrolledStudents++;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    public int getTotalEnrolledStudents() {
        return totalEnrolledStudents;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }



    public void enrollCourse(Course course) {
        enrolledCourses.add(course.getCourseCode());
    }

    public void assignGrade(Course course, Integer grade) {
        if (enrolledCourses.contains(course.getCourseCode())) {
            grades.put(course, grade);
        }
    }
}

class Course {
    private String courseCode;
    private String courseName;
    private int maxCapacity;
    private static int totalEnrolledStudents = 0;

    // Constructor
    public Course(String courseCode, String courseName, int maxCapacity) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.maxCapacity = maxCapacity;
    }

    // Getters
    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    // Static method to retrieve the total number of enrolled students
    public static int getTotalEnrolledStudents() {
        return totalEnrolledStudents;
    }
}

class CourseManagement {
    private static ArrayList<Course> courses = new ArrayList<>();
    private static ArrayList<Student> students = new ArrayList<>();
    private static HashMap<Student, HashMap<Course, Integer>> courseGrades = new HashMap<>();

    public static void addCourse(String courseCode, String courseName, int maxCapacity) {
        Course course = new Course(courseCode, courseName, maxCapacity);
        courses.add(course);
    }

    public static void enrollStudent(Student student, Course course) {
        students.add(student);
        student.enrollCourse(course);
    }

    public static void assignGrade(Student student, Course course, Integer grade) {
        student.assignGrade(course, grade);
        HashMap<Course, Integer> grades = courseGrades.get(student);
        if (grades == null) {
            grades = new HashMap<>();
        }
        grades.put(course, grade);
        courseGrades.put(student, grades);
    }

    public static void calculateOverallGrade(Student student) {
        HashMap<Course, Integer> grades = courseGrades.get(student);
        if (grades == null) {
            System.out.println("No grades found for student " + student.getName());
            return;
        }
        int total = 0;
        for (Integer grade : grades.values()) {
            total += grade;
        }
        System.out.println("Overall grade for student " + student.getName() + " is " + total / grades.size());
    }
    public static ArrayList<Course> getCourses() {
        return courses;
    }
    public static ArrayList<Student> getStudents() {
        return students;
    }
}

class Inputs {
    public static int getIntInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        int input = 0;
        try {
            input = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter an integer.");
            Inputs.getIntInput(prompt);
        }
        return input;
    }

    public static String getStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        String input = scanner.nextLine();
        while (input.isEmpty()) {
            System.out.println("Invalid input. Please enter a string.");
            System.out.print(prompt);
            input = scanner.nextLine();
        }
        return input;
    }
}

import java.sql.*;
import java.util.Scanner;

public class Main {
    private Connection connection;
    private Scanner scanner = new Scanner(System.in);

    public Main() {
        try {
            // Connect to SQLite database (it will create a new one if it doesn't exist)
            connection = DriverManager.getConnection("jdbc:sqlite:students.db");

            // Create the 'students' table if it does not exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS students ("
                    + "id TEXT PRIMARY KEY, "
                    + "name TEXT NOT NULL, "
                    + "age INTEGER NOT NULL, "
                    + "course TEXT NOT NULL);";
            Statement stmt = connection.createStatement();
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    public void addStudent() {
        try {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            System.out.print("Enter age: ");
            int age = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            System.out.print("Enter student ID: ");
            String studentId = scanner.nextLine();

            System.out.print("Enter course: ");
            String course = scanner.nextLine();

            // Insert the student into the database
            String insertSQL = "INSERT INTO students (id, name, age, course) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(insertSQL);
            pstmt.setString(1, studentId);
            pstmt.setString(2, name);
            pstmt.setInt(3, age);
            pstmt.setString(4, course);

            pstmt.executeUpdate();
            System.out.println("Student added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    public void viewStudents() {
        try {
            String query = "SELECT * FROM students";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (!rs.isBeforeFirst()) {
                System.out.println("No students available.");
                return;
            }

            System.out.println("Student List:");
            while (rs.next()) {
                System.out.println("Student ID: " + rs.getString("id") + ", Name: " + rs.getString("name") +
                        ", Age: " + rs.getInt("age") + ", Course: " + rs.getString("course"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving students: " + e.getMessage());
        }
    }

    public void updateStudent() {
        try {
            System.out.print("Enter student ID to update: ");
            String studentId = scanner.nextLine();

            String checkSQL = "SELECT * FROM students WHERE id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkSQL);
            checkStmt.setString(1, studentId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Student not found!");
                return;
            }

            // Proceed with updating the student
            System.out.print("Enter new name: ");
            String newName = scanner.nextLine();

            System.out.print("Enter new age: ");
            int newAge = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            System.out.print("Enter new course: ");
            String newCourse = scanner.nextLine();

            String updateSQL = "UPDATE students SET name = ?, age = ?, course = ? WHERE id = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateSQL);
            updateStmt.setString(1, newName);
            updateStmt.setInt(2, newAge);
            updateStmt.setString(3, newCourse);
            updateStmt.setString(4, studentId);

            updateStmt.executeUpdate();
            System.out.println("Student updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    public void deleteStudent() {
        try {
            System.out.print("Enter student ID to delete: ");
            String studentId = scanner.nextLine();

            String deleteSQL = "DELETE FROM students WHERE id = ?";
            PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL);
            deleteStmt.setString(1, studentId);

            int rowsAffected = deleteStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student removed successfully!");
            } else {
                System.out.println("Student not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }

    // Main method to test CRUD operations
    public static void main(String[] args) {
        Main manager = new Main();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nStudent Management System:");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manager.addStudent();
                    break;
                case 2:
                    manager.viewStudents();
                    break;
                case 3:
                    manager.updateStudent();
                    break;
                case 4:
                    manager.deleteStudent();
                    break;
                case 5:
                    System.out.println("Exiting system...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        scanner.close();
    }
}

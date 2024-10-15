// src/Student.java
public class Student extends Person {
    private String studentId;
    private String course;

    // Constructor
    public Student(String name, int age, String studentId, String course) {
        super(name, age);  // Call Person's constructor
        this.studentId = studentId;
        this.course = course;
    }

    // Getter and Setter for Student ID
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    // Getter and Setter for Course
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    // To display Student info
    @Override
    public String toString() {
        return super.toString() + ", Student ID: " + studentId + ", Course: " + course;
    }
}

public class Student {
    private String studentID;
    private String studentName;
    private int[] moduleMarks;
    private String grade;

    public Student(String studentID, String studentName) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.moduleMarks = new int[3]; // Assuming 3 modules
        this.grade = "N/A"; // Default grade
    }

    public String getStudentID() {
        return studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int[] getModuleMarks() {
        return moduleMarks;
    }

    public void setModuleMarks(int[] moduleMarks) {
        this.moduleMarks = moduleMarks;
        this.grade = calculateGrade(moduleMarks);
    }

    public String getGrade() {
        return grade;
    }

    public double calculateAverage(int[] marks) {
        int total = 0;
        for (int mark : marks) {
            total += mark;
        }
        return total / 3.0;
    }

    private String calculateGrade(int[] marks) {
        double average = calculateAverage(marks);
        if (average >= 80) {
            return "Distinction";
        } else if (average >= 70) {
            return "Merit";
        } else if (average >= 40) {
            return "Pass";
        } else {
            return "Fail";
        }
    }
}

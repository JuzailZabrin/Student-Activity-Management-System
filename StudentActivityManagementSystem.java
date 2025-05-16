import java.io.*; // Importing classes for file I/O operations
import java.util.*; // Importing utility classes like Scanner and Arrays

public class StudentActivityManagementSystem {
    private static final int MAXIMUM_CAPACITY = 100; // Maximum number of students
    private static Student[] students = new Student[MAXIMUM_CAPACITY]; // Array to hold student objects
    private static int studentCount = 0; // Counter to keep track of the number of students

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) { // Infinite loop to keep the program running until the user decides to exit from the program
            printMenu(); // Display the menu options
            int choice = getValidChoice(scanner); // Get a valid menu choice from the user

            switch (choice) {
                case 1 -> checkAvailableSeats(); 
                case 2 -> registerStudent(scanner); 
                case 3 -> deleteStudent(scanner); 
                case 4 -> findStudent(scanner); 
                case 5 -> storeStudentDetails(); 
                case 6 -> loadStudentDetails(); 
                case 7 -> viewStudentsByName(); 
                case 8 -> manageStudentResults(scanner); // Manage student results
                case 9 -> {
                    System.out.println("Exiting the program...........");
                    return; // Exit the program
                }
                default -> System.out.println("Invalid choice. Please try again."); // Handling invalid choice
            }
        }
    }

    private static int getValidChoice(Scanner scanner) {
        int choice;
        while (true) {
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) { // Check if the input is an integer
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 9) { // Check if the choice is within the valid range
                    scanner.nextLine(); // Consume newline
                    break;
                }
            } else {
                scanner.nextLine(); // Consume invalid input
            }
            System.out.println("Invalid choice. Please enter a number between 1 and 9.");
        }
        return choice; // Return the valid choice
    }

    private static void printMenu() {
        System.out.println("******************************************");
        System.out.println("*              MENU OPTION                *");
        System.out.println("*******************************************");
        System.out.println("1. Check available seats");
        System.out.println("2. Register student (with ID)");
        System.out.println("3. Delete student");
        System.out.println("4. Find student (with student ID)");
        System.out.println("5. Store student details into a file");
        System.out.println("6. Load student details from the file to the system");
        System.out.println("7. View the list of students based on their names");
        System.out.println("8. Manage student results");
        System.out.println("9. Exit");
    }

    private static void checkAvailableSeats() {
        System.out.println("Available seats: " + (MAXIMUM_CAPACITY - studentCount));
    }

    private static void registerStudent(Scanner scanner) { // check if the number of students has reached the maximum capacity
        if (studentCount >= MAXIMUM_CAPACITY) {
            System.out.println("No available seats.");
            return;
        }

        String id;
        while (true) { //prompt the user to enter a Student ID
            System.out.print("Enter Student ID (e.g., w2083187): ");
            id = scanner.nextLine(); // check if the entered ID matches the required pattern
            if (id.matches("^[a-zA-Z][a-zA-Z0-9]{7}$")) {  //If the ID is valif, exit from the loop
                break;
            } else { // If the ID is invalid, infrom the user and continue the loop
                System.out.println("Invalid Student ID. It must start with a letter, contain only letters and digits, and be exactly 8 characters long.");
            }
        }

        String name;
        while (true) {
            System.out.print("Enter Student Name: ");
            name = scanner.nextLine(); 
            if (name.matches("^[a-zA-Z ]+$")) { // check if the name contains only letters and spaces
                break;
            } else {
                System.out.println("Invalid Student Name. It must contain only letters and spaces.");
            }
        }

        students[studentCount++] = new Student(id, name); // Add the new student to the array
        System.out.println("Student registered successfully.");
    }

    private static void deleteStudent(Scanner scanner) {
        System.out.print("Enter Student ID to delete: ");
        String id = scanner.nextLine();
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentID().equals(id)) {
                students[i] = students[--studentCount]; // Replace the student to be deleted with the last student in the array
                students[studentCount] = null; // Nullify the last student position
                System.out.println("Student deleted successfully.");
                return;
            }
        }
        System.out.println("Student not found. Check the Student ID and try again.");
    }

    private static void findStudent(Scanner scanner) {
        System.out.print("Enter Student ID to find: ");
        String id = scanner.nextLine();
        for (Student student : students) { // Find the correct ID by continuing over the student array.
            if (student != null && student.getStudentID().equals(id)) { // Verify that the ID matches the input and that the student object is not null.

                 // Print the student's details if a match is found
                System.out.println("ID: " + student.getStudentID() + ", Name: " + student.getStudentName() + 
                    ", Marks: " + Arrays.toString(student.getModuleMarks()) + ", Grade: " + student.getGrade()); 
                return; // Exit the method after finding the student
            }
        }     
        // If no matching student is found, inform the user
        System.out.println("Student not found. Check the Student ID and try again.");
    }

    private static void storeStudentDetails() {
        // Try-with-resources statement: This will cause the writer to close automatically.
        try (PrintWriter writer = new PrintWriter(new FileWriter("students.txt"))) {
            writer.println(studentCount);       // Write the total count of students as the first line in the file

            for (int i = 0; i < studentCount; i++) {      
            // Format and write the student's ID, name, and module marks to the file
                writer.println(students[i].getStudentID() + "," + students[i].getStudentName() + "," + 
                    Arrays.toString(students[i].getModuleMarks()).replaceAll("[\\[\\]]", ""));
            }
            System.out.println("Student details stored successfully.");
        } catch (IOException e) { // Print an error message if an IOException occurs
            System.out.println("Error storing student details: " + e.getMessage());
        }
    }

    private static void loadStudentDetails() {
        // Use a try-with-resources statement to automatically close the reader
        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            // Read and parse the total count of students from the first line
            studentCount = Integer.parseInt(reader.readLine().trim());
            
            // Loop through each student and read their details from the file
            for (int i = 0; i < studentCount; i++) {
                // Split the line into individual data fields
                String[] studentData = reader.readLine().split(",");
                String id = studentData[0].trim();
                String name = studentData[1].trim();
                int[] marks = Arrays.stream(studentData, 2, 5) // Assume there are exactly 3 marks
                                    .map(String::trim)
                                    .mapToInt(Integer::parseInt)
                                    .toArray();
                
                // Create a new Student object and set its details
                students[i] = new Student(id, name);
                students[i].setModuleMarks(marks);
            }
            
            // Print a success message after loading the details
            System.out.println("Student details loaded successfully.");
        } catch (IOException e) {
            // Print an error message if an IOException occurs
            System.out.println("Error loading student details: " + e.getMessage());
        }
    }
    

    private static void viewStudentsByName() {
        // Sort the array of students by their names by ascending order
        Arrays.sort(students, 0, studentCount, Comparator.comparing(Student::getStudentName));
        for (int i = 0; i < studentCount; i++) { // loop through the stored array and print the details of each student
            // print student details in the specified format
            System.out.println("ID: " + students[i].getStudentID() + ", Name: " + students[i].getStudentName() + 
                ", Marks: " + Arrays.toString(students[i].getModuleMarks()) + ", Grade: " + students[i].getGrade());
        }
    }

    private static void manageStudentResults(Scanner scanner) {
        while (true) {
            System.out.println("a. Add student name");
            System.out.println("b. Add module marks");
            System.out.println("c. Generate summary report");
            System.out.println("d. Generate complete report");
            System.out.println("e. Go back to main menu");
            char choice = getValidSubChoice(scanner); // get a valid menu choice from the user (a - e)

            switch (choice) { //handle the menu choice using switch statement
                case 'a' -> addStudentName(scanner);
                case 'b' -> addModuleMarks(scanner);
                case 'c' -> generateSummaryReport();
                case 'd' -> generateCompleteReport();
                case 'e' -> {
                    return;
                } 
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static char getValidSubChoice(Scanner scanner) {
        char choice;
        while (true) {
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine(); // read the input as a string 
            if (input.length() == 1) { // check if the input is single character
                choice = input.charAt(0);
                if (choice >= 'a' && choice <= 'e') { // check if the character is within the valid rang (a-e) 
                    break; // valid choice found, exit the loop
                }
            }
            System.out.println("Invalid choice. Please enter a letter between 'a' and 'e'.");
        }
        return choice; // return the valid choice 
    }

    private static void addStudentName(Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine(); // Go through and remove any useless spaces.
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        if (id.isEmpty() || name.isEmpty()) { // check if the students ID or name is empty
            System.out.println("Student ID and Name cannot be empty.");
            return;
        }
        for (Student student : students) {     // Iterate over the list of students to find the matching ID 
            if (student != null && student.getStudentID().equals(id)) { // update the students name if the ID is founf
                student.setStudentName(name);
                System.out.println("Student name updated successfully.");
                return;
            }
        } // if the students ID is not found, display an error message
        System.out.println("Student not found. Check the Student ID and try again.");
    }

    private static void addModuleMarks(Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        for (Student student : students) {
            if (student != null && student.getStudentID().equals(id)) {
                int[] marks = new int[3]; // Array to store the marks for there modules
                marks[0] = getValidMark(scanner, "Enter Module 1 Marks: ");
                marks[1] = getValidMark(scanner, "Enter Module 2 Marks: ");
                marks[2] = getValidMark(scanner, "Enter Module 3 Marks: ");
                student.setModuleMarks(marks); // update the students module marks
                System.out.println("Module marks updated successfully.");
                return;
            }
        }
        System.out.println("Student not found. Check the Student ID and try again.");
    }

    private static int getValidMark(Scanner scanner, String prompt) {
        int mark;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {  // cehck if the next input is an integer
                mark = scanner.nextInt();
                if (mark >= 0 && mark <= 100) { // validate the integr is within the range 0 to 100
                    scanner.nextLine(); // Consume the newline
                    break;
                }
            } else {
                scanner.nextLine(); // Consume invalid input if not an integer
            }
            System.out.println("Invalid mark. Please enter a number between 0 and 100.");
        }
        return mark;
    }

    private static void generateSummaryReport() {
        if (studentCount == 0) { // cehck if there are any students to display
            System.out.println("No students to display.");
            return;
        }

        int totalStudents = studentCount;
        int studentsPassedModule1 = 0;
        int studentsPassedModule2 = 0;
        int studentsPassedModule3 = 0;

        // Iterate through the students array to count the number of students who passed each module
        for (Student student : students) {
            if (student != null) {
                int[] marks = student.getModuleMarks();
                if (marks[0] >= 40) studentsPassedModule1++;
                if (marks[1] >= 40) studentsPassedModule2++;
                if (marks[2] >= 40) studentsPassedModule3++;
            }
        }

        System.out.println("Total Students: " + totalStudents);
        System.out.println("Students Passed Module 1: " + studentsPassedModule1);
        System.out.println("Students Passed Module 2: " + studentsPassedModule2);
        System.out.println("Students Passed Module 3: " + studentsPassedModule3);
    }

    private static void generateCompleteReport() {
        if (studentCount == 0) {
            System.out.println("No students to display.");
            return;
        }

        Student[] sortedStudents = new Student[studentCount];
        System.arraycopy(students, 0, sortedStudents, 0, studentCount);

        // Bubble sort based on average marks
        for (int i = 0; i < sortedStudents.length - 1; i++) {
            for (int j = 0; j < sortedStudents.length - i - 1; j++) {
                double avg1 = calculateAverage(sortedStudents[j].getModuleMarks());
                double avg2 = calculateAverage(sortedStudents[j + 1].getModuleMarks());
                if (avg1 < avg2) {
                    Student temp = sortedStudents[j];
                    sortedStudents[j] = sortedStudents[j + 1];
                    sortedStudents[j + 1] = temp;
                }
            }
        }

        for (Student student : sortedStudents) { //retrieve the module marks fir the current students 
            int[] marks = student.getModuleMarks();
            double average = calculateAverage(marks); //Calculates the average marks
            // Print the students details including ID, name , induvidual module marks, total, average and grade
            System.out.println("ID: " + student.getStudentID() + ", Name: " + student.getStudentName() +
                    ", Module 1: " + marks[0] + ", Module 2: " + marks[1] + ", Module 3: " + marks[2] +
                    ", Total: " + (marks[0] + marks[1] + marks[2]) + ", Average: " + average + ", Grade: " + student.getGrade());
        }
    }

    private static double calculateAverage(int[] marks) {
        int total = 0; //Lopp through each mark in the array and add it to the total
        for (int mark : marks) {
            total += mark;
        } //calculate and return the average, dividing by 3.0 to ensure the result is a double
        return total / 3.0;
    }
}

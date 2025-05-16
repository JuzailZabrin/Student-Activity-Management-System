import java.io.Serializable;

public class Module implements Serializable {
    private String moduleName;
    private int marks;

    public Module(String moduleName, int marks) {
        this.moduleName = moduleName;
        this.marks = marks;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Module Name: " + moduleName + ", Marks: " + marks;
    }
}

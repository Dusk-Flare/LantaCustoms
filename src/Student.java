public class Student {
    private double average;
    private String name;
    private final int studentID;
    public Student(int ID, String name, double average){
        this.studentID = ID;
        this.name = name;
        this.average = average;
    }

    public void submitGrades(double grade1, double grade2, double grade3){
        average = (grade1 + grade2 + grade3)/3;
    }

    public void changeName(String name){
        this.name = name;
    }

    public boolean isStudentByID(int ID){
        return this.studentID == ID;
    }

    public String getName(){
        return this.name;
    }

    public double getAverage(){
        return this.average;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return super.equals(o);
        Student student = (Student) o;
        return studentID == student.studentID;
    }

    @Override
    public String toString(){
        return this.studentID+" | "+this.name+" | "+this.average;
    };
}

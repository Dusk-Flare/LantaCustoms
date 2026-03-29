import lanta.lists.CustomLinkedList;

public class Main {
    public static void main(String[] args) {
        CustomLinkedList<Integer> list1 = new CustomLinkedList<>();
        CustomLinkedList<Student> list2 = new CustomLinkedList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(1);
        list1.add(7);
        list1.print();
        list1.search(1).print();
        list1.deleteAllOf(2);
        list1.print();

        list2.add(new Student(3, "mike", 9));
        list2.add(new Student(3, "mike", 9));
        list2.add(new Student(1, "jake", 7));
        list2.add(new Student(2, "luke", 5));
        list2.add(new Student(3, "mike", 9));
        list2.add(new Student(3, "mike", 9));
        list2.add(new Student(4, "joke", 1));
        list2.add(new Student(5, "lisa", 7));
        list2.compareSearch((student) -> student.getAverage() > 6).print();
        list2.print();
        list2.compareDeleteAllOf(student -> student.isStudentByID(3));
        list2.print();

        for(Student st : list2){
            System.out.println(st);
        }
    }
}
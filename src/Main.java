import lanta.lists.CustomLinkedList;
import lanta.utils.MenuConstructor;

import java.util.Scanner;

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

        Scanner scanner = new Scanner(System.in);
        MenuConstructor menu = new MenuConstructor("Exit", "Say hello world", "Create a student");
        boolean wawa = true;
        while (wawa) {
            switch (menu.getOption(scanner)) {
                case 0:
                    wawa = false;
                    break;
                case 1:
                    System.out.println("Hello world");
                    break;
                case 2:
                    Student student = new Student(1, "mike", 9);
                    break;
            }
        }
    }
}
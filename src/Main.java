import lanta.lists.DoublyLinkedList;

public class Main {
    public static void main(String[] args) {
        DoublyLinkedList<Double> list = new DoublyLinkedList<>();
        System.out.println("Adding");
        list.append(2.0);
        list.append(3.0);
        list.append(4.0);
        list.add(0.0);
        list.print();
        System.out.println("Removing");
        list.add(3.0);
        list.print();
        list.remove(3.0);
        list.print();
        System.out.println("Iterating");
        for(double data : list){
            System.out.println(data);
        }
        System.out.println("Iterating in reverse");
        for (double data : list.reverse()){
            System.out.println(data);
        }
        System.out.println("Reversing");
        list = list.reverse();
        list.print();
    }
}
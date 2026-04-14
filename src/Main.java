import lanta.lists.CustomLinkedList;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] array = new String[]{"null", "null1", "null2", "null3"};
        CustomLinkedList<String> list = new CustomLinkedList<>(array);
        CustomLinkedList<String> list1 = new CustomLinkedList<>(list);
        ArrayList<String> arrayList = new ArrayList<>(List.of(array));
        CustomLinkedList<String> list2 = new CustomLinkedList<>(arrayList);
        System.out.println(list.equals(list1));
        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()){
            String name = iterator.next();
            System.out.println(name);
            if(name != null && name.equals("null2")){
                iterator.remove();
                System.out.println("Removed: "+name);
            }

        }
        list.print();
        list1.print();
        list2.print();
        System.out.println(list.equals(list1));
    }
}
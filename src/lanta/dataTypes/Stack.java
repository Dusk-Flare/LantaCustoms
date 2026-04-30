package lanta.dataTypes;

import lanta.dataTypes.lists.SinglyLinkedList;

public class Stack<T> {
    SinglyLinkedList<T> stack;
    public Stack(){
        stack = new SinglyLinkedList<>();
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public int size(){
        return stack.size();
    }

    public T pop(){
        return stack.pop();
    }

    public T peek(){
        return stack.peek();
    }

    public void push(T data){
        stack.push(data);
    }
}

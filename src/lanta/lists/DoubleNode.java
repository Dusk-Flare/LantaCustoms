package lanta.lists;

import java.util.Objects;
import java.util.function.Predicate;

class DoubleNode<T> {
    private DoubleNode<T> prevNode;
    private final T DATA;
    private DoubleNode<T> nextNode;
    public DoubleNode(T DATA){
        prevNode = null;
        this.DATA = DATA;
        nextNode = null;
    }

    public T value(){
        return DATA;
    }

    public void next(DoubleNode<T> node){
        nextNode = node;
    }

    public DoubleNode<T> next(){
        return nextNode;
    }

    public void previous(DoubleNode<T> node){
        prevNode = node;
    }

    public DoubleNode<T> previous(){
        return prevNode;
    }

    public boolean test(Predicate<T> condition){
        return condition.test(DATA);
    }

    public boolean test(Object data){
        return Objects.equals(data, DATA);
    }

    @Override
    public String toString() {
        if(DATA == null) return null;
        return DATA.toString();
    }
}

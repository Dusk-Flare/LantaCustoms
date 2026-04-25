package lanta.lists;

import java.util.Objects;
import java.util.function.Predicate;

class SinglyNode<T> {
    private final T DATA;
    private SinglyNode<T> nextNode;
    public SinglyNode(T DATA){
        this.DATA = DATA;
        nextNode = null;
    }

    public T value(){
        return DATA;
    }

    public void next(SinglyNode<T> node){
        nextNode = node;
    }

    public SinglyNode<T> next(){
        return nextNode;
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

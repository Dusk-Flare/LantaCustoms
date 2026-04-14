package lanta.lists;

import java.util.Objects;
import java.util.function.Predicate;

class CustomNode<T> {
    private final T DATA;
    private CustomNode<T> nextNode;
    public CustomNode(T DATA){
        this.DATA = DATA;
        nextNode = null;
    }

    public T value(){
        return DATA;
    }

    public void next(CustomNode<T> node){
        nextNode = node;
    }

    public CustomNode<T> next(){
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

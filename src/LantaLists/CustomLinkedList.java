package LantaLists;

import java.util.Iterator;
import java.util.function.Predicate;
public class CustomLinkedList<T> implements Iterable<T> {
    CustomNode<T> headNode;

    public Iterator<T> iterator() {
        return new CustomNodeIterator();
    }

    public CustomLinkedList<T> copy(){
        CustomLinkedList<T> copy = new CustomLinkedList<>();
        copy.addAll(this);
        return copy;
    }

    public int size(){
        int size = 0;
        for(T _ : this){
            size++;
        }
        return size;
    }

    public boolean isEmpty(){
        return headNode == null;
    }

    public T pop(){
        if(headNode == null) return null;
        T element = headNode.value();
        headNode = headNode.next();
        return element;
    }

    public T peek(){
        return headNode.value();
    }

    public void clear(){
        this.headNode = null;
    }

    public void print(){
        System.out.println(this);
    }

    public void add(T data){
        if(data == null) return;
        CustomNode<T> newNode = new CustomNode<>(data);
        if(headNode == null) headNode = newNode;
        else {
            for(CustomNode<T> cn = headNode; cn != null; cn = cn.next()){
                if(cn.next() == null){
                    cn.next(newNode);
                    break;
                }
            }
        }
    }

    public void addAll(Iterable<? extends T> iterable){
        for(T data : iterable){
            add(data);
        }
    }

    public void fixCycle(){
        CustomNode<T> slow = headNode;
        CustomNode<T> fast = headNode;
        while( fast != null && fast.next() != null){
            fast = fast.next().next();
            slow = slow.next();
            if(slow == fast) break;
        }
        if(fast == null || fast.next() == null) return;
        slow = headNode;
        while (slow != fast){
            slow = slow.next();
            fast = fast.next();
        }

        CustomNode<T> cycleBreaker = slow;
        while (cycleBreaker.next() != fast){
            cycleBreaker = cycleBreaker.next();
        }
        cycleBreaker.next(null);
    }

    public CustomLinkedList<T> search(T data){
        CustomLinkedList<T> result = new CustomLinkedList<>();
        for(T value : this){
            if(value.equals(data)) result.add(value);
        }
        return result;
    }

    public CustomLinkedList<T> compareSearch(Predicate<T> condition){
        CustomLinkedList<T> result = new CustomLinkedList<>();
        for(T value : this){
            if(condition.test(value)) result.add(value);
        }
        return result;
    }

    public CustomLinkedList<T> intersect(CustomLinkedList<T> list){
        CustomLinkedList<T> intersection = new CustomLinkedList<>();
        for(T data : this){
            intersection.add(list.search(data).pop());
        }
        return intersection;
    }

    public T removeFirstOf(T data){
        if(headNode == null) return null;
        if (headNode.value().equals(data)) return pop();
        CustomNode<T> previous = headNode;
        for(CustomNode<T> cn = headNode; cn != null; cn = cn.next()){
            if(cn.value().equals(data)){
                previous.next(cn.next());
                return cn.value();
            }
            previous = cn;
        }
        return null;
    }

    public T compareRemoveFirstOf(Predicate<T> condition){
        if(headNode == null) return null;
        if (condition.test(headNode.value())) return pop();
        CustomNode<T> previous = headNode;
        for(CustomNode<T> cn = headNode; cn != null; cn = cn.next()){
            if(condition.test(cn.value())){
                previous.next(cn.next());
                return cn.value();
            }
            previous = cn;
        }
        return null;
    }

    public boolean deleteAllOf(T data){
        boolean result = false;
        if(headNode == null) return false;
        while (headNode != null && headNode.value().equals(data)){
            pop();
            result = true;
        }
        CustomNode<T> previous = headNode;
        for(CustomNode<T> cn = headNode; cn != null; cn = cn.next()){
            if(cn.value().equals(data)){
                previous.next(cn.next());
                cn = previous;
                result = true;
            }
            previous = cn;
        }
        return result;
    }

    public boolean compareDeleteAllOf(Predicate<T> condition){
        boolean result = false;
        if(headNode == null) return false;
        while (headNode != null && condition.test(headNode.value())){
            pop();
            result = true;
        }
        CustomNode<T> previous = headNode;
        for(CustomNode<T> cn = headNode; cn != null; cn = cn.next()){
            if(condition.test(cn.value())){
                previous.next(cn.next());
                cn = previous;
                result = true;
            }
            previous = cn;
        }
        return result;
    }

    public void removeDuplicates(){
        for(T value : this){
            if(this.search(value).size() != 1) removeFirstOf(value);
        }
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("[");
        if(headNode == null) str.append("]");
        for(CustomNode<T> cn = headNode; cn != null; cn = cn.next()){
            str.append(cn);
            str.append(cn.next() != null ? ", " : "]");
        }
        return str.toString();
    }

    class CustomNodeIterator implements Iterator<T> {
        private CustomNode<T> currentNode;

        @Override
        public boolean hasNext() {
            if (currentNode == null) currentNode = headNode;
            else currentNode = currentNode.next();
            return currentNode != null;
        }

        @Override
        public T next() {
            return currentNode.value();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

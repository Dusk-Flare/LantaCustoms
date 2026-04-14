package lanta.lists;

import java.util.*;
import java.util.function.Predicate;

/**
 * Custom version of {@code LinkedList} created to help me study Data Structure
 * @param <T> This represents the type of data used within the List
 */
public class CustomLinkedList<T> extends AbstractCollection<T> implements Iterable<T> {
    CustomNode<T> headNode;

    public CustomLinkedList() {

    }

    public CustomLinkedList(Collection<? extends T> collection) {
        addAll(collection);
    }

    public CustomLinkedList(T[] array) {
        addAll(array);
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomNodeIterator();
    }

    public CustomLinkedList<T> copy(){
        return new CustomLinkedList<>(this);
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

    public void print(){
        System.out.println(this);
    }

    public void clear(){
        this.headNode = null;
    }

    public boolean add(T data){
        CustomNode<T> newNode = new CustomNode<>(data);
        if(headNode == null) headNode = newNode;
        else {
            for(CustomNode<T> cn = headNode; cn != null; cn = cn.next()){
                if(cn.next() == null){
                    cn.next(newNode);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection){
        boolean hasAdded = true;
        for(T data : collection){
            hasAdded &= add(data);
        }
        return hasAdded;
    }

    public boolean addAll(T[] array){
        boolean hasAdded = true;
        for(T data : array){
            hasAdded &= add(data);
        }
        return hasAdded;
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

    @Override
    public boolean contains(Object data){
        boolean contains = false;
        for(T value : this){
            contains |= Objects.equals(data, value);
        }
        return contains;
    }

    public CustomLinkedList<T> search(T data){
        return compareSearch(value -> Objects.equals(data, value));
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

    boolean deleteNode(CustomNode<T> node){
        CustomNode<T> previous = headNode;
        for(CustomNode<T> cn = headNode; cn != null; cn = cn.next()){
            if(cn.equals(node)){
                previous.next(cn.next());
                return true;
            }
            previous = cn;
        }
        return false;
    }

    @Override
    public boolean remove(Object data){
        return compareRemove(value -> Objects.equals(data, value));
    }

    public boolean compareRemove(Predicate<T> condition){
        if(headNode == null) return false;
        if (headNode.test(condition)) {
            pop();
            return true;
        }
        CustomNode<T> previous = headNode;
        for(CustomNode<T> cn = headNode; cn != null; cn = cn.next()){
            if(cn.test(condition)){
                previous.next(cn.next());
                return true;
            }
            previous = cn;
        }
        return false;
    }

    public T extract(T data){
        return compareExtract(value -> Objects.equals(data, value));
    }

    public T compareExtract(Predicate<T> condition){
        if(headNode == null) return null;
        if (headNode.test(condition)) return pop();
        CustomNode<T> previous = headNode;
        for(CustomNode<T> cn = headNode; cn != null; cn = cn.next()){
            if(cn.test(condition)){
                previous.next(cn.next());
                return cn.value();
            }
            previous = cn;
        }
        return null;
    }

    public boolean deleteAllOf(T data){
        return compareDeleteAllOf(value -> Objects.equals(data, value));
    }

    public boolean compareDeleteAllOf(Predicate<T> condition){
        boolean result = false;
        if(headNode == null) return false;
        while (headNode.test(condition)){
            pop();
            result = true;
        }
        CustomNode<T> previous = headNode;
        for(CustomNode<T> cn = headNode; cn != null; cn = cn.next()){
            if(cn.test(condition)){
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
            if(this.search(value).size() != 1) extract(value);
        }
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        Objects.requireNonNull(collection);
        boolean modified = false;
        for(T data : this){
            if(!collection.contains(data)){
                modified |= deleteAllOf(data);
            }
        }
        return modified;
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

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || (getClass() != o.getClass())) return super.equals(o);
        CustomLinkedList<?> newList = (CustomLinkedList<?>) o;
        Iterator<T> thisIterator = iterator();
        Iterator<?> otherIterator = newList.iterator();

        while(thisIterator.hasNext() && otherIterator.hasNext()){
            if(!Objects.equals(thisIterator.next(), otherIterator.next())) return false;
            if(thisIterator.hasNext() ^ otherIterator.hasNext()) return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hash = getClass().hashCode();
        for(T element : this) hash = 31 * hash + Objects.hashCode(element);
        return hash;
    }

    class CustomNodeIterator implements Iterator<T> {
        private CustomNode<T> nextNode;
        private CustomNode<T> currentNode;
        private CustomNode<T> lastNode;
        CustomNodeIterator(){
            nextNode = headNode;
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public T next() {
            if (nextNode == null) throw new NoSuchElementException();
            lastNode = currentNode;
            currentNode = nextNode;
            nextNode = nextNode.next();
            return currentNode.value();
        }

        @Override
        public void remove() {
            if(deleteNode(currentNode)) currentNode = lastNode;
        }

        public void removeIf(Predicate<T> condition){
            if(!currentNode.test(condition)) return;
            remove();
        }
    }
}

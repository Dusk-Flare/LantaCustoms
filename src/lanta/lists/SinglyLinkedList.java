package lanta.lists;

import java.util.*;
import java.util.function.Predicate;

public class SinglyLinkedList<T> extends AbstractCollection<T> implements Iterable<T> {
    SinglyNode<T> headNode;

    public SinglyLinkedList() {

    }

    public SinglyLinkedList(Collection<? extends T> collection) {
        addAll(collection);
    }

    public SinglyLinkedList(T[] array) {
        addAll(array);
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomNodeIterator();
    }

    public SinglyLinkedList<T> copy(){
        return new SinglyLinkedList<>(this);
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
        SinglyNode<T> newNode = new SinglyNode<>(data);
        if(headNode == null) headNode = newNode;
        else {
            for(SinglyNode<T> cn = headNode; cn != null; cn = cn.next()){
                if(cn.next() == null){
                    cn.next(newNode);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean compareAddAfter(T data, Predicate<T> condition){
        SinglyNode<T> newNode = new SinglyNode<>(data);
        if(headNode == null) headNode = newNode;
        else {
            for(SinglyNode<T> cn = headNode; cn != null; cn = cn.next()){
                if(cn.test(condition)){
                    newNode.next(cn.next());
                    cn.next(newNode);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean compareAddBefore(T data, Predicate<T> condition){
        SinglyNode<T> newNode = new SinglyNode<>(data);
        SinglyNode<T> previous = null;
        if(headNode == null) headNode = newNode;
        else {
            for(SinglyNode<T> cn = headNode; cn != null; cn = cn.next()){
                if(cn.test(condition)){
                    if(previous != null) previous.next(newNode);
                    newNode.next(cn);
                    return true;
                }
                previous = cn;
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
        SinglyNode<T> slow = headNode;
        SinglyNode<T> fast = headNode;
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

        SinglyNode<T> cycleBreaker = slow;
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

    public SinglyLinkedList<T> search(T data){
        return compareSearch(value -> Objects.equals(data, value));
    }

    public SinglyLinkedList<T> compareSearch(Predicate<T> condition){
        SinglyLinkedList<T> result = new SinglyLinkedList<>();
        for(T value : this){
            if(condition.test(value)) result.add(value);
        }
        return result;
    }

    public SinglyLinkedList<T> intersect(SinglyLinkedList<T> list){
        SinglyLinkedList<T> intersection = new SinglyLinkedList<>();
        for(T data : this){
            intersection.add(list.search(data).pop());
        }
        return intersection;
    }

    boolean deleteNode(SinglyNode<T> node){
        SinglyNode<T> previous = headNode;
        for(SinglyNode<T> cn = headNode; cn != null; cn = cn.next()){
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
        SinglyNode<T> previous = headNode;
        for(SinglyNode<T> cn = headNode; cn != null; cn = cn.next()){
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
        SinglyNode<T> previous = headNode;
        for(SinglyNode<T> cn = headNode; cn != null; cn = cn.next()){
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
        SinglyNode<T> previous = headNode;
        for(SinglyNode<T> cn = headNode; cn != null; cn = cn.next()){
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
        for(SinglyNode<T> cn = headNode; cn != null; cn = cn.next()){
            str.append(cn);
            str.append(cn.next() != null ? ", " : "]");
        }
        return str.toString();
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || (getClass() != o.getClass())) return super.equals(o);
        SinglyLinkedList<?> newList = (SinglyLinkedList<?>) o;
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
        private SinglyNode<T> nextNode;
        private SinglyNode<T> currentNode;
        private SinglyNode<T> lastNode;
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

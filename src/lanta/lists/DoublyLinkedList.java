package lanta.lists;

import java.util.*;
import java.util.function.Predicate;

/**
 * Custom version of {@code LinkedList} created to help me study Data Structure
 * @param <T> This represents the type of data used within the List
 */
public class DoublyLinkedList<T> extends AbstractCollection<T> implements Iterable<T> {
    DoubleNode<T> headNode;
    DoubleNode<T> leadNode;

    public DoublyLinkedList() {

    }

    public DoublyLinkedList(Collection<? extends T> collection) {
        addAll(collection);
    }

    public DoublyLinkedList(T[] array) {
        addAll(array);
    }

    @Override
    public Iterator<T> iterator() {
        return new DoubleNodeIterator(this);
    }

    public DoublyLinkedList<T> copy(){
        return new DoublyLinkedList<>(this);
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
        this.leadNode = null;
    }

    public DoublyLinkedList<T> reverse(){
        DoublyLinkedList<T> tempList = new DoublyLinkedList<>(this);
        DoublyLinkedList<T> reversed = new DoublyLinkedList<>();
        while(!tempList.isEmpty()){
            reversed.addFirst(tempList.pop());
        }
        return reversed;
    }

    public boolean compareAddAfter(T data, Predicate<T> condition){
        DoubleNode<T> newNode = new DoubleNode<>(data);
        if(headNode == null) headNode = newNode;
        else {
            for(DoubleNode<T> cn = headNode; cn != null; cn = cn.next()){
                if(cn.test(condition)){
                    newNode.next(cn.next());
                    cn.next(newNode);
                    newNode.previous(cn);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean compareAddBefore(T data, Predicate<T> condition){
        DoubleNode<T> newNode = new DoubleNode<>(data);
        if(headNode == null) headNode = newNode;
        else {
            for(DoubleNode<T> cn = leadNode; cn != null; cn = cn.previous()){
                if(cn.test(condition)){
                    newNode.previous(cn.previous());
                    cn.previous(newNode);
                    newNode.previous(cn);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addLast(T data){
        DoubleNode<T> newNode = new DoubleNode<>(data);
        if(headNode == null) return addFirst(data);
        if(leadNode == null && headNode.next() != null) leadNode = headNode.next();
        else if (leadNode == null) {
            headNode.next(newNode);
            leadNode = headNode.next();
        } else {
            leadNode.next(newNode);
            newNode.previous(leadNode);
            leadNode = leadNode.next();
            return true;
        }
        return false;
    }

    public boolean addFirst(T data){
        DoubleNode<T> newNode = new DoubleNode<>(data);
        if(leadNode == null && headNode != null) leadNode = headNode.next();
        if(headNode == null) headNode = newNode;
        else {
            headNode.previous(newNode);
            newNode.next(headNode);
            headNode = headNode.previous();
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection){
        boolean hasAdded = true;
        for(T data : collection){
            hasAdded &= addLast(data);
        }
        return hasAdded;
    }

    public boolean addAll(T[] array){
        boolean hasAdded = true;
        for(T data : array){
            hasAdded &= addLast(data);
        }
        return hasAdded;
    }

    public void fixCycle(){
        DoubleNode<T> slow = headNode;
        DoubleNode<T> fast = headNode;
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

        DoubleNode<T> cycleBreaker = slow;
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

    public DoublyLinkedList<T> search(T data){
        return compareSearch(value -> Objects.equals(data, value));
    }

    public DoublyLinkedList<T> compareSearch(Predicate<T> condition){
        DoublyLinkedList<T> result = new DoublyLinkedList<>();
        for(T value : this){
            if(condition.test(value)) result.addLast(value);
        }
        return result;
    }

    public DoublyLinkedList<T> intersect(DoublyLinkedList<T> list){
        DoublyLinkedList<T> intersection = new DoublyLinkedList<>();
        for(T data : this){
            intersection.addLast(list.search(data).pop());
        }
        return intersection;
    }

    boolean deleteNode(DoubleNode<T> node){
        DoubleNode<T> previous = headNode;
        for(DoubleNode<T> cn = headNode; cn != null; cn = cn.next()){
            if(cn.equals(node)){
                previous.next(cn.next());
                cn.next().previous(previous);
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
        DoubleNode<T> previous = headNode;
        for(DoubleNode<T> cn = headNode; cn != null; cn = cn.next()){
            if(cn.test(condition)){
                previous.next(cn.next());
                cn.next().previous(previous);
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
        DoubleNode<T> previous = headNode;
        for(DoubleNode<T> cn = headNode; cn != null; cn = cn.next()){
            if(cn.test(condition)){
                previous.next(cn.next());
                cn.next().previous(previous);
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
        DoubleNode<T> previous = headNode;
        for(DoubleNode<T> cn = headNode; cn != null; cn = cn.next()){
            if(cn.test(condition)){
                previous.next(cn.next());
                cn.next().previous(previous);
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
        for(DoubleNode<T> cn = headNode; cn != null; cn = cn.next()){
            str.append(cn);
            str.append(cn.next() != null ? ", " : "]");
        }
        return str.toString();
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || (getClass() != o.getClass())) return super.equals(o);
        DoublyLinkedList<?> newList = (DoublyLinkedList<?>) o;
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

    class DoubleNodeIterator implements Iterator<T> {
        private DoubleNode<T> nextNode;
        private DoubleNode<T> lastReturned;
        private final DoubleNode<T> headNode;
        private final DoubleNode<T> tailNode;

        DoubleNodeIterator(DoublyLinkedList<T> list) {
            this.headNode = list.headNode;
            this.tailNode = list.leadNode;
            this.nextNode = list.headNode;
            this.lastReturned = null;
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            lastReturned = nextNode;
            nextNode = nextNode.next();
            return lastReturned.value();
        }

        public boolean hasPrevious() {
            if (nextNode == null) return tailNode != null;
            return nextNode.previous() != null;
        }

        public T previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            if (nextNode == null) {
                lastReturned = tailNode;
                nextNode = tailNode;
            } else {
                lastReturned = nextNode.previous();
                nextNode = lastReturned;
            }
            return lastReturned.value();
        }

        @Override
        public void remove() {
            if (lastReturned == null) throw new IllegalStateException();
            deleteNode(lastReturned);
            if (lastReturned == nextNode) {
                nextNode = nextNode != null ? nextNode.next() : null;
            }
            lastReturned = null;
        }

        public void removeIf(Predicate<T> condition) {
            if (lastReturned == null) return;
            if (lastReturned.test(condition)) {
                remove();
            }
        }
    }
}

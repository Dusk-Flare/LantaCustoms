package lanta.dataTypes.lists;

class DoublyNode<T> extends SinglyNode<T> {
    private DoublyNode<T> prevNode;
    public DoublyNode(T DATA){
        super(DATA);
        prevNode = null;
    }

    @Override
    public DoublyNode<T> next(){
        return (DoublyNode<T>) super.next();
    }

    public void previous(DoublyNode<T> node){
        prevNode = node;
    }

    public DoublyNode<T> previous(){
        return prevNode;
    }
}

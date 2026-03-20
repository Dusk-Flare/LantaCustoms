package LantaLists;

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

    @Override
    public String toString() {
        return DATA.toString();
    }
}

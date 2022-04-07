public class Node {
    private int rowIndex;
    private int colIndex;
    private Node previous;
    private String direction;

    public Node(int rowIndex, int colIndex){
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public int getRowIndex(){
        return rowIndex;
    }

    public int getColIndex(){
        return colIndex;
    }

    public Node getPrevious(){
        return previous;
    }

    public void setPrevious(Node previous){
        this.previous = previous;
    }

    public String getDirection(){
        return direction;
    }

    public void setDirection(String direction){
        this.direction = direction;
    }
}

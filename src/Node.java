public class Node {
    private int rowIndex;
    private int colIndex;
    private Node previous;
    private String direction;

    public Node(int rowIndex, int colIndex){
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    /**
     * Get the row number of the node
     * @return the row number of the node
     */
    public int getRowIndex(){
        return rowIndex;
    }

    /**
     * Get the column number of the node
     * @return the column number of the node
     */
    public int getColIndex(){
        return colIndex;
    }

    /**
     * Get the previous node
     * @return the previous node
     */
    public Node getPrevious(){
        return previous;
    }

    /**
     * Set the previous node
     * @param previous the previous node
     */
    public void setPrevious(Node previous){
        this.previous = previous;
    }

    /**
     * Get the direction of the current node that moved
     * @return the direction of the node
     */
    public String getDirection(){
        return direction;
    }

    /**
     * Set the direction of the current node to move
     * @param direction the direction of the node
     */
    public void setDirection(String direction){
        this.direction = direction;
    }
}

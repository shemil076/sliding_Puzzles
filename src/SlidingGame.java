/**
 *  uow id : - w1836048
 *  IIT id : - 20200612
 *  Name   : - Pramuditha Shelum Bandara Karunarathna
 */



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class SlidingGame {
    private char[][] map;                  // char 2d array to store the the cells of the game (map)
    private boolean[][] gone;              // boolean 2d array to store the visited cells
    private ArrayList<Node> nodes = new ArrayList<Node>();      // store the nodes
    private ArrayList<String> paths = new ArrayList<String>();
    private final String[] directions = {"up", "down", "left", "right"};  // directions to move
    private Node visitedNode;               // visited node
    private Node adjacentNode;              // adjacent node of the current node
    private boolean found = false;
    private int stepCount = 0;
    private String filename;
    private Scanner input = new Scanner(System.in);


    public static void main(String[] args) {
        System.out.println("\n\n-----------------------WELCOME------------------------");
        System.out.println("This program will find the shortest path from the\nstart to the finish.");

        System.out.println("------------------------------------------------------");

        SlidingGame game = new SlidingGame();
        game.getFilePath();                 // get the file path from the user
        game.readMap();                     // read the map from the file
        game.printMap();                    // print the map to the console
        game.findPath();                    // find the path
    }

    /**
     * Get the file path from the user
     */
    public void getFilePath() {
        System.out.println("Enter the file path: ");
        filename = input.next();
        System.out.println("------------------------------------------------------");
    }


    /**
     * Read the map from the file and store it in a 2d char array
     */
    public void readMap() { // parser
        while (true) {  // loops until the user input a valid file.
            File file = new File( filename );
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();            //first line of the file
                int n = line.length();
                map = new char[n][n];                       // initialize the array according to the map in the file
                for (int i = 0; i < n; i++) {
                    map[i] = line.toCharArray();            // store the map line by line in an array
                    line = reader.readLine();               // read the next lines by looping
                }
                break;
            } catch (Exception e) {
                System.out.println("OOPS! Something went wrong!\n");
                getFilePath();          // get the file path again if the file is not found
                System.out.println();
            }
        }
    }

    /**
     * Print the map to the console that was read from the file
     */
    public void printMap() {
        for (char[] chars : map) {              // iterate over the rows
            for (char aChar : chars) {          // iterate over the columns
                System.out.print(aChar);
            }
            System.out.println();
        }
    }

    /**
     * Find the starting point of the map and added it to the nodes list and mark it as visited
     */
    public void findStart() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'S') {
                    Node node = new Node(i, j);
                    nodes.add(node);                                                // added the starting node to the nodes list
                    gone = new boolean[map.length][map[i].length];
                    gone[node.getRowIndex()][node.getColIndex()] = true;            // mark the start node as visited
                }
            }
        }
    }

    /**
     * Find the shortest path from the start to the finish
     */
    public void findPath() {
        long start = System.currentTimeMillis();
        findStart();                // find the starting point
        int visitedRow;
        int visitedCol;


        while (nodes.size() != 0) {
            visitedNode = nodes.get(0);
            visitedRow = visitedNode.getRowIndex();
            visitedCol = visitedNode.getColIndex();
            nodes.remove(0);

            if (map[visitedRow][visitedCol] == 'F') {
                found = true;
                break;
            } else {

                // move according to the direction to find the suitable path
                move(directions[2], -1, 0);
                move(directions[3], 1, 0);
                move(directions[1], 0, 1);
                move(directions[0], 0, -1);
            }
        }

        long now = System.currentTimeMillis();
        double elapsed = (now - start) / 1000.0;

        printPath();


        System.out.println("\nElapsed time = " + elapsed + " seconds");
    }


    /**
     * Move the node in the direction specified
     *
     * @param direction - the direction to move
     * @param x         - the x co-ordinate to move (Column)
     * @param y         - the y co-ordinate to move (row)
     */
    public void move(String direction, int x, int y) {

        int row = visitedNode.getRowIndex();   // get the row index of the visited node
        int col = visitedNode.getColIndex();   // get the column index of the visited node
        int rowCount = map.length;
        int colCount = map[0].length;


        while (true) {
            row += y;
            col += x;

            if (row >= 0 && col >= 0) {   // check if the node is in the map
                if (row < rowCount && col < colCount) { // check if the node is in the map
                    if (map[row][col] != '0') { // check if the node is a rock
                        if (!gone[row][col]) { // check if the node has been visited

                            if (map[row][col] == 'F') {    // check for the fish node

                                adjacentNode = new Node(row, col);         // next node to be visited
                                adjacentNode.setPrevious(visitedNode);
                                adjacentNode.setDirection(direction);
                                nodes.add(0, adjacentNode);
                                gone[row][col] = true;

                                break;
                            } else {

                                if (( row + y < 0 || col + x < 0) // if the next node is out of bounds
                                        || ( row + y >= map.length || col + x >= map.length)  // check if the next node is out of bounds
                                        || (map[ row + y][col + x] == '0'))     // check if the next node is a rock
                                {

                                    adjacentNode = new Node(row, col);  // next node to be visited
                                    adjacentNode.setPrevious(visitedNode);
                                    adjacentNode.setDirection(direction);
                                    nodes.add(adjacentNode);
                                    gone[row][col] = true;
                                    break;
                                }
                            }

                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            } else {
                break;
            }
        }
    }

    public void printPath() {
        if (found) {
            System.out.println("\nPath Found\n");

            // until the starting node is reached
            while (visitedNode.getPrevious() != null) {
                String movement = "Move " + visitedNode.getDirection() + " to " + "(" + (visitedNode.getColIndex() + 1) + ", " + (visitedNode.getRowIndex() + 1) + ")";
                paths.add(movement);
                visitedNode = visitedNode.getPrevious();
            }

            paths.add("Start at " + "(" + (visitedNode.getColIndex() + 1) + ", " + (visitedNode.getRowIndex() + 1) + ")");

            // print the path in reverse order to get the correct order
            for (int i = paths.size() - 1; i >= 0; i--) {
                stepCount++;
                System.out.println(stepCount + ". " + paths.get(i));
            }
            stepCount++;
            System.out.println(stepCount + ". Done!");
        }else{
            System.out.println("\n------------------------------------------------------");

            System.out.println("Sorry, Path is Not Found");
        }
    }
}


/**
 * Reference:
 *          https://www.youtube.com/watch?v=oDqjPvD54Ss&t=0s&ab_channel=WilliamFiset
 *          https://www.youtube.com/watch?v=KiCBXu4P-2Y&ab_channel=WilliamFiset
 *          https://www.youtube.com/watch?v=WMM6QvWjTfM&t=287s&ab_channel=KasunAratthanage
 *          https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class SlidingGame {
    private  char[][] map;
    private boolean [][] gone;
    private ArrayList<Node> nodes = new ArrayList<Node>();
    private Node visitedNode;
    private final String [] directions = {"up","down","left","right"};
    private Node adjacentNode;


    public static void main(String[] args) {
        SlidingGame game = new SlidingGame();
        game.readMap("test_1");
        game.printMap();
        game.findStart();
        game.findPath();
//        game.visualize();
    }



    public  void readMap(String filename) {
        File file = new File("tests/" + filename + ".txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            int n = line.length();
            map = new char[n][n];
            for (int i = 0; i < n; i++) {
                map[i] = line.toCharArray();
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void printMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }


    public void findStart() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'S') {
                    Node node = new Node(i, j);
//                    System.out.println("\nStart : " + (node.getColIndex()+1) +","+(node.getRowIndex()+1));
                    nodes.add(node);
                    gone = new boolean[map.length][map[i].length];
                    gone [node.getRowIndex()][node.getColIndex()] = true;
                }
            }
        }
    }

    public void findPath() {
        boolean found = false;
        int visitedRow;
        int visitedCol;
        int stepCount = 0;
        ArrayList<String> paths = new ArrayList<String>();


        while(nodes != null) {
            visitedNode = nodes.get(0);
            visitedRow = visitedNode.getRowIndex();
            visitedCol = visitedNode.getColIndex();
            nodes.remove(0);

            if (map[visitedRow][visitedCol] == 'F') {
                found = true;
                break;
            }else{
                move(directions[2],-1,0);
                move(directions[3],1,0);
                move(directions[1],0,1);
                move(directions[0],0,-1);
            }
        }

        if (found) {
            System.out.println("\nPath Found\n");
            while(visitedNode.getPrevious() != null) {
                String step = "Move " + visitedNode.getDirection() + " to " + "(" + (visitedNode.getColIndex() + 1) + ", " + (visitedNode.getRowIndex() + 1) + ")";
                paths.add(step);
                visitedNode = visitedNode.getPrevious();
            }
            paths.add("Start at " + "(" + (visitedNode.getColIndex() + 1) + ", " + (visitedNode.getRowIndex() + 1) + ")");

            Collections.reverse(paths);

            for (String path : paths) {
                stepCount++;
                System.out.println(stepCount + ". " + path);
            }
            stepCount++;
            System.out.println(stepCount + ". Done!");
        }

    }

    public void move(String direction, int x , int y) {
        int row = visitedNode.getRowIndex();
        int col = visitedNode.getColIndex();
        int newRow;
        int newCol;
        int rowCount = map.length;
        int colCount = map[0].length;


        while (true){
            row += y;
            col += x;

            if (row >= 0 && col >= 0){
                if (row < rowCount && col < colCount) {
                    if(map[row][col] != '0') {
                        if(!gone[row][col]){

                            if (map[row][col] == 'F'){
                                adjacentNode = new Node(row, col);
                                adjacentNode.setPrevious(visitedNode);
                                adjacentNode.setDirection(direction);
                                nodes.add(0,adjacentNode);
                                gone[row][col] = true;
                                break;
                            }else {
                                newRow = row +y;
                                newCol = col +x;

                                if ((newRow < 0 || newCol < 0) || (newRow >= map.length || newCol >= map.length) || (map[newRow][newCol] == '0')){
                                    adjacentNode = new Node(row, col);
                                    adjacentNode.setPrevious(visitedNode);
                                    adjacentNode.setDirection(direction);
                                    nodes.add(adjacentNode);
                                    gone[row][col] = true;
                                    break;

                                }

                            }

                        }else {
                            break;
                        }
                    }else {
                        break;
                    }
                }else{
                    break;
                }
            }else {
                break;
            }
        }
    }

    public void visualize(){
        char[][] visualizeMap = map.clone();

        for (int i = 1; i < visualizeMap.length; i++) {
            for (int j = 0; j < visualizeMap[0].length; j++) {
                if (map[i][j] == 'S' || map[i][j] == 'F') {
                    continue;
                }else if(gone[i][j] == true){
                    visualizeMap[i][j] = '@';
                }
            }
        }

        System.out.println("\n\n");
        for (int i = 0; i < visualizeMap.length; i++) {
            for (int j = 0; j < visualizeMap[i].length; j++) {
                System.out.print(visualizeMap[i][j]);
            }
            System.out.println();
        }
    }
}

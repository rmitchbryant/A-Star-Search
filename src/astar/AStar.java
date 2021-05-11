/*
 * Use the A* Search algorithm to determine the shortest path between a start
 * node and a goal node.
 */
package astar;

import java.util.*;

/**
 *
 * @author Robert M Bryant
 * @version 7/23/2020
 */
public class AStar {

    public static Scanner input = new Scanner(System.in);
    public static final int N = 15;
    public static final int DIAG_COST = 14;
    public static final int HORIZ_VERT_COST = 10;
    public static Node start, goal;

    public static PriorityQueue<Node> openList = new PriorityQueue<>();
    public static ArrayList<Node> path = new ArrayList<>();
    public static Node[][] state = new Node[N][N];
    public static char[][] board = new char[N][N];

    /**
     * Create the initial state of the board
     */
    public static void createState() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                state[i][j] = new Node(i, j, true, false);
                //Give each node a 10% chance to be non-passable
                int random = (int) (Math.random() * 10);
                if (random == 4) {
                    state[i][j].setPassable(false);
                }
            }
        }
    }

    /**
     * Print out the state
     *
     * @param state 2D array of Nodes
     */
    public static void printState(Node[][] state) {
        int countX = 0;
        int countY = 0;

        //Create a list of numbers to represent columns
        System.out.print("      ");
        for (int i = 0; i < N; i++) {
            System.out.printf("%2d| ", countY);
            countY++;
        }
        //Create each row starting with a number to represent each column
        System.out.println();
        for (int i = 0; i < N; i++) {
            System.out.printf("%2d ", countX);
            countX++;
            for (int j = 0; j < N; j++) {
                if (!state[i][j].isPassable()) {
                    board[i][j] = 'X';
                }
                System.out.print(" | " + board[i][j]);

            }
            System.out.println(" |");
        }

    }

    /**
     * Ask the user for the start coordinates. Test if they choose a passable
     * node or if it is out of bounds.
     *
     * @param state 2D array of Nodes
     */
    public static void chooseStart(Node[][] state) {
        int x, y;
        boolean valid = true;
        while (valid) {//Test if the coordinates are valid
            System.out.println("Please put in the start coordinates.");
            System.out.print("X coordinate: ");
            x = input.nextInt();
            System.out.print("Y coordinate: ");
            y = input.nextInt();
            if (x < 0 || x > N - 1 || y < 0 || y > N - 1) {
                System.out.println("That is out of bounds.");
            } else if (state[x][y].isPassable()) {
                start = state[x][y];
                state[x][y].setStart(true);
                valid = false;
            } else {
                System.out.println("Please choose a passable node.");
            }
        }

    }

    /**
     * Ask the user for the goal coordinates. Test if they choose a passable
     * node or if they are out of bounds.
     *
     * @param state 2D array of Node objects
     */
    public static void chooseGoal(Node[][] state) {
        int x, y;
        boolean valid = true;
        while (valid) {//Test if the coordinates are valid
            System.out.println("Please put in the goal coordinates.");
            System.out.print("X coordinate: ");
            x = input.nextInt();
            System.out.print("Y coordinate: ");
            y = input.nextInt();
            if (x < 0 || x > N - 1 || y < 0 || y > N - 1) {
                System.out.println("That is out of bounds.");
            } else if (state[x][y].isPassable()) {
                goal = state[x][y];
                state[x][y].setGoal(true);
                valid = false;
            } else {
                System.out.println("Please choose a passable node.");
            }
        }
    }

    /**
     * Search the neighbors of each node to determine the lowest F value
     *
     * @param state 2D array of nodes
     * @return true if there is a path, false if there is none
     */
    public static boolean AStarSearch(Node[][] state) {
        int xStart, yStart, xGoal, yGoal, h, g, f, xNeighbor, yNeighbor;
        Node current, neighbor = null;

        xStart = start.getX();
        yStart = start.getY();
        xGoal = goal.getX();
        yGoal = goal.getY();

        h = ((Math.abs(xStart - xGoal)) + (Math.abs(yStart - yGoal))) * 10;
        start.setG(0);
        start.setF(h);
        openList.add(start);

        while (true) {//Loop until all viable neighbors are checked for a path
            current = openList.poll();
            if (current == null || current.isGoal()) {
                break;
            }
            //Find neighbor to the top left
            xNeighbor = current.getX() - 1;
            yNeighbor = current.getY() - 1;
            if (xNeighbor > -1 && xNeighbor < N && yNeighbor > -1 && yNeighbor < N) {
                neighbor = state[xNeighbor][yNeighbor];
                if (neighbor.isPassable()) {//Check if the node is passable
                    if (!neighbor.isClosed()) {//Check if it was already visited
                        //Find the h value to the goal using Manhattan heuristic
                        h = (Math.abs(xNeighbor - xGoal)
                                + (Math.abs(yNeighbor - yGoal))) * 10;
                        g = current.getG() + DIAG_COST;
                        f = g + h;
                        //Assign a parent if there is none
                        if (neighbor.getParent() == null) {
                            neighbor.setParent(current);
                        }//Assign a new parent if the new f is less than the current f 
                        else if (neighbor.getF() > f) {
                            neighbor.setParent(current);
                        }
                        neighbor.setG(g);
                        neighbor.setF(f);
                        //Add the neighbor to the list
                        openList.add(neighbor);
                    }
                }
            }
            //Find neighbor to the top
            xNeighbor = current.getX();
            yNeighbor = current.getY() - 1;
            if (xNeighbor > -1 && xNeighbor < N && yNeighbor > -1 && yNeighbor < N) {
                neighbor = state[xNeighbor][yNeighbor];
                if (neighbor.isPassable()) {
                    if (!neighbor.isClosed()) {
                        h = (Math.abs(xNeighbor - xGoal)
                                + (Math.abs(yNeighbor - yGoal))) * 10;
                        g = current.getG() + HORIZ_VERT_COST;
                        f = g + h;
                        if (neighbor.getParent() == null) {
                            neighbor.setParent(current);
                        } else if (neighbor.getF() > f) {
                            neighbor.setParent(current);
                        }
                        neighbor.setG(g);
                        neighbor.setF(f);
                        openList.add(neighbor);
                    }
                }
            }
            //Find neighbor to the top right
            xNeighbor = current.getX() + 1;
            yNeighbor = current.getY() - 1;
            if (xNeighbor > -1 && xNeighbor < N && yNeighbor > -1 && yNeighbor < N) {
                neighbor = state[xNeighbor][yNeighbor];
                if (neighbor.isPassable()) {
                    if (!neighbor.isClosed()) {
                        h = (Math.abs(xNeighbor - xGoal)
                                + (Math.abs(yNeighbor - yGoal))) * 10;
                        g = current.getG() + DIAG_COST;
                        f = g + h;
                        if (neighbor.getParent() == null) {
                            neighbor.setParent(current);
                        } else if (neighbor.getF() > f) {
                            neighbor.setParent(current);
                        }
                        neighbor.setG(g);
                        neighbor.setF(f);
                        openList.add(neighbor);
                    }
                }
            }
            //Find neighbor to the left
            xNeighbor = current.getX() - 1;
            yNeighbor = current.getY();
            if (xNeighbor > -1 && xNeighbor < N && yNeighbor > -1 && yNeighbor < N) {
                neighbor = state[xNeighbor][yNeighbor];
                if (neighbor.isPassable()) {
                    if (!neighbor.isClosed()) {
                        h = (Math.abs(xNeighbor - xGoal)
                                + (Math.abs(yNeighbor - yGoal))) * 10;
                        g = current.getG() + HORIZ_VERT_COST;
                        f = g + h;
                        if (neighbor.getParent() == null) {
                            neighbor.setParent(current);
                        } else if (neighbor.getF() > f) {
                            neighbor.setParent(current);
                        }
                        neighbor.setG(g);
                        neighbor.setF(f);
                        openList.add(neighbor);
                    }
                }
            }
            //Find neighbor to the right
            xNeighbor = current.getX() + 1;
            yNeighbor = current.getY();
            if (xNeighbor > -1 && xNeighbor < N && yNeighbor > -1 && yNeighbor < N) {
                neighbor = state[xNeighbor][yNeighbor];
                if (neighbor.isPassable()) {
                    if (!neighbor.isClosed()) {
                        h = (Math.abs(xNeighbor - xGoal)
                                + (Math.abs(yNeighbor - yGoal))) * 10;
                        g = current.getG() + HORIZ_VERT_COST;
                        f = g + h;
                        if (neighbor.getParent() == null) {
                            neighbor.setParent(current);
                        } else if (neighbor.getF() > f) {
                            neighbor.setParent(current);
                        }
                        neighbor.setG(g);
                        neighbor.setF(g + h);
                        openList.add(neighbor);
                    }
                }
            }
            //Find neighbor to the bottom left
            xNeighbor = current.getX() - 1;
            yNeighbor = current.getY() + 1;
            if (xNeighbor > -1 && xNeighbor < N && yNeighbor > -1 && yNeighbor < N) {
                neighbor = state[xNeighbor][yNeighbor];
                if (neighbor.isPassable()) {
                    if (!neighbor.isClosed()) {
                        h = (Math.abs(xNeighbor - xGoal)
                                + (Math.abs(yNeighbor - yGoal))) * 10;
                        g = current.getG() + DIAG_COST;
                        f = g + h;
                        if (neighbor.getParent() == null) {
                            neighbor.setParent(current);
                        } else if (neighbor.getF() > f) {
                            neighbor.setParent(current);
                        }
                        neighbor.setG(g);
                        neighbor.setF(f);
                        openList.add(neighbor);
                    }
                }
            }
            //Find neighbor to the bottom
            xNeighbor = current.getX();
            yNeighbor = current.getY() + 1;
            if (xNeighbor > -1 && xNeighbor < N && yNeighbor > -1 && yNeighbor < N) {
                neighbor = state[xNeighbor][yNeighbor];
                if (neighbor.isPassable()) {
                    if (!neighbor.isClosed()) {
                        h = (Math.abs(xNeighbor - xGoal)
                                + (Math.abs(yNeighbor - yGoal))) * 10;
                        g = current.getG() + HORIZ_VERT_COST;
                        f = g + h;
                        if (neighbor.getParent() == null) {
                            neighbor.setParent(current);
                        } else if (neighbor.getF() > f) {
                            neighbor.setParent(current);
                        }
                        neighbor.setG(g);
                        neighbor.setF(f);
                        openList.add(neighbor);
                    }
                }
            }
            //Find neighbor to the bottom right
            xNeighbor = current.getX() + 1;
            yNeighbor = current.getY() + 1;
            if (xNeighbor > -1 && xNeighbor < N && yNeighbor > -1 && yNeighbor < N) {
                neighbor = state[xNeighbor][yNeighbor];
                if (neighbor.isPassable()) {
                    if (!neighbor.isClosed()) {
                        h = (Math.abs(xNeighbor - xGoal)
                                + (Math.abs(yNeighbor - yGoal))) * 10;
                        g = current.getG() + DIAG_COST;
                        f = g + h;
                        if (neighbor.getParent() == null) {
                            neighbor.setParent(current);
                        } else if (neighbor.getF() > f) {
                            neighbor.setParent(current);
                        }
                        neighbor.setG(g);
                        neighbor.setF(f);
                        openList.add(neighbor);
                    }
                }
            }
            current.setClosed(true);
        }
        //If the open list is emptied, there is no path. Return false.
        if (openList.isEmpty()) {
            return false;
        }//If we reach the goal before open list is empty. Build the path and
        //return True.
        else {
            buildPath(current);
        }
        return true;
    }

    /**
     * Once A* Search has finished, build the path of parent nodes
     *
     * @param current Node
     */
    public static void buildPath(Node current) {
        if (current.getParent() != null) {
            path.add(current);
            buildPath(current.getParent());

        } else {
            path.add(current);
        }
    }

    /**
     * Determine which nodes are start, goal, and are part of the path Mark them
     * with S, G, and O respectively
     *
     * @param state 2D Node array
     */
    public static void printPath(Node[][] state) {

        System.out.println();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = ' ';
            }
        }

        for (int i = 0; i < path.size(); i++) {
            if (state[path.get(i).getX()][path.get(i).getY()].isStart()) {
                board[path.get(i).getX()][path.get(i).getY()] = 'S';
            } else if (state[path.get(i).getX()][path.get(i).getY()].isGoal()) {
                board[path.get(i).getX()][path.get(i).getY()] = 'G';
            } else {
                board[path.get(i).getX()][path.get(i).getY()] = 'O';
            }
        }
        printState(state);
    }

    /**
     * Start running the program
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        createState();

        printPath(state);
        chooseStart(state);
        chooseGoal(state);
        if (!AStarSearch(state)) {
            System.out.println();
            System.out.println("There does not appear to be a path.");
        } else {
            printPath(state);
        }
    }

}

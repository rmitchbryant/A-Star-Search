/*
 * Class to create the node objects used for the A* Search algorithm
 */
package astar;

/**
 *
 * @author Robert M Bryant
 * @version 7/23/2020
 */
public class Node implements Comparable<Node> {
    
    private boolean passable;
    private boolean start;
    private boolean goal;
    private boolean closed;
    private Node parent;
    private int x, y, f, g, h;
    
    public Node(int x, int y, boolean passable, boolean closed){
        this.x = x;
        this.y = y;
        this.passable = passable;
        this.closed = closed;
        f = 0;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isPassable() {
        return passable;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isGoal() {
        return goal;
    }

    public void setGoal(boolean goal) {
        this.goal = goal;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
    
    public int compareTo(Node n) {
        if(this.f < n.f){
            return -1;
        }else if(this.f > n.f){
            return 1;
        }else
            return 0;
    }
    
}

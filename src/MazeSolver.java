/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    private final int[] nRows = {-1, 0, 1, 0}; // Row locations for neighbors in N, E, S, W order
    private final int[] nCols = {0, 1, 0, -1}; // Column locations for neighbors in N, E, S, W order

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // Create stack to store solution
        Stack<MazeCell> stack = new Stack<MazeCell>();
        // Start at the end cell
        MazeCell current = maze.getEndCell();
        stack.add(current);

        // Add cell's parent to the stack until find way to the start of the maze
        while(!current.equals(maze.getStartCell())) {
            current = current.getParent();
            stack.add(current);
        }

        // Reverse the stack by putting it into an arraylist
        ArrayList<MazeCell> sol = new ArrayList<MazeCell>();
        while (!stack.empty()) {
            sol.add(stack.pop());
        }
        return sol;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        Stack<MazeCell> s = new Stack<MazeCell>();
        // Set the first cell
        s.push(maze.getStartCell());
        MazeCell current = maze.getStartCell();
        current.setExplored(true);

        // While have not reached the end, continue finding neighbors and updating the stack
        while (!current.equals(maze.getEndCell()) && !s.empty()) {
            addValidNeighbors(s, s.pop());
        }

        // Return get_solution() to find the most efficient path found
        return getSolution();
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        Queue<MazeCell> q = new LinkedList<MazeCell>();
        // Set the first cell
        q.add(maze.getStartCell());
        MazeCell current = maze.getStartCell();
        current.setExplored(true);

        // While have not reached the end, continue finding neighbors and updating the queue
        while (!current.equals(maze.getEndCell()) && !q.isEmpty()) {
            addValidNeighbors(q, q.remove());
        }

        // Return get_solution() to find the most efficient path found
        return getSolution();
    }

    /**
     * Finds neighbors of a specific cell in the order:
     * NORTH, EAST, SOUTH, WEST
     * Stores parent cells and updates added cells to explored
     * @param s stack from DFS
     * @param cell cell to find neighbors of
     */
    public void addValidNeighbors(Stack s, MazeCell cell) {
        // Iterate through each neighbor (4 of them)
        for (int i = 0; i < 4; i++) {
            // Get row and col of neighbor
            int row = nRows[i] + cell.getRow();
            int col = nCols[i] + cell.getCol();

            // If it is a valid cell, add to stack, set parent as inputed cell, and set as explored
            if (maze.isValidCell(row, col)) {
                maze.getCell(row, col).setParent(cell);
                maze.getCell(row, col).setExplored(true);
                s.push(maze.getCell(row, col));
            }
        }
    }

    /**
     * Finds neighbors of a specific cell in the order:
     * NORTH, EAST, SOUTH, WEST
     * Stores parent cells and updates added cells to explored
     * @param q queue from BFS
     * @param cell cell to find neighbors of
     */
    public void addValidNeighbors(Queue q, MazeCell cell) {
        // Iterate through each neighbor (4 of them)
        for (int i = 0; i < 4; i++) {
            // Get row and col of neighbor
            int row = nRows[i] + cell.getRow();
            int col = nCols[i] + cell.getCol();

            // If it is a valid cell, add to queue, set parent as inputed cell, and set as explored
                if (maze.isValidCell(row, col)) {
                    maze.getCell(row, col).setParent(cell);
                    maze.getCell(row, col).setExplored(true);
                    q.add(maze.getCell(row, col));
                }
        }
    }
    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}

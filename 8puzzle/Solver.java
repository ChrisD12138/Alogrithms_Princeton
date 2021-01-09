import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class Solver {

    private final ArrayList<Board> solution = new ArrayList<>();
    private int moves = 0;
    // find a solution to the initial board (using the A* algorithm)

    private class Node implements Comparable<Node> {
        private final Board board;
        private final Node pre;

        public Node(Board board, Node pre) {
            this.board = board;
            this.pre = pre;
        }

        public int compareTo(Node node) {
            return this.board.manhattan() - node.board.manhattan();
        }
    }

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        MinPQ<Node> heap1 = new MinPQ<>();
        MinPQ<Node> heap2 = new MinPQ<>();
        Node ini1 = new Node(initial, null);
        Node ini2 = new Node(initial.twin(), null);
        heap1.insert(ini1);
        heap2.insert(ini2);
        while (!heap1.min().board.isGoal() && !heap2.min().board.isGoal()) {
            Node min1 = heap1.delMin();
            Node min2 = heap2.delMin();
            // System.out.println(min1.board.toString());
            // System.out.println(min2.board.toString());
            Iterable<Board> neighboerboards1 = min1.board.neighbors();
            Iterable<Board> neighboerboards2 = min2.board.neighbors();
            for (Board neighboerboard : neighboerboards1) {
                if (min1.pre == null || neighboerboard.equals(min1.pre.board)) {
                    Node neighboernode = new Node(neighboerboard, min1);
                    heap1.insert(neighboernode);
                }
            }
            for (Board neighboerboard : neighboerboards2) {
                if (min2.pre == null && neighboerboard.equals(min2.pre.board)) {
                    Node neighboernode = new Node(neighboerboard, min2);
                    heap2.insert(neighboernode);
                }
            }
        }

        if (heap1.min().board.isGoal()) {
            Node n = heap1.min();
            while (true) {
                solution.add(0, n.board);
                n = n.pre;
                if (n == null) break;
                moves++;
            }
        }
        else {
            moves = -1;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return moves != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        // In in = new In(args[0]);
        In in = new In("puzzle2x2-unsolvable3.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();

        // int [][] tiles = {{1,2,3},{4,5,6},{0,7,8}};
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
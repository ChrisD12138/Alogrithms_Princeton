/** *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;

public class Board {

    private final int[][] board;
    private final int dim;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        dim = tiles.length;
        board = new int[dim][dim];
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++) board[i][j] = tiles[i][j];
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dim).append("\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                sb.append(String.format("%2d ", board[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return dim;
    }


    // number of tiles out of place
    public int hamming() {
        int ham = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] != i*dim+j+1) ham++;
            }
        }
        ham--;
        return ham;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int md = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int num = board[i][j];
                if (num == 0) continue;
                int x = (num-1)/dim;
                int y = (num-1) % dim;
                md += Math.abs(x-i)+Math.abs(y-j);
            }
        }
        return md;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || y.getClass() != this.getClass()) return false;
        Board yBoard = (Board) y;
        if(yBoard.dim!=this.dim) return false;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (yBoard.board[i][j] != board[i][j]) return false;
            }
        }
        return true;
    }

    private void exchange(int i, int j, int ii, int jj) {
        int t  = board[i][j];
        board[i][j] = board[ii][jj];
        board[ii][jj] = t;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> res = new ArrayList<>();
        int[]neiX;
        int[]neiY;
        int zeroX = -1;
        int zeroY = -1;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                }
            }
        }
        if (zeroX == 0) {
            neiX = new int[1];
            neiX[0] = 1;
        }
        else if (zeroX == dim-1) {
            neiX = new int[1];
            neiX[0] = -1;
        }
        else {
            neiX = new int[2];
            neiX[0] = -1;
            neiX[1] = 1;
        }

        if (zeroY == 0) {
            neiY = new int[1];
            neiY[0] = 1;
        }
        else if (zeroY == dim-1) {
            neiY = new int[1];
            neiY[0] = -1;
        }
        else {
            neiY = new int[2];
            neiY[0] = -1;
            neiY[1] = 1;
        }

        for (int x:neiX) {
            Board newboard = new Board(board);
            newboard.exchange(zeroX+x, zeroY, zeroX, zeroY);
            res.add(newboard);
        }

        for (int y:neiY) {
            Board newboard = new Board(board);
            newboard.exchange(zeroX, zeroY+y, zeroX, zeroY);
            res.add(newboard);
        }
        return res;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twinBoard = new Board(board);
        for (int i = 0; i < 2; i++) {
            if (board[i][0] != 0 && board[i][1] != 0) {
                twinBoard.exchange(i, 0, i, 1);
                return twinBoard;
            }
        }
        throw new IllegalArgumentException();
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int [][] test = { {1, 2}, {3, 0}};
        Board b = new Board(test);
        System.out.println(b.toString());
    }
}

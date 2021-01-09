/* *****************************************************************************
 *  Name:              Xuannan Dong
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private int open_num=0;
    private int M;
    private int top = 0;
    private int bottom = M*M+1;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufExpectbottom;
    private boolean [][] open_grid; //用于每个点的开关状态。true表示开，false表示关闭。
    private int [][] neighbors = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
    public Percolation(int N){
        uf = new WeightedQuickUnionUF(N*N+2);
        ufExpectbottom = new WeightedQuickUnionUF(N*N+1);
        M = N;
        open_grid = new boolean[M][M];
        for(int i = 0; i < M; i++){
            for (int j = 0; j < M; j++) {
                open_grid[i][j] = false;
            }
        }
    }                // create N-by-N grid, with all sites initially blocked
    public void open(int row, int col){
        if (row>=M||col>=M||row<0||col<0) throw new IndexOutOfBoundsException();
        open_num +=1;
        open_grid[row][col] = true;
        if (row == 0){
            uf.union(xy2N(row, col),top);
            ufExpectbottom.union(xy2N(row, col),top);
        }

        if (row == M-1){
            uf.union(xy2N(row, col),bottom);
        }
        for (int [] neighbor:neighbors){
            int new_row = row+neighbor[0];
            int new_col = col+neighbor[1];
            if (new_row>=0 && new_row<M){
                if (new_col>=0 && new_col<M){
                    if (isOpen(new_row,new_col)){
                        uf.union(xy2N(row, col),xy2N(new_row,new_col));
                        ufExpectbottom.union(xy2N(row, col),xy2N(new_row,new_col));
                    }
                }
            }
        }
        return;
    }       // open the site (row, col) if it is not open already

    public boolean isOpen(int row, int col){
        return open_grid[row][col];
    }  // is the site (row, col) open?

    public boolean isFull(int row, int col){
        if (row>=M||col>=M||row<0||col<0) throw new IndexOutOfBoundsException();
        return ufExpectbottom.connected(xy2N(row,col),top);
    }  // is the site (row, col) full?

    public int numberOfOpenSites(){
        return open_num;
    }           // number of open sites

    public boolean percolates(){
        return uf.connected(top,bottom);
    }              // does the system percolate?

    public static void main(String[] args){

    }   // use for unit testing (not required, but keep this here for the autograder)

    private int xy2N(int row,int col){
        return row*M+col+1;
    }
}

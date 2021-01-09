import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
//import	java.lang.invoke.MethodHandleImpl.BindCaller.T;

public class PercolationStats {
    public static void main(String[] args) {
        int N = Integer.valueOf(args[0]);
        int T = Integer.valueOf(args[1]);
        PercolationStats ps = new PercolationStats(N,T);
        System.out.println("mean="+ps.mean());
        System.out.println("stddev="+ps.stddev());
        System.out.println("95% confidence interval="+"["+ps.confidenceLo()+","+ps.confidenceHi()+"]");
    }
    private double []result; //用于记录T次实验的结果
    public PercolationStats(int n, int trails){
        if (n<0||trails<0) throw new IndexOutOfBoundsException();
        result = new double[trails];
        for (int i = 0; i < trails; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()){
                int x =StdRandom.uniform(0, n);
                int y =StdRandom.uniform(0, n);
                // System.out.println(x+"#"+y);
                // System.out.println(i);
                perc.open(x,y);
            }
            result[i] = (double)perc.numberOfOpenSites()/(n*n);
        }
        // System.out.println(Arrays.toString(result));
    }   // perform T independent experiments on an N-by-N grid
    public double mean(){
        return StdStats.mean(result);
    }                                           // sample mean of percolation threshold
    public double stddev(){
        return StdStats.stddev(result);
    }                                         // sample standard deviation of percolation threshold
    public double confidenceLo(){
        return this.mean()-1.96*this.stddev()/(Math.pow(result.length,0.5));
    }                                  // low endpoint of 95% confidence interval
    public double confidenceHi(){
        return this.mean()+1.96*this.stddev()/(Math.pow(result.length,0.5));
    }                                 // high endpoint of 95% confidence interval
}

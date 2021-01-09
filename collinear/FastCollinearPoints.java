/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {

    private int numberOfSegments = 0;
    private final ArrayList<LineSegment> linesArray = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        int len = points.length;
        Point[] pointsFake = new Point[len];
        System.arraycopy(points, 0, pointsFake, 0, len);
        for (int i = 0; i < len; i++) { //以points[i] 为origin
            Point origin = points[i];
            Arrays.sort(pointsFake, origin.slopeOrder());
            LineSegment[] lines = searchForlines(origin, pointsFake);
            for (LineSegment line : lines) {
                linesArray.add(line);
                numberOfSegments++;
            }
        }
    } // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return numberOfSegments;
    }        // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] res = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) res[i] = linesArray.get(i);
        return res;
    }                // the line segments

    private LineSegment[] searchForlines(Point origin, Point[] points) {
        ArrayList<LineSegment> res = new ArrayList<>();
        ArrayList<Point> arr = new ArrayList<>();
        for (int i = 1; i < points.length; i++) {
            if (arr.isEmpty()) {
                arr.add(points[i]);
            }
            else if (slopeEqual(origin.slopeTo(points[i]), origin.slopeTo(points[i - 1]))) {
                arr.add(points[i]);
            }
            else if (arr.size() >= 3) {
                arr.add(origin);
                Point startPoint = Collections.min(arr);
                Point endPoint = Collections.max(arr);
                if (origin == startPoint) {
                    LineSegment ls = new LineSegment(startPoint, endPoint);
                    res.add(ls);
                }
                arr.clear();
                arr.add(points[i]);
            }
            else {
                arr.clear();
                arr.add(points[i]);
            }
        }
        if (arr.size() >= 3) {
            arr.add(origin);
            Point startPoint = Collections.min(arr);
            Point endPoint = Collections.max(arr);
            if (origin == startPoint) {
                LineSegment ls = new LineSegment(startPoint, endPoint);
                res.add(ls);
            }
        }
        LineSegment[] r = new LineSegment[res.size()];
        for (int i = 0; i < res.size(); i++) {
            r[i] = res.get(i);
        }
        return r;
    }

    private boolean slopeEqual(double slopeA, double slopeB) {
        if (slopeA == Double.POSITIVE_INFINITY && slopeB == Double.POSITIVE_INFINITY) {
            return true;
        }
        else return Math.abs(slopeA - slopeB) < 1e-10;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
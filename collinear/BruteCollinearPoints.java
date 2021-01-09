/**
 * ****************************************************************************
 * Name:
 * Date:
 * Description:
 * ***************************************************************************
 */

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private int numberOfSegments;
    private final ArrayList<LineSegment> lines = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {
        numberOfSegments = 0;
        int len = points.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                for (int k = j + 1; k < len; k++) {
                    for (int h = k + 1; h < len; h++) {
                        if (points[i].slopeTo(points[j]) == points[j].slopeTo(points[k]) &&
                                points[j].slopeTo(points[k]) == points[k].slopeTo(points[h])) {
                            numberOfSegments++;
                            Point[] pointForSeg = { points[i], points[j], points[k], points[h] };
                            Arrays.sort(pointForSeg);
                            lines.add(new LineSegment(pointForSeg[0], pointForSeg[3]));
                        }
                    }
                }
            }
        }
    }    // finds all line segments containing 4 points

    public int numberOfSegments() {
        return numberOfSegments;
    }        // the number of line segments

    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[lines.size()]);
    }                // the line segments

}

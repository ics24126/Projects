import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class BFS {


    private Set<Point> movedPixels;
    private Set<Point> visited = new HashSet<>();
    private int width;
    private int height;

    public BFS(List<Point> movementPoints, int width, int height) {
        this.movedPixels = new HashSet<>(movementPoints);
        this.width = width;
        this.height = height;
    }

    public List<List<Point>> findClusters() {
        List<List<Point>> clusters = new ArrayList<>();

        for (Point p : movedPixels) {
            if (!visited.contains(p)) {
                clusters.add(bfsFrom(p));
            }
        }

        return clusters;
    }

    private List<Point> bfsFrom(Point start) {
        List<Point> cluster = new ArrayList<>();
        Queue<Point> queue = new ArrayDeque<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Point p = queue.remove();
            cluster.add(p);

            for (Point n : getNeighbors(p)) {
                if (movedPixels.contains(n) && !visited.contains(n)) {
                    visited.add(n);
                    queue.add(n);
                }
            }
        }

        return cluster;
    }

    private List<Point> getNeighbors(Point p) {
        List<Point> list = new ArrayList<>();

        int x = p.x;
        int y = p.y;


        if (x + 1 < width)
            list.add(new Point(x + 1, y));


        if (x - 1 >= 0)
            list.add(new Point(x - 1, y));


        if (y + 1 < height)
            list.add(new Point(x, y + 1));

        if (y - 1 >= 0)
            list.add(new Point(x, y - 1));

        return list;
    }


    public List<List<Point>> filterClusters(List<List<Point>> clusters, int pixelThres) {
        List<List<Point>> result = new ArrayList<>();

        for (List<Point> c : clusters) {
            if (c.size() >= pixelThres) {
                result.add(c);
            }
        }

        return result;
    }

}





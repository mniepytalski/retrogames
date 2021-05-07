package pl.cbr.games.snake.geom2d;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Collision {

    public boolean check(List<Point> points) {
        Set<Point> allItems = new HashSet<>();
        Set<Point> duplicates = points.stream().filter(n -> !allItems.add(n)).collect(Collectors.toSet());
        return !duplicates.isEmpty();
    }

    public boolean check(List<Point> points1, List<Point> points2) {
        Set<Point> allItems = new HashSet<>(points1);
        Set<Point> duplicates = points2.stream().filter(n -> !allItems.add(n)).collect(Collectors.toSet());
        return !duplicates.isEmpty();
    }
}

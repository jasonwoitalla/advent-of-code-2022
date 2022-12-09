import java.util.HashMap;
import java.util.Scanner;

import utils.Point;

public class Day9 extends AbstractDay{

    private record Direction(int x, int y) {}

    public Day9() {
        super("inputs/day09.txt", 2);
    }

    @Override
    protected void part1(Scanner scan) {
        HashMap<Point, Integer> visited = new HashMap<>();

        Point head = new Point(0, 0);
        Point tail = new Point(0, 0);
        visited.put(new Point(tail.x(), tail.y()), 1);

        while(scan.hasNextLine()) {
            String command = scan.next();
            int amount = scan.nextInt();
            
            Direction dir = new Direction(0, 0);
            if(command.equals("U")) {
                dir = new Direction(0, 1);
            } else if(command.equals("D")) {
                dir = new Direction(0, -1);
            } else if(command.equals("L")) {
                dir = new Direction(-1, 0);
            } else if(command.equals("R")) {
                dir = new Direction(1, 0);
            }

            for(int i = 0; i < amount; i++) {
                moveHead(dir, head, tail);
                if(visited.get(tail) == null) {
                    visited.put(new Point(tail.x(), tail.y()), 1);
                } else {
                    visited.put(new Point(tail.x(), tail.y()), visited.get(tail)+1);
                }
            }
        }

        int amountVisited = visited.keySet().size();
        System.out.println("[Part 1] Amount of places the tail went: " + amountVisited);
        //System.out.println("[Part 1] All of the visited places: " + visited.keySet());
    }

    private void moveHead(Direction dir, Point head, Point tail) {
        head.move(dir.x(), dir.y());

        if(!tail.isTouching(head)) { // we must move the tail
            int moveX = head.x() - tail.x();
            int moveY = head.y() - tail.y();

            if(Math.abs(moveX) == 2)
                moveX /= 2;
            if(Math.abs(moveY) == 2)
                moveY /= 2;

            tail.move(moveX, moveY);

            //System.out.println("Head: " + head + " Tail: " + tail);
            //System.out.println("Moved the tail, are they touching now -> " + tail.isTouching(head));
        }
    }

    @Override
    protected void part2(Scanner scan) {
        HashMap<Point, Integer> visited = new HashMap<>();

        //Point head = new Point(0, 0);
        //Point tail = new Point(0, 0);
        int length = 10;
        Point[] rope = new Point[length];
        for(int i = 0; i < length; i++) {
            rope[i] = new Point(0, 0);
        }
        visited.put(new Point(rope[length-1].x(), rope[length-1].y()), 1);

        while(scan.hasNextLine()) {
            String command = scan.next();
            int amount = scan.nextInt();
            
            Direction dir = new Direction(0, 0);
            if(command.equals("U")) {
                dir = new Direction(0, 1);
            } else if(command.equals("D")) {
                dir = new Direction(0, -1);
            } else if(command.equals("L")) {
                dir = new Direction(-1, 0);
            } else if(command.equals("R")) {
                dir = new Direction(1, 0);
            }

            for(int i = 0; i < amount; i++) {
                moveHead(dir, rope);
                if(visited.get(rope[length-1]) == null) {
                    visited.put(new Point(rope[length-1].x(), rope[length-1].y()), 1);
                } else {
                    visited.put(new Point(rope[length-1].x(), rope[length-1].y()), visited.get(rope[length-1])+1);
                }
            }
        }

        int amountVisited = visited.keySet().size();
        System.out.println("[Part 2] Amount of places the tail went: " + amountVisited);
    }

    private void moveHead(Direction dir, Point[] rope) {
        rope[0].move(dir.x(), dir.y());
        for(int i = 1; i < rope.length; i++) {
            if(!rope[i].isTouching(rope[i-1])) { // move this knot
                int moveX = rope[i-1].x() - rope[i].x();
                int moveY = rope[i-1].y() - rope[i].y();

                // Handle diagonal movement
                if(Math.abs(moveX) == 2)
                    moveX /= 2;
                if(Math.abs(moveY) == 2)
                    moveY /= 2;

                rope[i].move(moveX, moveY);
            }
        }
    }
    
}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class AbstractDay {
    
    public AbstractDay(String path, int part) {
        try {
            Scanner scan = new Scanner(new File(path));
            final long startTime = System.currentTimeMillis();
            if(part == 1) {
                System.out.println("[AOC] Running Part 1");
                part1(scan);
            } else if(part == 2) {
                System.out.println("[AOC] Running Part 2");
                part2(scan);
            }
            final long endTime = System.currentTimeMillis();
            System.out.println("[AOC] Finished, ran in: " + (endTime - startTime) + "ms");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected abstract void part1(Scanner scan);
    protected abstract void part2(Scanner scan);
}

import java.util.Scanner;

public class Day4 extends AbstractDay{
    
    public Day4(String path) {
        //super(path, 1);
        super(path, 2);
    }

    @Override
    protected void part1(Scanner scan) {
        int totalOverlap = 0;

        while(scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] pairs = line.split(",");
            String[] pair1 = pairs[0].split("-");
            String[] pair2 = pairs[1].split("-");
            int[] idsPair1 = new int[]{Integer.parseInt(pair1[0]), Integer.parseInt(pair1[1])};
            int[] idsPair2 = new int[]{Integer.parseInt(pair2[0]), Integer.parseInt(pair2[1])};

            // check if group 2 is covered by group 1
            if(idsPair2[0] >= idsPair1[0] && idsPair2[0] < idsPair1[1] &&
                    idsPair2[1] <= idsPair1[1]) {
                totalOverlap++;
            }

            // check if group 1 is covered by group 2
            if(idsPair1[0] >= idsPair2[0] && idsPair1[0] < idsPair2[1] &&
                    idsPair1[1] <= idsPair2[1]) {
                totalOverlap++;
            }
        }

        System.out.println("[PART 1] Pairs that overlap: " + totalOverlap);
    }

    @Override
    protected void part2(Scanner scan) {
        int totalOverlap = 0;

        while(scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] pairs = line.split(",");
            String[] pair1 = pairs[0].split("-");
            String[] pair2 = pairs[1].split("-");
            int[] idsPair1 = new int[]{Integer.parseInt(pair1[0]), Integer.parseInt(pair1[1])};
            int[] idsPair2 = new int[]{Integer.parseInt(pair2[0]), Integer.parseInt(pair2[1])};

            // check if group 2 overlaps by group 1
            if(idsPair2[0] >= idsPair1[0] && idsPair2[0] <= idsPair1[1]) {
                totalOverlap++;
            }else if(idsPair1[0] >= idsPair2[0] && idsPair1[0] <= idsPair2[1]) { // check if group 1 overlaps by group 2
                totalOverlap++;
            }
        }

        System.out.println("[PART 2] Pairs that overlap: " + totalOverlap);
    }

    
}

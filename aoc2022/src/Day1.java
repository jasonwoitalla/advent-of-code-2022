import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day1 {
    
    public static void main(String[] args) {
        int[] topCalories = new int[3];
        int elf = 1;
        Scanner scan;
        try {
            scan = new Scanner(new File("inputs/day1.txt"));
            int count = 0;
            while(scan.hasNextLine()) {
                String line = scan.nextLine();
                if(line == "") {
                    if(count > topCalories[0]) { // this count needs to go into the list
                        topCalories[0] = count;
                        int index = 0;
                        while(index < topCalories.length-1 && topCalories[index] > topCalories[index+1]) {
                            int temp = topCalories[index];
                            topCalories[index] = topCalories[index+1];
                            topCalories[index+1] = temp;
                            index++;
                        }
                    }
                    elf++;
                    count = 0;
                } else {
                    count += Integer.parseInt(line);
                }
            }

            int sum = topCalories[0] + topCalories[1] + topCalories[2];
            System.out.println("Highest calories: " + sum);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

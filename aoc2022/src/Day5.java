import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Prompt:
 * 
 */
public class Day5 extends AbstractDay{

    public Day5(String path) {
        //super(path, 1);
        super(path, 2);
    }

    Stack<Character>[] initStacks(Scanner scan) {
        // STEP 1: We first need to create our array of stacks that are in the
        // file. We could do this with the known 9 crate stacks
        String line = scan.nextLine();
        int totalStacks = 0;
        ArrayList<ArrayList<Character>> rawStacks = new ArrayList<>(); 
        while(line != "") {
            int stack = 0;
            for(int i = 1; i < line.length(); i+=4) {
                // Check if there is a stack here
                if(rawStacks.size() < stack + 1) {
                    rawStacks.add(new ArrayList<Character>());
                }

                if(Character.isAlphabetic(line.charAt(i))) {
                    rawStacks.get(stack).add(line.charAt(i));
                }
                stack++;
                if(stack > totalStacks)
                    totalStacks = stack;
            }
            line = scan.nextLine();
        }

        // STEP 2: Initialize our stack structure to make the third part easier
        Stack<Character>[] crates = new Stack[totalStacks];
        for(int i = 0; i < crates.length; i++) {
            // since stacks are LIFO, we have to add the crates at the
            // bottom into the stack first.
            crates[i] = new Stack<>();
            for(int j = rawStacks.get(i).size() - 1; j >= 0; j--) {
                crates[i].push(rawStacks.get(i).get(j));
            }
        }

        return crates;
    }

    @Override
    protected void part1(Scanner scan) {
        Stack<Character>[] crates = initStacks(scan);

        // STEP 3: Finally we can parse out all the moves to find the final stacks
        while(scan.hasNextLine()) {
            // The lines are written in quite a difficult format, we will
            // convert into an easier format to parse first.
            String line = scan.nextLine();
            line = line.replace("move ", "").replace(" from ", ":");
            line = line.replace(" to ", ",");

            // We have converted the line into the following format:
            // <number>:<from>,<to>
            String[] numberTo = line.split(":");
            String[] fromTo = numberTo[1].split(",");

            int amount = Integer.parseInt(numberTo[0]);
            int startStack = Integer.parseInt(fromTo[0]);
            int goalStack = Integer.parseInt(fromTo[1]);

            // Now we will move the amount of objects from start to goal
            for(int i = 0; i < amount; i++) {
                if(!crates[startStack-1].isEmpty())
                    crates[goalStack-1].push(crates[startStack-1].pop());
            }
        }

        // STEP 4: Construct the final message by peeking at all of our stacks
        String finalMessage = "";
        for(int i = 0; i < crates.length; i++) {
            finalMessage += crates[i].peek();
        }

        System.out.println("[PART 1] Top of all of our stacks is: " + finalMessage);
    }

    private void printStacks(Stack<Character>[] stacks) {
        for(int i = 0; i < stacks.length; i++) {
            System.out.println("Stack " + (i+1) + " has: " + stacks[i]);
        }
    }

    @Override
    protected void part2(Scanner scan) {
        Stack<Character>[] stacks = initStacks(scan);
        while(scan.hasNextLine()) {
            // The lines are written in quite a difficult format, we will
            // convert into an easier format to parse first.
            String line = scan.nextLine();
            line = line.replace("move ", "").replace(" from ", ":");
            line = line.replace(" to ", ",");

            // We have converted the line into the following format:
            // <number>:<from>,<to>
            String[] numberTo = line.split(":");
            String[] fromTo = numberTo[1].split(",");

            int amount = Integer.parseInt(numberTo[0]);
            int startStack = Integer.parseInt(fromTo[0]) - 1;
            int goalStack = Integer.parseInt(fromTo[1]) - 1;

            // Remove the crates and store them in an array
            Character[] moving = new Character[amount];
            for(int i = 0; i < amount; i++) {
                if(!stacks[startStack].isEmpty()) {
                    moving[amount-i-1] = stacks[startStack].pop();
                }
            }

            // Move the crates in the array to the goal stack
            for(int i = 0; i < amount; i++) {
                stacks[goalStack].push(moving[i]);
            }
        }

        // STEP 4: Construct the final message by peeking at all of our stacks
        String finalMessage = "";
        for(int i = 0; i < stacks.length; i++) {
            finalMessage += stacks[i].peek();
        }

        System.out.println("[PART 2] Top of all of our stacks is: " + finalMessage);
    }
    
}

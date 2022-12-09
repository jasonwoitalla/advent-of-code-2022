import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Prompt:
 * The expedition can depart as soon as the final supplies have been unloaded from the ships. Supplies are stored in stacks of marked crates, but because the needed supplies are buried under many other crates, the crates need to be rearranged.

The ship has a giant cargo crane capable of moving crates between stacks. To ensure none of the crates get crushed or fall over, the crane operator will rearrange them in a series of carefully-planned steps. After the crates are rearranged, the desired crates will be at the top of each stack.

The Elves don't want to interrupt the crane operator during this delicate procedure, but they forgot to ask her which crate will end up where, and they want to be ready to unload them as soon as possible so they can embark.

They do, however, have a drawing of the starting stacks of crates and the rearrangement procedure (your puzzle input). For example:

    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2

In this example, there are three stacks of crates. Stack 1 contains two crates: crate Z is on the bottom, and crate N is on top. Stack 2 contains three crates; from bottom to top, they are crates M, C, and D. Finally, stack 3 contains a single crate, P.

Then, the rearrangement procedure is given. In each step of the procedure, a quantity of crates is moved from one stack to a different stack. In the first step of the above rearrangement procedure, one crate is moved from stack 2 to stack 1, resulting in this configuration:

[D]        
[N] [C]    
[Z] [M] [P]
 1   2   3 

In the second step, three crates are moved from stack 1 to stack 3. Crates are moved one at a time, so the first crate to be moved (D) ends up below the second and third crates:

        [Z]
        [N]
    [C] [D]
    [M] [P]
 1   2   3

Then, both crates are moved from stack 2 to stack 1. Again, because crates are moved one at a time, crate C ends up below crate M:

        [Z]
        [N]
[M]     [D]
[C]     [P]
 1   2   3

Finally, one crate is moved from stack 1 to stack 2:

        [Z]
        [N]
        [D]
[C] [M] [P]
 1   2   3

The Elves just need to know which crate will end up on top of each stack; in this example, the top crates are C in stack 1, M in stack 2, and Z in stack 3, so you should combine these together and give the Elves the message CMZ.

After the rearrangement procedure completes, what crate ends up on top of each stack?

Your puzzle answer was VRWBSFZWM.
--- Part Two ---

As you watch the crane operator expertly rearrange the crates, you notice the process isn't following your prediction.

Some mud was covering the writing on the side of the crane, and you quickly wipe it away. The crane isn't a CrateMover 9000 - it's a CrateMover 9001.

The CrateMover 9001 is notable for many new and exciting features: air conditioning, leather seats, an extra cup holder, and the ability to pick up and move multiple crates at once.

Again considering the example above, the crates begin in the same configuration:

    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

Moving a single crate from stack 2 to stack 1 behaves the same as before:

[D]        
[N] [C]    
[Z] [M] [P]
 1   2   3 

However, the action of moving three crates from stack 1 to stack 3 means that those three moved crates stay in the same order, resulting in this new configuration:

        [D]
        [N]
    [C] [Z]
    [M] [P]
 1   2   3

Next, as both crates are moved from stack 2 to stack 1, they retain their order as well:

        [D]
        [N]
[C]     [Z]
[M]     [P]
 1   2   3

Finally, a single crate is still moved from stack 1 to stack 2, but now it's crate C that gets moved:

        [D]
        [N]
        [Z]
[M] [C] [P]
 1   2   3

In this example, the CrateMover 9001 has put the crates in a totally different order: MCD.

Before the rearrangement process finishes, update your simulation so that the Elves know where they should stand to be ready to unload the final supplies. After the rearrangement procedure completes, what crate ends up on top of each stack?

Your puzzle answer was RBTWJWMCF.
 */
public class Day5 extends AbstractDay{

    public Day5(String path) {
        super(path, 1);
        //super(path, 2);
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

    // private void printStacks(Stack<Character>[] stacks) {
    //     for(int i = 0; i < stacks.length; i++) {
    //         System.out.println("Stack " + (i+1) + " has: " + stacks[i]);
    //     }
    // }

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

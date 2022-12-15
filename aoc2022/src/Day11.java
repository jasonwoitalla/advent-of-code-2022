import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Stream;

import utils.Monkey;
import utils.Expression;
import utils.Expression.TOKEN;

public class Day11 extends AbstractDay{

    HashMap<String, Long> env;

    public Day11() {
        super("inputs/day11.txt", 1);
    }

    @Override
    protected void part1(Scanner scan) {
        env = new HashMap<>();
        LinkedList<Monkey> monkeys = parseMonkeys(scan);

        for(int m = 0; m < monkeys.size(); m++) {
            System.out.println("Monkey " + m + ": " + monkeys.get(m).getOperation());
            System.out.println("Monkey " + m + ": " + monkeys.get(m).getFunction());
        }

        int numRounds = 10000;
        for(int i = 0; i < numRounds; i++) {
            // System.out.println("Looking at a round of monkeys: " + i);
            for(int m = 0; m < monkeys.size(); m++) {
                while(monkeys.get(m).hasItems()) {
                    long item = monkeys.get(m).getOperatedItem(env);
                    int index = (int)monkeys.get(m).getNextMonkeyPass(env);
                    monkeys.get(index).addItem(item);
                }
            }
        }

        for(int m = 0; m < monkeys.size(); m++) {
            System.out.println("Monkey " + m + ": " + monkeys.get(m).getInspected());
        }

        System.out.println("Monkey Business: " + getMonkeyBusiness(monkeys));
    }

    private LinkedList<Monkey> parseMonkeys(Scanner scan) {
        LinkedList<Monkey> monkeys = new LinkedList<>();
        Monkey curMonkey = new Monkey(-1);
        Expression operation = new Expression(TOKEN.NONE, 0);
        int test = 0, testTrue = 0;
        long modProduct = 1;
        boolean readingTest = false;

        while(scan.hasNextLine()) {
            String command = scan.next();
            if(command.equals("Monkey")) {
                System.out.println("Parsed in a monkey, adding it to the list");
                String monkey = scan.next();
                monkey = monkey.substring(0, monkey.length()-1);
                curMonkey = new Monkey(Integer.parseInt(monkey));
            } else if(command.equals("items:")) {
                String[] startingItems = scan.nextLine().replace(" ", "").split(",");
                Long[] items = Stream.of(startingItems).map(Long::valueOf).toArray(Long[]::new);
                curMonkey.setItems(new LinkedList<>(Arrays.asList(items)));
            } else if(command.equals("Operation:")) {
                scan.next();
                scan.next();

                String val1 = scan.next();
                String op = scan.next();
                String val2 = scan.next();
                operation = getMonkeyOperation(op, val1, val2);
                curMonkey.setOperation(operation);

            } else if(command.equals("divisible")) {
                scan.next();
                test = scan.nextInt();
                modProduct *= test;
                readingTest = true;
            } else if(command.equals("monkey")) {
                if(readingTest) {
                    System.out.println("Reading in the true value");
                    testTrue = scan.nextInt();
                    readingTest = false;
                } else {
                    System.out.println("Finished monkey parsing, adding it to the list");
                    Expression function = getMonkeyExpression(operation, test, testTrue, scan.nextInt());
                    curMonkey.setFunction(function);
                    readingTest = false;
                    monkeys.add(curMonkey);
                    curMonkey = null;
                }
            }
        }
        env.put("modProduct", modProduct);
        System.out.println("Mod product: " + modProduct);
        return monkeys;
    }

    private Expression getIntExpression(String val) {
        if(val.equals("old")) {
            return new Expression(TOKEN.VAR, "old");
        } else {
            return new Expression(TOKEN.INT, Integer.parseInt(val));
        }
    }

    private Expression getMonkeyOperation(String op, String val1, String val2) {
        Expression expr1 = getIntExpression(val1);
        Expression expr2 = getIntExpression(val2);
        TOKEN operation = TOKEN.NONE;

        if(op.equals("*")) {
            operation = TOKEN.MULT;
        } else if(op.equals("+")) {
            operation = TOKEN.ADD;
        }

        return new Expression(TOKEN.MOD, 
            new Expression(operation, expr1, expr2),
            new Expression(TOKEN.VAR, "modProduct"));
    }

    private Expression getMonkeyExpression(Expression op, int test, int trueVal, int falseVal) {
        return new Expression(TOKEN.IF, 
        new Expression(TOKEN.DIVISIBLE, 
            op, 
            new Expression(TOKEN.INT, test)),
        new Expression(TOKEN.INT, trueVal),
        new Expression(TOKEN.INT, falseVal));
    }

    public long getMonkeyBusiness(LinkedList<Monkey> monkeys) {
        long[] highestBusiness = new long[2];

        for(int i = 0; i < monkeys.size(); i++) {
            if(monkeys.get(i).getInspected() > highestBusiness[0]) {
                highestBusiness[0] = monkeys.get(i).getInspected(); // insert into the array

                if(highestBusiness[0] > highestBusiness[1]) { // swap to keep array sorted
                    long temp = highestBusiness[0];
                    highestBusiness[0] = highestBusiness[1];
                    highestBusiness[1] = temp;
                }
            }
        }

        return highestBusiness[0] * highestBusiness[1];
    }

    @Override
    protected void part2(Scanner scan) {
    }
    
}

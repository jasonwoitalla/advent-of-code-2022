package utils;

import java.util.HashMap;
import java.util.LinkedList;

public class Monkey {
    
    private int id;
    private long inspected;
    private LinkedList<Long> items;
    private Expression operation;
    private Expression function;

    public Monkey(int id) {
        this.id = id;
        inspected = 0;
    }

    public int getId() {
        return id;
    }

    public long getInspected() {
        return inspected;
    }

    public void setInspected(int inspected) {
        this.inspected = inspected;
    }

    public void setItems(LinkedList<Long> items) {
        this.items = items;
    }

    public Expression getFunction() {
        return function;
    }

    public void setFunction(Expression function) {
        this.function = function;
    }

    public Expression getOperation() {
        return operation;
    }

    public void setOperation(Expression operation) {
        this.operation = operation;
    }

    public void addItem(long item) {
        items.add(item);
    }

    public long removeFirstItem() {
        return items.removeFirst();
    }

    public boolean hasItems() {
        return items.size() > 0;
    }

    public long firstItem() {
        return items.getFirst();
    }

    public long getOperatedItem(HashMap<String, Long> env) {
        env.put("old", firstItem());
        return operation.evaluate(env).intRes();
    }

    public long getNextMonkeyPass(HashMap<String, Long> env) {
        inspected++;
        env.put("old", removeFirstItem());
        return function.evaluate(env).intRes();
    }
}

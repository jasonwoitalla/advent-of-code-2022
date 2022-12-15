package utils;

import java.util.HashMap;

public class Expression {
    
    public enum TOKEN {
        ADD,
        MULT,
        DIV,
        VAR,
        IF,
        INT,
        BOOL,
        DIVISIBLE,
        MOD,
        NONE
    }

    public record Result(long intRes, boolean boolRes) {}

    private TOKEN token;
    private Expression arg1, arg2, arg3;
    private int value;
    private boolean boolValue;
    private String name;

    public Expression(TOKEN token, Expression arg1, Expression arg2, Expression arg3) {
        this.token = token;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
    }

    public Expression(TOKEN token, Expression arg1, Expression arg2) {
        this(token, arg1, arg2, null);
    }

    public Expression(TOKEN token, Expression arg1) {
        this(token, arg1, null);
    }

    public Expression(TOKEN token, int value) {
        this.token = token;
        this.value = value;
    }

    public Expression(TOKEN token, String name) {
        this.token = token;
        this.name = name;
    }

    public Result evaluate(HashMap<String, Long> env) {
        switch(token) {
            case ADD:
                Result i1 = arg1.evaluate(env);
                Result i2 = arg2.evaluate(env);
                return new Result(i1.intRes() + i2.intRes(), true);
            case BOOL:
                return new Result(0, boolValue);
            case DIV:
                i1 = arg1.evaluate(env);
                i2 = arg2.evaluate(env);
                return new Result((i1.intRes() / i2.intRes()), true);
            case IF:
                Result condition = arg1.evaluate(env);
                if(condition.boolRes()) {
                    return arg2.evaluate(env);
                } else {
                    return arg3.evaluate(env);
                }
            case INT:
                return new Result(value, false);
            case MULT:
                i1 = arg1.evaluate(env);
                i2 = arg2.evaluate(env);
                return new Result(i1.intRes() * i2.intRes(), true);
            case VAR:
                return new Result(env.get(name), false);
            case DIVISIBLE:
                i1 = arg1.evaluate(env);
                i2 = arg2.evaluate(env);
                return new Result(i1.intRes(), i1.intRes() % i2.intRes() == 0);
            case MOD:
                i1 = arg1.evaluate(env);
                i2 = arg2.evaluate(env);
                return new Result(i1.intRes() % i2.intRes(), false);
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        if(token == TOKEN.IF) {
            return "IF(" + arg1 + " " + arg2 + " " + arg3 + ")";
        } else if(token == TOKEN.INT) {
            return "INT(" + value + ")";
        } else if(token == TOKEN.VAR) {
            return "VAR('" + name + "')";
        } else {
            return token.name() + "(" + arg1 + " " + arg2 + ")";
        }
    }
}

package io.github.medjed.jsonpathcompiler.expressions.filter;

import io.github.medjed.jsonpathcompiler.InvalidPathException;

public enum LogicalOperator {

    AND("&&"),
    OR("||");

    private final String operatorString;

    LogicalOperator(String operatorString) {
        this.operatorString = operatorString;
    }

    public String getOperatorString() {
        return operatorString;
    }

    @Override
    public String toString() {
        return operatorString;
    }

    public static LogicalOperator fromString(String operatorString){
        if(AND.operatorString.equals(operatorString)) return AND;
        else if(OR.operatorString.equals(operatorString)) return OR;
        else throw new InvalidPathException("Failed to parse operator " + operatorString);
    }
}

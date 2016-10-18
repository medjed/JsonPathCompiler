package io.github.medjed.jsonpathcompiler.expressions.filter;

import io.github.medjed.jsonpathcompiler.Predicate;

public abstract class ExpressionNode implements Predicate
{

    public static ExpressionNode createExpressionNode(ExpressionNode right, LogicalOperator operator,  ExpressionNode left){
        if(operator == LogicalOperator.AND){
            if((right instanceof LogicalExpressionNode) && ((LogicalExpressionNode)right).getOperator() == LogicalOperator.AND ){
                LogicalExpressionNode len = (LogicalExpressionNode) right;
                return len.append(left);
            } else {
                return LogicalExpressionNode.createLogicalAnd(left, right);
            }
        } else {
            if((right instanceof LogicalExpressionNode) && ((LogicalExpressionNode)right).getOperator() == LogicalOperator.OR ){
                LogicalExpressionNode len = (LogicalExpressionNode) right;
                return len.append(left);
            } else {
                return LogicalExpressionNode.createLogicalOr(left, right);
            }
        }
    }
}

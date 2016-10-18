package io.github.medjed.jsonpathcompiler.expressions.filter;

import io.github.medjed.jsonpathcompiler.Predicate;

public interface Evaluator {
    boolean evaluate(ValueNode left, ValueNode right, Predicate.PredicateContext ctx);
}
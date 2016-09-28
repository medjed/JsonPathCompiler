package com.dena.analytics.jsonpathcompiler.expressions.filter;

import com.dena.analytics.jsonpathcompiler.Predicate;

public interface Evaluator {
    boolean evaluate(ValueNode left, ValueNode right, Predicate.PredicateContext ctx);
}
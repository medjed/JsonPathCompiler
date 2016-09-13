package com.github.kysnm.jsonpathcompiler.internal.filter;

import com.github.kysnm.jsonpathcompiler.Predicate;

public interface Evaluator {
    boolean evaluate(ValueNode left, ValueNode right, Predicate.PredicateContext ctx);
}
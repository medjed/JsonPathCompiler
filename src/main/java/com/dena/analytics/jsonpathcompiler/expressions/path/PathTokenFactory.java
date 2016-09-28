package com.dena.analytics.jsonpathcompiler.expressions.path;

import com.dena.analytics.jsonpathcompiler.Predicate;
import com.dena.analytics.jsonpathcompiler.expressions.function.Parameter;

import java.util.Collection;
import java.util.List;

import static java.util.Collections.singletonList;

public class PathTokenFactory {

    public static RootPathToken createRootPathToken(char token) {
        return new RootPathToken(token);
    }

    public static PathToken createSinglePropertyPathToken(String property, boolean singleQuote) {
        return new PropertyPathToken(singletonList(property), singleQuote);
    }

    public static PathToken createPropertyPathToken(List<String> properties, boolean singleQuote) {
        return new PropertyPathToken(properties, singleQuote);
    }

    public static PathToken createSliceArrayPathToken(final ArraySliceOperation arraySliceOperation) {
        return new ArrayPathToken(arraySliceOperation);
    }

    public static PathToken createIndexArrayPathToken(final ArrayIndexOperation arrayIndexOperation) {
        return new ArrayPathToken(arrayIndexOperation);
    }

    public static PathToken createWildCardPathToken() {
        return new WildcardPathToken();
    }

    public static PathToken crateScanToken() {
        return new ScanPathToken();
    }

    public static PathToken createPredicatePathToken(Collection<Predicate> predicates) {
        return new PredicatePathToken(predicates);
    }

    public static PathToken createPredicatePathToken(Predicate predicate) {
        return new PredicatePathToken(predicate);
    }

    public static PathToken createFunctionPathToken(String function, List<Parameter> parameters) {
        return new FunctionPathToken(function, parameters);
    }
}

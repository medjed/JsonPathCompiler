package com.github.kysnm.jsonpathcompiler.expressions.function.text;

import com.github.kysnm.jsonpathcompiler.expressions.EvaluationContext;
import com.github.kysnm.jsonpathcompiler.expressions.PathRef;
import com.github.kysnm.jsonpathcompiler.expressions.function.Parameter;
import com.github.kysnm.jsonpathcompiler.expressions.function.PathFunction;

import java.util.List;

/**
 * Provides the length of a JSONArray Object
 *
 * Created by mattg on 6/26/15.
 */
public class Length implements PathFunction {

    @Override
    public Object invoke(String currentPath, PathRef parent, Object model, EvaluationContext ctx, List<Parameter> parameters) {
        if(ctx.configuration().jsonProvider().isArray(model)){
            return ctx.configuration().jsonProvider().length(model);
        } else if(ctx.configuration().jsonProvider().isMap(model)){
            return ctx.configuration().jsonProvider().length(model);
        }
        return null;
    }
}
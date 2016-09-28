package com.dena.analytics.jsonpathcompiler.expressions.function;

import com.dena.analytics.jsonpathcompiler.expressions.EvaluationContext;
import com.dena.analytics.jsonpathcompiler.expressions.PathRef;

import java.util.List;

/**
 * Defines the default behavior which is to return the model that is provided as input as output
 *
 * Created by mattg on 6/26/15.
 */
public class PassthruPathFunction implements PathFunction {

    @Override
    public Object invoke(String currentPath, PathRef parent, Object model, EvaluationContext ctx, List<Parameter> parameters) {
        return model;
    }
}

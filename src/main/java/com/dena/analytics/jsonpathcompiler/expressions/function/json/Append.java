package com.dena.analytics.jsonpathcompiler.expressions.function.json;

import com.dena.analytics.jsonpathcompiler.expressions.EvaluationContext;
import com.dena.analytics.jsonpathcompiler.expressions.PathRef;
import com.dena.analytics.jsonpathcompiler.expressions.function.Parameter;
import com.dena.analytics.jsonpathcompiler.expressions.function.PathFunction;
import com.dena.analytics.jsonpathcompiler.spi.json.JsonProvider;

import java.util.List;

/**
 * Appends JSON structure to the current document so that you can utilize the JSON added thru another function call.
 * If there are multiple parameters then this function call will add each element that is json to the structure
 *
 * Created by mgreenwood on 12/14/15.
 */
public class Append implements PathFunction
{
    @Override
    public Object invoke(String currentPath, PathRef parent, Object model, EvaluationContext ctx, List<Parameter> parameters) {
        JsonProvider jsonProvider = ctx.configuration().jsonProvider();
        if (parameters != null && parameters.size() > 0) {
            for (Parameter param : parameters) {
                if (jsonProvider.isArray(model)) {
                    int len = jsonProvider.length(model);
                    jsonProvider.setArrayIndex(model, len, param.getCachedValue());
                }
            }
        }
        return model;
    }
}

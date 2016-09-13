package com.github.kysnm.jsonpathcompiler.internal.function.text;

import com.github.kysnm.jsonpathcompiler.internal.EvaluationContext;
import com.github.kysnm.jsonpathcompiler.internal.PathRef;
import com.github.kysnm.jsonpathcompiler.internal.function.Parameter;
import com.github.kysnm.jsonpathcompiler.internal.function.PathFunction;

import java.util.List;

/**
 * String function concat - simple takes a list of arguments and/or an array and concatenates them together to form a
 * single string
 *
 * Created by mgreenwood on 12/11/15.
 */
public class Concatenate implements PathFunction {
    @Override
    public Object invoke(String currentPath, PathRef parent, Object model, EvaluationContext ctx, List<Parameter> parameters) {
        StringBuffer result = new StringBuffer();
        if(ctx.configuration().jsonProvider().isArray(model)){
            Iterable<?> objects = ctx.configuration().jsonProvider().toIterable(model);
            for (Object obj : objects) {
                if (obj instanceof String) {
                    result.append(obj.toString());
                }
            }
        }
        if (parameters != null) {
            for (Parameter param : parameters) {
                if (param.getCachedValue() != null) {
                    result.append(param.getCachedValue().toString());
                }
            }
        }
        return result.toString();
    }
}

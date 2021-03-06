/*
 * Copyright 2011 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.medjed.jsonpathcompiler.expressions.path;

import io.github.medjed.jsonpathcompiler.Option;
import io.github.medjed.jsonpathcompiler.PathNotFoundException;
import io.github.medjed.jsonpathcompiler.expressions.PathRef;
import io.github.medjed.jsonpathcompiler.expressions.Utils;
import io.github.medjed.jsonpathcompiler.expressions.function.PathFunction;
import io.github.medjed.jsonpathcompiler.spi.json.JsonProvider;

import java.util.ArrayList;
import java.util.List;

public abstract class PathToken {

    private PathToken prev;
    private PathToken next;
    private Boolean definite = null;
    private Boolean upstreamDefinite = null;

    PathToken appendTailToken(PathToken next) {
        this.next = next;
        this.next.prev = this;
        return next;
    }

    void handleObjectProperty(String currentPath, Object model, EvaluationContextImpl ctx, List<String> properties) {

        if(properties.size() == 1) {
            String property = properties.get(0);
            String evalPath = Utils.concat(currentPath, "['", Utils.escape(property, true), "']");
            Object propertyVal = readObjectProperty(property, model, ctx);
            if(propertyVal == JsonProvider.UNDEFINED){
                // Conditions below heavily depend on current token type (and its logic) and are not "universal",
                // so this code is quite dangerous (I'd rather rewrite it & move to PropertyPathToken and implemented
                // WildcardPathToken as a dynamic multi prop case of PropertyPathToken).
                // Better safe than sorry.
                assert this instanceof PropertyPathToken : "only PropertyPathToken is supported";

                if(isLeaf()) {
                    if(ctx.options().contains(Option.DEFAULT_PATH_LEAF_TO_NULL)){
                        propertyVal =  null;
                    } else {
                        if(ctx.options().contains(Option.SUPPRESS_EXCEPTIONS) ||
                           !ctx.options().contains(Option.REQUIRE_PROPERTIES)){
                            return;
                        } else {
                            throw new PathNotFoundException("No results for path: " + evalPath);
                        }
                    }
                } else {
                    if (! (isUpstreamDefinite() && isTokenDefinite()) &&
                       !ctx.options().contains(Option.REQUIRE_PROPERTIES) ||
                       ctx.options().contains(Option.SUPPRESS_EXCEPTIONS)){
                        // If there is some indefiniteness in the path and properties are not required - we'll ignore
                        // absent property. And also in case of exception suppression - so that other path evaluation
                        // branches could be examined.
                        return;
                    } else {
                        throw new PathNotFoundException("Missing property in path " + evalPath);
                    }
                }
            }
            PathRef pathRef = ctx.forUpdate() ? PathRef.create(model, property) : PathRef.NO_OP;
            if (isLeaf()) {
                ctx.addResult(evalPath, pathRef, propertyVal);
            }
            else {
                next().evaluate(evalPath, pathRef, propertyVal, ctx);
            }
        } else {
            ArrayList<String> escapedProperties = new ArrayList<>(properties.size());
            for (int i = 0; i < properties.size(); i++) {
                escapedProperties.add(i, Utils.escape(properties.get(i), true));
            }
            String evalPath = currentPath + "[" + Utils.join(", ", "'", escapedProperties) + "]";

            assert isLeaf() : "non-leaf multi props handled elsewhere";

            Object merged = ctx.jsonProvider().createMap();
            for (String property : properties) {
                Object propertyVal;
                if(hasProperty(property, model, ctx)) {
                    propertyVal = readObjectProperty(property, model, ctx);
                    if(propertyVal == JsonProvider.UNDEFINED){
                        if(ctx.options().contains(Option.DEFAULT_PATH_LEAF_TO_NULL)) {
                            propertyVal = null;
                        } else {
                            continue;
                        }
                    }
                } else {
                    if(ctx.options().contains(Option.DEFAULT_PATH_LEAF_TO_NULL)){
                        propertyVal = null;
                    } else if (ctx.options().contains(Option.REQUIRE_PROPERTIES)) {
                        throw new PathNotFoundException("Missing property in path " + evalPath);
                    } else {
                        continue;
                    }
                }
                ctx.jsonProvider().setProperty(merged, property, propertyVal);
            }
            PathRef pathRef = ctx.forUpdate() ? PathRef.create(model, properties) : PathRef.NO_OP;
            ctx.addResult(evalPath, pathRef, merged);
        }
    }

    private static boolean hasProperty(String property, Object model, EvaluationContextImpl ctx) {
        return ctx.jsonProvider().getPropertyKeys(model).contains(property);
    }

    private static Object readObjectProperty(String property, Object model, EvaluationContextImpl ctx) {
        return ctx.jsonProvider().getMapValue(model, property);
    }


    protected void handleArrayIndex(int index, String currentPath, Object model, EvaluationContextImpl ctx) {
        String evalPath = Utils.concat(currentPath, "[", String.valueOf(index), "]");
        PathRef pathRef = ctx.forUpdate() ? PathRef.create(model, index) : PathRef.NO_OP;
        try {
            Object evalHit = ctx.jsonProvider().getArrayIndex(model, index);
            if (isLeaf()) {
                ctx.addResult(evalPath, pathRef, evalHit);
            } else {
                next().evaluate(evalPath, pathRef, evalHit, ctx);
            }
        } catch (IndexOutOfBoundsException e) {
        }
    }

    public PathToken prev(){
        return prev;
    }

    public PathToken next() {
        if (isLeaf()) {
            throw new IllegalStateException("Current path token is a leaf");
        }
        return next;
    }

    public boolean isLeaf() {
        return next == null;
    }

    public boolean isRoot() {
        return  prev == null;
    }

    public boolean isUpstreamDefinite() {
        if (upstreamDefinite == null) {
            upstreamDefinite = isRoot() || prev.isTokenDefinite() && prev.isUpstreamDefinite();
        }
        return upstreamDefinite;
    }

    public int getTokenCount() {
        int cnt = 1;
        PathToken token = this;

        while (!token.isLeaf()){
            token = token.next();
            cnt++;
        }
        return cnt;
    }

    public boolean isPathDefinite() {
        if(definite != null){
            return definite.booleanValue();
        }
        boolean isDefinite = isTokenDefinite();
        if (isDefinite && !isLeaf()) {
            isDefinite = next.isPathDefinite();
        }
        definite = isDefinite;
        return isDefinite;
    }

    @Override
    public String toString() {
        if (isLeaf()) {
            return getPathFragment();
        } else {
            return getPathFragment() + next().toString();
        }
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void invoke(PathFunction pathFunction, String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx) {
        ctx.addResult(currentPath, parent, pathFunction.invoke(currentPath, parent, model, ctx, null));
    }

    public abstract void evaluate(String currentPath, PathRef parent,  Object model, EvaluationContextImpl ctx);

    public abstract boolean isTokenDefinite();

    public abstract String getPathFragment();

}

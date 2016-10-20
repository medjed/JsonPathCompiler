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

import io.github.medjed.jsonpathcompiler.InvalidPathException;
import io.github.medjed.jsonpathcompiler.PathNotFoundException;
import io.github.medjed.jsonpathcompiler.expressions.PathRef;
import io.github.medjed.jsonpathcompiler.expressions.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PropertyPathToken extends PathToken {

    private final List<String> properties;
    private final String stringDelimiter;
    private final boolean singleQuote;

    private static final char SINGLE_QUOTE = '\'';
    private static final char DOUBLE_QUOTE = '"';

    public PropertyPathToken(List<String> properties, boolean singleQuote) {
        if (properties.isEmpty()) {
            throw new InvalidPathException("Empty properties");
        }
        this.properties = properties;
        this.stringDelimiter = singleQuote ? Character.toString(SINGLE_QUOTE) : Character.toString(DOUBLE_QUOTE);
        this.singleQuote = singleQuote;
    }

    public List<String> getProperties() {
        return properties;
    }

    public boolean singlePropertyCase() {
        return properties.size() == 1;
    }

    public boolean multiPropertyMergeCase() {
        return isLeaf() && properties.size() > 1;
    }

    public boolean multiPropertyIterationCase() {
        // Semantics of this case is the same as semantics of ArrayPathToken with INDEX_SEQUENCE operation.
        return ! isLeaf() && properties.size() > 1;
    }

    @Override
    public void evaluate(String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx) {
        // Can't assert it in ctor because isLeaf() could be changed later on.
        assert Utils.onlyOneIsTrueNonThrow(singlePropertyCase(), multiPropertyMergeCase(), multiPropertyIterationCase());

        if (!ctx.jsonProvider().isMap(model)) {
            if (! isUpstreamDefinite()) {
                return;
            } else {
                String m = model == null ? "null" : model.getClass().getName();

                throw new PathNotFoundException(String.format(
                        "Expected to find an object with property %s in path %s but found '%s'. " +
                        "This is not a json object according to the JsonProvider: '%s'.",
                        getPathFragment(), currentPath, m, ctx.configuration().jsonProvider().getClass().getName()));
            }
        }

        if (singlePropertyCase() || multiPropertyMergeCase()) {
            handleObjectProperty(currentPath, model, ctx, properties);
            return;
        }

        assert multiPropertyIterationCase();
        final List<String> currentlyHandledProperty = new ArrayList<String>(1);
        currentlyHandledProperty.add(null);
        for (final String property : properties) {
            currentlyHandledProperty.set(0, property);
            handleObjectProperty(currentPath, model, ctx, currentlyHandledProperty);
        }
    }

    @Override
    public boolean isTokenDefinite() {
        // in case of leaf multiprops will be merged, so it's kinda definite
        return singlePropertyCase() || multiPropertyMergeCase();
    }

    @Override
    public String getPathFragment() {
        ArrayList<String> escapedProperties = new ArrayList<>(properties.size());
        for (int i = 0; i < properties.size(); i++) {
            escapedProperties.add(i, Utils.escape(properties.get(i), singleQuote));
        }
        return new StringBuilder()
                .append("[")
                .append(Utils.join(",", stringDelimiter, escapedProperties))
                .append("]").toString();
    }
}

package com.dena.analytics.jsonpathcompiler.expressions.path;

public interface PathTokenAppender {
    PathTokenAppender appendPathToken(PathToken next);
}

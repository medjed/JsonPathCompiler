package com.github.kysnm.jsonpathcompiler.expressions.path;

public interface PathTokenAppender {
    PathTokenAppender appendPathToken(PathToken next);
}

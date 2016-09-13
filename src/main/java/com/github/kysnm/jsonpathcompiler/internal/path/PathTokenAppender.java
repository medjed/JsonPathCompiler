package com.github.kysnm.jsonpathcompiler.internal.path;

public interface PathTokenAppender {
    PathTokenAppender appendPathToken(PathToken next);
}

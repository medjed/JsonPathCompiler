package io.github.medjed.jsonpathcompiler.expressions.path;

public interface PathTokenAppender {
    PathTokenAppender appendPathToken(PathToken next);
}

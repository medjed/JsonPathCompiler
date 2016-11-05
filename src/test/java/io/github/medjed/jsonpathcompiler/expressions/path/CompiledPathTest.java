
package io.github.medjed.jsonpathcompiler.expressions.path;

import io.github.medjed.jsonpathcompiler.expressions.Path;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class CompiledPathTest
{
    @Test
    public void getRoot() {
        Path compiledPath = PathCompiler.compile("$.foo[0].bar");
        PathToken parts = compiledPath.getRoot();
        assertTrue(parts instanceof RootPathToken);
    }

    @Test
    public void getParentPath() {
        Path compiledPath = PathCompiler.compile("$.foo[0].bar");
        assertEquals("$['foo'][0]", compiledPath.getParentPath());
    }

    @Test
    public void getTail() {
        Path compiledPath = PathCompiler.compile("$.foo[0].bar");
        assertEquals("['bar']", compiledPath.getTail().toString());
    }

    @Test
    public void getTailPath() {
        {
            Path compiledPath = PathCompiler.compile("$.foo[0].bar");
            assertEquals("['bar']", compiledPath.getTailPath());
        }
        {
            Path compiledPath = PathCompiler.compile("$.foo[0].bar[0]");
            assertEquals("[0]", compiledPath.getTailPath());
        }
    }
}

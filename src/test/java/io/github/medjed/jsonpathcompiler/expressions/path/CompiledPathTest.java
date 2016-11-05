
package io.github.medjed.jsonpathcompiler.expressions.path;

import io.github.medjed.jsonpathcompiler.expressions.Path;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class CompiledPathTest
{
    @Test
    public void testToString() {
        Path compiledPath = PathCompiler.compile("$.foo[0].bar");
        PathToken parts = compiledPath.getRoot();
        assertEquals("$['foo'][0]['bar']", compiledPath.toString());
    }

    @Test
    public void testGetRoot() {
        Path compiledPath = PathCompiler.compile("$.foo[0].bar");
        PathToken parts = compiledPath.getRoot();
        assertTrue(parts instanceof RootPathToken);
    }

    @Test
    public void testGetParentPath() {
        Path compiledPath = PathCompiler.compile("$.foo[0].bar");
        assertEquals("$['foo'][0]", compiledPath.getParentPath());
    }

    @Test
    public void testGetTail() {
        Path compiledPath = PathCompiler.compile("$.foo[0].bar");
        assertEquals("['bar']", compiledPath.getTail().toString());
    }

    @Test
    public void testGetTailPath() {
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

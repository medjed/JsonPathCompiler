package io.github.medjed.jsonpathcompiler.expressions.path;

import org.junit.Test;
import java.util.ArrayList;
import static junit.framework.TestCase.assertEquals;

public class PropertyPathTokenTest {
    @Test
    public void testGetPathFragment() {
        {
            String path = PropertyPathToken.getPathFragment("foo'bar");
            assertEquals("['foo\\\'bar']", path);
        }
        {
            String path = PropertyPathToken.getPathFragment("foo'bar", true);
            assertEquals("['foo\\\'bar']", path);
        }
        {
            String path = PropertyPathToken.getPathFragment("foo'bar", false);
            assertEquals("[\"foo'bar\"]", path);
        }
    }
    @Test
    public void testGetPathFragmentList() {
        {
            ArrayList<String> properties = new ArrayList<>();
            properties.add("foo'bar");
            properties.add("foo'bar");
            String path = PropertyPathToken.getPathFragment(properties);
            assertEquals("['foo\\\'bar','foo\\\'bar']", path);
        }
    }
}

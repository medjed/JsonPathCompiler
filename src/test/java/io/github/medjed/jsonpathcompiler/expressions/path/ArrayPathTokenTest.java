package io.github.medjed.jsonpathcompiler.expressions.path;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class ArrayPathTokenTest {
    @Test
    public void testGetPathFragmentIndex() {
        String path = ArrayPathToken.getPathFragment(0);
        assertEquals("[0]", path);
    }

    @Test
    public void testGetPathFragmentIndexes() {
        ArrayList<Integer> indexes = new ArrayList<>();
        indexes.add(0);
        indexes.add(1);
        String path = ArrayPathToken.getPathFragment(indexes);
        assertEquals("[0,1]", path);
    }

    @Test
    public void testGetPathFragmentSlices() {
        String path = ArrayPathToken.getPathFragment(0, 2);
        assertEquals("[0:2]", path);
    }
}
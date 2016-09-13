package com.github.kysnm.jsonpathcompiler.internal;

import com.github.kysnm.jsonpathcompiler.BaseTest;
import com.github.kysnm.jsonpathcompiler.Criteria;
import com.github.kysnm.jsonpathcompiler.DocumentContext;
import com.github.kysnm.jsonpathcompiler.Filter;
import com.github.kysnm.jsonpathcompiler.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class JsonContextTest extends BaseTest {

	@Test
    public void cached_path_with_predicates() {

        Filter feq = Filter.filter(Criteria.where("category").eq("reference"));
        Filter fne = Filter.filter(Criteria.where("category").ne("reference"));
        
        DocumentContext JsonDoc = JsonPath.parse(JSON_DOCUMENT);

        List<String> eq = JsonDoc.read("$.store.book[?].category", feq);
        List<String> ne = JsonDoc.read("$.store.book[?].category", fne);

        Assertions.assertThat(eq).contains("reference");
        Assertions.assertThat(ne).doesNotContain("reference");
    }

}

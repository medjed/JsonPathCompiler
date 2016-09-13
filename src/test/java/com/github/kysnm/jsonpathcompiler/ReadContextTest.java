package com.github.kysnm.jsonpathcompiler;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static com.github.kysnm.jsonpathcompiler.JsonPath.using;

public class ReadContextTest extends BaseTest {

    @Test
    public void json_can_be_fetched_as_string() {

        String expected = "{\"category\":\"reference\",\"author\":\"Nigel Rees\",\"title\":\"Sayings of the Century\",\"display-price\":8.95}";

        String jsonString1 = using(JSON_SMART_CONFIGURATION).parse(JSON_BOOK_DOCUMENT).jsonString();

        Assertions.assertThat(jsonString1).isEqualTo(expected);
    }

}

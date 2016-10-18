package io.github.medjed.jsonpathcompiler;

import io.github.medjed.jsonpathcompiler.expressions.filter.FilterCompiler;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FilterCompilerTest {

    @Test
    public void valid_filters_compile() {
        Assertions.assertThat(FilterCompiler.compile("[?(@)]").toString()).isEqualTo("[?(@)]");
        Assertions.assertThat(FilterCompiler.compile("[?(@)]").toString()).isEqualTo("[?(@)]");
        Assertions.assertThat(FilterCompiler.compile("[?(@.firstname)]").toString()).isEqualTo("[?(@['firstname'])]");
        Assertions.assertThat(FilterCompiler.compile("[?($.firstname)]").toString()).isEqualTo("[?($['firstname'])]");
        Assertions.assertThat(FilterCompiler.compile("[?(@['firstname'])]").toString()).isEqualTo("[?(@['firstname'])]");
        Assertions.assertThat(FilterCompiler.compile("[?($['firstname'].lastname)]").toString()).isEqualTo("[?($['firstname']['lastname'])]");
        Assertions.assertThat(FilterCompiler.compile("[?($['firstname']['lastname'])]").toString()).isEqualTo("[?($['firstname']['lastname'])]");
        Assertions.assertThat(FilterCompiler.compile("[?($['firstname']['lastname'].*)]").toString()).isEqualTo("[?($['firstname']['lastname'][*])]");
        Assertions.assertThat(FilterCompiler.compile("[?($['firstname']['num_eq'] == 1)]").toString()).isEqualTo("[?($['firstname']['num_eq'] == 1)]");
        Assertions.assertThat(FilterCompiler.compile("[?($['firstname']['num_gt'] > 1.1)]").toString()).isEqualTo("[?($['firstname']['num_gt'] > 1.1)]");
        Assertions.assertThat(FilterCompiler.compile("[?($['firstname']['num_lt'] < 11.11)]").toString()).isEqualTo("[?($['firstname']['num_lt'] < 11.11)]");
        Assertions.assertThat(FilterCompiler.compile("[?($['firstname']['str_eq'] == 'hej')]").toString()).isEqualTo("[?($['firstname']['str_eq'] == 'hej')]");
        Assertions.assertThat(FilterCompiler.compile("[?($['firstname']['str_eq'] == '')]").toString()).isEqualTo("[?($['firstname']['str_eq'] == '')]");
        Assertions.assertThat(FilterCompiler.compile("[?($['firstname']['str_eq'] == null)]").toString()).isEqualTo("[?($['firstname']['str_eq'] == null)]");
        Assertions.assertThat(FilterCompiler.compile("[?($['firstname']['str_eq'] == true)]").toString()).isEqualTo("[?($['firstname']['str_eq'] == true)]");
        Assertions.assertThat(FilterCompiler.compile("[?($['firstname']['str_eq'] == false)]").toString()).isEqualTo("[?($['firstname']['str_eq'] == false)]");
        Assertions.assertThat(FilterCompiler.compile("[?(@.firstname && @.lastname)]").toString()).isEqualTo("[?(@['firstname'] && @['lastname'])]");
        Assertions.assertThat(FilterCompiler.compile("[?((@.firstname || @.lastname) && @.and)]").toString()).isEqualTo("[?((@['firstname'] || @['lastname']) && @['and'])]");
        Assertions.assertThat(FilterCompiler.compile("[?((@.a || @.b || @.c) && @.x)]").toString()).isEqualTo("[?((@['a'] || @['b'] || @['c']) && @['x'])]");
        Assertions.assertThat(FilterCompiler.compile("[?((@.a && @.b && @.c) || @.x)]").toString()).isEqualTo("[?((@['a'] && @['b'] && @['c']) || @['x'])]");
        Assertions.assertThat(FilterCompiler.compile("[?((@.a && @.b || @.c) || @.x)]").toString()).isEqualTo("[?(((@['a'] && @['b']) || @['c']) || @['x'])]");
        Assertions.assertThat(FilterCompiler.compile("[?((@.a && @.b) || (@.c && @.d))]").toString()).isEqualTo("[?((@['a'] && @['b']) || (@['c'] && @['d']))]");
        Assertions.assertThat(FilterCompiler.compile("[?(@.a IN [1,2,3])]").toString()).isEqualTo("[?(@['a'] IN [1,2,3])]");
        Assertions.assertThat(FilterCompiler.compile("[?(@.a IN {'foo':'bar'})]").toString()).isEqualTo("[?(@['a'] IN {'foo':'bar'})]");
        Assertions.assertThat(FilterCompiler.compile("[?(@.value<'7')]").toString()).isEqualTo("[?(@['value'] < '7')]");
        Assertions.assertThat(FilterCompiler.compile("[?(@.message == 'it\\\\')]").toString()).isEqualTo("[?(@['message'] == 'it\\\\')]");
        Assertions.assertThat(FilterCompiler.compile("[?(@.message.min() > 10)]").toString()).isEqualTo("[?(@['message'].min() > 10)]");
        Assertions.assertThat(FilterCompiler.compile("[?(@.message.min()==10)]").toString()).isEqualTo("[?(@['message'].min() == 10)]");
        Assertions.assertThat(FilterCompiler.compile("[?(10 == @.message.min())]").toString()).isEqualTo("[?(10 == @['message'].min())]");
        Assertions.assertThat(FilterCompiler.compile("[?(((@)))]").toString()).isEqualTo("[?(@)]");
        Assertions.assertThat(FilterCompiler.compile("[?(@.name =~ /.*?/i)]").toString()).isEqualTo("[?(@['name'] =~ /.*?/i)]");
        Assertions.assertThat(FilterCompiler.compile("[?(@.name =~ /.*?/)]").toString()).isEqualTo("[?(@['name'] =~ /.*?/)]");
        Assertions.assertThat(FilterCompiler.compile("[?($[\"firstname\"][\"lastname\"])]").toString()).isEqualTo("[?($['firstname']['lastname'])]");
        Assertions.assertThat(FilterCompiler.compile("[?($[\"firstname\"].lastname)]").toString()).isEqualTo("[?($['firstname']['lastname'])]");
        Assertions.assertThat(FilterCompiler.compile("[?($[\"firstname\", \"lastname\"])]").toString()).isEqualTo("[?($['firstname','lastname'])]");
        Assertions.assertThat(FilterCompiler.compile("[?(((@.a && @.b || @.c)) || @.x)]").toString()).isEqualTo("[?(((@['a'] && @['b']) || @['c']) || @['x'])]");

    }

    @Test
    public void string_quote_style_is_serialized() {
        Assertions.assertThat(FilterCompiler.compile("[?('apa' == 'apa')]").toString()).isEqualTo("[?('apa' == 'apa')]");
        Assertions.assertThat(FilterCompiler.compile("[?('apa' == \"apa\")]").toString()).isEqualTo("[?('apa' == \"apa\")]");
    }

    @Test
    public void string_can_contain_path_chars() {
        Assertions.assertThat(FilterCompiler.compile("[?(@[')]@$)]'] == ')]@$)]')]").toString()).isEqualTo("[?(@[')]@$)]'] == ')]@$)]')]");
        Assertions.assertThat(FilterCompiler.compile("[?(@[\")]@$)]\"] == \")]@$)]\")]").toString()).isEqualTo("[?(@[')]@$)]'] == \")]@$)]\")]");
    }

    @Test(expected = InvalidPathException.class)
    public void invalid_path_when_string_literal_is_unquoted() {
        FilterCompiler.compile("[?(@.foo == x)]");
    }

    @Test
    public void or_has_lower_priority_than_and() {
        Assertions.assertThat(FilterCompiler.compile("[?(@.category == 'fiction' && @.author == 'Evelyn Waugh' || @.price > 15)]").toString())
                .isEqualTo("[?((@['category'] == 'fiction' && @['author'] == 'Evelyn Waugh') || @['price'] > 15)]");
    }

    @Test
    public void invalid_filters_does_not_compile() {
        assertInvalidPathException("[?(@))]");
        assertInvalidPathException("[?(@ FOO 1)]");
        assertInvalidPathException("[?(@ || )]");
        assertInvalidPathException("[?(@ == 'foo )]");
        assertInvalidPathException("[?(@ == 1' )]");
        assertInvalidPathException("[?(@.foo bar == 1)]");
        assertInvalidPathException("[?(@.i == 5 @.i == 8)]");
        assertInvalidPathException("[?(!5)]");
        assertInvalidPathException("[?(!'foo')]");
    }

    @Test
    // issue #178
    public void compile_and_serialize_not_exists_filter(){
        Filter compiled = FilterCompiler.compile("[?(!@.foo)]");
        String serialized = compiled.toString();
        assertThat(serialized).isEqualTo("[?(!@['foo'])]");
    }



    private void assertInvalidPathException(String filter){
        try {
            FilterCompiler.compile(filter);
            throw new AssertionError("Expected " + filter + " to throw InvalidPathException");
        } catch (InvalidPathException e){
            //e.printStackTrace();
        }
    }
}

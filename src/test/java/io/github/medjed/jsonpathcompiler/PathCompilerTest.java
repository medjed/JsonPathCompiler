package io.github.medjed.jsonpathcompiler;

import io.github.medjed.jsonpathcompiler.expressions.Path;
import io.github.medjed.jsonpathcompiler.expressions.path.PathCompiler;
import io.github.medjed.jsonpathcompiler.spi.json.JsonSmartJsonProvider;
import io.github.medjed.jsonpathcompiler.expressions.path.PathToken;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class PathCompilerTest {


    @Ignore("Backward compatibility <= 2.0.0")
    @Test(expected = InvalidPathException.class)
    public void a_path_must_start_with_$_or_at() {
        PathCompiler.compile("x");
    }

    @Ignore("Backward compatibility <= 2.0.0")
    @Test(expected = InvalidPathException.class)
    public void a_square_bracket_may_not_follow_a_period() {
        PathCompiler.compile("$.[");
    }

    @Test(expected = InvalidPathException.class)
    public void a_root_path_must_be_followed_by_period_or_bracket() {
        PathCompiler.compile("$X");
    }

    @Test
    public void a_root_path_can_be_compiled() {
        Assertions.assertThat(PathCompiler.compile("$").toString()).isEqualTo("$");
        Assertions.assertThat(PathCompiler.compile("@").toString()).isEqualTo("@");
    }

    @Test(expected = InvalidPathException.class)
    public void a_path_may_not_end_with_period() {
        PathCompiler.compile("$.");
    }

    @Test(expected = InvalidPathException.class)
    public void a_path_may_not_end_with_period_2() {
        PathCompiler.compile("$.prop.");
    }

    @Test(expected = InvalidPathException.class)
    public void a_path_may_not_end_with_scan() {
        PathCompiler.compile("$..");
    }

    @Test(expected = InvalidPathException.class)
    public void a_path_may_not_end_with_scan_2() {
        PathCompiler.compile("$.prop..");
    }

    @Test
    public void a_property_token_can_be_compiled() {
        Assertions.assertThat(PathCompiler.compile("$.prop").toString()).isEqualTo("$['prop']");
        Assertions.assertThat(PathCompiler.compile("$.1prop").toString()).isEqualTo("$['1prop']");
        Assertions.assertThat(PathCompiler.compile("$.@prop").toString()).isEqualTo("$['@prop']");
    }

    @Test
    public void a_bracket_notation_property_token_can_be_compiled() {
        Assertions.assertThat(PathCompiler.compile("$['prop']").toString()).isEqualTo("$['prop']");
        Assertions.assertThat(PathCompiler.compile("$['1prop']").toString()).isEqualTo("$['1prop']");
        Assertions.assertThat(PathCompiler.compile("$['@prop']").toString()).isEqualTo("$['@prop']");
        Assertions.assertThat(PathCompiler.compile("$[  '@prop'  ]").toString()).isEqualTo("$['@prop']");
        Assertions.assertThat(PathCompiler.compile("$[\"prop\"]").toString()).isEqualTo("$['prop']");
    }

    @Test
    public void a_multi_property_token_can_be_compiled() {
        Assertions.assertThat(PathCompiler.compile("$['prop0', 'prop1']").toString()).isEqualTo("$['prop0','prop1']");
        Assertions.assertThat(PathCompiler.compile("$[  'prop0'  , 'prop1'  ]").toString()).isEqualTo("$['prop0','prop1']");
    }

    @Test
    public void a_property_chain_can_be_compiled() {
        Assertions.assertThat(PathCompiler.compile("$.abc").toString()).isEqualTo("$['abc']");
        Assertions.assertThat(PathCompiler.compile("$.aaa.bbb").toString()).isEqualTo("$['aaa']['bbb']");
        Assertions.assertThat(PathCompiler.compile("$.aaa.bbb.ccc").toString()).isEqualTo("$['aaa']['bbb']['ccc']");
    }

    @Test(expected = InvalidPathException.class)
    public void a_property_may_not_contain_blanks() {
        Assertions.assertThat(PathCompiler.compile("$.foo bar").toString());
    }

    @Test
    public void a_wildcard_can_be_compiled() {
        Assertions.assertThat(PathCompiler.compile("$.*").toString()).isEqualTo("$[*]");
        Assertions.assertThat(PathCompiler.compile("$[*]").toString()).isEqualTo("$[*]");
        Assertions.assertThat(PathCompiler.compile("$[ * ]").toString()).isEqualTo("$[*]");
    }

    @Test
    public void a_wildcard_can_follow_a_property() {
        Assertions.assertThat(PathCompiler.compile("$.prop[*]").toString()).isEqualTo("$['prop'][*]");
        Assertions.assertThat(PathCompiler.compile("$['prop'][*]").toString()).isEqualTo("$['prop'][*]");
    }

    @Test
    public void an_array_index_path_can_be_compiled() {
        Assertions.assertThat(PathCompiler.compile("$[1]").toString()).isEqualTo("$[1]");
        Assertions.assertThat(PathCompiler.compile("$[1,2,3]").toString()).isEqualTo("$[1,2,3]");
        Assertions.assertThat(PathCompiler.compile("$[ 1 , 2 , 3 ]").toString()).isEqualTo("$[1,2,3]");
    }

    @Test
    public void an_array_slice_path_can_be_compiled() {
        Assertions.assertThat(PathCompiler.compile("$[-1:]").toString()).isEqualTo("$[-1:]");
        Assertions.assertThat(PathCompiler.compile("$[1:2]").toString()).isEqualTo("$[1:2]");
        Assertions.assertThat(PathCompiler.compile("$[:2]").toString()).isEqualTo("$[:2]");
    }

    @Test
    public void an_inline_criteria_can_be_parsed() {
        Assertions.assertThat(PathCompiler.compile("$[?(@.foo == 'bar')]").toString()).isEqualTo("$[?]");
        Assertions.assertThat(PathCompiler.compile("$[?(@.foo == \"bar\")]").toString()).isEqualTo("$[?]");
    }

    @Test
    public void a_placeholder_criteria_can_be_parsed() {

        Predicate p = new Predicate() {
            @Override
            public boolean apply(PredicateContext ctx) {
                return false;
            }
        };
        Assertions.assertThat(PathCompiler.compile("$[?]", p).toString()).isEqualTo("$[?]");
        Assertions.assertThat(PathCompiler.compile("$[?,?]", p, p).toString()).isEqualTo("$[?,?]");
        Assertions.assertThat(PathCompiler.compile("$[?,?,?]", p, p, p).toString()).isEqualTo("$[?,?,?]");
    }

    @Test
    public void a_scan_token_can_be_parsed() {
        Assertions.assertThat(PathCompiler.compile("$..['prop']..[*]").toString()).isEqualTo("$..['prop']..[*]");
    }

    @Test
    public void issue_predicate_can_have_escaped_backslash_in_prop() {
        String json = "{\n"
                + "    \"logs\": [\n"
                + "        {\n"
                + "            \"message\": \"it\\\\\",\n"
                + "            \"id\": 2\n"
                + "        }\n"
                + "    ]\n"
                + "}";
        // message: it\ -> (after json escaping) -> "it\\" -> (after java escaping) -> "\"it\\\\\""

        Path path = PathCompiler.compile("$.logs[?(@.message == 'it\\\\')].message");
        List<String> result = read(json, path);

        assertThat(result).containsExactly("it\\");
    }

    @Test
    public void issue_predicate_can_have_bracket_in_regex() {
        String json = "{\n"
                + "    \"logs\": [\n"
                + "        {\n"
                + "            \"message\": \"(it\",\n"
                + "            \"id\": 2\n"
                + "        }\n"
                + "    ]\n"
                + "}";

        Path path = PathCompiler.compile("$.logs[?(@.message =~ /\\(it/)].message");
        List<String> result = read(json, path);

        assertThat(result).containsExactly("(it");
    }

    @Test
    public void issue_predicate_can_have_and_in_regex() {
        String json = "{\n"
                + "    \"logs\": [\n"
                + "        {\n"
                + "            \"message\": \"it\",\n"
                + "            \"id\": 2\n"
                + "        }\n"
                + "    ]\n"
                + "}";

//        List<String> result = JsonPath.read(json, "$.logs[?(@.message =~ /&&|it/)].message");
        Path path = PathCompiler.compile("$.logs[?(@.message =~ /&&|it/)].message");
        List<String> result = read(json, path);

        assertThat(result).containsExactly("it");
    }

    @Test
    public void issue_predicate_can_have_and_in_prop() {
        String json = "{\n"
                + "    \"logs\": [\n"
                + "        {\n"
                + "            \"message\": \"&& it\",\n"
                + "            \"id\": 2\n"
                + "        }\n"
                + "    ]\n"
                + "}";

        Path path = PathCompiler.compile("$.logs[?(@.message == '&& it')].message");
        List<String> result = read(json, path);

        assertThat(result).containsExactly("&& it");
    }

    @Test
    public void issue_predicate_brackets_must_change_priorities() {
        String json = "{\n"
                + "    \"logs\": [\n"
                + "        {\n"
                + "            \"id\": 2\n"
                + "        }\n"
                + "    ]\n"
                + "}";

        Path path = PathCompiler.compile("$.logs[?(@.message && (@.id == 1 || @.id == 2))].id");
        List<String> result = read(json, path);
        assertThat(result).isEmpty();

        path = PathCompiler.compile("$.logs[?((@.id == 2 || @.id == 1) && @.message)].id");
        result = read(json, path);
        assertThat(result).isEmpty();
    }

    @Test
    public void issue_predicate_or_has_lower_priority_than_and() {
        String json = "{\n"
                + "    \"logs\": [\n"
                + "        {\n"
                + "            \"id\": 2\n"
                + "        }\n"
                + "    ]\n"
                + "}";

        Path path = PathCompiler.compile("$.logs[?(@.x && @.y || @.id)]");
        List<String> result = read(json, path);
        assertThat(result).hasSize(1);
    }

    @Test
    public void issue_predicate_can_have_square_bracket_in_prop() {
        String json = "{\n"
                + "    \"logs\": [\n"
                + "        {\n"
                + "            \"message\": \"] it\",\n"
                + "            \"id\": 2\n"
                + "        }\n"
                + "    ]\n"
                + "}";

        Path path = PathCompiler.compile("$.logs[?(@.message == '] it')].message");
        List<String> result = read(json, path);

        assertThat(result).containsExactly("] it");
    }

    @Test(expected = InvalidPathException.class)
    public void array_indexes_must_be_separated_by_commas() {
        PathCompiler.compile("$[0, 1, 2 4]");
    }

    @Test(expected = InvalidPathException.class)
    public void trailing_comma_after_list_is_not_accepted() {
        PathCompiler.compile("$['1','2',]");
    }

    @Test(expected = InvalidPathException.class)
    public void accept_only_a_single_comma_between_indexes() { PathCompiler.compile("$['1', ,'3']"); }

    @Test
    public void a_doc_context_must_be_true() {
        assert(PathCompiler.isProbablyJsonPath("$.prop"));
    }

    @Test
    public void a_bracket_notation_doc_context_must_be_true() {
        assert(PathCompiler.isProbablyJsonPath("$['prop']"));
    }

    @Test
    public void a_parent_path_must_be_resolved() {
        Path path = PathCompiler.compile("$.prop[0]");
        assertThat(path.getParentPath()).isEqualTo("$['prop']");
    }

    @Test
    public void a_single_quote_must_be_escaped() {
        Path path = PathCompiler.compile("$['\\'foo']");
        assertEquals("$['\\'foo']", path.toString());
    }

    @Test
    public void path_must_be_taken_out_a_token_step_by_step() {
        Path path = PathCompiler.compile("$.aaa.bbb.ccc");
        List<String> paths = new ArrayList<>();
        StringBuilder partialPath = new StringBuilder("$");
        PathToken pathToken = path.getRoot();
        int count = pathToken.getTokenCount();
        for (int i = 1; i < count; i++) {
            pathToken = pathToken.next();
            partialPath.append(pathToken.getPathFragment().toString());
            paths.add(partialPath.toString());
        }

        assertThat(paths).contains("$['aaa']", "$['aaa']['bbb']", "$['aaa']['bbb']['ccc']");
    }

    @Test(expected = InvalidPathException.class)
    public void property_must_be_separated_by_commas() {
        PathCompiler.compile("$['aaa'}'bbb']");
    }

    public static List<String> read(String json, Path path) {
        Object jsonObject = new JsonSmartJsonProvider().parse(json);
        Configuration configuration = Configuration.defaultConfiguration();

        boolean optAsPathList = configuration.containsOption(Option.AS_PATH_LIST);
        boolean optAlwaysReturnList = configuration.containsOption(Option.ALWAYS_RETURN_LIST);
        boolean optSuppressExceptions = configuration.containsOption(Option.SUPPRESS_EXCEPTIONS);

        try {
            if(path.isFunctionPath()){
                if(optAsPathList || optAlwaysReturnList){
                    throw new JsonPathException("Options " + Option.AS_PATH_LIST + " and " + Option.ALWAYS_RETURN_LIST + " are not allowed when using path functions!");
                }
                return path.evaluate(jsonObject, jsonObject, configuration).getValue(true);

            } else if(optAsPathList){
                return  (List<String>)path.evaluate(jsonObject, jsonObject, configuration).getPath();

            } else {
                Object res = path.evaluate(jsonObject, jsonObject, configuration).getValue(false);
                if(optAlwaysReturnList && path.isDefinite()){
                    Object array = configuration.jsonProvider().createArray();
                    configuration.jsonProvider().setArrayIndex(array, 0, res);
                    return (List<String>)array;
                } else {
                    return (List<String>)res;
                }
            }
        } catch (RuntimeException e){
            if(!optSuppressExceptions){
                throw e;
            } else {
                if(optAsPathList){
                    return (List<String>)configuration.jsonProvider().createArray();
                } else {
                    if(optAlwaysReturnList){
                        return (List<String>)configuration.jsonProvider().createArray();
                    } else {
                        return (List<String>)(path.isDefinite() ? null : configuration.jsonProvider().createArray());
                    }
                }
            }
        }
    }

}

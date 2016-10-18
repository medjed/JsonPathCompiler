package io.github.medjed.jsonpathcompiler;

import io.github.medjed.jsonpathcompiler.spi.mapper.JsonSmartMappingProvider;
import io.github.medjed.jsonpathcompiler.spi.json.JsonSmartJsonProvider;

import java.util.Arrays;

public class Configurations {

    public static final Configuration JSON_SMART_CONFIGURATION = Configuration
            .builder()
            .mappingProvider(new JsonSmartMappingProvider())
            .jsonProvider(new JsonSmartJsonProvider())
            .build();

    public static Iterable<Configuration> configurations() {
        return Arrays.asList(
               JSON_SMART_CONFIGURATION
        );
    }
}

package com.github.kysnm.jsonpathcompiler;

import com.github.kysnm.jsonpathcompiler.spi.json.JsonSmartJsonProvider;
import com.github.kysnm.jsonpathcompiler.spi.mapper.JsonSmartMappingProvider;

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

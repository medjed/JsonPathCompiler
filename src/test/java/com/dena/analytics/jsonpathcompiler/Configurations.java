package com.dena.analytics.jsonpathcompiler;

import com.dena.analytics.jsonpathcompiler.spi.json.JsonSmartJsonProvider;
import com.dena.analytics.jsonpathcompiler.spi.mapper.JsonSmartMappingProvider;

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

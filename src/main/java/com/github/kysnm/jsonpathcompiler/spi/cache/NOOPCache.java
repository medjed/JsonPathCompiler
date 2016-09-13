package com.github.kysnm.jsonpathcompiler.spi.cache;

import com.github.kysnm.jsonpathcompiler.JsonPath;

public class NOOPCache implements Cache {

    @Override
    public JsonPath get(String key) {
        return null;
    }

    @Override
    public void put(String key, JsonPath value) {
    }
}

package com.github.kysnm.jsonpathcompiler.spi.cache;

import com.github.kysnm.jsonpathcompiler.InvalidJsonException;
import com.github.kysnm.jsonpathcompiler.JsonPath;

public interface Cache {

	/**
     * Get the Cached JsonPath
     * @param key cache key to lookup the JsonPath
     * @return JsonPath
     */
	public JsonPath get(String key);
	
	/**
     * Add JsonPath to the cache
     * @param key cache key to store the JsonPath
     * @param value JsonPath to be cached
     * @return void
     * @throws InvalidJsonException
     */
	public void put(String key, JsonPath value);
}

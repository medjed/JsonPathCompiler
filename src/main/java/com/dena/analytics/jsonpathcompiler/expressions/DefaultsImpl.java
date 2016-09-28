package com.dena.analytics.jsonpathcompiler.expressions;

import com.dena.analytics.jsonpathcompiler.spi.json.JsonSmartJsonProvider;
import com.dena.analytics.jsonpathcompiler.Configuration.Defaults;
import com.dena.analytics.jsonpathcompiler.Option;
import com.dena.analytics.jsonpathcompiler.spi.json.JsonProvider;
import com.dena.analytics.jsonpathcompiler.spi.mapper.JsonSmartMappingProvider;
import com.dena.analytics.jsonpathcompiler.spi.mapper.MappingProvider;

import java.util.EnumSet;
import java.util.Set;

public final class DefaultsImpl implements Defaults {

  public static final DefaultsImpl INSTANCE = new DefaultsImpl();

  private final MappingProvider mappingProvider = new JsonSmartMappingProvider();

  @Override
  public JsonProvider jsonProvider() {
    return new JsonSmartJsonProvider();
  }

  @Override
  public Set<Option> options() {
    return EnumSet.noneOf(Option.class);
  }

  @Override
  public MappingProvider mappingProvider() {
    return mappingProvider;
  }

  private DefaultsImpl() {
  };

}

package com.github.kysnm.jsonpathcompiler.internal;

import com.github.kysnm.jsonpathcompiler.Configuration.Defaults;
import com.github.kysnm.jsonpathcompiler.Option;
import com.github.kysnm.jsonpathcompiler.spi.json.JsonProvider;
import com.github.kysnm.jsonpathcompiler.spi.json.JsonSmartJsonProvider;
import com.github.kysnm.jsonpathcompiler.spi.mapper.JsonSmartMappingProvider;
import com.github.kysnm.jsonpathcompiler.spi.mapper.MappingProvider;

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

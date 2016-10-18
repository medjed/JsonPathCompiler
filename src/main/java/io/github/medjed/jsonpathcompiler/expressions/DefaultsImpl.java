package io.github.medjed.jsonpathcompiler.expressions;

import io.github.medjed.jsonpathcompiler.Configuration;
import io.github.medjed.jsonpathcompiler.Option;
import io.github.medjed.jsonpathcompiler.spi.json.JsonProvider;
import io.github.medjed.jsonpathcompiler.spi.mapper.JsonSmartMappingProvider;
import io.github.medjed.jsonpathcompiler.spi.json.JsonSmartJsonProvider;
import io.github.medjed.jsonpathcompiler.spi.mapper.MappingProvider;

import java.util.EnumSet;
import java.util.Set;

public final class DefaultsImpl implements Configuration.Defaults
{

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

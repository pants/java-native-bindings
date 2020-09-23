package wtf.pants.bindings.generators;

import wtf.pants.bindings.mappings.ClassMap;

import java.util.List;

public interface BindingsGenerator {
    List<String> generateIds(List<ClassMap> classMaps);
}

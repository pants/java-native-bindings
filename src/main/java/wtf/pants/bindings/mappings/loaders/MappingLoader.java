package wtf.pants.bindings.mappings.loaders;

import wtf.pants.bindings.mappings.ClassMap;

import java.io.File;
import java.util.List;

public interface MappingLoader {

    List<ClassMap> loadMappingFiles(File file);

}

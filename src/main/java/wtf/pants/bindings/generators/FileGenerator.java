package wtf.pants.bindings.generators;

import wtf.pants.bindings.mappings.ClassMap;

import java.io.PrintWriter;
import java.util.List;

public interface FileGenerator {
    String fileName();
    void generate(PrintWriter pw, List<ClassMap> classMaps);
}

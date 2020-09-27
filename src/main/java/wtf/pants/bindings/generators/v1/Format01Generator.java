package wtf.pants.bindings.generators.v1;

import wtf.pants.bindings.generators.BindingsGenerator;
import wtf.pants.bindings.generators.FileGenerator;
import wtf.pants.bindings.mappings.ClassMap;

import java.io.PrintWriter;
import java.util.List;

public class Format01Generator implements BindingsGenerator {

    @Override
    public void generateFiles(List<ClassMap> classMaps) {
        final ClassIndex classIndex = new ClassIndex();
        classIndex.generateFile(classMaps);

        for (ClassMap classMap : classMaps) {
            final ClassHeader classHeader = new ClassHeader(classMap);
            classHeader.generateFile(classMaps);
        }
    }
}

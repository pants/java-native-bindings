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
        final PrintWriter classIndexPrintWriter = classIndex.createPrintWriter();
        if (classIndexPrintWriter == null) {
            System.out.println("Failed on generator " + classIndex.getClass().getName());
            return;
        }
        classIndex.generate(classIndexPrintWriter, classMaps);
        classIndexPrintWriter.flush();
        classIndexPrintWriter.close();

        for (ClassMap classMap : classMaps) {
            final ClassHeader classHeader = new ClassHeader(classMap);
            final PrintWriter printWriter = classHeader.createPrintWriter();
            if (printWriter == null) {
                System.out.println("Failed on generator " + classHeader.getClass().getName());
                return;
            }
            classHeader.generate(printWriter, classMaps);
            printWriter.flush();
            printWriter.close();
        }

        System.out.println("Complete");
    }
}

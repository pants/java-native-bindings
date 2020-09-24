package wtf.pants.bindings.generators.v1;

import wtf.pants.bindings.generators.FileGenerator;
import wtf.pants.bindings.mappings.ClassMap;
import wtf.pants.bindings.mappings.MemberMap;
import wtf.pants.bindings.util.FileUtils;
import wtf.pants.bindings.util.StringUtils;

import java.io.PrintWriter;
import java.util.List;

public class ClassHeader implements FileGenerator {

    private final ClassMap targetClass;

    public ClassHeader(ClassMap targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public String fileName() {
        return StringUtils.camelToSnake(targetClass.getCleanClassName());
    }

    private void generateFields(PrintWriter pw, List<ClassMap> classMaps) {

    }

    @Override
    public void generate(PrintWriter pw, List<ClassMap> classMaps) {
        List<String> resourceLines = FileUtils.getResourceLines("/templates/v1/class_header.h");
        if (resourceLines == null) {
            System.out.println("Invalid resource URI, how did this happen?");
            return;
        }

        for (String resourceLine : resourceLines) {
            if (resourceLine.contains("<class_name>")) {
                pw.println(resourceLine.replace("<class_name>", targetClass.getCleanClassName()));
            } else if (resourceLine.contains("<fields>")) {
                generateFields(pw, classMaps);
            } else {
                pw.println(resourceLine);
            }
        }
    }
}

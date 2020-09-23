package wtf.pants.bindings.generators.v1;

import wtf.pants.bindings.generators.FileGenerator;
import wtf.pants.bindings.mappings.ClassMap;
import wtf.pants.bindings.mappings.MemberMap;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;

public class ClassIndex implements FileGenerator {

    @Override
    public String fileName() {
        return "class_index.h";
    }

    public void generate(PrintWriter pw, List<ClassMap> classMaps) {
        var res = getClass().getResource("templates/v1/" + fileName());
        try {
            Files.readAllLines(new File(res.toURI()).toPath()).forEach(s -> {
                if (s.equals("<generate>")) {
                    generateIndex(pw, classMaps);
                } else {
                    pw.println(s);
                }
            });
        } catch (IOException | URISyntaxException exception) {
            exception.printStackTrace();
        }
    }

    //todo: make this actually readable
    protected String createMethodIdLine(ClassMap classMap, MemberMap member) {
        String getIdStr;
        if (member.isStatic()) {
            getIdStr = member.isMethod() ? "staticMethodId" : "staticFieldId";
        } else {
            getIdStr = member.isMethod() ? "methodId" : "fieldId";
        }

        String methodLine = String.format(
                "j%sID m_%s_%s = %s(c_%s, \"%s\", \"%s\");",
                member.isMethod() ? "method" : "field",
                classMap.getCleanClassName(), member.getCleanName(),
                getIdStr,
                classMap.getCleanClassName(),
                member.getObfuscatedName(),
                member.getObfuscatedSignature()
        );

        return methodLine;
    }

    private void generateIndex(PrintWriter pw, List<ClassMap> classMaps) {
        classMaps.forEach(classMap -> {
            pw.print("    ");
            pw.println(String.format("jclass c_%s = findClass(\"%s\");", classMap.getCleanClassName(), classMap.getObfuscatedName()));
            classMap.getMembers().forEach(member -> {
                pw.print("    ");
                pw.println(createMethodIdLine(classMap, member));
            });

            pw.println();
        });
    }

}

package wtf.pants.bindings.generators.v1;

import wtf.pants.bindings.generators.FileGenerator;
import wtf.pants.bindings.mappings.ClassMap;
import wtf.pants.bindings.mappings.MemberMap;
import wtf.pants.bindings.mappings.MethodArg;
import wtf.pants.bindings.util.FileUtils;
import wtf.pants.bindings.util.Functional;
import wtf.pants.bindings.util.StringUtils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClassHeader implements FileGenerator {

    private final ClassMap targetClass;

    public ClassHeader(ClassMap targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public String fileName() {
        return StringUtils.camelToSnake(targetClass.getCleanClassName()) + ".h";
    }

    private void generateMember(PrintWriter pw, MemberMap memberMap, List<ClassMap> classMaps) {
        final String jniRetType = memberMap.getJNIReturnClass(classMaps);
        pw.print("    ");
        if (memberMap.isStatic()) {
            pw.print("static");
        }

        pw.print(jniRetType);
        pw.print(" ");
        pw.print(memberMap.getCleanName());
        pw.print("(");
        if (memberMap.isStatic()) {
            pw.print("JNIEnv *env");
        }

        if (memberMap.isMethod()) {
            getMethodArguments(memberMap, classMaps).forEach(methodArg -> {
                pw.print(methodArg.getArgumentType() + " " + methodArg.getArgumentName());
            });
        }

        pw.println(") {");
        pw.print("        ");
        pw.println();
        pw.print("    ");
        pw.println("}");
    }

    public List<MethodArg> getMethodArguments(MemberMap memberMap, List<ClassMap> classMaps) {
        String obfuscatedSignature = memberMap.getObfuscatedSignature();

        if (obfuscatedSignature.startsWith("()")) {
            return new ArrayList<>();
        }

        obfuscatedSignature = obfuscatedSignature.substring(1, obfuscatedSignature.lastIndexOf(")"));

        final List<MethodArg> methodArgs = new ArrayList<>();
        boolean isArray = false;

        int argument = 0;
        while (obfuscatedSignature.length() != 0) {
            char c = obfuscatedSignature.charAt(0);

            final String argumentName;
            if (memberMap.getParams().size() > argument) {
                argumentName = memberMap.getParams().get(argument);
            } else {
                argumentName = "arg" + argument;
            }

            argument++;

            if (c == 'L') {
                final int classEnd = obfuscatedSignature.indexOf(";");

                if (isArray) {
                    obfuscatedSignature = obfuscatedSignature.substring(obfuscatedSignature.indexOf(";") + 1);
                    isArray = false;
                    continue;
                }

                final String className = obfuscatedSignature.substring(1, classEnd);
                final Optional<ClassMap> deobf = ClassMap.deobfuscateClassName(className, classMaps);

                if (deobf.isPresent()) {
                    obfuscatedSignature = obfuscatedSignature.substring(obfuscatedSignature.indexOf(";") + 1);
                    methodArgs.add(new MethodArg(deobf.get().getCleanClassName(), argumentName));
                    continue;
                }
            }

            if (isArray) {
                obfuscatedSignature = obfuscatedSignature.substring(1);
                isArray = false;
                continue;
            }

            if (c == '[') {
                isArray = true;
            }

            final String jniType = StringUtils.getJNITypeFromBytecode(obfuscatedSignature);
            methodArgs.add(new MethodArg(jniType, argumentName));
            obfuscatedSignature = obfuscatedSignature.substring(1);
        }

        return methodArgs;
    }

    @Override
    public void generate(PrintWriter pw, List<ClassMap> classMaps) {
        List<String> resourceLines = FileUtils.getResourceLines("/templates/v1/class_header.h");
        if (resourceLines == null) {
            System.out.println("Invalid resource URI, how did this happen?");
            return;
        }

        final String parent;
        if (targetClass.getParentClass() != null) {
            parent = targetClass.getParentClass().getCleanClassName();
        } else {
            parent = "JavaClass";
        }

        for (String resourceLine : resourceLines) {
            String line = resourceLine
                    .replace("<class_name>", targetClass.getCleanClassName())
                    .replace("<parent>", parent);

            if (resourceLine.contains("<fields>")) {
                targetClass.getMembers().forEach(member -> {
                    generateMember(pw, member, classMaps);
                });
            } else {
                pw.println(line);
            }
        }
    }
}

package wtf.pants.bindings.mappings;

import java.util.ArrayList;
import java.util.List;

public class MemberMap {

    private final boolean method;
    private final String obfuscatedMethodSignature;
    private final String obfuscatedName;
    private final String intermediateName;
    private final String cleanName;

    private final List<String> params = new ArrayList<>();

    private boolean isStatic = false;

    public MemberMap(boolean method, String obfuscatedMethodSignature, String obfuscatedName, String intermediateName, String cleanName) {
        this.method = method;
        this.obfuscatedMethodSignature = obfuscatedMethodSignature;
        this.obfuscatedName = obfuscatedName;
        this.intermediateName = intermediateName;
        this.cleanName = cleanName;
    }

    public String getObfuscatedSignature() {
        return obfuscatedMethodSignature;
    }

    public String getObfuscatedName() {
        return obfuscatedName;
    }

    public String getIntermediateName() {
        return intermediateName;
    }

    public String getCleanName() {
        return cleanName;
    }

    public String getCleanSignature(final List<ClassMap> classes) {
        final String signature = getObfuscatedSignature();
        final StringBuilder cleanSig = new StringBuilder();

        boolean inClass = false;
        StringBuilder classNameBuilder = new StringBuilder();

        for (int i = 0; i < signature.length(); i++) {
            char c = signature.charAt(i);
            if (c == 'L' && !inClass) {
                inClass = true;
            } else if (c == ';' && inClass) {
                final String classReference = classNameBuilder.toString();

                final var classStream = classes.stream();
                final var filteredClasses = classStream.filter(cm -> cm.getObfuscatedName().equals(classReference));
                final var optionalClassMap = filteredClasses.findFirst();
                final var classToAppend = optionalClassMap.map(ClassMap::getCleanName).orElse(classNameBuilder.toString());

                cleanSig.append(classToAppend);
                inClass = false;
                classNameBuilder = new StringBuilder();
            } else if (inClass) {
                classNameBuilder.append(c);
                continue;
            }

            cleanSig.append(c);
        }

        return cleanSig.toString();
    }

    public boolean isMethod() {
        return method;
    }

    public List<String> getParams() {
        return params;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }
}

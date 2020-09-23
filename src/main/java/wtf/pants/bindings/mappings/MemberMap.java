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

    public String getCleanMethodSignature(final List<ClassMap> classes) {
        final String methodSig = getObfuscatedSignature();
        final StringBuilder cleanSig = new StringBuilder();

        final String signatureArgs = methodSig.substring(1).split("\\)")[0];
        final String signatureRet = methodSig.substring(0, methodSig.indexOf(")") - 1);

        cleanSig.append("(");

        int index = 0;
        char firstChar = signatureArgs.charAt(0);
        while ((firstChar = signatureArgs.charAt(index++)) != ')') {
            switch (firstChar) {
                case 'L':
                    //handle class
                    signatureArgs.substring(index, signatureArgs.indexOf(";", index));
                    break;
                default:
                    cleanSig.append(firstChar);
            }
        }

        cleanSig.append(")");


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

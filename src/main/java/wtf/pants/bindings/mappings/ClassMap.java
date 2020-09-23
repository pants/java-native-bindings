package wtf.pants.bindings.mappings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClassMap {

    private final String obfuscatedName;
    private final String intermediateName;
    private final String cleanName;

    private final List<MemberMap> members = new ArrayList<>();

    public ClassMap(String obfuscatedName, String intermediateName, String cleanName) {
        this.obfuscatedName = obfuscatedName;
        this.intermediateName = intermediateName;
        this.cleanName = cleanName;
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

    public String getCleanClassName() {
        if(cleanName.contains("/") && cleanName.lastIndexOf("/") < cleanName.length()) {
            return cleanName.substring(cleanName.lastIndexOf("/") + 1);
        } else {
            return cleanName;
        }
    }

    public List<MemberMap> getFields() {
        return members.stream().filter(c -> !c.isMethod()).collect(Collectors.toList());
    }

    public List<MemberMap> getMethods() {
        return members.stream().filter(MemberMap::isMethod).collect(Collectors.toList());
    }

    public List<MemberMap> getMembers() {
        return members;
    }

    public static ClassMap parseClassMap(final String[] line) {
        final String obfuscatedName = line[1];
        final String intermediateName = line[2];
        final String cleanName = line[3];

        return new ClassMap(obfuscatedName, intermediateName, cleanName);
    }

    @Override
    public String toString() {
        return cleanName;
    }
}

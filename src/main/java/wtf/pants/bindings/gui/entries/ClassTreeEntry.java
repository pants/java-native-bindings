package wtf.pants.bindings.gui.entries;

import wtf.pants.bindings.mappings.ClassMap;

public class ClassTreeEntry implements TreeEntry {

    private final ClassMap classMap;

    public ClassTreeEntry(ClassMap classMap){
        this.classMap = classMap;
    }

    @Override
    public String toString() {
        final String cleanName = classMap.getCleanName();
        return cleanName.substring(cleanName.lastIndexOf("/") + 1);
    }

    public ClassMap getClassMap() {
        return classMap;
    }
}

package wtf.pants.bindings.mappings;

import java.util.*;

public class MapProvider {
    private final List<ClassMap> classMaps;

    public MapProvider(List<ClassMap> classMaps) {
        this.classMaps = classMaps;
    }

    public Optional<ClassMap> findClass(String cleanName) {
        return this.classMaps.stream().filter(c -> c.getCleanName().equals(cleanName)).findFirst();
    }

    public List<ClassMap> getMappings() {
        return classMaps;
    }
}

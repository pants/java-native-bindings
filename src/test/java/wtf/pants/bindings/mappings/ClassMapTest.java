package wtf.pants.bindings.mappings;

import static org.junit.Assert.*;

import org.junit.Test;

public class ClassMapTest {
    @Test
    public void testGetCleanClassName() {
        final String obfuscatedName = "abc";
        final String intermediateName = "some/intermediate/name";

        final String cleanNameWithPath = "some/path/to/Class01";
        final String cleanNameWithoutPath = "Class01";

        //Test to make sure we can get a clean class name from a path to the class file.
        {
            ClassMap classMapWithPath = new ClassMap(obfuscatedName, intermediateName, cleanNameWithPath);
            final String actualCleanClassNameFromPath = classMapWithPath.getCleanClassName();
            assertEquals(cleanNameWithoutPath, actualCleanClassNameFromPath);
        }

        //Test to make sure we can get a clean class name from a clean name
        {
            ClassMap classMapWithoutPath = new ClassMap(obfuscatedName, intermediateName, cleanNameWithoutPath);
            final String actualCleanClassName = classMapWithoutPath.getCleanClassName();
            assertEquals(cleanNameWithoutPath, actualCleanClassName);
        }
    }
}

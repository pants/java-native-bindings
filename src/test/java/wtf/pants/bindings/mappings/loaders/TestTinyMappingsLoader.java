package wtf.pants.bindings.mappings.loaders;

import static org.junit.Assert.*;

import org.junit.Test;
import wtf.pants.bindings.mappings.ClassMap;

import java.util.ArrayList;
import java.util.List;

public class TestTinyMappingsLoader {

    @Test
    public void testParseClassMap() {
        final StringBuilder stringBuilder = new StringBuilder();
        final String classLine = "c\tobfuscated\tintermediate\tclean";
        final String[] delimited = classLine.split("\t");
        final ClassMap classMap = TinyMappingsLoader.parseClassMap(delimited);

        {
            final String expectedObfuscatedName = "obfuscated";
            final String actualObfuscatedName = classMap.getObfuscatedName();
            assertEquals(expectedObfuscatedName, actualObfuscatedName);
        }

        {
            final String expectedIntermediateName = "intermediate";
            final String actualIntermediateName = classMap.getIntermediateName();
            assertEquals(expectedIntermediateName, actualIntermediateName);
        }

        {
            final String expectedCleanName = "clean";
            final String actualCleanName = classMap.getCleanName();
            assertEquals(expectedCleanName, actualCleanName);
        }
    }

    @Test
    public void testIterateLines() {
        final List<String> testLines = new ArrayList<>();
        testLines.add("c\tobfuscated\tintermediate\tclean");
        testLines.add("\tm\t(Lsignature;)V\tobfuscated\tintermediate\tclean");
        testLines.add("\t\tp\t(Lsignature;)V\tobfuscated\tintermediate\tclean");
        testLines.add("\tf\t(Lsignature;)V\tobfuscated\tintermediate\tclean");

        final TinyMappingsLoader tinyMappingsLoader = new TinyMappingsLoader();
        final List<ClassMap> classes = tinyMappingsLoader.iterateLines(testLines);

        final int expectedClassCount = 1;
        final int actualClassCount = classes.size();
        assertEquals("Invalid class count", expectedClassCount, actualClassCount);

        final ClassMap firstEntry = classes.get(0);

        final int expectedMemberCount = 2;
        final int actualMemberCount = firstEntry.getMembers().size();
        assertEquals("Invalid member count", expectedMemberCount, actualMemberCount);
    }

}

package wtf.pants.bindings.generators.v1;

import org.junit.Test;
import wtf.pants.bindings.mappings.ClassMap;
import wtf.pants.bindings.mappings.MemberMap;

import static org.junit.Assert.*;

public class ClassIndexTest {

    @Test
    public void testCreateMethodIdLine() {
        final ClassMap testClassMap = new ClassMap("abc", "Class01", "some/class/path/TestClass");

        final String obfuscatedMethodName = "a";
        final String obfuscatedMethodSig = "(Ljava/lang/String;)V";
        final MemberMap testMemberMap = new MemberMap(true, obfuscatedMethodSig, obfuscatedMethodName, "method_001", "getInstance");
        testMemberMap.setStatic(true);

        final ClassIndex classIndex = new ClassIndex();

        final String actualLine = classIndex.createMethodIdLine(testClassMap, testMemberMap);
        final String expectedLine = "jmethodID m_TestClass_getInstance = staticMethodId(c_TestClass, \"a\", \"(Ljava/lang/String;)V\");";

        assertEquals(expectedLine, actualLine);
    }
}

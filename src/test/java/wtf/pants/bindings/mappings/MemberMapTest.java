package wtf.pants.bindings.mappings;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class MemberMapTest {

    @Test
    public void testGetCleanSignature() {
        final List<ClassMap> classes = new ArrayList<>();
        final ClassMap testClassMap = new ClassMap("abc", "some/class/path/Class_01", "some/class/path/TestClass");
        classes.add(testClassMap);


        //Field Signature
        {
            final MemberMap testField = new MemberMap(false,
                    "Labc;", "a",
                    "field_01", "someField"
            );

            final String expectedCleanSignature = "Lsome/class/path/TestClass;";
            final String actualCleanSignature = testField.getCleanSignature(classes);
            assertEquals(expectedCleanSignature, actualCleanSignature);
        }

        //Method Signature
        {
            final MemberMap testMethod = new MemberMap(false,
                    "(Labc;IIF)Labc;", "a",
                    "method_01", "testMethod"
            );

            final String expectedMethodSignature = "(Lsome/class/path/TestClass;IIF)Lsome/class/path/TestClass;";
            final String actualMethodSignature = testMethod.getCleanSignature(classes);
            assertEquals(expectedMethodSignature, actualMethodSignature);
        }
    }

}

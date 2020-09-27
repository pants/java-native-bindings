package wtf.pants.bindings.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilsTest {

    @Test
    public void testCamelToSnake() {
        final String expectedCase = "upper_camel_case";

        //assert the conversion works
        final String expectedCamelCase = "camel_case";
        final String actualCamelCase = StringUtils.camelToSnake("camelCase");
        assertEquals(expectedCamelCase, actualCamelCase);

        //asset that it also works with UpperCamelCsse
        final String actualUpperCamelCase = StringUtils.camelToSnake("UpperCamelCase");
        assertEquals(expectedCase, actualUpperCamelCase);
    }

    @Test
    public void testGetJNIReturnFromSignature() {
        final String testObjectSignature = "Ljava/lang/String;";
        final String testFloatSignature = "F";
        final String testBooleanArraySignature = "[Z";

        final String expectedObjectReturnType = "jobject";
        final String actualObjectReturnType = StringUtils.getJNITypeFromBytecode(testObjectSignature);
        assertEquals(expectedObjectReturnType, actualObjectReturnType);

        final String expectedFloatReturnType = "jfloat";
        final String actualFloatReturnType = StringUtils.getJNITypeFromBytecode(testFloatSignature);
        assertEquals(expectedFloatReturnType, actualFloatReturnType);

        final String expectedBoolArrayType = "jbooleanArray";
        final String actualBoolArrayType = StringUtils.getJNITypeFromBytecode(testBooleanArraySignature);
        assertEquals(expectedBoolArrayType, actualBoolArrayType);
    }

}

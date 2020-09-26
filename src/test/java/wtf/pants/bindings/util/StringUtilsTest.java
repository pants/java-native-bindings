package wtf.pants.bindings.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilsTest {

    @Test
    public void testCamelToSnake() {
        final String expectedCase = "upper_camel_case";

        //assert the conversion works
        final String actualCamelCase = StringUtils.camelToSnake("camelCase");
        assertEquals(expectedCase, actualCamelCase);

        //asset that it also works with UpperCamelCsse
        final String actualUpperCamelCase = StringUtils.camelToSnake("UpperCamelCase");
        assertEquals(expectedCase, actualUpperCamelCase);
    }

    @Test
    public void testGetJNIReturnFromSignature() {
        final String testObjectSignature = "Ljava/lang/String;";
        final String testFloatSignature = "F";
        final String testBooleanArraySignature = "[Z";
        final String testMethodSignature = "(FFI)[I";

        final String expectedObjectReturnType = "jobject";
        final String actualObjectReturnType = StringUtils.getJNIReturnFromSignature(testObjectSignature);
        assertEquals(expectedObjectReturnType, actualObjectReturnType);

        final String expectedFloatReturnType = "jfloat";
        final String actualFloatReturnType = StringUtils.getJNIReturnFromSignature(testFloatSignature);
        assertEquals(expectedFloatReturnType, actualFloatReturnType);

        final String expectedBoolArrayType = "jbooleanArray";
        final String actualBoolArrayType = StringUtils.getJNIReturnFromSignature(testBooleanArraySignature);
        assertEquals(expectedBoolArrayType, actualBoolArrayType);

        final String expectedMethodRetType = "jintArray";
        final String actualMethodRetType = StringUtils.getJNIReturnFromSignature(testMethodSignature);
        assertEquals(expectedMethodRetType, actualMethodRetType);
    }

}

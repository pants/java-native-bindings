package wtf.pants.bindings.util;

import org.junit.Test;
import wtf.pants.bindings.generators.v1.ClassHeader;
import wtf.pants.bindings.mappings.ClassMap;

import static org.junit.Assert.*;

public class StringUtilsTest {

    @Test
    public void testCamelToSnake(){
        final String expectedCase = "upper_camel_case";

        //assert the conversion works
        final String actualCamelCase = StringUtils.camelToSnake("camelCase");
        assertEquals(expectedCase, actualCamelCase);

        //asset that it also works with UpperCamelCsse
        final String actualUpperCamelCase = StringUtils.camelToSnake("UpperCamelCase");
        assertEquals(expectedCase, actualUpperCamelCase);

    }

}
